package com.guia.ficheros.lectura;

import com.guia.ficheros.model.Pais;
import com.guia.ficheros.util.PaisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lector de TXT con formato "clave=valor" y bloques separados por "---".
 * Aquí se usa BufferedReader (la otra forma clásica de leer línea a línea).
 *
 * CASOS CONTROLADOS (ver resources/paises_errores.txt):
 *   falta el '=' · clave vacía (=Europa) · clave repetida · clave desconocida ·
 *   espacios alrededor del '=' · valor que contiene '=' (Peso=MXN) · valor vacío (moneda=) ·
 *   falta un campo obligatorio · varias líneas en blanco seguidas · comentarios (#) ·
 *   último bloque sin '---' al final del fichero
 */
public class LectorTxt {

    private static final Logger logger = LoggerFactory.getLogger(LectorTxt.class);

    private static final String SEPARADOR_BLOQUE = "---";
    private static final Set<String> CLAVES = Set.of("nombre", "continente", "poblacion", "idiomas", "moneda");

    private final File fichero;

    public LectorTxt(File fichero) {
        this.fichero = fichero;
    }

    public List<Pais> leerPaises() {
        List<Pais> listadoPaises = new ArrayList<>();

        if (!fichero.exists()) {
            logger.error("No existe el fichero {}", fichero.getAbsolutePath());
            return listadoPaises;
        }

        Set<String> nombresLeidos = new HashSet<>();

        try (BufferedReader br = Files.newBufferedReader(fichero.toPath(), StandardCharsets.UTF_8)) {

            Map<String, String> bloque = new LinkedHashMap<>();
            List<String> erroresBloque = new ArrayList<>();
            int lineaInicioBloque = 1;
            int numLinea = 0;
            String linea;

            // readLine() devuelve null al llegar al final del fichero
            while ((linea = br.readLine()) != null) {
                numLinea++;
                String t = PaisFactory.quitarBom(linea).trim();

                if (t.startsWith("#")) continue;

                if (t.equals(SEPARADOR_BLOQUE)) {
                    procesarBloque(bloque, erroresBloque, lineaInicioBloque, listadoPaises, nombresLeidos);
                    bloque = new LinkedHashMap<>();
                    erroresBloque = new ArrayList<>();
                    lineaInicioBloque = numLinea + 1;
                    continue;
                }
                if (t.isEmpty()) {
                    if (bloque.isEmpty()) lineaInicioBloque = numLinea + 1; // líneas en blanco antes del bloque
                    continue;
                }

                // CASO: falta el '='
                int igual = t.indexOf('=');
                if (igual < 0) {
                    erroresBloque.add("línea " + numLinea + " sin '=' (" + t + ")");
                    continue;
                }
                // split por el PRIMER '=': "moneda=Peso=MXN" -> clave "moneda", valor "Peso=MXN"
                String clave = PaisFactory.normalizar(t.substring(0, igual));
                String valor = t.substring(igual + 1).trim();

                if (clave.isEmpty()) {
                    erroresBloque.add("línea " + numLinea + " sin clave (" + t + ")");
                    continue;
                }
                if (!CLAVES.contains(clave)) {
                    logger.warn("Línea {}: clave desconocida '{}' ignorada", numLinea, clave);
                    continue;
                }
                // CASO: clave repetida -> error (no sabemos cuál es la buena)
                if (bloque.containsKey(clave)) {
                    erroresBloque.add("clave '" + clave + "' repetida en la línea " + numLinea);
                    continue;
                }
                bloque.put(clave, valor);
            }

            // CASO: el último bloque no termina en '---' -> hay que procesarlo igualmente
            procesarBloque(bloque, erroresBloque, lineaInicioBloque, listadoPaises, nombresLeidos);

        } catch (IOException e) {
            logger.error("Error leyendo {}", fichero.getName(), e);
        }

        logger.info("{}: {} países cargados", fichero.getName(), listadoPaises.size());
        return listadoPaises;
    }

    private void procesarBloque(Map<String, String> bloque, List<String> errores, int linea,
                                List<Pais> destino, Set<String> nombresLeidos) {
        if (bloque.isEmpty() && errores.isEmpty()) return; // bloque vacío: nada que hacer

        String quien = "Bloque de la línea " + linea
                + (bloque.get("nombre") != null ? " (" + bloque.get("nombre") + ")" : "");

        if (!errores.isEmpty()) {
            logger.error("{} descartado: {}", quien, String.join("; ", errores));
            return;
        }
        try {
            Pais p = PaisFactory.crear(
                    bloque.get("nombre"),
                    bloque.get("continente"),
                    PaisFactory.parsearPoblacion(bloque.get("poblacion")),
                    PaisFactory.parsearIdiomas(bloque.get("idiomas")),
                    bloque.get("moneda"));
            if (!nombresLeidos.add(PaisFactory.normalizar(p.getNombre()))) {
                throw new IllegalArgumentException("país duplicado");
            }
            destino.add(p);
        } catch (IllegalArgumentException e) {
            logger.error("{} descartado: {}", quien, e.getMessage());
        }
    }
}
