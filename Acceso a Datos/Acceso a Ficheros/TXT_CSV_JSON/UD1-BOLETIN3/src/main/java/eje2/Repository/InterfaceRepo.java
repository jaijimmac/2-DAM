package eje2.Repository;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import eje2.Entity.Pais;

public interface InterfaceRepo {

	public List<Pais> leerPaises() throws FileNotFoundException;
	public void escribirPaises();
	
	public List<Pais> listadoPaises() throws FileNotFoundException;
	public Optional<Pais> obtenerPais(String nombre);
	public Pais annadirPais(Pais pais);
	public Pais modificarPais(Pais pais);
	public void eliminarPais(String nombre);
	
	public Pais annadirIdioma(String nombrePais,String idioma);
	public void calcularPaises(String nombre);
	
}
