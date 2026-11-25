package Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import Entity.Cliente;
import Entity.TipoHabitacion;

public interface IClienteRepository {
	
	 List<Cliente> ordenarPorReservasYNombre();
	 double importeTotal(Cliente c);
	 Optional<Cliente> clienteMayorGasto();
	 List<Cliente> clientesConEntradaEnMes(int mes, int anio);
	 Map<TipoHabitacion, Long> contarReservasPorTipo();
}
