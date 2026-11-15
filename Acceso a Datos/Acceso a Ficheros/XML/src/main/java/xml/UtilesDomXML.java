package xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UtilesDomXML {
	
	private String ruta;
	
	public UtilesDomXML(String ruta) {
		this.ruta = ruta;
	}
	
	private static final Logger logger = LogManager.getLogger(UtilesDomXML.class);
	
    public Document leerDocumetoDeXML(String nombreFichero) {
    	DocumentBuilderFactory factory =   DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder;
    	Document doc = null;
    	
    	try {
    		 builder = factory.newDocumentBuilder();
             doc = builder.parse(new File(ruta, nombreFichero));
             
  
    	} catch (SAXException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (ParserConfigurationException e) {
        	e.printStackTrace();
        }
    	
    	return doc;
    }
    
    public Editorial fromElementToEditorial(Element element) {
    	Editorial e = new Editorial();
    	
    	Element nombre = (Element) element.getElementsByTagName("nombre").item(0);
    	Element cif = (Element) element.getElementsByTagName("cif").item(0);
    	
    	e.setNombre(nombre.getTextContent());
    	e.setCif(Integer.parseInt(cif.getTextContent()));
    	
    	return e;
    }
    
    public Libro fromElementToLibro(Element element) {
    	
    	Libro nuevo = new Libro();
    
    	String id = element.getAttribute("id");
		String categoria = element.getAttribute("categoria");
		Element titulo = (Element) element.getElementsByTagName("titulo").item(0);
		Element autor = (Element) element.getElementsByTagName("autor").item(0);
		Element editorial = (Element) element.getElementsByTagName("editorial").item(0);
		
		nuevo.setId(Long.parseLong(id));
		nuevo.setCategoria(categoria);
		nuevo.setTitulo(titulo.getTextContent());
		nuevo.setAutor(autor.getTextContent());
		nuevo.setEditorial(fromElementToEditorial(editorial));
		
		return nuevo;
    }	
    
    public List<Libro> leerLibrosDeXML(Element raiz){
    	List<Libro> listadoLibros = new ArrayList<Libro>();
    	NodeList libros = raiz.getElementsByTagName("libro");
    	
    	for(int i = 0; i<libros.getLength(); i++) {
    		Libro libro = fromElementToLibro((Element) libros.item(i));
    		listadoLibros.add(libro);
    	}
    	
    	return listadoLibros;
    }
}
