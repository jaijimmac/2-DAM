package Apartado2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class AnalizadorTemperaturas{
	
	private static final String rutaFicheros = "src/main/resources";
	
	public static void main(String[] args)  throws FileNotFoundException{
		
		AnalizadorTemperaturas at = new AnalizadorTemperaturas();
		
		String nombreFicheroTemperaturas = args[0];
		int umbral = Integer.parseInt( args[1]);
		String nombreFicheroUmbral = args[1] + ".txt";
		
        List<Integer> listaDatos = at.leerDatos(nombreFicheroTemperaturas, umbral);
		at.escribriDatos(listaDatos, rutaFicheros, nombreFicheroUmbral);



        // APARTADO 4 //
        List<Integer> listaDatosConsola = at.leerDatosConsola(nombreFicheroTemperaturas, umbral);

        // APARTADO 3 //
        System.out.println("Umbral: " + umbral + " --> " + "Dias: " + listaDatosConsola.size());

	}
	
	public List<Integer> leerDatos(String ruta, int umbral) {
		Scanner sc = null;
		List<Integer> listadoTemperaturas = new ArrayList<Integer>();
		
		try {
			FileReader fr = new FileReader(ruta);
			sc = new Scanner(fr);
			while(sc.hasNextInt()) {
				if(sc.nextInt() >= umbral) {
					listadoTemperaturas.add(sc.nextInt());
				}
			}

		} catch (Exception e) {
			
			System.err.println("Error: El proceso con umbral: "+ umbral +  " ha fallado. Motivo: " +  e.getMessage());
		} finally {
			sc.close();
		}
		
		return listadoTemperaturas;
	}
	
	public void escribriDatos(List<Integer> listado, String ruta, String nombreFichero) {
            PrintWriter out = null;
            File dir = new File(ruta, "datos");

            if (!dir.exists()){
                dir.mkdir();
            }

            dir.canRead();

            File file = new File(ruta + "/datos", nombreFichero);
            try {

                FileWriter ficheroEscritura = new FileWriter(file, StandardCharsets.UTF_8);
                out = new PrintWriter(ficheroEscritura);
                out.printf(Locale.ENGLISH, "%-15s %5s%n", "Número Días", listado.size());
                out.println();
            } catch (Exception e) {

                throw new RuntimeException(e);
            } finally {

                if (out != null)
                    out.close();
            }
		
	}

	
	public String removerTxt(String nombreArchivo) {
		
        if (nombreArchivo != null && nombreArchivo.toLowerCase().endsWith(".txt")) {
            return nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
        }
        
        return nombreArchivo;
    }

    public List<Integer> leerDatosConsola(String ruta, int umbral) throws FileNotFoundException {
        List<Integer> listadoTemperaturas = new ArrayList<Integer>();

        Scanner sc = new Scanner(new FileReader(ruta));
        try {
            while(sc.hasNextInt()) {
                int temperatura = sc.nextInt();
                if(temperatura >= umbral) {
                    listadoTemperaturas.add(temperatura);
                }
            }
        } finally {
            sc.close();
        }

        return listadoTemperaturas;
    }
}
