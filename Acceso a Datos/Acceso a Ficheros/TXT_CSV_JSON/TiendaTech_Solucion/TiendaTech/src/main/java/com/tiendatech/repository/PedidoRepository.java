package com.tiendatech.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiendatech.exception.DatosInvalidosException;
import com.tiendatech.model.Cliente;
import com.tiendatech.model.EstadoPedido;
import com.tiendatech.model.LineaPedido;
import com.tiendatech.model.Pedido;
import com.tiendatech.model.Producto;
import com.tiendatech.util.BackupUtil;
import com.tiendatech.util.FechaUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repositorio de pedidos. "Base de datos": TXT con formato propio
 *   idPedido;fecha;idCliente;P001x2,P003x1;ESTADO
 *
 * Necesita los repositorios de clientes y productos para convertir los ids
 * en referencias a objetos. Por eso hay que cargar productos y clientes ANTES.
 */
public class PedidoRepository {

    private static final Logger logger = LoggerFactory.getLogger(PedidoRepository.class);

    private static final String CABECERA =
            "# Formato: idPedido;fecha;idCliente;lineas;estado" + System.lineSeparator()
          + "# lineas = idProductoxCantidad separados por comas";

    private final Path ruta;
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    private final Map<String, Pedido> pedidos = new LinkedHashMap<>();

    /*
     * Mayor número de pedido visto en el fichero, INCLUIDOS los descartados.
     * Si solo miráramos los válidos, el siguiente id sería PED-010, que ya existe
     * en el fichero (aunque sea erróneo) y tendríamos ids repetidos.
     */
    private int maxNumeroLeido = 0;

    public PedidoRepository(Path ruta,
                            ClienteRepository clienteRepo, ProductoRepository productoRepo) {
        this.ruta = ruta;
        this.clienteRepo = clienteRepo;
        this.productoRepo = productoRepo;
    }

    // ===================== LECTURA =====================

    public void cargar() throws IOException {
        pedidos.clear();
        maxNumeroLeido = 0;
        List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);

        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i).trim();
            if (linea.isEmpty() || linea.startsWith("#")) continue; // vacías y comentarios fuera

            String id = linea.split(";", -1)[0].trim();
            // Si el primer campo no tiene forma de id (PED-xxx), identificamos el error por la línea
            String quien = id.matches("PED-\\d+") ? id + " (línea " + (i + 1) + ")" : "de la línea " + (i + 1);
            maxNumeroLeido = Math.max(maxNumeroLeido, numeroDeId(id));
            try {
                Pedido p = parsear(linea);
                if (pedidos.containsKey(p.getId())) {
                    logger.error("Pedido {} descartado: id duplicado", quien);
                    continue;
                }
                pedidos.put(p.getId(), p);
            } catch (DatosInvalidosException e) {
                logger.error("Pedido {} descartado: {}", quien, e.getMessage());
            } catch (RuntimeException e) {
                logger.error("Pedido {} descartado: línea con formato inesperado", quien, e);
            }
        }
        logger.info("Cargados {} pedidos de {}", pedidos.size(), ruta.getFileName());
    }

    private Pedido parsear(String linea) throws DatosInvalidosException {
        String[] c = linea.split(";", -1);
        if (c.length != 5) {
            throw new DatosInvalidosException("formato incorrecto (" + c.length + " campos en vez de 5)");
        }

        String id = c[0].trim();
        if (!id.matches("PED-\\d+")) throw new DatosInvalidosException("id de pedido no válido (" + id + ")");

        LocalDate fecha;
        try {
            fecha = FechaUtil.parsear(c[1]);
        } catch (DateTimeParseException e) {
            throw new DatosInvalidosException("fecha no válida (" + c[1].trim() + ")");
        }

        String idCliente = c[2].trim();
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new DatosInvalidosException("el cliente " + idCliente + " no existe"));

        List<LineaPedido> lineas = parsearLineas(c[3]);

        EstadoPedido estado;
        try {
            estado = EstadoPedido.valueOf(c[4].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DatosInvalidosException("estado no válido (" + c[4].trim() + ")");
        }

        return new Pedido(id, fecha, cliente, lineas, estado);
    }

    /** "P001x2,P003x1" -> lista de LineaPedido */
    private List<LineaPedido> parsearLineas(String texto) throws DatosInvalidosException {
        if (texto.isBlank()) {
            throw new DatosInvalidosException("el pedido no tiene productos");
        }
        List<LineaPedido> lineas = new ArrayList<>();
        for (String parte : texto.split(",")) {
            parte = parte.trim();
            int x = parte.lastIndexOf('x');
            if (x <= 0) {
                throw new DatosInvalidosException("línea de producto mal formada (" + parte + ")");
            }
            String idProducto = parte.substring(0, x);

            int cantidad;
            try {
                cantidad = Integer.parseInt(parte.substring(x + 1));
            } catch (NumberFormatException e) {
                throw new DatosInvalidosException("cantidad no numérica en " + parte);
            }
            if (cantidad <= 0) {
                throw new DatosInvalidosException("cantidad no válida en " + parte);
            }

            Producto producto = productoRepo.findById(idProducto)
                    .orElseThrow(() -> new DatosInvalidosException("el producto " + idProducto + " no existe"));

            // Histórico: se usa el precio actual; NO se toca el stock
            lineas.add(new LineaPedido(producto, cantidad, producto.getPrecio()));
        }
        return lineas;
    }

    // ===================== ESCRITURA =====================

    /** Añade el pedido en memoria y al FINAL del fichero (append). */
    public void anadir(Pedido p) throws IOException {
        pedidos.put(p.getId(), p);
        maxNumeroLeido = Math.max(maxNumeroLeido, numeroDeId(p.getId()));

        String texto = aLinea(p) + System.lineSeparator();
        // Si el fichero no acaba en salto de línea, lo añadimos para no pegar dos pedidos
        if (Files.exists(ruta) && Files.size(ruta) > 0) {
            String actual = Files.readString(ruta, StandardCharsets.UTF_8);
            if (!actual.endsWith("\n")) {
                texto = System.lineSeparator() + texto;
            }
        }
        Files.writeString(ruta, texto, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * Reescribe el fichero completo (necesario al cambiar un estado, porque en un
     * TXT no se puede modificar "una línea" sin reescribirlo todo).
     * Las líneas erróneas del fichero original se pierden, pero quedan en el backup.
     */
    public void guardarTodo() throws IOException {
        BackupUtil.hacerCopia(ruta);
        List<String> lineas = new ArrayList<>();
        lineas.add(CABECERA);
        lineas.add("");
        pedidos.values().forEach(p -> lineas.add(aLinea(p)));
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }

    private String aLinea(Pedido p) {
        String productos = p.getLineas().stream()
                .map(l -> l.getProducto().getId() + "x" + l.getCantidad())
                .collect(Collectors.joining(","));
        return String.join(";", p.getId(), FechaUtil.formatear(p.getFecha()),
                p.getCliente().getId(), productos, p.getEstado().name());
    }

    // ===================== CRUD EN MEMORIA =====================

    public List<Pedido> findAll() {
        return new ArrayList<>(pedidos.values());
    }

    public Optional<Pedido> findById(String id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public String siguienteId() {
        return String.format("PED-%03d", maxNumeroLeido + 1);
    }

    /** "PED-012" -> 12. Cualquier cosa que no tenga forma de id cuenta como 0. */
    private static int numeroDeId(String id) {
        if (id == null || !id.matches("PED-\\d+")) {
            return 0;
        }
        return Integer.parseInt(id.substring(4));
    }
}
