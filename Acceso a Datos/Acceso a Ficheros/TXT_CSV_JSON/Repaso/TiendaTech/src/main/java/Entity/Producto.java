package Entity;

import java.util.List;

public class Producto {

	private Long id;
	private String nombre;
	private String categoria;
	private double precio;
	private int stock;
	private List<String> etiquetas;
	
	public Producto(Long id, String nombre, String categoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
	}


	public Producto(Long id, String nombre, String categoria, double precio, int stock, List<String> etiquetas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
		this.etiquetas = etiquetas;
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public List<String> getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(List<String> etiquetas) {
		this.etiquetas = etiquetas;
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Producto:\n");
	    sb.append("[-] ID: ").append(id).append("\n");
	    sb.append("[-] Nombre: ").append(nombre).append("\n");
	    sb.append("[-] Categoria: ").append(categoria).append("\n");
	    sb.append("[-] Precio ").append(precio).append("\n");
	    sb.append("[-] Stock: ").append(stock).append("\n");
	    sb.append("[-] Etiquetas: ").append(etiquetas).append("\n");
	    return sb.toString();
	}
	
}
