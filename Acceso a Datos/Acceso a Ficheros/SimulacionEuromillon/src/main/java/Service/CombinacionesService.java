package Service;

import java.util.List;

import Entity.Combinaciones;
import Repository.HistóricoCombinaciones;

public class CombinacionesService implements ICombinacionService{
	
	private HistóricoCombinaciones repo;
	
	public CombinacionesService(String ruta) {
		repo = new HistóricoCombinaciones(ruta);
	}

	@Override
	public void leerCSV() {
		repo.leerCSV();
		
	}
	
	@Override
	public List<Combinaciones> listar() {
		return repo.listar();
	}


	@Override
	public String calcularNumeroEestrellas() {
		return repo.calcularNumeroEstrellas();
	}

	@Override
	public String numeroCombinaciones() {
		return repo.numeroCombinaciones();
	}

	@Override
	public String combinacionFrecuente() {
		return repo.combinacionFrecuente();
	}

	
}
