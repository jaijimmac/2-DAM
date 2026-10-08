package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main6 implements FilenameFilter {

    private static final Logger logger = LogManager.getLogger(Main6.class);

    static void main(String[] args) {
        Main6 main = new Main6();

        File ruta = new File("C:\\Users\\alumno\\Desktop\\Jaime DAM\\2-DAM\\prueba");

        List<File> lista = main.filtrarPorExtension(ruta, ".txt");
        List<File> lista2 = main.filtrarPorExtensionConLambda(ruta, ".txt");

        for(File file : lista){
            System.out.println("File: " + file.getName());
        }
        for(File file : lista2){
            System.out.println("TXT: " + file.getName());
        }
    }

    public List<File> filtrarPorExtension(File file, String extension){
        List<File> listaArchivos = new ArrayList<>();

        if (accept(file, extension)) {
            listaArchivos.add(file);
        }else{
            File[] childs = file.listFiles();

            for(File child: childs){
                List<File> list = filtrarPorExtension(child, extension);
                listaArchivos.addAll(list);
            }
        }

        return listaArchivos;
    }

    public List<File> filtrarPorExtensionConLambda(File file, String extension){
        List<File> listaArchivos = new ArrayList<>();

        if (file.isFile()){
            File[] txt = file.listFiles((d, name) ->name.endsWith(".txt"));
            listaArchivos.addAll(Arrays.asList(txt));
        }else {
            File[] children = file.listFiles();
            assert children != null;
            for(File child: children){
                List<File> lista = filtrarPorExtensionConLambda(child, extension);
                listaArchivos.addAll(lista);
            }
        }


        return listaArchivos;
    }

    @Override
    public boolean accept(File dir, String name) {
        if (dir.isDirectory()) {
            return false;
        }
        return name.toLowerCase().endsWith(".txt");
    }
}
