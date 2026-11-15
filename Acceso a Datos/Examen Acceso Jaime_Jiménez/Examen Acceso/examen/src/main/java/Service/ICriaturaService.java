package Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import Entity.Criatura;
import Entity.TipoCriatura;

public interface ICriaturaService {
	
	public String leerArchivo();
	
	public Collection<Criatura> findAll();
	public Optional<Criatura> findById(String nombre);
	public Criatura addCriatura(Criatura criatura);
	public List<Criatura> obtenerCriaturaDeTipo(TipoCriatura tipo);
	
	public void exportarJSON(Collection<Criatura> criaturas, String nombreFichero);
}
