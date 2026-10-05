package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;

public class Main4 {
    private static final Logger logger = LogManager.getLogger(Main4.class);

     static void main(String[] args) {
        Main4 main = new Main4();

        logger.debug("Ingrese la ruta del directorio");

        Scanner teclado = new Scanner(System.in);
        String ruta = teclado.nextLine();
        File directorio = new File(ruta);

        if(!directorio.exists()) {
            throw new Exception("No existe el directorio");
        }
        main.mostrarDirectorio(directorio);

    }

    public void mostrarDirectorio(File file){
        File[] list = file.listFiles();

        for(int i = 0; i<list.length; i++) {
            if (list[i].isDirectory()){
                logger.debug("------------------------------------");
                logger.debug("Directorio padre: " + list[i].getName());
                logger.debug("------------------------------------");
                mostrarDirectorio(list[i]);
            }else {
                logger.debug("Archivo: " + list[i].getName());
            }
        }
    }
}
