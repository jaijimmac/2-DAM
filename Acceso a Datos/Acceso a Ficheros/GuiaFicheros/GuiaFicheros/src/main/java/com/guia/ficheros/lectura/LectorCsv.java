package com.guia.ficheros.lectura;

import com.guia.ficheros.model.Pais;
import com.guia.ficheros.util.CsvUtil;
import com.guia.ficheros.util.PaisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Lector de CSV con el MISMO esquema que tu leerPaises() (Scanner + while + split),
 * pero controlando todos los errores típicos.
 *
 * CASOS CONTROLADOS (ver resources/paises_errores.csv):
 *   Fichero:  no existe · vacío · BOM de Excel · cabecera sin una columna · separador ';'
 *   Línea:    vacía · comentario (#) · campo vacío (doble coma ,,) · faltan columnas ·
 *             sobran columnas · separador distinto (;) · comillas · coma dentro de comillas ·
 *             comillas sin cerrar · espacios sobrantes · "null"/"N/A" como texto ·
 *             número con puntos (67.000.000) · número con letras · número negativo ·
 *             dobles espacios en la lista de idiomas · valor fuera de lista (continente) ·
 *             mayúsculas/tildes distintas (europa, Africa) · registro duplicado ·
 *             última línea sin salto de línea
 */
public class LectorCsv {

    private static final Logger logger = LoggerFactory.getLogger(LectorCsv.class);

    // Columnas obligatorias de la cabecera (ya normalizadas: sin tildes y en minúsculas)
    private static final List<String> COLUMNAS = List.of("nombre", "continente", "poblacion", "idioma", "moneda");

    private final File fichero;
    private final char separador;

    public LectorCsv(File fichero) {
        this(fichero, ',');
    }

    public LectorCsv(File fichero, char separador) {
        this.fichero = fichero;
        this.separador = separador;
    }

    public List<Pais> leerPaises() {
        List<Pais> listadoPaises = new ArrayList<>();

        // CASO: el fichero no existe -> mensaje claro con la ruta COMPLETA (para ver dónde lo busca)
        if (!fichero.exists()) {
            logger.error("No existe el fichero {}", fichero.getAbsolutePath());
            return listadoPaises;
        }

        // try-with-resources: el Scanner se cierra SIEMPRE, aunque haya excepciones.
        // UTF-8 explícito: sin él, en Windows las tildes pueden salir como "Ã©".
        try (Scanner in = new Scanner(fichero, StandardCharsets.UTF_8)) {

            // CASO: fichero vacío -> in.nextLine() lanzaría NoSuchElementException
            if (!in.hasNextLine()) {
                logger.error("El fichero {} está vacío", fichero.getName());
                return listadoPaises;
            }

            // CABECERA: se lee y se mapea nombre de columna -> posición.
            // Así funciona aunque las columnas vengan en otro orden.
            String cabecera = PaisFactory.quitarBom(in.nextLine());
            Map<String, Integer> columnas = leerCabecera(cabecera);
            int totalColumnas = CsvUtil.partirLinea(cabecera, separador).size();

            Set<String> nombresLeidos = new HashSet<>();
            int numLinea = 1;

            while (in.hasNextLine()) {
                String valor = in.nextLine();
                numLinea++;

                // CASO: líneas vacías y comentarios -> se ignoran sin error
                if (valor.isBlank() || valor.trim().startsWith("#")) continue;

                // CLAVE: el try/catch va DENTRO del while.
                // Si estuviera fuera, la primera línea mala pararía la lectura de TODO el fichero.
                try {
                    Pais pais = parsearLinea(valor, columnas, totalColumnas);

                    // CASO: duplicado (Set.add devuelve false si ya estaba)
                    if (!nombresLeidos.add(PaisFactory.normalizar(pais.getNombre()))) {
                        throw new IllegalArgumentException("país duplicado (" + pais.getNombre() + ")");
                    }
                    listadoPaises.add(pais);

                } catch (IllegalArgumentException e) {
                    // NumberFormatException es hija de IllegalArgumentException: también entra aquí
                    logger.error("Línea {} descartada: {}  ->  {}", numLinea, e.getMessage(), valor.trim());
                }
            }

        } catch (IOException e) {
            // new Scanner(File, Charset) lanza IOException (FileNotFoundException es una de ellas)
            logger.error("No se puede leer {}", fichero.getAbsolutePath(), e);
        } catch (IllegalArgumentException e) {
            // Solo llega aquí un error de CABECERA (los de línea se capturan dentro del while)
            logger.error("Cabecera no válida en {}: {}", fichero.getName(), e.getMessage());
        }

        logger.info("{}: {} países cargados", fichero.getName(), listadoPaises.size());
        return listadoPaises;
    }

    private Map<String, Integer> leerCabecera(String cabecera) {
        List<String> nombres = CsvUtil.partirLinea(cabecera, separador);

        // CASO: el fichero usa otro separador (todo sale en una sola columna)
        if (nombres.size() == 1) {
            char otro = separador == ',' ? ';' : ',';
            if (cabecera.indexOf(otro) >= 0) {
                throw new IllegalArgumentException("parece que el separador es '" + otro
                        + "' y no '" + separador + "'. Usa new LectorCsv(fichero, '" + otro + "')");
            }
        }

        Map<String, Integer> posiciones = new HashMap<>();
        for (int i = 0; i < nombres.size(); i++) {
            posiciones.put(PaisFactory.normalizar(nombres.get(i)), i);
        }

        // CASO: falta una columna obligatoria en la cabecera
        for (String col : COLUMNAS) {
            if (!posiciones.containsKey(col)) {
                throw new IllegalArgumentException("falta la columna '" + col + "'");
            }
        }
        return posiciones;
    }

    private Pais parsearLinea(String linea, Map<String, Integer> col, int totalColumnas) {
        List<String> campos = CsvUtil.partirLinea(linea, separador); // lanza si hay comillas sin cerrar

        // CASO: separador distinto en ESTA línea (Nigeria;África;...)
        if (campos.size() == 1 && linea.indexOf(';') >= 0) {
            throw new IllegalArgumentException("usa ';' como separador en vez de '" + separador + "'");
        }
        // CASO: faltan o sobran columnas -> se comprueba ANTES de hacer campos.get(i)
        //       (si no, ArrayIndexOutOfBounds / IndexOutOfBounds)
        if (campos.size() < totalColumnas) {
            throw new IllegalArgumentException("faltan columnas (" + campos.size() + " de " + totalColumnas + ")");
        }
        if (campos.size() > totalColumnas) {
            throw new IllegalArgumentException("sobran columnas (" + campos.size() + " de " + totalColumnas + ")");
        }

        // Cada campo se limpia/valida en PaisFactory (espacios, comillas, "null", números...)
        return PaisFactory.crear(
                campos.get(col.get("nombre")),
                campos.get(col.get("continente")),
                PaisFactory.parsearPoblacion(campos.get(col.get("poblacion"))),
                PaisFactory.parsearIdiomas(campos.get(col.get("idioma"))),
                campos.get(col.get("moneda")));
    }
}
