package xml;

public class Editorial {
	private String nombre;
	private int cif;
	
	public Editorial() {}
	
	public Editorial(String nombre, int cif) {
		super();
		this.nombre = nombre;
		this.cif = cif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCif() {
		return cif;
	}

	public void setCif(int cif) {
		this.cif = cif;
	}

	@Override
	public String toString() {
		return "Editorial [nombre=" + nombre + ", cif=" + cif + "]";
	}
	
	
	
}
