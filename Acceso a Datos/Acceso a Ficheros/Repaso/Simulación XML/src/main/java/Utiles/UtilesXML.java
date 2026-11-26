package Utiles;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Entity.Cliente;
import Entity.Reserva;
import Entity.TipoHabitacion;
import Exception.DatosInvalidosException;


public class UtilesXML {

	private static final Logger LOGGER = LogManager.getLogger(UtilesXML.class);
	
	public Set<Cliente> leerClientes(File fichero){
		Set<Cliente> listadoClientes = new TreeSet<Cliente>();
		int errores = 0;
		
		if (fichero == null) { 
            throw new DatosInvalidosException("El fichero no puede ser null.");
        }
		if (!fichero.exists() || fichero.length() == 0) {
			throw new DatosInvalidosException("El fichero está vacío o no contiene clientes.");
        }
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fichero);
            doc.getDocumentElement().normalize();
            
            /* ejemplo para no sobreescribir e pegar
             *   // 2. Obtener el elemento raíz (<usuarios>)
            Element raiz = doc.getDocumentElement();

            // 3. Crear el nuevo nodo (<usuario>) y sus hijos
            Element nuevoUsuario = doc.createElement("usuario");
            
            Element nombre = doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode("Carlos"));*/
            
            NodeList clientes = doc.getElementsByTagName("cliente");
            Set<Long> idsLeidos = new HashSet<>();
            
            for(int i = 0; i < clientes.getLength(); i++) {
            	Element elementoCliente = (Element) clientes.item(i); 
            	String idTexto = elementoCliente.getAttribute("id");
            	
            	try {
            		
            		Cliente cliente = leerCliente(elementoCliente);
            		validarCliente(cliente);
            		
            		if (!idsLeidos.add(cliente.getId())) {
                        throw new DatosInvalidosException("el id " + cliente.getId() + " está repetido.");
                    }
            		listadoClientes.add(cliente);
            		
            	} catch (DatosInvalidosException | NumberFormatException | DateTimeParseException  e) {
            		errores++;
            		LOGGER.error("El cliente nº " + (i + 1)+ " (id=" + idTexto + ") descartado: " + e.getMessage());
            	}
            }
            
		} catch (ParserConfigurationException e) {
            throw new DatosInvalidosException("Error al configurar el lector XML: " + e.getMessage());
        } catch (SAXException e) {
            throw new DatosInvalidosException("El fichero XML está mal formado: " + e.getMessage());
        } catch (IOException e) {
            throw new DatosInvalidosException("Error al leer el fichero XML: " + e.getMessage());
        }
		
		
		LOGGER.info("Clientes cargados: " + listadoClientes.size() + ". Clientes descartados: " + errores);
		return listadoClientes;
	}
	
	public void escribirClientes(Set<Cliente> listadoClientes, String ruta) {
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);
            
            Element raiz = doc.createElement("hotel");
            doc.appendChild(raiz);
            
            for(Cliente c : listadoClientes) {
            	raiz.appendChild(crearCliente(doc, c));
            }
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            transformer.transform(new DOMSource(doc), new StreamResult(ruta));
			
		} catch (ParserConfigurationException e) {
            throw new DatosInvalidosException("Error al crear el documento XML: " + e.getMessage());
        } catch (TransformerException e) {
            throw new DatosInvalidosException("Error al escribir el fichero XML: " + e.getMessage());
        }
    }
	
	
	private Cliente leerCliente(Element e) {
		Cliente cliente = new Cliente(
				getTexto(e, "nombreApellidos"), 
				Integer.parseInt(getTexto(e, "edad")), 
				getTexto(e, "email"));
		
		cliente.setId(Long.parseLong(e.getAttribute("id").trim()));
		
		NodeList listadoReservas = e.getElementsByTagName("reserva");
		for(int i = 0; i<listadoReservas.getLength(); i++) {
			Element reservaElemento = (Element) listadoReservas.item(i);
			
			 String tipoTexto = reservaElemento.getAttribute("tipo");
			TipoHabitacion tipo;
			
			try {
			    tipo = TipoHabitacion.valueOf(tipoTexto.toUpperCase());
			} catch (IllegalArgumentException | NullPointerException ex) {
			    throw new DatosInvalidosException("El tipo de habitación '" + tipoTexto + "' no es válido");
			}

			Reserva reserva = new Reserva(tipo,
			        LocalDate.parse(getTexto(reservaElemento, "fechaEntrada")),
			        Integer.parseInt(getTexto(reservaElemento, "noches")),
			        Double.parseDouble(getTexto(reservaElemento, "precioNoche")));
			
			
			reserva.setId((reservaElemento.getAttribute("id").trim()));
			
			validarReserva(reserva);
			
			cliente.addReserva(reserva);
		}
		
		return cliente;
	}
	
	private String getTexto(Element padre, String etiqueta) {
        NodeList nodos = padre.getElementsByTagName(etiqueta);
        if (nodos.getLength() == 0) {
            return null;
        }
        String texto = nodos.item(0).getTextContent().trim();
        return texto.isEmpty() ? null : texto;
    }
	
	private Element crearCliente(Document doc, Cliente c) {
        Element cliente = doc.createElement("cliente");
        cliente.setAttribute("id", String.valueOf(c.getId()));

        crearHijo(doc, cliente, "nombreApellidos", c.getNombreApellidos());
        crearHijo(doc, cliente, "edad", String.valueOf(c.getEdad()));
        crearHijo(doc, cliente, "email", c.getEmail());

        Element reservas = doc.createElement("reservas");
        for (Reserva r : c.getReservas()) {
            Element reserva = doc.createElement("reserva");
            reserva.setAttribute("id", r.getId() != null ? r.getId() : "");

            crearHijo(doc, reserva, "fechaEntrada", String.valueOf(r.getFechaEntrada()));
            crearHijo(doc, reserva, "noches",  String.format(Locale.US, "%.2f", r.getPrecioNoche()));
            crearHijo(doc, reserva, "precioNoche", String.valueOf(r.getPrecioNoche()));

            reservas.appendChild(reserva);
        }
        cliente.appendChild(reservas);

        return cliente;
    }

    private void crearHijo(Document doc, Element padre, String etiqueta, String valor) {
        Element hijo = doc.createElement(etiqueta);
        hijo.setTextContent(valor != null ? valor : "");
        padre.appendChild(hijo);
    }
	
	private void validarCliente(Cliente c) {
		if(c.getNombreApellidos() == null || c.getNombreApellidos().isBlank()) {
			throw new DatosInvalidosException("El nombre no puede ser null o estar vacío.");
		}
		
		if (c.getEdad() < 18 || c.getEdad() > 99) {
			throw new DatosInvalidosException("La edad tiene que estar entre 18 y 99 años.");
		}
		
		if (c.getEmail() == null || !c.getEmail().contains("@")) {
			throw new DatosInvalidosException("El email debe de contener un @");
		}
		
	}
	
	private void validarReserva(Reserva r) {
		if(r.getFechaEntrada() == null) {
			throw new DatosInvalidosException("La fecha de entrada no puede ser null");
		}
		
		if(r.getNoches() < 1 || r.getNoches() > 30) {
			throw new DatosInvalidosException("El numero de noches debe estar entre 1 y 30");
		}
		
		if (r.getPrecioNoche() <= 0) { 
	        throw new DatosInvalidosException("El precio por noche debe ser mayor que 0");
	    }
		
		TipoHabitacion tipo = r.getTipoHabitacion();
		boolean valido = false;

		for (TipoHabitacion t : TipoHabitacion.values()) {
		    if (t == tipo) {
		        valido = true;
		    }
		}

		if (!valido) {
		    throw new DatosInvalidosException("El tipo de habitación no es válido");
		}
		
	}

	


}

