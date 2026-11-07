package eje2.Service;

import java.util.List;
import java.util.Optional;
import  eje2.Entity.Pais;

public interface InterfaceService {


	public List<Pais> listadoPaises();
	public Optional<Pais> obtenerPais(String nombre);
	public Pais annadirPais(Pais pais);
	public Pais modificarPais(Pais pais);
	public void eliminarPais(String nombre);
	
	public Pais annadirIdioma(String nombrePais,String idioma);
	public void calcularPaises(String nombre);
}