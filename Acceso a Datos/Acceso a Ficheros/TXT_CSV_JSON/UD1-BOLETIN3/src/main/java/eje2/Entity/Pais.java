package eje2.Entity;

import java.util.List;
import java.util.ArrayList;

public class Pais {
	
	String nombre;
	String continente;
	int poblacion;
	List<String> idioma;
	String moneda;
	
	
	public Pais(String nombre, String continente) {
		this.nombre = nombre;
		this.continente = continente;
	}
	
	public Pais(String nombre, String continente, int poblacion, List<String> idioma, String moneda) {
		super();
		this.nombre = nombre;
		this.continente = continente;
		this.poblacion = poblacion;
		this.idioma = idioma;
		this.moneda = moneda;
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContinente() {
		return continente;
	}
	public void setContinente(String continente) {
		this.continente = continente;
	}
	public int getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}
	
	
	public List<String> getIdioma() {
		return idioma;
	}

	public void setIdioma(List<String> idioma) {
		this.idioma = idioma;
	}

	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Pais:\n");
	    sb.append("[-] Nombre: ").append(nombre).append("\n");
	    sb.append("[-] Continente: ").append(continente).append("\n");
	    sb.append("[-] Población: ").append(poblacion).append("\n");
	    sb.append("[-] Idioma: ").append(idioma).append("\n");
	    sb.append("[-] Moneda: ").append(moneda);
	    return sb.toString();
	}


	
}
