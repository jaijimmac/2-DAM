package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio3 {
    static void main(String[] args) throws IOException {
        Process p = Runtime.getRuntime().exec("tasklist");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        }catch (Exception e){
            System.out.printf("Error: " + e.getMessage());
        }
    }
}
