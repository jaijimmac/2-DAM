package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class Main10 {
    private static final Logger logger = LogManager.getLogger(Main10.class);

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ingrese la ruta del directorio a organizar: ");
        String ruta = teclado.nextLine();

        File directorio = new File(ruta);

        if (!directorio.exists() || !directorio.isDirectory()) {
            logger.error("La ruta no existe o no es un directorio: {}", ruta);
            return;
        }

        Main10 organizador = new Main10();
        int movidos = organizador.organizarDescargas(directorio);
        logger.info("Organización completada. Ficheros movidos: {}", movidos);
    }

    public int organizarDescargas(File directorio) {
        File[] ficheros = directorio.listFiles();
        if (ficheros == null || ficheros.length == 0) {
            logger.warn("El directorio está vacío.");
            return 0;
        }

        int movidos = 0;

        for (File fichero : ficheros) {
            if (fichero.isDirectory()) continue;

            String nombre = fichero.getName();
            String extension = obtenerExtension(nombre);

            if (extension.isEmpty()) {
                logger.warn("Fichero sin extensión ignorado: {}", nombre);
                continue;
            }

            File subcarpeta = new File(directorio, extension);
            if (!subcarpeta.exists()) {
                subcarpeta.mkdir();
                logger.debug("Carpeta creada: {}", subcarpeta.getPath());
            }

            File destino = resolverConflicto(subcarpeta, nombre);

            boolean exito = fichero.renameTo(destino);
            if (exito) {
                logger.info("Movido: {} → {}", nombre, destino.getPath());
                movidos++;
            } else {
                logger.error("No se pudo mover: {}", nombre);
            }
        }

        return movidos;
    }

    private String obtenerExtension(String nombre) {
        int punto = nombre.lastIndexOf('.');
        if (punto == -1 || punto == nombre.length() - 1) return "";
        return nombre.substring(punto + 1).toLowerCase();
    }

    private File resolverConflicto(File carpeta, String nombre) {
        File candidato = new File(carpeta, nombre);
        if (!candidato.exists()) return candidato;

        String base = nombre.substring(0, nombre.lastIndexOf('.'));
        String ext  = nombre.substring(nombre.lastIndexOf('.'));

        int contador = 1;
        do {
            String nuevoNombre = base + "(" + contador + ")" + ext;
            candidato = new File(carpeta, nuevoNombre);
            contador++;
        } while (candidato.exists());

        return candidato;
    }


}