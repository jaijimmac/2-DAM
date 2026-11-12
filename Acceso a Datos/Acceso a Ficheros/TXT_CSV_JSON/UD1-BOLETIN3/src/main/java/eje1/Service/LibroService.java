package eje1.Service;

import java.util.List;
import java.util.Optional;

import eje1.Entity.Libro;
import eje1.Repository.LibroRepository;

public class LibroService implements ILibrosService{
	
	private LibroRepository repo;
	
	public LibroService(String ruta) {
		this.repo = new LibroRepository(ruta);
	}

	@Override
	public List<Libro> listadoLibro() {
		return repo.listadoLibro();
	}

	@Override
	public Optional<Libro> buscarLibro(String isbn) {
		if(isbn == null) {
			throw new IllegalArgumentException("isbn no puede ser null.");
		}
		
		Optional<Libro> encontrado = repo.buscarLibro(isbn);
		
		if(!encontrado.isPresent()) throw new RuntimeException("Libro no encontrado");
		
		return encontrado;
	}

	@Override
	public Libro annadirLibro(Libro libro) {
		boolean erroneo = libro.getTitulo() == null || libro.getIsbn() == null || libro.getEditorial() == null;
		if(erroneo) throw new IllegalArgumentException("El nombre, isbn y editorial no pueden ser nulos.");
		
		return repo.annadirLibro(libro);
	}

	@Override
	public Libro editarLibro(Libro libro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Libro eliminarLibro(Long isbn) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void escribirCSV(String pais) {
		if(pais == null) {
			throw new IllegalArgumentException("pais no puede ser null.");
		}
		
		repo.escribirCSV(pais);
	}


}
