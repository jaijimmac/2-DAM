package com.guia.ficheros.lectura;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.guia.ficheros.model.Pais;
import com.guia.ficheros.util.JsonUtil;
import com.guia.ficheros.util.JsonUtil.Fragmento;
import com.guia.ficheros.util.PaisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lector de JSON tolerante a errores.
 *
 * CASOS CONTROLADOS (ver resources/paises_errores.json):
 *   Fichero:  no existe · vacío · no es UTF-8 · no es un array (un objeto suelto)
 *   Sintaxis: falta una coma · coma final antes de } · comilla sin cerrar
 *   Campos:   null · falta el campo · número como texto ("1412000000") ·
 *             texto no numérico ("38 millones") · decimal donde va entero · booleano ·
 *             texto donde va lista ("Hindi Inglés") · nulls/vacíos dentro de la lista ·
 *             número donde va texto (123) · objeto donde va texto (moneda: {...}) ·
 *             claves con mayúsculas/tildes ("Población") · campos desconocidos
 */
public class LectorJson {

    private static final Logger logger = LoggerFactory.getLogger(LectorJson.class);

    private static final Set<String> CAMPOS_CONOCIDOS =
            Set.of("nombre", "continente", "poblacion", "idiomas", "idioma", "moneda");

    private final File fichero;

    public LectorJson(File fichero) {
        this.fichero = fichero;
    }

    public List<Pais> leerPaises() {
        List<Pais> listadoPaises = new ArrayList<>();

        if (!fichero.exists()) {
            logger.error("No existe el fichero {}", fichero.getAbsolutePath());
            return listadoPaises;
        }

        String contenido;
        try {
            contenido = Files.readString(fichero.toPath(), StandardCharsets.UTF_8);
        } catch (MalformedInputException e) {
            // CASO: el fichero se guardó en ANSI/ISO-8859-1 en vez de UTF-8
            logger.error("{} no está guardado en UTF-8 (vuelve a guardarlo con esa codificación)", fichero.getName());
            return listadoPaises;
        } catch (IOException e) {
            logger.error("No se puede leer {}", fichero.getName(), e);
            return listadoPaises;
        }

        contenido = PaisFactory.quitarBom(contenido).trim();

        // CASO: fichero vacío
        if (contenido.isEmpty()) {
            logger.error("El fichero {} está vacío", fichero.getName());
            return listadoPaises;
        }
        // CASO: no es un array -> avisamos, pero intentamos leer los objetos igualmente
        if (!contenido.startsWith("[")) {
            logger.warn("{} no empieza por '[': no es un array; se leen los objetos que contenga", fichero.getName());
        }

        Set<String> nombresLeidos = new HashSet<>();

        // Cada objeto {...} se procesa por separado: uno roto no tumba a los demás
        for (Fragmento f : JsonUtil.extraerObjetos(contenido)) {
            String donde = "Objeto nº " + f.numero() + " (línea " + f.linea() + ")";

            // 1) SINTAXIS
            JsonObject obj;
            try {
                obj = JsonUtil.parsearEstricto(f.texto()).getAsJsonObject();
            } catch (JsonParseException e) {
                logger.error("{} descartado: JSON mal formado ({})", donde, e.getMessage());
                continue;
            }

            // 2) TIPOS Y CONTENIDO
            try {
                Pais pais = parsear(obj, donde);
                if (!nombresLeidos.add(PaisFactory.normalizar(pais.getNombre()))) {
                    throw new IllegalArgumentException("país duplicado (" + pais.getNombre() + ")");
                }
                listadoPaises.add(pais);
            } catch (IllegalArgumentException e) {
                logger.error("{} descartado: {}", donde, e.getMessage());
            } catch (RuntimeException e) {
                // Red de seguridad: nada inesperado debe parar la carga
                logger.error("{} descartado: estructura inesperada", donde, e);
            }
        }

        logger.info("{}: {} países cargados", fichero.getName(), listadoPaises.size());
        return listadoPaises;
    }

    private Pais parsear(JsonObject obj, String donde) {
        // CASO: claves "Nombre", "Población"... -> se normalizan (sin tildes, minúsculas)
        Map<String, JsonElement> campos = new HashMap<>();
        for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
            String clave = PaisFactory.normalizar(e.getKey());
            campos.put(clave, e.getValue());
            // CASO: campo desconocido -> se ignora, pero se avisa
            if (!CAMPOS_CONOCIDOS.contains(clave)) {
                logger.warn("{}: campo desconocido '{}' ignorado", donde, e.getKey());
            }
        }

        JsonElement idiomas = campos.containsKey("idiomas") ? campos.get("idiomas") : campos.get("idioma");

        return PaisFactory.crear(
                texto(campos.get("nombre"), "nombre"),
                texto(campos.get("continente"), "continente"),
                entero(campos.get("poblacion"), "poblacion", donde),
                listaTextos(idiomas, "idiomas", donde),
                texto(campos.get("moneda"), "moneda"));
    }

    /**
     * Campo de TEXTO.
     *   falta / null      -> null (PaisFactory decide si es obligatorio)
     *   "Asia"            -> "Asia"
     *   123               -> ERROR (número donde va texto)
     *   {...} o [...]     -> ERROR (getAsString() lanzaría una excepción y pararía todo)
     */
    private String texto(JsonElement e, String campo) {
        if (e == null || e.isJsonNull()) return null;
        if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
            return e.getAsString();
        }
        throw new IllegalArgumentException("'" + campo + "' debe ser un texto, no " + e);
    }

    /**
     * Campo NUMÉRICO ENTERO.
     *   46000000          -> 46000000
     *   "1412000000"      -> se REPARA (warn): venía como texto
     *   "38 millones"     -> ERROR
     *   67000000.5        -> ERROR (no es entero)
     *   true / {...}      -> ERROR
     */
    private Long entero(JsonElement e, String campo, String donde) {
        if (e == null || e.isJsonNull()) return null;
        if (!e.isJsonPrimitive()) {
            throw new IllegalArgumentException("'" + campo + "' debe ser un número, no " + e);
        }
        JsonPrimitive p = e.getAsJsonPrimitive();

        if (p.isNumber()) {
            BigDecimal valor = p.getAsBigDecimal();
            if (valor.stripTrailingZeros().scale() > 0) {
                throw new IllegalArgumentException("'" + campo + "' debe ser entero, no " + valor);
            }
            try {
                return valor.longValueExact();
            } catch (ArithmeticException ex) {
                throw new IllegalArgumentException("'" + campo + "' es demasiado grande (" + valor + ")");
            }
        }
        if (p.isString()) {
            Long n = PaisFactory.parsearPoblacion(p.getAsString()); // lanza si no es numérico
            logger.warn("{}: '{}' venía como texto (\"{}\"), se convierte a número", donde, campo, p.getAsString());
            return n;
        }
        throw new IllegalArgumentException("'" + campo + "' debe ser un número, no " + e);
    }

    /**
     * Campo LISTA de textos.
     *   ["Inglés", "Francés"]      -> [Inglés, Francés]
     *   ["Japonés", null, "  "]    -> [Japonés]  (nulls y vacíos fuera)
     *   "Hindi Inglés"             -> se REPARA (warn): texto en vez de lista
     *   [{...}] o 123              -> ERROR
     */
    private List<String> listaTextos(JsonElement e, String campo, String donde) {
        List<String> lista = new ArrayList<>();
        if (e == null || e.isJsonNull()) return lista;

        if (e.isJsonArray()) {
            for (JsonElement item : e.getAsJsonArray()) {
                if (item.isJsonNull()) continue;
                if (!item.isJsonPrimitive()) {
                    throw new IllegalArgumentException("elemento no válido en '" + campo + "': " + item);
                }
                lista.add(item.getAsString());
            }
            return lista;
        }
        if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
            logger.warn("{}: '{}' venía como texto en vez de lista, se separa por espacios", donde, campo);
            return PaisFactory.parsearIdiomas(e.getAsString());
        }
        throw new IllegalArgumentException("'" + campo + "' debe ser una lista, no " + e);
    }
}
