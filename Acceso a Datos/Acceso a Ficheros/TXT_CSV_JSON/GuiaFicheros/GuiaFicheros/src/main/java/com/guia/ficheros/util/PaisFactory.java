package com.guia.ficheros.util;

import com.guia.ficheros.model.Pais;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Limpieza + validación de un país en UN SOLO SITIO.
 * La usan los tres lectores (CSV, JSON y TXT): así una regla se escribe una vez.
 *
 * Si algo no es válido lanza IllegalArgumentException con un mensaje claro;
 * el lector la captura, la registra con logger.error y sigue con el siguiente.
 */
public final class PaisFactory {

    private static final Logger logger = LoggerFactory.getLogger(PaisFactory.class);

    private PaisFactory() {}

    // clave normalizada (sin tildes, minúsculas) -> forma correcta
    private static final Map<String, String> CONTINENTES = Map.of(
            "africa", "África",
            "america", "América",
            "asia", "Asia",
            "europa", "Europa",
            "oceania", "Oceanía",
            "antartida", "Antártida");

    /** Crea el país validando todo. */
    public static Pais crear(String nombre, String continente, Long poblacion,
                             List<String> idiomas, String moneda) {
        nombre = limpiar(nombre);
        continente = limpiar(continente);
        moneda = limpiar(moneda);

        // Obligatorios
        if (nombre == null)     throw new IllegalArgumentException("falta el nombre");
        if (continente == null) throw new IllegalArgumentException("falta el continente");

        // Valor dentro de una lista cerrada (y de paso se corrigen mayúsculas/tildes)
        String continenteOk = CONTINENTES.get(normalizar(continente));
        if (continenteOk == null) {
            throw new IllegalArgumentException("continente desconocido (" + continente + ")");
        }
        if (!continenteOk.equals(continente)) {
            logger.warn("País {}: continente '{}' corregido a '{}'", nombre, continente, continenteOk);
        }

        // Números: obligatorio y con rango lógico
        if (poblacion == null) throw new IllegalArgumentException("falta la población");
        if (poblacion < 0)     throw new IllegalArgumentException("población negativa (" + poblacion + ")");

        // Listas: quitar nulls y vacíos
        List<String> idiomasOk = idiomas == null ? new ArrayList<>() : idiomas.stream()
                .map(PaisFactory::limpiar)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));

        return new Pais(nombre, continenteOk, poblacion, idiomasOk, moneda);
    }

    /**
     * Normaliza un texto leído de fichero:
     *   null -> null
     *   "  Asia  " -> "Asia"                (espacios)
     *   "\"Asia\"" -> "Asia"                (comillas sobrantes)
     *   "", "null", "NULL", "N/A", "-" -> null   (valores "vacíos" escritos como texto)
     */
    public static String limpiar(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).trim();
        }
        if (s.isEmpty() || s.equalsIgnoreCase("null") || s.equalsIgnoreCase("N/A") || s.equals("-")) {
            return null;
        }
        return s;
    }

    /**
     * Texto -> número, con mensajes claros.
     *   ""            -> null  (crear() dirá "falta la población")
     *   "67.000.000"  -> 67000000  (se REPARA y se avisa con warn)
     *   "diez"        -> IllegalArgumentException
     */
    public static Long parsearPoblacion(String texto) {
        String t = limpiar(texto);
        if (t == null) return null;

        // Reparación: puntos como separador de miles (67.000.000)
        if (t.matches("\\d{1,3}(\\.\\d{3})+")) {
            String sinPuntos = t.replace(".", "");
            logger.warn("Población '{}' escrita con puntos de miles: se interpreta como {}", t, sinPuntos);
            t = sinPuntos;
        }
        try {
            return Long.parseLong(t);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("población no numérica (" + t + ")");
        }
    }

    /** "Inglés  Francés" -> [Inglés, Francés]. Varios espacios seguidos no generan idiomas vacíos. */
    public static List<String> parsearIdiomas(String texto) {
        String t = limpiar(texto);
        if (t == null) return new ArrayList<>();
        return Arrays.stream(t.split("\\s+"))      // \\s+ = uno o MÁS espacios/tabuladores
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /** "Población" -> "poblacion". Para comparar sin importar tildes ni mayúsculas. */
    public static String normalizar(String s) {
        if (s == null) return "";
        return Normalizer.normalize(s.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    /** Excel añade un carácter invisible (BOM, ﻿) al principio de los ficheros UTF-8. */
    public static String quitarBom(String s) {
        return (s != null && s.startsWith("﻿")) ? s.substring(1) : s;
    }
}
