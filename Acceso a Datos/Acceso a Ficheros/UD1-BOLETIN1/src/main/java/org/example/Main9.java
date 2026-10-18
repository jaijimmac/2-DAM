package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;

public class Main9 {
    private static final Logger logger = LogManager.getLogger(Main9.class);

    public static void main(String[] args) {
        logger.debug("Ingrese la ruta del directorio");
        Scanner teclado = new Scanner(System.in);
        String ruta = teclado.nextLine();
        File directorio = new File(ruta);

        if (!directorio.exists() || !directorio.isDirectory()) {
            logger.error("La ruta no existe o no es un directorio.");
            return;
        }

        Main9 main9 = new Main9();
        int total = main9.contarFicheros(directorio);

        System.out.println("Se van a eliminar " + total + " fichero(s). ¿Confirmas? (s/n)");
        String respuesta = teclado.nextLine().trim().toLowerCase();

        if (respuesta.equals("s")) {
            main9.borrarDirectorio(directorio);
            logger.info("Directorio eliminado correctamente.");
        } else {
            logger.info("Operación cancelada.");
        }
    }

    public int contarFicheros(File dir) {
        int count = 0;
        File[] contenido = dir.listFiles();
        if (contenido == null) return 0;
        for (File f : contenido) {
            if (f.isDirectory()) {
                count += contarFicheros(f);
            } else {
                count++;
            }
        }
        return count;
    }

    public void borrarDirectorio(File dir) {
        File[] contenido = dir.listFiles();
        if (contenido != null) {
            for (File f : contenido) {
                if (f.isDirectory()) {
                    borrarDirectorio(f);
                } else {
                    f.delete();
                }
            }
        }
        dir.delete();
    }
}