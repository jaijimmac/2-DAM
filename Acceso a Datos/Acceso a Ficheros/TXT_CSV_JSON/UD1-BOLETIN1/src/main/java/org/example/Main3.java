package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.Exception;

public class Main3 {
    private static final Logger logger = LogManager.getLogger(Main3.class);

    static void main(String[] args) throws IOException {

        String ruta = System.getProperty("user.home");

        File dir = new File(ruta, "miDirectorio");
        try {
            dir.mkdir();
            logger.debug("Directorio creado: " + dir.getAbsolutePath());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        if (dir.exists()) {
            File a1 = new File(dir, "lectura.txt");
            File a2 = new File(dir, "normal.txt");
           try {
               a1.createNewFile();
               a2.createNewFile();

               logger.debug("Fichero creado: " + a1.getName());
               logger.debug("Fichero creado: " + a2.getName());

               a1.setReadOnly();
               logger.debug(a1.getName() + " marcado como solo lectura.");

               String nombreAntes = a2.getName();
               a2.renameTo(new File(a2.getParent() + File.separator + "renombrado.txt"));
               logger.debug(nombreAntes + " renombrado a: " + a2.getName());

               a1.delete();
               if (a1.exists()){
                   logger.debug("No se ha podido borrar " + a1.getName());
               }
              String nombreAntesBorrar = a1.getName();
               a1.setWritable(true);
               a1.delete();
               if (!a1.exists()){
                   logger.debug(nombreAntesBorrar + " Se borro corrctamente");
               }
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
            logger.debug("Contenido de miDirectorio ");
            for (File i : dir.listFiles()){
                logger.debug("-" + i.getName());
            }

        }


    }
}
