package org.example;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.printf("Ingresa la ruta.");
        String ruta = teclado.nextLine();

        File directorio = new File(ruta);

        if(directorio.exists()){

            File[] archivos = directorio.listFiles();
            Long nDir = 0L;
            Long nAr = 0L;

            for (int i=0; i<archivos.length; i++) {
                File archivo = archivos[i];
                nAr = archivo.length();

                if(archivo.isDirectory()){
                    System.out.println("[D] " + archivo.getName());
                }
            }
        }
    }
}
