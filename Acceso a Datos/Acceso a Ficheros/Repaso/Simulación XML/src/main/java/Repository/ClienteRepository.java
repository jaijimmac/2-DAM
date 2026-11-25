package Repository;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import Entity.Cliente;
import Entity.TipoHabitacion;

public class ClienteRepository implements IClienteRepository{
	
	private File fichero;
	private Set<Cliente> listadoClientes;
	
	public ClienteRepository(String ruta) {
		if(ruta.isEmpty()) {
			throw new IllegalArgumentException("La ruta no puede ser null.");
		}
		
		this.fichero = new File(ruta);
		this.listadoClientes = new TreeSet<Cliente>();
	}

	@Override
	public List<Cliente> ordenarPorReservasYNombre() {
		return null;
	}

	@Override
	public double importeTotal(Cliente c) {
		return 0;
	}

	@Override
	public Optional<Cliente> clienteMayorGasto() {
		return Optional.empty();
	}

	@Override
	public List<Cliente> clientesConEntradaEnMes(int mes, int anio) {
		return null;
	}

	@Override
	public Map<TipoHabitacion, Long> contarReservasPorTipo() {
		return null;
	}

}
