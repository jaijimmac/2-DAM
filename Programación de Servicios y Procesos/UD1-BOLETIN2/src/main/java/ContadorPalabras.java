import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class ContadorPalabras {
    private static final String rutaFicheros = "src/main/resources";

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ContadorPalabras cp = new ContadorPalabras();


        String nombreFicheroEntrada = args[0];
        String palabra = args[1];
        String nombreFicheroResultado = args[2] + ".txt";


        int numPalabras = cp.contarPalabras(nombreFicheroEntrada, palabra);
        cp.escribirResultado(rutaFicheros,palabra, nombreFicheroResultado, numPalabras);
    }


    public int contarPalabras(String ruta, String palabra) throws FileNotFoundException, IOException {
        Scanner sc = null;
        int contador = 0;
        try{
            FileReader fr = new FileReader(ruta);
            sc = new Scanner(fr);

            while(sc.hasNext()){
                String valor = sc.next();
                if(valor.equalsIgnoreCase(palabra)){
                    contador++;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return contador;
    }

    public void escribirResultado(String ruta, String palabra, String nombreArchivo, int nPalabras){
        PrintWriter out = null;
        File dir = new File(ruta, "palabras");
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(ruta + "/palabras", nombreArchivo);
        try{

            FileWriter ficheroSalida = new FileWriter(file, StandardCharsets.UTF_8);
            out = new PrintWriter(ficheroSalida);
            out.printf(Locale.ENGLISH, "%-15s %5s%n", palabra, nPalabras);
            out.println();

        } catch (Exception e) {

            throw new RuntimeException(e);
        } finally {

            if (out != null)
                out.close();
        }
    }
}
