package eje2.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import  eje2.Entity.Pais;
import  eje2.Repository.PaisRepository;

public class PaisService implements InterfaceService{
	
	private PaisRepository repo;
	
	
	public PaisService(String ruta) {
		repo = new PaisRepository(ruta);
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
		if(nombre == null) {
			throw new IllegalArgumentException("El nombre es nulo.");
		}
		
		return repo.obtenerPais(nombre);
	}

	@Override
	public Pais annadirPais(Pais pais) {
		if(pais.getNombre() == null || pais.getContinente() == null) {
			throw new IllegalArgumentException("El nombre y el continente del país no pueden ser nulos.");
		} 
		
		return repo.annadirPais(pais);
	}

	@Override
	public Pais modificarPais(Pais pais) {
		if(pais.getNombre() == null || pais.getContinente() == null) {
			throw new IllegalArgumentException("El nombre y el continente del país no pueden ser nulos.");
		}
		
		return repo.modificarPais(pais);
	}

	@Override
	public void eliminarPais(String nombre) {
		if(nombre == null) {
			throw new IllegalArgumentException("El nombre es nulo.");
		}
		
		repo.eliminarPais(nombre);
	}
	
	
	@Override
	public Pais annadirIdioma(String nombrePais, String idioma) {
		
		if(nombrePais == null ||  idioma == null) {
			throw new IllegalArgumentException("El nombre o el idioma del país no pueden ser nulos.");
		} 
		
		return repo.annadirIdioma(nombrePais, idioma);
	}

	@Override
	public void calcularPaises(String nombre) {
		if(nombre == null) {
			throw new IllegalArgumentException("El continente no pueden ser nulos.");
		}
		
		repo.calcularPaises(nombre);
	}

	
	

}
