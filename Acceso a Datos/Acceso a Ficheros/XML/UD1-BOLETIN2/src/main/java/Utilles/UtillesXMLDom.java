package Utilles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Entity.Empleado;
import Entity.Puesto;
import Exceptions.EmpleadoException;

public class UtillesXMLDom {

    private static final Logger LOGGER = LogManager.getLogger(UtillesXMLDom.class);

    public List<Empleado> leerEmpleados(File fichero) {
        if (fichero == null) {
            throw new EmpleadoException("El fichero no puede ser null.");
        }

        List<Empleado> empleados = new ArrayList<>();
        if (!fichero.exists() || fichero.length() == 0) {
            return empleados;
        }

        int errores = 0;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fichero);
            doc.getDocumentElement().normalize();

            NodeList nodosEmpleado = doc.getElementsByTagName("empleado");
            Set<Long> idsLeidos = new HashSet<>();

            for (int i = 0; i < nodosEmpleado.getLength(); i++) {
                Element eEmpleado = (Element) nodosEmpleado.item(i);
                String idTexto = eEmpleado.getAttribute("id");

                try {
                    Empleado empleado = leerEmpleado(eEmpleado);
                    validarLeido(empleado);

                    if (!idsLeidos.add(empleado.getId())) {
                        throw new EmpleadoException("el id " + empleado.getId() + " está repetido.");
                    }
                    empleados.add(empleado);

                } catch (EmpleadoException ex) {
                    errores++;
                    LOGGER.warn("Empleado nº " + (i + 1) + " (id=" + idTexto + ") descartado: " + ex.getMessage());
                }
            }

        } catch (ParserConfigurationException e) {
            throw new EmpleadoException("Error al configurar el lector XML: " + e.getMessage());
        } catch (SAXException e) {
            throw new EmpleadoException("El fichero XML está mal formado: " + e.getMessage());
        } catch (IOException e) {
            throw new EmpleadoException("Error al leer el fichero XML: " + e.getMessage());
        }

        LOGGER.info("Lectura de " + fichero.getName() + ": " + empleados.size()
                + " empleados cargados, " + errores + " errores.");
        return empleados;
    }

    private Empleado leerEmpleado(Element eEmpleado) {
        Empleado empleado = new Empleado(
                getTexto(eEmpleado, "nombreApellidos"),
                getTexto(eEmpleado, "empresa"),
                null);
        empleado.setId(parseLong(eEmpleado.getAttribute("id"), "id"));
        empleado.setEdad(parseInt(getTexto(eEmpleado, "edad"), "edad"));

        NodeList nodosPuesto = eEmpleado.getElementsByTagName("puesto");
        for (int j = 0; j < nodosPuesto.getLength(); j++) {
            Element ePuesto = (Element) nodosPuesto.item(j);

            Puesto puesto = new Puesto(
                    getTexto(ePuesto, "nombrePuesto"),
                    getTexto(ePuesto, "departamento"),
                    parseInt(getTexto(ePuesto, "salario"), "salario"));

            String idPuesto = ePuesto.getAttribute("id").trim();
            puesto.setId(idPuesto.isEmpty() ? null : idPuesto);

            empleado.addPuesto(puesto);
        }
        return empleado;
    }

    private void validarLeido(Empleado e) {
        if (e.getId() == null) {
            throw new EmpleadoException("no tiene id.");
        }
        if (e.getNombreApellidos() == null || e.getNombreApellidos().isBlank) {
            throw new EmpleadoException("no tiene nombre y apellidos.");
        }
        if (e.getEdad() < 0) {
            throw new EmpleadoException("la edad no puede ser negativa (" + e.getEdad() + ").");
        }
        if (e.getEmpresa() == null) {
            throw new EmpleadoException("no tiene empresa.");
        }
        if (e.getPuestos().isEmpty()) {
            throw new EmpleadoException("no tiene ningún puesto.");
        }
        for (Puesto p : e.getPuestos()) {
            if (p.getNombrePuesto() == null) {
                throw new EmpleadoException("tiene un puesto sin nombre.");
            }
            if (p.getSalario() <= 0) {
                throw new EmpleadoException("el puesto '" + p.getNombrePuesto() + "' tiene un salario no válido ("
                        + p.getSalario() + ").");
            }
        }
    }

    private String getTexto(Element padre, String etiqueta) {
        NodeList nodos = padre.getElementsByTagName(etiqueta);
        if (nodos.getLength() == 0) {
            return null;
        }
        String texto = nodos.item(0).getTextContent().trim();
        return texto.isEmpty() ? null : texto;
    }

    private Long parseLong(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(valor.trim());
        } catch (NumberFormatException e) {
            throw new EmpleadoException("el campo '" + campo + "' no es un número válido ('" + valor + "').");
        }
    }

    private int parseInt(String valor, String campo) {
        if (valor == null) {
            return 0;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new EmpleadoException("el campo '" + campo + "' no es un número válido ('" + valor + "').");
        }
    }

    public void escribirEmpleados(List<Empleado> empleados, File fichero) {
        if (fichero == null) {
            throw new EmpleadoException("El fichero no puede ser null.");
        }
        if (empleados == null) {
            throw new EmpleadoException("La lista de empleados no puede ser null.");
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);

            Element raiz = doc.createElement("empleados");
            doc.appendChild(raiz);

            for (Empleado e : empleados) {
                raiz.appendChild(crearEmpleado(doc, e));
            }

            if (fichero.getParentFile() != null) {
                fichero.getParentFile().mkdirs();
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc), new StreamResult(fichero));

        } catch (ParserConfigurationException e) {
            throw new EmpleadoException("Error al crear el documento XML: " + e.getMessage());
        } catch (TransformerException e) {
            throw new EmpleadoException("Error al escribir el fichero XML: " + e.getMessage());
        }
    }

    private Element crearEmpleado(Document doc, Empleado e) {
        Element empleado = doc.createElement("empleado");
        empleado.setAttribute("id", String.valueOf(e.getId()));

        crearHijo(doc, empleado, "nombreApellidos", e.getNombreApellidos());
        crearHijo(doc, empleado, "edad", String.valueOf(e.getEdad()));
        crearHijo(doc, empleado, "empresa", e.getEmpresa());

        Element puestos = doc.createElement("puestos");
        for (Puesto p : e.getPuestos()) {
            Element puesto = doc.createElement("puesto");
            puesto.setAttribute("id", p.getId() != null ? p.getId() : "");

            crearHijo(doc, puesto, "nombrePuesto", p.getNombrePuesto());
            crearHijo(doc, puesto, "departamento", p.getDepartamento());
            crearHijo(doc, puesto, "salario", String.valueOf(p.getSalario()));

            puestos.appendChild(puesto);
        }
        empleado.appendChild(puestos);

        return empleado;
    }

    private void crearHijo(Document doc, Element padre, String etiqueta, String valor) {
        Element hijo = doc.createElement(etiqueta);
        hijo.setTextContent(valor != null ? valor : "");
        padre.appendChild(hijo);
    }
}