package org.example;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

class Main {
    static void main(String[] args) {
        String archivo = "src/main/resources/Alumnos.txt";
        Main main = new Main();
        List<Persona> listaPersonas = new ArrayList<>();
        Double[] listaNotas = {7.80, 5.46};
        listaPersonas.add(new Persona(1L,"Jaime",listaNotas));
        listaPersonas.add(new Persona(2L,"Paula",listaNotas));

        main.escribePersonas(listaPersonas, archivo);
    }

    public void escribePersonas(List<Persona> personas, String rutaFichero) {
        PrintWriter out = null;
        try {
            FileWriter ficheroSalida;
            ficheroSalida = new FileWriter(rutaFichero, StandardCharsets.UTF_8);
            out = new PrintWriter(ficheroSalida);
            out.printf("Nombre ", "%NOTAS");
            for (Persona p : personas) {
                out.printf(Locale.ENGLISH, "%-10s", p.getNombre());
                for (double nota : p.getNotas()) {
                    out.printf(Locale.ENGLISH, "%6.2f", nota);
                }
                out.println();
            }
        } catch (IOException e) {

        } finally {
            if (out != null)
                out.close();
        }
    }

}
