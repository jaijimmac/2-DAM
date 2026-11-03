import com.sun.tools.javac.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//public class Pruebas  {
//    static void main(String[] args){
//        String[] comando = {"py", "src/main/resources/proceso.py"};
//        ProcessBuilder pb = new ProcessBuilder(comando);
//        pb.inheritIO();
//        try {
//            Process p = pb.start();
//
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
//}

public class Pruebas {
    private final static String directorioGenerarClasses = "target\\classes";
    private final static String rutaSourceJava = "src\\main\\java\\Procesos\\ProcesoHijo.java";
    private final static String rutaClase = "Procesos.ProcesoHijo";


    private static final Logger logger = LogManager.getLogger(Pruebas.class);

    static void main(String[] args) {
        Pruebas p = new Pruebas();
        int codigo = p.lanzaCompilador();
        String a1 = "Este es el argumento n1";
        String a2 = "Este es el argumento n2";
        int comando = p.ejecutaClase(rutaClase, a1, a2);
    }
    public int lanzaCompilador() {
        ProcessBuilder pb;
        int exit = 0;
        try {
            String[] comando = { "javac", "-d", directorioGenerarClasses, rutaSourceJava};
            pb = new ProcessBuilder(comando);
            pb.inheritIO();
            Process proceso = pb.start();
            exit = proceso.waitFor();
            logger.debug("javac terminó con código {}", exit);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return exit;
    }

    public int ejecutaClase (String clase, String a1, String a2) {
        int resultado = -1;
        String claseCompleta =  clase;
        try {
            String[] comando = {"java", "-cp",directorioGenerarClasses, claseCompleta, a1, a2 };
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);
            pb.inheritIO();
            Process proceso = pb.start();
            int exit = proceso.waitFor();
            System.out.println(exit);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return resultado;
    }
}







