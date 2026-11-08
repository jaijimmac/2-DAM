package Entity;

import java.time.LocalDate;
import java.util.List;

public class Cliente {

	private Long id;
	private String nombre;
	private String apellidos;
	private double email;
	private Direccion direccion;
	private List<String> telefonos;
	private boolean vip;
	
	
	public Cliente() {}
	
	public Cliente(Long id, String nombre, String apellidos, double email, Direccion direccion, List<String> telefonos,
			boolean vip) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.direccion = direccion;
		this.telefonos = telefonos;
		this.vip = vip;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public double getEmail() {
		return email;
	}

	public void setEmail(double email) {
		this.email = email;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public List<String> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<String> telefonos) {
		this.telefonos = telefonos;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Cliente:\n");
	    sb.append("[-] ID: ").append(id).append("\n");
	    sb.append("[-] Nombre: ").append(nombre).append("\n");
	    sb.append("[-] Apellidos: ").append(apellidos).append("\n");
	    sb.append("[-] Email ").append(email).append("\n");
	    sb.append("[-] Dirección: ").append(direccion).append("\n");
	    sb.append("[-] Teléfonos: ").append(telefonos).append("\n");
	    
	    if(vip) {
	    	sb.append("[-] Vip: Si").append("\n");
	    }else {
	    	sb.append("[-] Vip: No").append("\n");
	    }
	    
	    return sb.toString();
	}
	
	
}
