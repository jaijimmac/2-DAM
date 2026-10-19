package org.example;

import com.sun.tools.jconsole.JConsoleContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        File archivo = new File("src/main/resources/Alumnos.txt");
        Scanner in = null;
        Long contador = 1L;
        List<Persona> listaAlumnos = new ArrayList<>();

        try {
            try {
                FileReader fichero = new FileReader(archivo);
                in = new Scanner(fichero);
                Persona p1 = new Persona();

                while (in.hasNext()) {
                    String valor =  in.next();
                    boolean esNum;

                    try {
                        Double.parseDouble(valor);
                        esNum = true;
                    } catch (NumberFormatException e) {
                        esNum = false;
                    }

                    if (!esNum){
                        p1.setId(contador);
                        p1.setNombre(valor);
                        System.out.println(p1.toString());
                        contador++;
                    }else {
                        p1.annadirNotas(Double.parseDouble(valor));
                        listaAlumnos.add(p1);

                    }
                }


            } finally {
                if (in != null) {
                    in.close();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
