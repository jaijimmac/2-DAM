package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Main3 {
    private static final Logger logger = LogManager.getLogger(Main3.class);

    static void main(String[] args) throws IOException {

        String ruta = System.getProperty("user.home");

        File dir = new File(ruta, "miDirectorio");
        dir.mkdir();

        if (dir.exists()) {
            File a1 = new File(dir, "lectura.txt");
            File a2 = new File(dir, "normal.txt");

            a1.createNewFile();
            a2.createNewFile();

            a1.setReadOnly();
            a2.renameTo(new File(ruta + "renombrado.txt"));


        }


    }
}
