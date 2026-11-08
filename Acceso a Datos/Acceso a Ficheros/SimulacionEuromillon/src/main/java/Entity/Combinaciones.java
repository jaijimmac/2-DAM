package Entity;

import java.time.LocalDate;
import java.util.List;

public class Combinaciones {

	private LocalDate fecha;
	private String combinacionGanadora;
	private List<Integer> estrellas;
	
	
	public Combinaciones() {}
	
	public Combinaciones(LocalDate fecha, String combinacionGanadora, List<Integer> estrellas) {
		super();
		this.fecha = fecha;
		this.combinacionGanadora = combinacionGanadora;
		this.estrellas = estrellas;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getCombinacionGanadora() {
		return combinacionGanadora;
	}

	public void setCombinacionGanadora(String combinacionGanadora) {
		this.combinacionGanadora = combinacionGanadora;
	}

	public List<Integer> getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(List<Integer> estrellas) {
		this.estrellas = estrellas;
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Euromillón:\n");
	    sb.append("[-] Fecha: ").append(fecha).append("\n");
	    sb.append("[-] Combinación Ganadora: ").append(combinacionGanadora).append("\n");
	    sb.append("[-] Estrellas: ").append(estrellas).append("\n");
	    return sb.toString();
	}
	
	
	
}
