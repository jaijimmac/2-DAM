package Service;

import java.util.List;

import Entity.Combinaciones;

public interface ICombinacionService {
	
	public void leerCSV();
	public List<Combinaciones> listar();


	public String calcularNumeroEestrellas();
	public String numeroCombinaciones();
	public String combinacionFrecuente();
}
