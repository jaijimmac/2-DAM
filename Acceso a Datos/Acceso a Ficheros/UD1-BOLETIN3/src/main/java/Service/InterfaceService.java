package Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import Entity.Pais;

public interface InterfaceService {

	public List<Pais> leerPaises() throws FileNotFoundException;
	public void escribirPaises();
	
	public List<Pais> listadoPaises();
	public Optional<Pais> obtenerPais(String nombre);
	public Pais annadirPais(Pais pais);
	public Pais modificarPais(Pais pais);
	public void eliminarPais(String nombre);
}
