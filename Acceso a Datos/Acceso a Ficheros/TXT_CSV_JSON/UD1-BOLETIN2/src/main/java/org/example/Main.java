package org.example;

import org.example.Entity.DiffFolder;
import org.example.Entity.ResultadoComparacion;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class Main {
    static void main(String[] args) {

        String ruta1 ="C:\\Users\\alumno\\Desktop\\Jaime DAM\\2-DAM\\prueba";
        String ruta2 ="C:\\Users\\alumno\\Desktop\\Jaime DAM\\2-DAM\\prueba\\carpetaBusca";

        File r1 = new File(ruta1);
        File r2 = new File(ruta2);

        DiffFolder diffFolder = new DiffFolder(r1, r2);

        Collection<ResultadoComparacion> lista = diffFolder.comparame();

        for(ResultadoComparacion r:lista){
            System.out.printf(r.toString());
        }



    }
}
