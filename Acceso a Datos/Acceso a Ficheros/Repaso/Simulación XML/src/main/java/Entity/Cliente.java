package Entity;

import java.util.List;
import java.util.Objects;

public class Cliente {
	
	private Long id;
	private String nombreApellidos;
	private int edad;
	private String email;
	private List<Reserva> reservas;
	
	public Cliente(String nombreApellidos, int edad, String email) {
		super();
		this.nombreApellidos = nombreApellidos;
		this.edad = edad;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreApellidos() {
		return nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(edad), email, id, nombreApellidos, reservas);
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
		return edad == other.edad && Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(nombreApellidos, other.nombreApellidos) && Objects.equals(reservas, other.reservas);
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombreApellidos=" + nombreApellidos + ", edad=" + edad + ", email=" + email
				+ ", reservas=" + reservas + "]";
	}
	
	
	

}
