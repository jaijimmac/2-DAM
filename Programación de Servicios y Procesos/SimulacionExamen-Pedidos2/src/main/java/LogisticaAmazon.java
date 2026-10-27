import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class LogisticaAmazon {

    private final static String directorioGenerarClasses = "target\\classes";
    private final static String rutaSourceJava = "src\\main\\java\\ExtraerProvincia.java";
    private final static String rutaClase = "ExtraerProvincia";
    private static final String rutaFichero = "src/main/resources/pedidos.txt";

    static void main(String[] args) {

        LogisticaAmazon logistica = new LogisticaAmazon();

        String[] provincias = {"Sevilla", "Córdoba", "Huelva", "Cádiz", "Jaén", "Almería","Granada", "Málaga" };

        try {

            logistica.lanzarCompilador();
            Map<String, Integer> resultados = logistica.ejecuta(rutaClase, provincias, rutaFichero);

            int total = 0;
            for (Map.Entry<String, Integer> e : resultados.entrySet()) {
                System.out.println(e.getKey());
                total += e.getValue();
            }

            System.out.println("Nº total de Pedidos : " + total);

        } catch (Exception e) {

            System.out.println("Error en lanzar compilador " + e.getMessage());
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


    public Map<String, Integer> ejecuta(String clase, String[] provincias, String ruta) {
        String claseCompleta = clase;

        List<ProcessBuilder> listaPb = new ArrayList<ProcessBuilder>();
        List<Process> listaProcesos = new ArrayList<>();

        Map<String, Integer> resultados = new HashMap<String, Integer>();

        try {
            for(String p: provincias){
                String[] comando = {"java", "-cp", directorioGenerarClasses, claseCompleta, p, ruta};
                ProcessBuilder pb = new ProcessBuilder(comando);
                //pb.inheritIO();
                listaPb.add(pb);
            }

            for (ProcessBuilder prb : listaPb) {
                listaProcesos.add(prb.start());
            }

            int i = 0;
            for (Process process : listaProcesos) {
                String provincia = provincias[i];
                i++;

                List<String> lineasSalida = new ArrayList<String>();
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String linea;

                while ((linea = br.readLine()) != null) {
                    lineasSalida.add(linea);
                }

                /*
                while ((linea = br.readLine()) != null) {
                    if(linea.contains(provincia)){
                        lineasSalida.add(linea);
                    }
                }
                */

                //Se controlan las excepciones, problema tildes consola

                StringBuilder errores = new StringBuilder();
                BufferedReader brErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                while ((linea = brErr.readLine()) != null) {
                    errores.append(linea).append(System.lineSeparator());
                }

                int exit = process.waitFor();

                if (exit != 0) {
                    throw new RuntimeException("El proceso hijo terminó con código " + exit + System.lineSeparator() + errores);
                }

                for(String li: lineasSalida){
                    resultados.put(li, Integer.parseInt(li.split(":")[1].trim()));
                }

            }

        } catch (Exception e) {
            System.out.println("Error al lanzar los procesos hijos: " + e.getMessage());
        }

        return resultados;
    }
}
