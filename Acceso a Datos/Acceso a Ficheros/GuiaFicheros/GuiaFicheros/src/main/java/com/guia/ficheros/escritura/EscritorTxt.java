package com.guia.ficheros.escritura;

import com.guia.ficheros.model.Pais;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Formato clave=valor con bloques separados por "---".
 *
 * CASOS CONTROLADOS AL ESCRIBIR:
 *   SOBRESCRIBIR (escribirPaises) vs AÑADIR AL FINAL (anadirPais, modo APPEND) ·
 *   el fichero no acaba en salto de línea antes de añadir · valores null ·
 *   valores con saltos de línea (romperían el formato) · carpeta que no existe
 */
public class EscritorTxt {

    private static final Logger logger = LoggerFactory.getLogger(EscritorTxt.class);

    /** Sobrescribe el fichero entero (CREATE + TRUNCATE_EXISTING por defecto). */
    public boolean escribirPaises(List<Pais> paises, File fichero) {
        if (paises == null) {
            logger.error("No se puede escribir {}: la lista es null", fichero.getName());
            return false;
        }
        try {
            crearCarpeta(fichero);
            try (BufferedWriter bw = Files.newBufferedWriter(fichero.toPath(), StandardCharsets.UTF_8)) {
                bw.write("# Países en formato clave=valor. Cada país termina con una línea ---");
                bw.newLine();
                for (Pais p : paises) {
                    if (p == null) continue;
                    escribirBloque(bw, p);
                }
            }
            logger.info("{}: {} países escritos", fichero.getName(), paises.size());
            return true;
        } catch (IOException e) {
            logger.error("No se pudo escribir {}", fichero.getAbsolutePath(), e);
            return false;
        }
    }

    /** Añade un país al FINAL sin borrar lo que ya hay (APPEND). */
    public boolean anadirPais(Pais p, File fichero) {
        if (p == null) {
            logger.error("No se puede añadir un país null");
            return false;
        }
        try {
            crearCarpeta(fichero);
            Path ruta = fichero.toPath();

            // CASO: si el fichero no acaba en salto de línea, lo nuevo se pegaría a la última línea
            boolean necesitaSalto = Files.exists(ruta) && Files.size(ruta) > 0
                    && !Files.readString(ruta, StandardCharsets.UTF_8).endsWith("\n");

            try (BufferedWriter bw = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                if (necesitaSalto) bw.newLine();
                escribirBloque(bw, p);
            }
            logger.info("{}: país {} añadido al final", fichero.getName(), p.getNombre());
            return true;
        } catch (IOException e) {
            logger.error("No se pudo añadir a {}", fichero.getAbsolutePath(), e);
            return false;
        }
    }

    private void escribirBloque(BufferedWriter bw, Pais p) throws IOException {
        linea(bw, "nombre", p.getNombre());
        linea(bw, "continente", p.getContinente());
        linea(bw, "poblacion", String.valueOf(p.getPoblacion()));
        linea(bw, "idiomas", p.getIdiomas() == null ? null : String.join(" ", p.getIdiomas()));
        linea(bw, "moneda", p.getMoneda());
        bw.write("---");
        bw.newLine();
    }

    private void linea(BufferedWriter bw, String clave, String valor) throws IOException {
        // null -> "clave="  ;  un salto de línea dentro del valor rompería el formato -> espacio
        String v = valor == null ? "" : valor.replace("\r", " ").replace("\n", " ");
        bw.write(clave + "=" + v);
        bw.newLine();
    }

    private void crearCarpeta(File fichero) throws IOException {
        if (fichero.getParentFile() != null) {
            Files.createDirectories(fichero.getParentFile().toPath());
        }
    }
}
