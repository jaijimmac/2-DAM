package Service;

import java.util.List;
import java.util.Optional;

import Entity.Empleado;

public interface IEmpleadoService {
	
	public List<Empleado> findAll();
	public Optional<Empleado> findById(Long id);
	
	public Empleado addEmpleado(Empleado empleado);
	public Empleado modiEmpleado(Empleado empleado);
	public void delEmpleado(Long id);

}
