package org.example;

import com.sun.tools.javac.Main;

import java.io.IOException;

public class Pruebas  {
      static void main(String[] args){
          Pruebas main = new Pruebas();
        Runtime rt = Runtime.getRuntime();
        System.out.println("Memoria Libre:" + rt.freeMemory());
        System.out.println("Total Memoria:" + rt.totalMemory());
        System.out.println("Max Memoria:" + rt.maxMemory());
        System.out.println("Nº Procesadores" + rt.availableProcessors());
        System.out.println("JRE Version:" + rt.version());
        System.out.println("Terminando programa.. .");
        main.abrirNotepad(rt);
        rt.exit(1);

    }

    public void abrirNotepad(Runtime rt) {
            String[] informacionProceso = {"notePad.exe"};
            Process proceso;
            try {
                proceso = rt.exec(informacionProceso);
                int codigoRetorno = proceso.waitFor();
                System.out.println(codigoRetorno);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

    }
}
