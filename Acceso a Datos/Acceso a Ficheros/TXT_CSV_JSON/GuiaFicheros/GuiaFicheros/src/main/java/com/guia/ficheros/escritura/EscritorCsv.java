package com.guia.ficheros.escritura;

import com.guia.ficheros.model.Pais;
import com.guia.ficheros.util.CsvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * CASOS CONTROLADOS AL ESCRIBIR:
 *   la carpeta no existe · lista null · países null dentro de la lista ·
 *   campos null (moneda) · lista de idiomas null · valores con coma ("Bosnia, Herzegovina") ·
 *   valores con comillas · tildes (UTF-8) · fallo a mitad de escritura (fichero temporal)
 */
public class EscritorCsv {

    private static final Logger logger = LoggerFactory.getLogger(EscritorCsv.class);
    private static final char SEP = ',';

    public boolean escribirPaises(List<Pais> paises, File fichero) {
        if (paises == null) {
            logger.error("No se puede escribir {}: la lista es null", fichero.getName());
            return false;
        }

        Path destino = fichero.toPath();
        // ESCRITURA SEGURA: se escribe en un .tmp y al final se renombra.
        // Si el programa falla a mitad, el fichero original sigue intacto.
        Path temporal = destino.resolveSibling(destino.getFileName() + ".tmp");

        try {
            // CASO: la carpeta no existe -> se crea (si no, NoSuchFileException)
            if (destino.getParent() != null) {
                Files.createDirectories(destino.getParent());
            }

            int escritos = 0;
            try (BufferedWriter bw = Files.newBufferedWriter(temporal, StandardCharsets.UTF_8)) {
                bw.write("Nombre,Continente,Población,Idioma,Moneda");
                bw.newLine();

                for (Pais p : paises) {
                    if (p == null) {
                        logger.warn("País null en la lista: se ignora");
                        continue;
                    }
                    String idiomas = p.getIdiomas() == null ? "" : String.join(" ", p.getIdiomas());
                    bw.write(String.join(String.valueOf(SEP),
                            CsvUtil.escapar(p.getNombre(), SEP),       // null -> "" ; "a, b" -> "\"a, b\""
                            CsvUtil.escapar(p.getContinente(), SEP),
                            String.valueOf(p.getPoblacion()),          // sin String.format: nunca sale "1.000"
                            CsvUtil.escapar(idiomas, SEP),
                            CsvUtil.escapar(p.getMoneda(), SEP)));
                    bw.newLine();
                    escritos++;
                }
            } // <- aquí se cierra el writer y se vuelca todo al disco

            try {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING);
            }
            logger.info("{}: {} países escritos", fichero.getName(), escritos);
            return true;

        } catch (IOException e) {
            logger.error("No se pudo escribir {}", fichero.getAbsolutePath(), e);
            try {
                Files.deleteIfExists(temporal);
            } catch (IOException ignorada) {
                // no hay nada más que hacer
            }
            return false;
        }
    }
}
