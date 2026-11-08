package com.guia.ficheros.escritura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guia.ficheros.model.Pais;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * CASOS CONTROLADOS AL ESCRIBIR:
 *   todo en una línea (setPrettyPrinting) · campos null que desaparecen (serializeNulls) ·
 *   caracteres como = o ' escritos como = (disableHtmlEscaping) ·
 *   países null en la lista · carpeta que no existe · tildes (UTF-8)
 */
public class EscritorJson {

    private static final Logger logger = LoggerFactory.getLogger(EscritorJson.class);

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()        // con saltos de línea y sangría
            .serializeNulls()           // "moneda": null en vez de quitar el campo
            .disableHtmlEscaping()      // "Peso=MXN" en vez de "Peso=MXN"
            .create();

    public boolean escribirPaises(List<Pais> paises, File fichero) {
        if (paises == null) {
            logger.error("No se puede escribir {}: la lista es null", fichero.getName());
            return false;
        }
        List<Pais> sinNulls = paises.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (sinNulls.size() != paises.size()) {
            logger.warn("Se han ignorado {} países null", paises.size() - sinNulls.size());
        }

        try {
            if (fichero.getParentFile() != null) {
                Files.createDirectories(fichero.getParentFile().toPath());
            }
            // Gson escribe directamente en el Writer: no hace falta montar el String entero
            try (Writer w = Files.newBufferedWriter(fichero.toPath(), StandardCharsets.UTF_8)) {
                GSON.toJson(sinNulls, w);
            }
            logger.info("{}: {} países escritos", fichero.getName(), sinNulls.size());
            return true;
        } catch (IOException e) {
            logger.error("No se pudo escribir {}", fichero.getAbsolutePath(), e);
            return false;
        }
    }
}
