package com.guia.ficheros;

import com.guia.ficheros.escritura.EscritorCsv;
import com.guia.ficheros.escritura.EscritorJson;
import com.guia.ficheros.escritura.EscritorTxt;
import com.guia.ficheros.lectura.LectorCsv;
import com.guia.ficheros.lectura.LectorJson;
import com.guia.ficheros.lectura.LectorTxt;
import com.guia.ficheros.model.Pais;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ejecuta todos los casos:
 *   1) Lee cada fichero con errores (los errores salen por el logger).
 *   2) Escribe lo leído en la carpeta salida/.
 *   3) Vuelve a leer lo escrito: no debe aparecer NINGÚN error (ida y vuelta).
 */
public class App {

    private static final File RES = new File("resources");
    private static final File SALIDA = new File("salida");

    public static void main(String[] args) {

        // ============ CSV ============
        titulo("CSV: paises_errores.csv");
        List<Pais> csv = new LectorCsv(new File(RES, "paises_errores.csv")).leerPaises();
        mostrar(csv);

        titulo("CSV: casos de fichero completo");
        new LectorCsv(new File(RES, "no_existe.csv")).leerPaises();
        new LectorCsv(new File(RES, "paises_vacio.csv")).leerPaises();
        new LectorCsv(new File(RES, "paises_sin_columna.csv")).leerPaises();
        new LectorCsv(new File(RES, "paises_puntoycoma.csv")).leerPaises();
        System.out.println("  -> Con el separador correcto:");
        mostrar(new LectorCsv(new File(RES, "paises_puntoycoma.csv"), ';').leerPaises());

        titulo("CSV: escritura e ida y vuelta");
        List<Pais> conProblemas = new ArrayList<>(csv);
        conProblemas.add(null);                                                    // país null
        conProblemas.add(new Pais("Tierra \"Media\"", "Europa", 1, null, null));   // comillas y nulls
        new EscritorCsv().escribirPaises(conProblemas, new File(SALIDA, "paises.csv"));
        List<Pais> releidoCsv = new LectorCsv(new File(SALIDA, "paises.csv")).leerPaises();
        comprobar(conProblemas.size() - 1, releidoCsv.size());

        // ============ JSON ============
        titulo("JSON: paises_errores.json");
        List<Pais> json = new LectorJson(new File(RES, "paises_errores.json")).leerPaises();
        mostrar(json);

        titulo("JSON: casos de fichero completo");
        new LectorJson(new File(RES, "no_existe.json")).leerPaises();
        new LectorJson(new File(RES, "paises_vacio.json")).leerPaises();
        mostrar(new LectorJson(new File(RES, "paises_no_array.json")).leerPaises());

        titulo("JSON: escritura e ida y vuelta");
        new EscritorJson().escribirPaises(json, new File(SALIDA, "paises.json"));
        comprobar(json.size(), new LectorJson(new File(SALIDA, "paises.json")).leerPaises().size());

        // ============ TXT ============
        titulo("TXT: paises_errores.txt");
        List<Pais> txt = new LectorTxt(new File(RES, "paises_errores.txt")).leerPaises();
        mostrar(txt);

        titulo("TXT: escritura, append e ida y vuelta");
        EscritorTxt escritorTxt = new EscritorTxt();
        File salidaTxt = new File(SALIDA, "paises.txt");
        escritorTxt.escribirPaises(txt, salidaTxt);
        escritorTxt.anadirPais(new Pais("Noruega", "Europa", 5400000,
                new ArrayList<>(Arrays.asList("Noruego")), "Corona noruega"), salidaTxt);
        comprobar(txt.size() + 1, new LectorTxt(salidaTxt).leerPaises().size());

        titulo("FIN: revisa la carpeta salida/ y el fichero errores.log");
    }

    private static void mostrar(List<Pais> paises) {
        System.out.println("  Países válidos: " + paises.size());
        paises.forEach(p -> System.out.println("    " + p));
    }

    private static void comprobar(int esperados, int leidos) {
        System.out.println(esperados == leidos
                ? "  OK: se han releído los " + leidos + " países escritos sin errores"
                : "  FALLO: se escribieron " + esperados + " y se han releído " + leidos);
    }

    private static void titulo(String t) {
        System.out.println("\n" + "=".repeat(75) + "\n  " + t + "\n" + "=".repeat(75));
    }
}
