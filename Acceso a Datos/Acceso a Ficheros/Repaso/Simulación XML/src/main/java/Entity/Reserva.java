package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class Reserva {
	
	private static int contador = 1;
	
	private String id;
	private TipoHabitacion tipoHabitacion;
	private LocalDate fechaEntrada;
	private int noches;
	private double precioNoche;
	
	public Reserva(TipoHabitacion tipoHabitacion, LocalDate fechaEntrada, int noches, double precioNoche) {
		this.id = String.format("R%03d", contador++);
		this.tipoHabitacion = tipoHabitacion;
		this.fechaEntrada = fechaEntrada;
		this.noches = noches;
		this.precioNoche = precioNoche;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public int getNoches() {
		return noches;
	}

	public void setNoches(int noches) {
		this.noches = noches;
	}

	public double getPrecioNoche() {
		return precioNoche;
	}

	public void setPrecioNoche(double precioNoche) {
		this.precioNoche = precioNoche;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(fechaEntrada, id, Integer.valueOf(noches), Double.valueOf(precioNoche), tipoHabitacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(fechaEntrada, other.fechaEntrada) && Objects.equals(id, other.id)
				&& noches == other.noches
				&& Double.doubleToLongBits(precioNoche) == Double.doubleToLongBits(other.precioNoche)
				&& tipoHabitacion == other.tipoHabitacion;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", tipoHabitacion=" + tipoHabitacion + ", fecha=" + fechaEntrada + ", noches=" + noches
				+ ", precioNoche=" + precioNoche + "]";
	}
	
	

}
