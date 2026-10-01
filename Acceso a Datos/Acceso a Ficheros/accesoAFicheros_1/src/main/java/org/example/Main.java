package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    static void main() {

        String rutaDirectorio = "C:\\Users\\alumno\\Desktop\\Jaime DAM\\2-DAM\\Acceso a Datos\\Acceso a Ficheros";
        String rutaRelativa = ".";

        File directorio = new File(rutaDirectorio);
        File fichero = new File(rutaRelativa, "fichero.txt");

        String sistemaOperativo = System.getProperty("os.name").toLowerCase();

        if (sistemaOperativo.contains("win")) {

            rutaDirectorio = "C:\\Users\\alumno\\Desktop\\Jaime DAM\\2-DAM\\Acceso a Datos\\Acceso a Ficheros";
        } else if (sistemaOperativo.contains("nix") || sistemaOperativo.contains("nux") || sistemaOperativo.contains("mac")) {

            rutaDirectorio = "/home/alumno/Desktop/Jaime DAM/2-DAM/Acceso a Datos/Acceso a Ficheros";
        } else {
            System.out.println("Sistema operativo desconocido.");
        }

        try {

            boolean creado = fichero.createNewFile();
        } catch (IOException e) {

            logger.error("Error al crear fichero: " + e.getMessage());
        }
    }
}



