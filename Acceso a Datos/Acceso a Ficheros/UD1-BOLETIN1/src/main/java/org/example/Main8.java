package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

public class Main8 {

    private static final Logger logger = LogManager.getLogger(Main8.class);

    static void main(String[] args) {

        Main8 main8 = new Main8();

        logger.debug("Ingrese la ruta del directorio");
        Scanner teclado = new Scanner(System.in);
        String ruta = teclado.nextLine();
        File directorio = new File(ruta);

        List<File> ficheros =  main8.buscar(directorio);

        if(ficheros.isEmpty()){
            logger.error("No se encontro el directorio");
        }

        ficheros.sort(Comparator.comparingLong(File::length).reversed());

        logger.info("Los 5 ficheros más grandes:");
        for (int i = 0; i < Math.min(5, ficheros.size()); i++) {
            File f = ficheros.get(i);
            logger.info("{} - {} bytes", f.getName(), f.length());
        }

        ficheros.sort(Comparator.comparingLong(File::lastModified).reversed());

        logger.info("Ficheros por fecha de modificación:");
        for (File f : ficheros) {
            logger.info("{} - {}", f.getName(), new Date(f.lastModified()) );
        }
    }

    public List<File> buscar(File ruta) {
        List<File> ficheros = new ArrayList<>();

        File[] contenido = ruta.listFiles();
        if (contenido != null) {
            for (File f : contenido) {
                if (f.isFile()) {
                    ficheros.add(f);
                }
            }
        }

        return ficheros;
    }

}
