import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DetectaDevoluciones {

	

	private static final String RUTA_SALIDA = "src/main/resources/devoluciones.txt";

	public static void main(String[] args) {

		DetectaDevoluciones dd = new DetectaDevoluciones();

		List<String> devoluciones = dd.leerEntrada();
		dd.escribirDevoluciones(devoluciones);
		dd.mostrarSalidas(devoluciones);

		System.exit(devoluciones.size()); 
	}

	
	public List<String> leerEntrada() {
		List<String> devoluciones = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] campos = linea.split(";");
				if (campos.length == 5 && Integer.parseInt(campos[3].trim()) < 0) {
					devoluciones.add(linea);
				}
			}
		} catch (Exception e) {
			System.err.println("Error al leer la entrada estándar: " + e.getMessage());
			System.exit(255);
		}

		return devoluciones;
	}

	public void escribirDevoluciones(List<String> listado) {
		PrintWriter out = null;

		try {
			FileWriter fw = new FileWriter(new File(RUTA_SALIDA), StandardCharsets.UTF_8);
			out = new PrintWriter(fw);
			for (String linea : listado) {
				out.println(linea);
			}
		} catch (Exception e) {
			System.err.println("Error al escribir devoluciones: " + e.getMessage());
			System.exit(255);
		} finally {
			if (out != null)
				out.close();
		}
	}

	public void mostrarSalidas(List<String> listado) {
		double importe = 0;

		for (String linea : listado) {
			String[] campos = linea.split(";");
			int unidades = Integer.parseInt(campos[3].trim());
			double precio = Double.parseDouble(campos[4].trim());
			importe += -unidades * precio;
		}

		System.out.println(String.format(Locale.ENGLISH, "%.2f", importe));
	}
}