import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ExtraerProvincia {
    static void main(String[] args) {

        ExtraerProvincia ex = new ExtraerProvincia();
        String nombreProvincia = args[0];
        String rutaFichero = args[1];
        String extension = args[0] + ".txt";

        List<String> listaProvincia = ex.leerFicheero(rutaFichero, nombreProvincia);
        ex.escribirFichero(listaProvincia,extension);
    }

    public List<String> leerFicheero(String rutaFichero, String nombreProvincia) {
        Scanner sc = null;
        List<String> listado = new ArrayList<>();

        try {
            FileReader fr = new FileReader(rutaFichero);
            sc = new Scanner(fr);
            sc.nextLine();
            while(sc.hasNextLine()) {
                String valor = sc.nextLine();
                if(valor.contains(nombreProvincia)) {
                    listado.add(valor);
                }
            }

            System.out.println(nombreProvincia + ":" +  listado.size());

        } catch (Exception e) {

            System.err.println("Error al leer nss");
        } finally {

            sc.close();
        }

        return listado;
    }

    PrintWriter out = null;

    public void escribirFichero(List<String> listadoProvincias, String nombreProvincia) {
        File file = new File("src/main/resources", nombreProvincia);
        try {

            FileWriter ficheroEscritura = new FileWriter(file, StandardCharsets.UTF_8);
            out = new PrintWriter(ficheroEscritura);
            for(String linea: listadoProvincias) {
                out.printf(Locale.ENGLISH, linea);
                out.println();
            }
        } catch (Exception e) {

            throw new RuntimeException(e);
        } finally {

            if (out != null)
                out.close();
        }
    }
}
