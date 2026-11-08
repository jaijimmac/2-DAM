package com.guia.ficheros.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Lectura "tolerante" de un array JSON.
 *
 * PROBLEMA: JsonParser.parseString(ficheroEntero) falla con UN solo objeto mal
 * escrito (falta una coma, una comilla...) y se pierden TODOS los registros.
 *
 * SOLUCIÓN: recortar cada objeto {...} de primer nivel y parsearlo por separado.
 */
public final class JsonUtil {

    private JsonUtil() {}

    private static final Gson GSON = new Gson();

    /** Un objeto recortado: su número de orden, la línea donde empieza y su texto. */
    public record Fragmento(int numero, int linea, String texto) {}

    public static List<Fragmento> extraerObjetos(String json) {
        List<Fragmento> fragmentos = new ArrayList<>();
        int profundidad = 0, inicio = -1, linea = 1, lineaInicio = 1;
        boolean enCadena = false, escape = false;

        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);

            if (ch == '\n') {
                linea++;
                enCadena = false;   // una cadena JSON no puede tener saltos de línea:
                escape = false;     // si seguía abierta es que le falta la comilla de cierre
                continue;
            }
            if (enCadena) {
                if (escape) escape = false;
                else if (ch == '\\') escape = true;
                else if (ch == '"') enCadena = false;
                continue;           // las llaves dentro de un texto no cuentan
            }
            if (ch == '"') {
                enCadena = true;
            } else if (ch == '{') {
                if (profundidad == 0) { inicio = i; lineaInicio = linea; }
                profundidad++;
            } else if (ch == '}' && profundidad > 0) {
                profundidad--;
                if (profundidad == 0) {
                    fragmentos.add(new Fragmento(fragmentos.size() + 1, lineaInicio, json.substring(inicio, i + 1)));
                }
            }
        }
        if (profundidad > 0 && inicio >= 0) {   // objeto sin cerrar al final
            fragmentos.add(new Fragmento(fragmentos.size() + 1, lineaInicio, json.substring(inicio)));
        }
        return fragmentos;
    }

    /**
     * Parsea en modo ESTRICTO. JsonParser.parseString es "lenient" y acepta cosas
     * que no son JSON válido (textos sin comillas, comas finales...).
     */
    public static JsonElement parsearEstricto(String texto) {
        try (JsonReader reader = new JsonReader(new StringReader(texto))) {
            reader.setLenient(false);
            JsonElement e = GSON.getAdapter(JsonElement.class).read(reader);
            if (reader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonParseException("texto sobrante después del objeto");
            }
            return e;
        } catch (IOException | IllegalStateException e) {
            throw new JsonParseException(mensajeCorto(e), e);
        }
    }

    /** Gson añade al mensaje un enlace de ayuda muy largo; lo quitamos. */
    public static String mensajeCorto(Throwable e) {
        String m = e.getMessage() == null ? e.toString() : e.getMessage();
        int corte = m.indexOf(" See https");
        return corte > 0 ? m.substring(0, corte) : m;
    }
}
