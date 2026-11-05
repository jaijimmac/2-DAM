package Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import Entity.Pais;
import Repository.PaisRepository;

public class PaisService implements InterfaceService{
	
	private PaisRepository repo;
	
	
	public PaisService(String ruta) {
		repo = new PaisRepository(ruta);
	}

	@Override
	public List<Pais> leerPaises() throws FileNotFoundException {
		return repo.leerPaises();
	}

	@Override
	public void escribirPaises() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pais> listadoPaises() {
		try {
			return repo.listadoPaises();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Optional<Pais> obtenerPais(String nombre) {
		return repo.obtenerPais(nombre);
	}

	@Override
	public Pais annadirPais(Pais pais) {
		return null;
	}

	@Override
	public Pais modificarPais(Pais pais) {
		
		return null;
	}

	@Override
	public void eliminarPais(String nombre) {
		
	}

}
