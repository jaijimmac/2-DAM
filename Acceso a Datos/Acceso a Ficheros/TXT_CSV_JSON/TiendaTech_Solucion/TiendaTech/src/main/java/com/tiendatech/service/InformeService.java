package com.tiendatech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.tiendatech.exception.EntidadNoEncontradaException;
import com.tiendatech.exception.OperacionNoPermitidaException;
import com.tiendatech.model.Cliente;
import com.tiendatech.model.EstadoPedido;
import com.tiendatech.model.LineaPedido;
import com.tiendatech.model.Pedido;
import com.tiendatech.model.Producto;
import com.tiendatech.repository.ClienteRepository;
import com.tiendatech.repository.ProductoRepository;
import com.tiendatech.util.FechaUtil;
import com.tiendatech.util.Rutas;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/** Genera los ficheros de salida: facturas (TXT) e informes (CSV, JSON, TXT). */
public class InformeService {

    private static final Logger logger = LoggerFactory.getLogger(InformeService.class);

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    private static final String DOBLE = "=".repeat(43);
    private static final String SIMPLE = "-".repeat(43);

    private final PedidoService pedidoService;
    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;
    private final Path carpeta;

    public InformeService(PedidoService pedidoService, ProductoRepository productoRepo,
                          ClienteRepository clienteRepo, Path carpeta) {
        this.pedidoService = pedidoService;
        this.productoRepo = productoRepo;
        this.clienteRepo = clienteRepo;
        this.carpeta = carpeta;
    }

    // ===================== FACTURA (TXT) =====================

    public Path generarFactura(String idPedido)
            throws EntidadNoEncontradaException, OperacionNoPermitidaException, IOException {
        Pedido p = pedidoService.buscarPorId(idPedido);
        if (p.getEstado() == EstadoPedido.CANCELADO) {
            String msg = "No se puede generar la factura de " + idPedido + ": el pedido está CANCELADO";
            logger.error(msg);
            throw new OperacionNoPermitidaException(msg);
        }

        PedidoService.Totales t = pedidoService.calcularTotales(p);
        Cliente c = p.getCliente();
        int pctVip = (int) Math.round(pedidoService.getConfig().getDescuentoVip() * 100);
        int pctIva = (int) Math.round(pedidoService.getConfig().getIva() * 100);

        Path destino = Rutas.FACTURAS.resolve("factura_" + p.getId() + ".txt");
        Files.createDirectories(destino.getParent());

        // try-with-resources: el writer se cierra solo aunque haya una excepción
        try (BufferedWriter w = Files.newBufferedWriter(destino, StandardCharsets.UTF_8)) {
            linea(w, DOBLE);
            linea(w, "                TIENDATECH");
            linea(w, DOBLE);
            linea(w, "Factura:   " + p.getId());
            linea(w, "Fecha:     " + FechaUtil.formatear(p.getFecha()));
            linea(w, "Cliente:   " + c.getNombreCompleto() + " (" + c.getId() + ")");
            linea(w, "Dirección: " + (c.getDireccion() != null ? c.getDireccion() : "no disponible"));
            linea(w, SIMPLE);
            linea(w, String.format("%-22s%5s%8s%8s", "Producto", "Cant.", "P.Unit", "Total"));
            for (LineaPedido l : p.getLineas()) {
                linea(w, String.format(Locale.US, "%-22s%5d%8.2f%8.2f",
                        recortar(l.getProducto().getNombre(), 21), l.getCantidad(),
                        l.getPrecioUnitario(), l.getImporte()));
            }
            linea(w, SIMPLE);
            linea(w, importe("Subtotal:", t.subtotal()));
            linea(w, importe("Descuento VIP (" + pctVip + "%):", t.descuento()));
            linea(w, importe("Base imponible:", t.base()));
            linea(w, importe("IVA (" + pctIva + "%):", t.iva()));
            linea(w, importe("TOTAL:", t.total()));
            linea(w, DOBLE);
        }
        logger.info("Factura generada: " + destino.getFileName());
        return destino;
    }

    private static void linea(BufferedWriter w, String texto) throws IOException {
        w.write(texto);
        w.newLine();
    }

    private static String importe(String etiqueta, double valor) {
        return String.format(Locale.US, "%-30s%13.2f", etiqueta, valor);
    }

    /**
     * "Taiwán" -> "Taiwan". Quita tildes y espacios de los nombres de fichero:
     * en algunos sistemas los nombres con tildes dan InvalidPathException.
     */
    private static String nombreSeguro(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")      // quita las marcas (tildes)
                .replaceAll("[^A-Za-z0-9_-]", "_");
    }

    private static String recortar(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max);
    }

    // ===================== VENTAS POR CATEGORÍA (CSV) =====================

    public Path ventasPorCategoria() throws IOException {
        // categoría -> [unidades, importe]
        Map<String, double[]> acumulado = new LinkedHashMap<>();
        for (Pedido p : pedidoService.listarVentas()) {
            for (LineaPedido l : p.getLineas()) {
                double[] a = acumulado.computeIfAbsent(l.getProducto().getCategoria(), k -> new double[2]);
                a[0] += l.getCantidad();
                a[1] += l.getImporte();
            }
        }

        List<String> lineas = new ArrayList<>();
        lineas.add("categoria,unidades_vendidas,importe_total");
        acumulado.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue()[1], a.getValue()[1])) // de mayor a menor
                .forEach(e -> lineas.add(String.format(Locale.US, "%s,%d,%.2f",
                        e.getKey(), (int) e.getValue()[0], e.getValue()[1])));

        Path destino = carpeta.resolve("ventas_por_categoria.csv");
        Files.write(destino, lineas, StandardCharsets.UTF_8);
        logger.info("Informe generado: " + destino.getFileName());
        return destino;
    }

    // ===================== INFORME DE CLIENTES (JSON) =====================

    public Path informeClientes() throws IOException {
        JsonArray array = new JsonArray();
        List<Pedido> ventas = pedidoService.listarVentas();

        for (Cliente c : clienteRepo.findAll()) {
            List<Pedido> suyos = ventas.stream()
                    .filter(p -> p.getCliente().getId().equals(c.getId()))
                    .collect(Collectors.toList());

            double totalGastado = suyos.stream()
                    .mapToDouble(p -> pedidoService.calcularTotales(p).total())
                    .sum();

            // producto -> unidades compradas
            Map<Producto, Integer> unidades = new LinkedHashMap<>();
            for (Pedido p : suyos) {
                for (LineaPedido l : p.getLineas()) {
                    unidades.merge(l.getProducto(), l.getCantidad(), Integer::sum);
                }
            }
            String favorito = unidades.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(e -> e.getKey().getNombre())
                    .orElse(null);

            JsonObject o = new JsonObject();
            o.addProperty("id", c.getId());
            o.addProperty("nombre_completo", c.getNombreCompleto());
            o.addProperty("vip", c.isVip());
            o.addProperty("numero_pedidos", suyos.size());
            o.addProperty("total_gastado", PedidoService.redondear(totalGastado));
            if (favorito == null) {
                o.add("producto_favorito", JsonNull.INSTANCE);
            } else {
                o.addProperty("producto_favorito", favorito);
            }
            array.add(o);
        }

        Path destino = carpeta.resolve("informe_clientes.json");
        Files.writeString(destino, GSON.toJson(array), StandardCharsets.UTF_8);
        logger.info("Informe generado: " + destino.getFileName());
        return destino;
    }

    // ===================== STOCK BAJO (TXT) =====================

    public Path stockBajo(int umbral) throws IOException {
        List<Producto> bajos = productoRepo.findAll().stream()
                .filter(p -> p.getStock() < umbral)
                .sorted(Comparator.comparingInt(Producto::getStock))
                .collect(Collectors.toList());

        List<String> lineas = new ArrayList<>();
        lineas.add("PRODUCTOS CON STOCK INFERIOR A " + umbral + " UNIDADES");
        lineas.add("Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lineas.add(SIMPLE);
        if (bajos.isEmpty()) {
            lineas.add("Ningún producto por debajo del umbral.");
        }
        for (Producto p : bajos) {
            lineas.add(String.format("%-5s %-24s %4d uds.%s", p.getId(), p.getNombre(), p.getStock(),
                    p.getStock() == 0 ? "  <-- AGOTADO" : ""));
        }

        Path destino = carpeta.resolve("stock_bajo.txt");
        Files.write(destino, lineas, StandardCharsets.UTF_8);
        logger.info("Informe generado: " + destino.getFileName());
        return destino;
    }

    // ===================== TOP N (consola) =====================

    /** Devuelve producto -> unidades vendidas, ordenado de más a menos. */
    public List<Map.Entry<Producto, Integer>> topVendidos(int n) {
        Map<Producto, Integer> unidades = new LinkedHashMap<>();
        for (Pedido p : pedidoService.listarVentas()) {
            for (LineaPedido l : p.getLineas()) {
                unidades.merge(l.getProducto(), l.getCantidad(), Integer::sum);
            }
        }
        return unidades.entrySet().stream()
                .sorted(Map.Entry.<Producto, Integer>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    // ===================== PEDIDOS POR CIUDAD (CSV) =====================

    public Path pedidosPorCiudad(String ciudad) throws IOException {
        List<String> lineas = new ArrayList<>();
        lineas.add("id_pedido,fecha,cliente,estado,total");
        for (Pedido p : pedidoService.listar()) {
            Cliente c = p.getCliente();
            if (c.getDireccion() != null && c.getDireccion().getCiudad().equalsIgnoreCase(ciudad.trim())) {
                lineas.add(String.format(Locale.US, "%s,%s,%s,%s,%.2f",
                        p.getId(), FechaUtil.formatear(p.getFecha()), c.getNombreCompleto(),
                        p.getEstado(), pedidoService.calcularTotales(p).total()));
            }
        }
        Path destino = carpeta.resolve("pedidos_" + nombreSeguro(ciudad) + ".csv");
        Files.write(destino, lineas, StandardCharsets.UTF_8);
        logger.info("Informe generado: " + destino.getFileName() + " (" + (lineas.size() - 1) + " pedidos)");
        return destino;
    }

    // ===================== V2: PRODUCTOS POR PAÍS (JSON) =====================

    public Path productosPorPais(String pais) throws IOException {
        JsonArray array = new JsonArray();
        for (Producto p : productoRepo.findAll()) {
            if (p.getProveedor() == null || !p.getProveedor().getPais().equalsIgnoreCase(pais.trim())) {
                continue;
            }
            JsonObject o = new JsonObject();
            o.addProperty("id", p.getId());
            o.addProperty("nombre", p.getNombre());
            o.addProperty("categoria", p.getCategoria());
            o.addProperty("precio", p.getPrecio());
            o.addProperty("stock", p.getStock());
            JsonArray etiquetas = new JsonArray();
            p.getEtiquetas().forEach(etiquetas::add);
            o.add("etiquetas", etiquetas);

            JsonObject prov = new JsonObject();
            prov.addProperty("nombre", p.getProveedor().getNombre());
            prov.addProperty("pais", p.getProveedor().getPais());
            prov.addProperty("email", p.getProveedor().getEmail());
            o.add("proveedor", prov);

            array.add(o);
        }
        Path destino = carpeta.resolve("productos_" + nombreSeguro(pais) + ".json");
        Files.writeString(destino, GSON.toJson(array), StandardCharsets.UTF_8);
        logger.info("Informe generado: " + destino.getFileName() + " (" + array.size() + " productos)");
        return destino;
    }
}
