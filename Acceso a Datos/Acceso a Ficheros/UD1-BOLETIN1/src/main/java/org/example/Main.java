package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        logger.debug("Ingrese la ruta del directorio");
        String ruta = teclado.nextLine();

        File directorio = new File(ruta);

        if(directorio.exists()){

            File[] archivos = directorio.listFiles();
            if (archivos == null) {}
            Long nDir = 0L;
            Long nAr = 0L;

            for (int i=0; i<archivos.length; i++) {
                File archivo = archivos[i];

                if(archivo.isDirectory()){
                   logger.debug("[D] " + archivo.getName());
                   nDir++;
                }else {
                    logger.debug("[F] " + archivo.getName());
                    nAr++;
                }
            }
            logger.debug("Número total de ficheros: "+ nAr);
            logger.debug("Número total de directorios: "+ nDir);
        }
    }
}
