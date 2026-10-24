import java.io.File;
import java.io.IOException;

public class Main {
    private final static String directorioGenerarClasses = "target\\classes";
    private final static String rutaSourceJava = "src\\main\\java\\ContadorPalabras.java";
    private final static String rutaClase = "ContadorPalabras";
    private static final String rutaFichero = "src/main/resources/archivoLectura.txt";


    public static void main(String[] args) {

        Main m = new Main();
        m.lanzarCompilador();

        String[] palabras= { "servicio", "hilo", "proceso", "multihilo", "concurrencia" };

        for(String palabra: palabras) {
            int comando = m.ejecutarClase(rutaClase, rutaFichero, palabra, palabra);
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

        } catch(Exception e){
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        }

        return exit;
    }

    public int ejecutarClase(String clase, String ruta, String palabra, String nombreFichero) {
        int reesultado = -1;
        String claseCompleta = clase;

        try {
            String[] comando = {"java", "-cp", directorioGenerarClasses, claseCompleta, ruta,palabra, nombreFichero };
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);
            pb.inheritIO();

            Process process = pb.start();
            int exit = process.waitFor();
            System.out.println(exit);

        } catch (Exception e) {
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        }

        return reesultado;
    }
}