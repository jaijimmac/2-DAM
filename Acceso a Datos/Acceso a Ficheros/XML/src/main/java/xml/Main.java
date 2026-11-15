package xml;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {
	private static final String RUTA_RESOURCES = "src/main/resources/";
	private static final Logger logger = LogManager.getLogger(Main.class);
	
    public static void main(String[] args) {
        
    	UtilesDomXML util = new UtilesDomXML(RUTA_RESOURCES);
    	Document doc = util.leerDocumetoDeXML("libros.xml");
    	
    	
    	//Element titulo = (Element) doc.getElementsByTagName("titulo").item(0);
    	//Libro libro = new Libro(titulo.getTextContent());
    	
    	Element raiz = doc.getDocumentElement();
    	List<Libro> libros = util.leerLibrosDeXML(raiz);
        
    	libros.forEach(libro -> {
    		logger.info(libro);
    	});
    }

}
