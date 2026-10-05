package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;

public class Main5 {
    private static final Logger logger = LogManager.getLogger(Main5.class);

    static void main(String[] args) {
        
        Main5 main5 = new Main5();

        logger.debug("Ingrese la ruta del directorio");

        Scanner teclado = new Scanner(System.in);
        String ruta = teclado.nextLine();
        File directorio = new File(ruta);

        if (!directorio.exists()) {
            throw new RuntimeException("No existe el directorio");
        }

        if (!directorio.isDirectory()) {
            throw new RuntimeException("La ruta no es un directorio");
        }

        double tamanio = main5.mostrarDirectorio(directorio);

        logger.debug("Tamaño completo (BYTES): " + tamanio);
        logger.debug("Tamaño completo (MEGABYTES): "
                + (tamanio / 1024.0 / 1024.0));


    }

    public double mostrarDirectorio(File file){
        File[] list = file.listFiles();
        double tamanio = 0;

        for(int i = 0; i<list.length; i++) {
            if (list[i].isDirectory()){

                tamanio += mostrarDirectorio(list[i]);
            }else {
                tamanio += list[i].length();
            }
        }

        return tamanio;
    }
}
