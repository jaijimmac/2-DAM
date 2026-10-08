package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class Main8 implements Comparator {

    private static final Logger logger = LogManager.getLogger(Main.class);

    static void main(String[] args) {

    }

    public List<File> buscarPrimeros(File ruta){

        return null;
    }

    @Override
    public int compare(File a1, File a2) {
        return Double.compare(a1.getTotalSpace(), a2.getTotalSpace());
    }
}
