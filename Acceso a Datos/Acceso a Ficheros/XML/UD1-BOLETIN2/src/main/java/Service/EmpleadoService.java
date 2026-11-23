package Service;

import java.util.List;
import java.util.Optional;

import Entity.Empleado;
import Repository.EmpleadoRepository;

public class EmpleadoService implements IEmpleadoService{
	
	private EmpleadoRepository repo;
	
	public EmpleadoService(String ruta) {
		if(ruta == null || ruta.isEmpty()){
			throw  new IllegalArgumentException("La ruta es nula o esta vacía");
	    }
		
		this.repo = new EmpleadoRepository(ruta);
	}

	@Override
	public List<Empleado> findAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Empleado> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public Empleado addEmpleado(Empleado empleado) {
	
		return null;
	}

	@Override
	public Empleado modiEmpleado(Empleado empleado) {
		
		return null;
	}

	@Override
	public void delEmpleado(Long id) {
		
		
	}

}
