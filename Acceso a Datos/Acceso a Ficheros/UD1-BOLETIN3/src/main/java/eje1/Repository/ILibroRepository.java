package eje1.Repository;

import java.util.List;
import java.util.Optional;

import eje1.Entity.Libro;

public interface ILibroRepository {
	
	public List<Libro> listadoLibro();
	public Optional<Libro> buscarLibro(String isbm);
	
	public Libro annadirLibro(Libro libro);
	public Libro editarLibro(Libro libro);
	public Libro eliminarLibro(Long isbn);
	
	public void leerLibros();
	public void escribirLibros();
	
	public void escribirCSV(String pais);

}
