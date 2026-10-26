package Apartado2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LanzadorAnalisisTemperaturas {
	
	private final static String directorioGenerarClasses = "target\\classes";
    private final static String rutaSourceJava = "src\\main\\java\\Apartado2\\AnalizadorTemperaturas.java";
    private final static String rutaClase = "Apartado2.AnalizadorTemperaturas";
    private static final String rutaFichero = "src/main/resources";

	public static void main(String[] args) {
		
		LanzadorAnalisisTemperaturas lz = new LanzadorAnalisisTemperaturas();
		
		lz.lanzarCompilador();
		lz.ejecutarClase(rutaClase, rutaFichero + "/datos.txt");
		
		Map<String, Integer> listado = lz.leerResultado(rutaFichero + "/datos");
		
		System.out.println("Resultado Mapeado");
		for(Map.Entry<String, Integer> item: listado.entrySet()) {
			System.out.println("Umbral " + item.getKey() + " --> " +  item.getValue());
		}
		
	}
	

	public int lanzarCompilador() {
        ProcessBuilder pb;
        int exit = 0;

        try {
            String[] command = {"javac", "-d", directorioGenerarClasses, rutaSourceJava};
            pb = new ProcessBuilder(command);
           //pb.inheritIO();

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
               //pb.inheritIO(); 
               listaPb.add(pb);
            }
           
         for(ProcessBuilder pb: listaPb) {
         	process = pb.start();
         	
         	BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
         	String linea;
         	while((linea = br.readLine()) != null) {
         		int v = Integer.parseInt(linea.split(":")[1].trim());
         	}
         }
         
        process.getErrorStream();
         
         int exit = process.waitFor();
         resultado = exit;
         
        }catch (Exception e) {
        	
        	System.out.println("Error" + e.getMessage());
        }

        return resultado;
    }
	
	public Map<String, Integer> leerResultado(String ruta) {
		Scanner sc = null;
		Map<String, Integer> listado = new HashMap<String, Integer>();
		
		File dir = new File(ruta);
		File[] listadoArchivos =  dir.listFiles();
		try {
			for(File arc: listadoArchivos) {
				FileReader fr = new FileReader(arc);
				sc = new Scanner(fr);
				while(sc.hasNext()) {
					String valor = sc.next();
					while(sc.hasNextInt()) {
						listado.put(removerTxt(arc.getName()), sc.nextInt());
						
					}
				}
			}	
		} catch (Exception e) {
			
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			
			sc.close();
		}
		
		return listado;
	}
	
	public String removerTxt(String nombreArchivo) {
        if (nombreArchivo != null && nombreArchivo.toLowerCase().endsWith(".txt")) {
            return nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
        }
        return nombreArchivo;
    }
	
	
}
