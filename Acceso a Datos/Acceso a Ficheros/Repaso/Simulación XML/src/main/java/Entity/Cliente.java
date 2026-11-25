package Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Exception.DatosInvalidosException;

public class Cliente implements Comparable<Cliente>{
	
	private static long contador = 1;
	
	private Long id;
	private String nombreApellidos;
	private int edad;
	private String email;
	private List<Reserva> reservas;
	
	public Cliente(String nombreApellidos, int edad, String email) {
		this.id = contador++;
		this.nombreApellidos = nombreApellidos;
		this.edad = edad;
		this.email = email;
		this.reservas = new ArrayList<Reserva>();
	}
	
	public void addReserva(Reserva nueva) {
		
		if(nueva.getId() == null || nueva.getId().isBlank()) {
			throw new DatosInvalidosException("El id de la reserva no puede ser null o estar en blanco");
		}
		
	    if (nueva.getTipoHabitacion() == null) {
	        throw new DatosInvalidosException("El tipo de habitación no es válido.");
	    }
	    if (nueva.getFechaEntrada() == null) {
	        throw new DatosInvalidosException("La fecha de entrada no puede ser null.");
	    }
	    if (nueva.getNoches() < 1 || nueva.getNoches() > 30) {
	        throw new DatosInvalidosException("El número de noches debe estar entre 1 y 30.");
	    }
	    if (nueva.getPrecioNoche() <= 0) {
	        throw new DatosInvalidosException("El precio por noche debe ser mayor que 0.");
	    }

	    LocalDate inicioNueva = nueva.getFechaEntrada();
	    LocalDate finNueva = inicioNueva.plusDays(nueva.getNoches());

	    for (Reserva r : reservas) {
	        LocalDate inicio = r.getFechaEntrada();
	        LocalDate fin = inicio.plusDays(r.getNoches());

	        if (inicioNueva.isBefore(fin) && inicio.isBefore(finNueva)) {
	            throw new DatosInvalidosException("La reserva " + nueva.getId()
	                    + " se solapa con la reserva " + r.getId());
	        }
	    }

	    reservas.add(nueva);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
        this.id = id;
        if (id >= contador) {          
            contador = id + 1;
        }
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

	@Override
	public int compareTo(Cliente otro) {
	    int res = this.nombreApellidos.compareTo(otro.nombreApellidos);
	    if (res == 0) {
	        res = Long.compare(this.id, otro.id);
	    }
	    return res;
	}
	
	
	

}
