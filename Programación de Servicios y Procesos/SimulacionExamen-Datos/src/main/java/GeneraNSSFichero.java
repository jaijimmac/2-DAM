import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GeneraNSSFichero {
    public static void main(String[] args) {

        GeneraNSSFichero g = new GeneraNSSFichero();
        String rutaFichero = args[0];

        try {
            List<String> listadoNSS = g.leerNss(rutaFichero);
            g.escribirNss(listadoNSS);
            g.contadorLineas(rutaFichero, listadoNSS.size());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public List<String> leerNss(String rutaFichero){

        Scanner sc = null;
        List<String> listadoNss = new ArrayList<>();
        try {
            FileReader fr = new FileReader(rutaFichero);
            sc = new Scanner(fr);
            while(sc.hasNext()) {
                String valor = sc.nextLine();
                if(valor.startsWith("AN")){
                    //listadoNss.add(valor.replace(",", ""));
                    listadoNss.add(valor);
                }
            }
        } catch (Exception e) {

            System.out.println("Error al leer nss");
        } finally {
            sc.close();
        }

        return listadoNss;
    }

    public void escribirNss(List<String> listadoNss) {
        PrintWriter out = null;

        File file = new File("src/main/resources", "NSSs.txt");
        try {
            int contador = 1;
            FileWriter ficheroEscritura = new FileWriter(file, StandardCharsets.UTF_8);
            out = new PrintWriter(ficheroEscritura);
            for(String nss: listadoNss) {
                out.printf(Locale.ENGLISH, "%-5s %5s%n", contador, nss);
                out.println();
                contador++;
            }
        } catch (Exception e) {

            throw new RuntimeException(e);
        } finally {

            if (out != null)
                out.close();
        }
    }

    public void contadorLineas(String rutaFichero, int lineasSC){
        Scanner sc = null;
        List<String> listadoDNI = new ArrayList<>();
        int contadorLineas = 0;

        try {
            FileReader fr = new FileReader(rutaFichero);
            sc = new Scanner(fr);
            while(sc.hasNext()) {
                String valor = sc.nextLine();
                contadorLineas++;
            }
            System.out.println("Numeros de lineas SC: " + lineasSC );
            System.out.println("Numero de Lineas del Documento: " + contadorLineas);
        } catch (Exception e) {

            System.out.println("Error al leer nss");
        } finally {
            sc.close();
        }

    }
}
