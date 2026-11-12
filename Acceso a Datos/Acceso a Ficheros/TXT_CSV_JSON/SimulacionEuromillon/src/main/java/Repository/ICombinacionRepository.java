package Repository;

import java.util.List;

import Entity.Combinaciones;

public interface ICombinacionRepository {
	
	public void leerCSV();
	public List<Combinaciones> listar();

	public String calcularNumeroEstrellas();
	public String numeroCombinaciones();
	public String combinacionFrecuente();
}
