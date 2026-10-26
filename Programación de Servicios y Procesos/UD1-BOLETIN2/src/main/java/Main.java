import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        int resultado = -1;
        Process process = null;
        String claseCompleta = clase;
        String[] palabras= { "servicio", "hilo", "proceso", "multihilo", "concurrencia" };
        List<ProcessBuilder> listaPb = new ArrayList<ProcessBuilder>();
        
        try{
        	for(String palabra1: palabras) {
         	   
         	   String[] comando = {"java", "-cp", directorioGenerarClasses, claseCompleta, ruta, palabra1, nombreFichero };
               ProcessBuilder pb = new ProcessBuilder(comando);
               pb.redirectErrorStream(true);
               pb.inheritIO(); 
                
               listaPb.add(pb);
            }
           
         for(ProcessBuilder pb: listaPb) {
        	 
         	process = pb.start();
         }
         
         int exit = process.waitFor();
         
        }catch (Exception e) {
        	
        	System.out.println("Error" + e.getMessage());
        }

        return resultado;
    }
}