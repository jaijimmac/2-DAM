package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main2 {
    private static final Logger logger = LogManager.getLogger(Main2.class);

    static void main(String[] args) throws IOException {
        logger.debug("Ingrese la ruta del directorio");

        Scanner teclado = new Scanner(System.in);
        String ruta = teclado.nextLine();

        File file = new File(ruta);

        if (!file.exists()) {
            throw new Exception("No existe esa ruta / directorio");
        }

        logger.debug("Nombre: "+ file.getName());
        logger.debug("Ruta: "+ file.getPath());
        logger.debug("Ruta Absoluta: "+ file.getAbsolutePath());
        logger.debug("Ruta Canónica: "+ file.getCanonicalPath());
        logger.debug("Directorio Padre: "+ file.getParent());
        if (file.isDirectory()){
            File[] archivos = file.listFiles();
            logger.debug("Tipo: Directorio");
            logger.debug("Número Elementos: "+ archivos.length);

        }else {
            logger.debug("Tipo: Archivo");
        }
        logger.debug("Permisos: (Lectura, Escritura, Ejecucion) "+ file.canRead() + " " + file.canWrite() + " " + file.canExecute());
        logger.debug("Oculto: "+ file.isHidden());
        logger.debug("Tamaño: "+ file.getTotalSpace());
        logger.debug("Fecha Ult.Modificación: " + file.lastModified());
    }
}
