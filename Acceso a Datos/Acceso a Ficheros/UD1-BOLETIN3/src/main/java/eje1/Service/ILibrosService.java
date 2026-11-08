package eje1.Service;

import java.util.List;
import java.util.Optional;

import eje1.Entity.Libro;

public interface ILibrosService {

	public List<Libro> listadoLibro();
	public Optional<Libro> buscarLibro(String isbm);
	
	public Libro annadirLibro(Libro libro);
	public Libro editarLibro(Libro libro);
	public Libro eliminarLibro(Long isbn);
	
	public void escribirCSV(String pais);
	
}
