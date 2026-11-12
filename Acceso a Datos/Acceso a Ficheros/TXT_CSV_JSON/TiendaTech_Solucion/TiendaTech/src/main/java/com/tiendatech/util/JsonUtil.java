package com.tiendatech.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lectura "tolerante" de un array JSON.
 *
 * PROBLEMA: si se parsea el fichero entero de una vez y UN objeto tiene un error
 * de sintaxis (falta una coma, una comilla...), Gson lanza una excepción y se
 * pierden TODOS los clientes, también los correctos.
 *
 * SOLUCIÓN: recorrer el texto carácter a carácter, recortar cada objeto {...} de
 * primer nivel y parsearlo POR SEPARADO. Si uno falla, se descarta solo ese.
 */
public final class JsonUtil {

    private JsonUtil() {}

    private static final Gson GSON = new Gson();
    private static final Pattern ID = Pattern.compile("\"id\"\\s*:\\s*\"?([^\",}\\s]+)");

    /** Un objeto recortado del fichero: su posición y su texto. */
    public record Fragmento(int numero, int linea, String texto) {

        /** Intenta sacar el id aunque el objeto esté roto (para que el log sea útil). */
        public String idAproximado() {
            Matcher m = ID.matcher(texto);
            return m.find() ? m.group(1) : null;
        }

        public String descripcion() {
            String id = idAproximado();
            return (id != null ? id : "nº " + numero) + " (línea " + linea + ")";
        }
    }

    /** Recorta cada objeto {...} de primer nivel del array. */
    public static List<Fragmento> extraerObjetos(String json) {
        List<Fragmento> fragmentos = new ArrayList<>();
        int profundidad = 0;
        int inicio = -1;
        int linea = 1;
        int lineaInicio = 1;
        boolean enCadena = false;
        boolean escape = false;

        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);

            if (ch == '\n') {
                linea++;
                // En JSON una cadena no puede tener saltos de línea: si estábamos
                // dentro de una, es que le falta la comilla de cierre.
                enCadena = false;
                escape = false;
                continue;
            }

            if (enCadena) {
                if (escape)           escape = false;
                else if (ch == '\\')  escape = true;
                else if (ch == '"')   enCadena = false;
                continue;             // las llaves dentro de un texto no cuentan
            }

            if (ch == '"') {
                enCadena = true;
            } else if (ch == '{') {
                if (profundidad == 0) {
                    inicio = i;
                    lineaInicio = linea;
                }
                profundidad++;
            } else if (ch == '}' && profundidad > 0) {
                profundidad--;
                if (profundidad == 0) {
                    fragmentos.add(new Fragmento(fragmentos.size() + 1, lineaInicio,
                            json.substring(inicio, i + 1)));
                }
            }
        }

        // Objeto sin cerrar al final del fichero: se devuelve igual para que falle al parsear y se registre
        if (profundidad > 0 && inicio >= 0) {
            fragmentos.add(new Fragmento(fragmentos.size() + 1, lineaInicio, json.substring(inicio)));
        }
        return fragmentos;
    }

    /**
     * Parsea UN objeto en modo ESTRICTO.
     * (JsonParser.parseString es "lenient" y acepta cosas que no son JSON válido,
     *  como textos sin comillas; aquí queremos detectarlas como error.)
     */
    public static JsonElement parsearEstricto(String texto) throws JsonParseException {
        try (JsonReader reader = new JsonReader(new StringReader(texto))) {
            reader.setLenient(false);
            JsonElement elemento = GSON.getAdapter(JsonElement.class).read(reader);
            if (reader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonParseException("hay texto sobrante después del objeto");
            }
            return elemento;
        } catch (IOException | IllegalStateException e) {
            throw new JsonParseException(e.getMessage(), e);
        }
    }
}
