package Entity;


public class Pais {
	
	String nombre;
	String continente;
	int poblacion;
	String idioma;
	String moneda;
	
	
	public Pais() {
	}
	
	public Pais(String nombre, String continente, int poblacion, String idioma, String moneda) {
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
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
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
		return "Pais [nombre=" + nombre + ", continente=" + continente + ", poblacion=" + poblacion + ", idioma="
				+ idioma + ", moneda=" + moneda + "]";
	}
	
	
	
	
	

}
