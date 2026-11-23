package Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Entity.Empleado;

public class EmpleadoRepository implements IEmpleadoRepository{
	
	private List<Empleado> listadoEmpleados;
	private File fichero;
	
	public EmpleadoRepository(String ruta) {
		this.fichero = new File(ruta);
		this.listadoEmpleados = new ArrayList<>();
	}

	@Override
	public List<Empleado> findAll() {
		if(this.listadoEmpleados.isEmpty()) {
			return leerArchivo(); 
		} else {
			return this.listadoEmpleados;
		}
	}

	@Override
	public Optional<Empleado> findById(Long id) {
		return this.listadoEmpleados
				.stream()
				.filter(e -> e.getId().equals(id))
				.findFirst();
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

	private List<Empleado> leerArchivo(){
		
		return null;
	}
	

}
