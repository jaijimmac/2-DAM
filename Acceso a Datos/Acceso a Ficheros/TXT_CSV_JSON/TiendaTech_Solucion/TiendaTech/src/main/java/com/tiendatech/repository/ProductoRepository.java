package com.tiendatech.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiendatech.exception.DatosInvalidosException;
import com.tiendatech.model.Producto;
import com.tiendatech.model.Proveedor;
import com.tiendatech.util.BackupUtil;
import com.tiendatech.util.Validador;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/** Repositorio de productos. "Base de datos": fichero CSV (formato v1 o v2). */
public class ProductoRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductoRepository.class);


    public static final String CABECERA_V1 = "id,nombre,categoria,precio,stock,etiquetas";
    public static final String CABECERA_V2 = CABECERA_V1 + ",proveedor_nombre,proveedor_pais,proveedor_email";

    private final Path ruta;

    // LinkedHashMap: búsqueda rápida por id y mantiene el orden del fichero
    private final Map<String, Producto> productos = new LinkedHashMap<>();
    private boolean formatoV2;

    public ProductoRepository(Path ruta) {
        this.ruta = ruta;
    }

    // ===================== LECTURA =====================

    public void cargar() throws IOException {
        productos.clear();
        List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
        if (lineas.isEmpty()) {
            return;
        }

        // La versión del fichero se detecta por la cabecera
        String cabecera = lineas.get(0).trim();
        formatoV2 = cabecera.equals(CABECERA_V2);
        if (!formatoV2 && !cabecera.equals(CABECERA_V1)) {
            logger.error("Cabecera de {} no reconocida: '{}'. Se intenta leer como formato v1",
                    ruta.getFileName(), cabecera);
        }

        for (int i = 1; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            if (linea.isBlank()) continue;

            String id = linea.split(",", -1)[0].trim();
            String quien = id.isEmpty() ? "de la línea " + (i + 1) : id + " (línea " + (i + 1) + ")";
            try {
                Producto p = parsear(linea);
                if (productos.containsKey(p.getId())) {
                    logger.error("Producto {} descartado: id duplicado", quien);
                    continue; // se conserva el primero
                }
                productos.put(p.getId(), p);
            } catch (DatosInvalidosException e) {
                // Una línea mala NO para la carga del resto
                logger.error("Producto {} descartado: {}", quien, e.getMessage());
            } catch (RuntimeException e) {
                // Red de seguridad ante cualquier línea con una forma inesperada
                logger.error("Producto {} descartado: línea con formato inesperado", quien, e);
            }
        }
        logger.info("Cargados {} productos de {} (formato {})",
                productos.size(), ruta.getFileName(), formatoV2 ? "v2" : "v1");
    }

    private Producto parsear(String linea) throws DatosInvalidosException {
        // -1 para que NO se pierdan los campos vacíos del final (etiquetas vacías)
        String[] c = linea.split(",", -1);
        int columnas = formatoV2 ? 9 : 6;
        if (c.length != columnas) {
            throw new DatosInvalidosException("número de columnas incorrecto (" + c.length
                    + " en vez de " + columnas + ")");
        }

        double precio;
        try {
            precio = Double.parseDouble(c[3].trim());
        } catch (NumberFormatException e) {
            throw new DatosInvalidosException("precio no numérico (" + c[3] + ")");
        }

        int stock;
        try {
            stock = Integer.parseInt(c[4].trim());
        } catch (NumberFormatException e) {
            throw new DatosInvalidosException("stock no numérico (" + c[4] + ")");
        }

        Producto p = new Producto(c[0].trim(), c[1].trim(), c[2].trim(), precio, stock,
                parsearEtiquetas(c[5]));

        if (formatoV2 && !c[6].isBlank()) {
            p.setProveedor(new Proveedor(c[6].trim(), c[7].trim(), c[8].trim()));
        }

        Validador.validarProducto(p);
        return p;
    }

    private List<String> parsearEtiquetas(String texto) {
        if (texto == null || texto.isBlank()) {
            return new ArrayList<>();
        }
        // | es un carácter especial en regex, hay que escaparlo
        return Arrays.stream(texto.split("\\|"))
                .map(String::trim)
                .filter(e -> !e.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // ===================== ESCRITURA =====================

    public void guardar() throws IOException {
        BackupUtil.hacerCopia(ruta);
        List<String> lineas = new ArrayList<>();
        lineas.add(formatoV2 ? CABECERA_V2 : CABECERA_V1);
        for (Producto p : productos.values()) {
            lineas.add(aLineaCsv(p, formatoV2));
        }
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }

    private String aLineaCsv(Producto p, boolean v2) {
        // Locale.US: con el Locale español String.format escribiría 79,90 y rompería el CSV
        String linea = String.join(",",
                p.getId(), p.getNombre(), p.getCategoria(),
                String.format(Locale.US, "%.2f", p.getPrecio()),
                String.valueOf(p.getStock()),
                String.join("|", p.getEtiquetas()));
        if (v2) {
            Proveedor pr = p.getProveedor();
            linea += pr == null ? ",,," : "," + pr.getNombre() + "," + pr.getPais() + "," + pr.getEmail();
        }
        return linea;
    }

    /**
     * Versión 2: convierte un CSV v1 en v2 añadiendo las columnas del proveedor vacías.
     * Copia TODAS las líneas tal cual (también las erróneas) para no perder datos.
     */
    public static int migrarAV2(Path origen, Path destino) throws IOException {
        List<String> lineas = Files.readAllLines(origen, StandardCharsets.UTF_8);
        List<String> salida = new ArrayList<>();
        if (lineas.isEmpty()) {
            return 0;
        }
        if (lineas.get(0).trim().equals(CABECERA_V2)) {
            salida.addAll(lineas); // ya estaba migrado
        } else {
            salida.add(CABECERA_V2);
            for (int i = 1; i < lineas.size(); i++) {
                if (!lineas.get(i).isBlank()) {
                    salida.add(lineas.get(i) + ",,,");
                }
            }
        }
        Files.write(destino, salida, StandardCharsets.UTF_8);
        return salida.size() - 1;
    }

    // ===================== CRUD EN MEMORIA =====================

    public List<Producto> findAll() {
        return new ArrayList<>(productos.values());
    }

    public Optional<Producto> findById(String id) {
        return Optional.ofNullable(productos.get(id));
    }

    public boolean existe(String id) {
        return productos.containsKey(id);
    }

    public void save(Producto p) {
        productos.put(p.getId(), p);
    }

    public boolean delete(String id) {
        return productos.remove(id) != null;
    }

    public boolean isFormatoV2() {
        return formatoV2;
    }
}
