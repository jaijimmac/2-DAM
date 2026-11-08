package com.guia.ficheros.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Partir una línea CSV correctamente.
 *
 * ¿Por qué no basta con split(",")?
 *   1) split(",") ELIMINA los campos vacíos del final:
 *        "a,b,,".split(",")      -> [a, b]          (¡2 campos!)
 *        "a,b,,".split(",", -1)  -> [a, b, "", ""]  (4 campos, bien)
 *   2) No entiende las comillas:
 *        "\"Bosnia, Herzegovina\",Europa".split(",") -> ["\"Bosnia", " Herzegovina\"", "Europa"]
 *
 * Este método recorre la línea carácter a carácter y respeta las comillas.
 */
public final class CsvUtil {

    private CsvUtil() {}

    public static List<String> partirLinea(String linea, char separador) {
        List<String> campos = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        boolean dentroDeComillas = false;

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c == '"') {
                // "" dentro de comillas = una comilla literal  ("Él dijo ""hola""")
                if (dentroDeComillas && i + 1 < linea.length() && linea.charAt(i + 1) == '"') {
                    actual.append('"');
                    i++;
                } else {
                    dentroDeComillas = !dentroDeComillas;
                }
            } else if (c == separador && !dentroDeComillas) {
                campos.add(actual.toString().trim());
                actual.setLength(0);
            } else {
                actual.append(c);
            }
        }

        if (dentroDeComillas) {
            throw new IllegalArgumentException("comillas sin cerrar");
        }
        campos.add(actual.toString().trim()); // el último campo (aunque esté vacío)
        return campos;
    }

    /** Para escribir: si el valor tiene el separador, comillas o saltos de línea, va entre comillas. */
    public static String escapar(String valor, char separador) {
        if (valor == null) return "";
        if (valor.indexOf(separador) >= 0 || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
}
