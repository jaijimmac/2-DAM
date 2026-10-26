package Apartado2;

import java.util.ArrayList;
import java.util.List;

public class LanzadorAnalisisTemperaturas {
	
	private final static String directorioGenerarClasses = "target\\classes";
    private final static String rutaSourceJava = "src\\main\\java\\Apartado2\\AnalizadorTemperaturas.java";
    private final static String rutaClase = "AnalizadorTemperaturas";
    private static final String rutaFichero = "src/main/resources/datos.txt";

	public static void main(String[] args) {
		
		LanzadorAnalisisTemperaturas lz = new LanzadorAnalisisTemperaturas();
		lz.lanzarCompilador();
		
		lz.ejecutarClase(rutaClase, rutaFichero);
		
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
	
	
	public int ejecutarClase(String clase, String ruta) {
        int resultado = -1;
        Process process = null;
        String claseCompleta = clase;
        
        int[] umbrales = {10, 20, 25, 30, 35};
       
        List<ProcessBuilder> listaPb = new ArrayList<ProcessBuilder>();
       
        try{
        	for(int umbral: umbrales) {
        		
         	   String[] comando = {"java", "-cp", directorioGenerarClasses, claseCompleta, ruta, Integer.toString(umbral)};
               ProcessBuilder pb = new ProcessBuilder(comando);
               pb.redirectErrorStream(true);
               pb.inheritIO(); 
                
               listaPb.add(pb);
            }
           
         for(ProcessBuilder pb: listaPb) {
         	process = pb.start();
         }
         
         int exit = process.waitFor();
         System.out.println(exit);
         
        }catch (Exception e) {
        	
        	System.out.println("Error" + e.getMessage());
        }

        return resultado;
    }
}
