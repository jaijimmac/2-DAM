package Apartado2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

public class LanzadorAnalisisTemperaturas {

    private final static String directorioGenerarClasses = "target\\classes";
    private final static String rutaSourceJava = "src\\main\\java\\Apartado2\\AnalizadorTemperaturas.java";
    private final static String rutaClase = "Apartado2.AnalizadorTemperaturas";
    private static final String rutaFichero = "src/main/resources";

    public static void main(String[] args) {

        LanzadorAnalisisTemperaturas lz = new LanzadorAnalisisTemperaturas();
        lz.lanzarCompilador();

        try {
            /*
            lz.ejecuta(rutaClase, rutaFichero + "/datos.txt");

            System.out.println("Se han analizado todas las temperaturas.");
            System.out.println("Resultados:");

            Map<String, Integer> listado = lz.leerResultado(rutaFichero + "/datos");
            int total = 0;
            for (Map.Entry<String, Integer> e : listado.entrySet()) {
                System.out.println("Umbral " + e.getKey() + " → " + e.getValue() + " días");
                total += e.getValue();
            }
            System.out.println("Total de días contados: " + total);
            */

            Map<String, Integer> listado = lz.ejecutarClase(rutaClase, rutaFichero + "/datos.txt");

            System.out.println("Se han analizado todas las temperaturas.");
            System.out.println("Resultados:");
            int total = 0;
            for (Map.Entry<String, Integer> item : listado.entrySet()) {
                System.out.println("Umbral " + item.getKey() + " → " + item.getValue() + " días");
                total += item.getValue();
            }
            System.out.println("Suma total de días contados: " + total);

        } catch (Exception e) {
            System.out.println("ERROR propagado desde un proceso hijo:");
            System.out.println(e.getMessage());
        }
    }

    public int lanzarCompilador() {
        ProcessBuilder pb;
        int exit = 0;

        try {
            String[] command = {"javac", "-d", directorioGenerarClasses, rutaSourceJava};
            pb = new ProcessBuilder(command);
            pb.inheritIO();
            Process process = pb.start();
            exit = process.waitFor();

        } catch (Exception e) {
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        }

        return exit;
    }


    public int ejecuta(String clase, String ruta) {
        int resultado = 0;
        String claseCompleta = clase;

        int[] umbrales = {10, 20, 25, 30, 35};

        List<ProcessBuilder> listaPb = new ArrayList<ProcessBuilder>();
        List<Process> listaProcesos = new ArrayList<Process>();

        try {
            for (int umbral : umbrales) {
                String[] comando = {"java", "-cp", directorioGenerarClasses, claseCompleta, ruta, Integer.toString(umbral)};
                ProcessBuilder pb = new ProcessBuilder(comando);
                pb.inheritIO();
                listaPb.add(pb);
            }

            for (ProcessBuilder pb : listaPb) {
                listaProcesos.add(pb.start());
            }

            for (Process process : listaProcesos) {
                int exit = process.waitFor();
                if (exit != 0) {
                    resultado = exit;
                }
            }

        } catch (Exception e) {
            System.out.println("Error al lanzar los procesos hijos: " + e.getMessage());
            resultado = -1;
        }

        return resultado;
    }

    public Map<String, Integer> leerResultado(String ruta) {
        Map<String, Integer> listado = new TreeMap<>();

        File dir = new File(ruta);
        File[] listadoArchivos = dir.listFiles();

        if (listadoArchivos == null) {
            System.out.println("ERROR: no existe el directorio " + ruta);
            return listado;
        }

        for (File arc : listadoArchivos) {
            try (Scanner sc = new Scanner(new FileReader(arc))) {
                while (sc.hasNext()) {
                    if (sc.hasNextInt()) {
                        listado.put(removerTxt(arc.getName()), sc.nextInt());
                    } else {
                        sc.next();
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        return listado;
    }

    public String removerTxt(String nombreArchivo) {
        if (nombreArchivo != null && nombreArchivo.toLowerCase().endsWith(".txt")) {
            return nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
        }
        return nombreArchivo;
    }

    public Map<String, Integer> ejecutarClase(String clase, String ruta) throws Exception {
        String claseCompleta = clase;

        int[] umbrales = {10, 20, 25, 30, 35};

        List<ProcessBuilder> listaPb = new ArrayList<ProcessBuilder>();
        List<Process> listaProcesos = new ArrayList<Process>();
        Map<String, Integer> listado = new TreeMap<String, Integer>();

        for (int umbral : umbrales) {
            String[] comando = {"java", "-cp", directorioGenerarClasses, claseCompleta, ruta, Integer.toString(umbral)};
            ProcessBuilder pb = new ProcessBuilder(comando);
            listaPb.add(pb);
        }

        for (ProcessBuilder pb : listaPb) {
            listaProcesos.add(pb.start());
        }

        for (Process process : listaProcesos) {

            List<String> lineasSalida = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineasSalida.add(linea);
            }

            StringBuilder errores = new StringBuilder();
            BufferedReader brErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((linea = brErr.readLine()) != null) {
                errores.append(linea).append(System.lineSeparator());
            }

            int exit = process.waitFor();

            if (exit != 0) {
                throw new RuntimeException("El proceso hijo terminó con código " + exit + System.lineSeparator() + errores);
            }

            for (String l : lineasSalida) {
                String[] partes = l.split(":");
                if (partes.length == 2) {
                    listado.put(partes[0].trim(), Integer.parseInt(partes[1].trim()));
                }
            }
        }

        return listado;
    }
}