package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main7 {
    private static final Logger logger = LogManager.getLogger(Main7.class);

    static void main(String[] args) {
        Main7 main = new Main7();

        File ruta = new File("C:\\Users\\alumno\\Desktop\\Jaime DAM\\2-DAM\\prueba");
        List<File> lista = main.buscarFichero(ruta, "busca");

        logger.debug("Nº archivos: " + lista.size());

    }

    public List<File> buscarFichero(File file, String nombre){
        List<File> listaEncontrados = new ArrayList<>();

        if(file.isFile() && file.getName().toLowerCase().contains(nombre.toLowerCase())){
            listaEncontrados.add(file);
        }else if (file.isDirectory()){
            File[] listChild = file.listFiles();
            for(File child: listChild){
                List<File> list = buscarFichero(child, nombre);
                listaEncontrados.addAll(list);
            }
        }

        return listaEncontrados;
    }



}
