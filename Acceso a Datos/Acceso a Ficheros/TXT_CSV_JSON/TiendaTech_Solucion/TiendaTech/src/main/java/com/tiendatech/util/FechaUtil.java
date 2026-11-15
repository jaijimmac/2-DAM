package com.tiendatech.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class FechaUtil {

    private FechaUtil() {}

    /*
     * OJO: con ResolverStyle.STRICT hay que usar "uuuu" (año) en vez de "yyyy"
     * (año de la era); si no, falla incluso con fechas correctas.
     * STRICT hace que 32/10/2026 o 29/02/2025 den error en lugar de "ajustarse".
     */
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static LocalDate parsear(String texto) throws DateTimeParseException {
        return LocalDate.parse(texto.trim(), FORMATO);
    }

    public static String formatear(LocalDate fecha) {
        return fecha == null ? "" : fecha.format(FORMATO);
    }
}
