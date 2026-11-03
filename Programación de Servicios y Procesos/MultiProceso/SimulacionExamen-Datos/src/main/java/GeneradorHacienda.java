import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneradorHacienda {

    private final static String directorioGenerarClasses = "target\\classes";
    private final static String rutaSourceJava = "src\\main\\java\\GeneraNSSFichero.java";
    private final static String rutaClase = "GeneraNSSFichero";

    private static final String rutaFichero = "src/main/resources/datos.txt";

    public static void main(String[] args) {
        //Scanner teclado = new Scanner(System.in);
        //String ruta = teclado.nextLine();
        GeneradorHacienda g = new GeneradorHacienda();

        try {
            g.lanzarCompilador();
            g.ejecuta(rutaClase, rutaFichero);

        } catch (Exception e){
            System.err.println("Error en GeneradorHacienda: " + e.getMessage());
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

        } catch (Exception e) {
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        }

        return exit;
    }

    public int ejecuta(String clase, String rutaDatos) {
        int resultado = 0;
        String claseCompleta = clase;

        List<ProcessBuilder> listaPb = new ArrayList<ProcessBuilder>();
        List<Process> listaProcesos = new ArrayList<>();
        try {

            String[] comando = {"java", "-cp", directorioGenerarClasses, claseCompleta, rutaDatos};
            ProcessBuilder pb = new ProcessBuilder(comando);
            //pb.inheritIO();
            listaPb.add(pb);

            for (ProcessBuilder prb : listaPb) {
                listaProcesos.add(prb.start());
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

                System.out.println(lineasSalida.get(0));
                System.out.println(lineasSalida.get(1));

                double personas = Integer.parseInt(lineasSalida.get(0).split(":")[1].trim());
                double total = Integer.parseInt(lineasSalida.get(1).split(":")[1].trim());

                double porcentaje = (personas * 100) / total;
                System.out.printf("Porcentaje: %.2f %%%n", porcentaje);
            }

        } catch (Exception e) {
            System.out.println("Error al lanzar los procesos hijos: " + e.getMessage());
            resultado = -1;
        }

        return resultado;
    }
}
