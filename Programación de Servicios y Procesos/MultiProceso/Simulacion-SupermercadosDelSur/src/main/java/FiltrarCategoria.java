import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class FiltrarCategoria {

	private static final String DIR_SALIDA = "src/main/resources/categorias";

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Uso: FiltrarCategoria <fichero> <categoria>");
			System.exit(1);
		}

		String rutaFichero = args[0];
		String categoria = args[1].trim();

		FiltrarCategoria fg = new FiltrarCategoria();

		List<String> listadoCat = fg.leerFichero(rutaFichero, categoria);

		if (listadoCat.isEmpty()) {
			System.exit(2); 
		}

		fg.escribirCategorias(listadoCat, categoria);
		fg.mostrarSalidas(listadoCat, categoria);
		System.exit(0);
	}

	
	public List<String> leerFichero(String rutaFichero, String categoria) {
		Scanner sc = null;
		List<String> listadoCat = new ArrayList<>();

		try {
			FileReader fr = new FileReader(rutaFichero, StandardCharsets.UTF_8);
			sc = new Scanner(fr);
			while (sc.hasNextLine()) {
				String valor = sc.nextLine();
				String[] campos = valor.split(";");
				
				if (campos.length == 5 && campos[2].trim().equalsIgnoreCase(categoria)) {
					listadoCat.add(valor);
				}
			}
		} catch (Exception e) {
			System.err.println("Error al leer el fichero " + rutaFichero + ": " + e.getMessage());
			System.exit(1);
		} finally {
			if (sc != null)
				sc.close();
		}

		return listadoCat;
	}

	
	public void escribirCategorias(List<String> listado, String categoria) {
		PrintWriter out = null;

		File dir = new File(DIR_SALIDA);
		if (!dir.exists()) {
			dir.mkdirs(); 
		}

		File file = new File(dir, categoria + ".txt");
		try {
			FileWriter ficheroEscritura = new FileWriter(file, StandardCharsets.UTF_8);
			out = new PrintWriter(ficheroEscritura);
			for (String linea : listado) {
				out.println(linea);
			}
		} catch (Exception e) {
			System.err.println("Error al escribir " + file.getName() + ": " + e.getMessage());
			System.exit(1);
		} finally {
			if (out != null)
				out.close();
		}
	}

	
	public void mostrarSalidas(List<String> listado, String categoria) {
		double importe = 0;

		for (String linea : listado) {
			String[] campos = linea.split(";");
			int numUnidades = Integer.parseInt(campos[3].trim());
			double precio = Double.parseDouble(campos[4].trim());
			importe += numUnidades * precio; 
		}

		
		System.out.println(String.format(Locale.ENGLISH, "%s : %d : %.2f", categoria, listado.size(), importe));
	}
}