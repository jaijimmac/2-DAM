import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GestorVentas {

	private final static String directorioGenerarClasses = "target\\classes";
	private final static String rutaSourceFiltrar = "src\\main\\java\\FiltrarCategoria.java";
	private final static String rutaSourceDevoluciones = "src\\main\\java\\DetectaDevoluciones.java";
	private final static String claseFiltrar = "FiltrarCategoria";
	private final static String claseDevoluciones = "DetectaDevoluciones";

	private static final String rutaFichero = "src/main/resources/ventas.txt";

	private int totalLineasFiltradas = 0;
	private double totalImporte = 0;

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		//System.out.print("Categorías (separadas por comas): ");
		//String[] categorias = teclado.nextLine().split(",");
		//teclado.close();
		
		String[] categorias = {"Frutas", "Bebidas", "Lacteos", "Juguetes"};

		GestorVentas g = new GestorVentas();

		if (g.lanzarCompilador() != 0) {
			System.out.println("Error de compilación de los hijos");
			return;
		}

		g.ejecuta(rutaFichero, categorias);
	}

	public int lanzarCompilador() {

		ProcessBuilder pb;
		int exit = 0;

		try {
			String[] command = { "javac", "-encoding", "UTF-8", "-d", directorioGenerarClasses,
					rutaSourceFiltrar, rutaSourceDevoluciones };
			pb = new ProcessBuilder(command);
			pb.inheritIO();
			Process process = pb.start();
			exit = process.waitFor();

		} catch (Exception e) {
			System.out.println("Error al ejecutar el compilador: " + e.getMessage());
			exit = -1;
		}

		return exit;
	}

	public int ejecuta(String rutaDatos, String[] categorias) {
		int resultado = 0;

		List<ProcessBuilder> listaPb = new ArrayList<ProcessBuilder>();
		List<Process> listaProcesos = new ArrayList<>();
		List<String> nombresCat = new ArrayList<>();

		try {
			
			for (String c : categorias) {
				String cat = c.trim();
				if (cat.isEmpty())
					continue;
				String[] comando = { "java", "-cp", directorioGenerarClasses, claseFiltrar, rutaDatos, cat };
				ProcessBuilder pb = new ProcessBuilder(comando);
				pb.redirectError(ProcessBuilder.Redirect.INHERIT);
				listaPb.add(pb);
				nombresCat.add(cat);
			}

			for (ProcessBuilder prb : listaPb) {
				listaProcesos.add(prb.start());
			}

			ProcessBuilder pbDev = new ProcessBuilder("java", "-cp", directorioGenerarClasses, claseDevoluciones);
			pbDev.redirectError(ProcessBuilder.Redirect.INHERIT);
			Process procesoDev = pbDev.start();

			
			List<String> lineasFichero = Files.readAllLines(Path.of(rutaDatos), StandardCharsets.UTF_8);
			enviarLineas(procesoDev, lineasFichero);

			
			System.out.println("\n===== RESUMEN POR CATEGORÍA =====");
			for (int i = 0; i < listaProcesos.size(); i++) {
				procesarFiltro(listaProcesos.get(i), nombresCat.get(i));
			}

			
			String importeDev = leerLinea(procesoDev);
			int numDev = procesoDev.waitFor(); 

			
			System.out.println("---------------------------------");
			System.out.printf("Nº total de líneas filtradas : %d%n", totalLineasFiltradas);
			System.out.printf("Importe neto total           : %.2f €%n", totalImporte);
			System.out.println("\n===== DEVOLUCIONES =====");
			System.out.printf("Nº de devoluciones           : %d de %d líneas%n", numDev, lineasFichero.size());
			System.out.printf("Importe devuelto             : %s €%n", importeDev);
			System.out.printf("%% de líneas que son devolución: %.2f %%%n", numDev * 100.0 / lineasFichero.size());

		} catch (Exception e) {
			System.out.println("Error al lanzar los procesos hijos: " + e.getMessage());
			resultado = -1;
		}

		return resultado;
	}


	private void procesarFiltro(Process process, String categoria) throws Exception {
		String salida = leerLinea(process);
		int exit = process.waitFor();

		switch (exit) {
		case 0:
			
			String[] partes = salida.split(":");
			int n = Integer.parseInt(partes[1].trim());
			double importe = Double.parseDouble(partes[2].trim());
			totalLineasFiltradas += n;
			totalImporte += importe;
			System.out.printf("%-10s: %2d líneas | %8.2f €%n", partes[0].trim(), n, importe);
			break;
		case 2:
			System.out.printf("%-10s: sin ventas (no se genera fichero)%n", categoria);
			break;
		default:
			System.out.printf("%-10s: ERROR en el proceso (código %d)%n", categoria, exit);
		}
	}

	
	private void enviarLineas(Process p, List<String> lineas) throws Exception {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream(), StandardCharsets.UTF_8));
		for (String l : lineas) {
			bw.write(l);
			bw.newLine();
		}
		
		bw.close();
	}

	
	private String leerLinea(Process p) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
		String linea = br.readLine();
		return linea == null ? "" : linea;
	}
}