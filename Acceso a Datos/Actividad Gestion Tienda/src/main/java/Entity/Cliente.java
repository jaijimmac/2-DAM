package Entity;

import java.util.Objects;

public class Cliente {

	private String email;
	private String nombre;
	private String telefono;

	public Cliente() {}

	public Cliente(String email, String nombre, String telefono) {
		this.email = email;
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public String getEmail() {
		return this.email;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getTelefono() {
		return this.telefono;
	}
	
	public String setEmail(String email) {
		return this.email = email;
	}
	
	public String setNombre(String nombre) {
		return this.nombre = nombre;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(email, other.email) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(telefono, other.telefono);
	}

	@Override
	public String toString() {
		return "Cliente [email=" + email + ", nombre=" + nombre + ", telefono=" + telefono + "]";
	}
	
	
	
	
}
