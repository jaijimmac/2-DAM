package Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import Entity.Criatura;
import Entity.TipoCriatura;
import Exception.CriaturasFantasticasException;
import Repository.CriaturaRepository;

public class CriaturaService implements ICriaturaService {
	
	private CriaturaRepository repo;
	
	public String leerArchivo() {
		return repo.leerArchivo();
	}

	
	public CriaturaService(String ruta) {
		this.repo = new CriaturaRepository(ruta);
	}

	@Override
	public Collection<Criatura> findAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Criatura> findById(String nombre) {
	    if (nombre == null || nombre.isBlank()) {
	        new CriaturasFantasticasException("El nombre no puede ser nulo o vacío");
	        return Optional.empty();
	    }
	    
	    return repo.findById(nombre.trim());
	}

	@Override
	public Criatura addCriatura(Criatura criatura) {
	    if (criatura == null || criatura.getNombre() == null || criatura.getNombre().isBlank()) {
	        new CriaturasFantasticasException("La criatura no puede ser nula ni tener el nombre vacío");
	        return null;
	    }
	    
	    return repo.addCriatura(criatura);
	}

	@Override
	public List<Criatura> obtenerCriaturaDeTipo(TipoCriatura tipo) {
	    if (tipo == null) {
	        new CriaturasFantasticasException("El tipo no puede ser nulo");
	        return List.of();
	    }
	    
	    return repo.obtenerCriaturaDeTipo(tipo);
	}

	@Override
	public void exportarJSON(Collection<Criatura> criaturas, String nombreFichero) {
	    if (nombreFichero == null || nombreFichero.isBlank()) {
	        new CriaturasFantasticasException("El nombre del fichero no puede ser nulo ni estar vacío");
	        return;
	    }
	    
	    if (criaturas == null || criaturas.isEmpty()) {
	        new CriaturasFantasticasException("No hay criaturas para exportar");
	        return;
	    }

	    repo.exportarJSON(criaturas, nombreFichero.trim());
	}

}
