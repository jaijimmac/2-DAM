package org.example;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio1 {
    static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        String[] rutaChrome = {"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", "https://jaimejimenez03.github.io/"};

        try {
            rt.exec(rutaChrome);
        }catch (Exception e){
            System.out.printf("Error: " + e.getMessage());
        }
    }
}
