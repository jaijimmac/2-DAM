package org.example;

import java.io.IOException;

public class Ejercicio2 {
    static void main(String[] args) {
        ProcessBuilder pb;
        String[] comando = {"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", "https://jaimejimenez03.github.io/"};
        pb = new ProcessBuilder(comando);
        pb.inheritIO();

        try {
            Process process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
