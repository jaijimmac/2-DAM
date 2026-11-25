package Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import Entity.Cliente;
import Entity.Reserva;
import Entity.TipoHabitacion;

public interface IClienteRepository {
	
	 Cliente addCliente(Cliente c, List<Reserva> reservas);
	 List<Cliente> ordenarPorReservasYNombre();
	 double importeTotal(Cliente c);
	 Optional<Cliente> clienteMayorGasto();
	 List<Cliente> clientesConEntradaEnMes(int mes, int anio);
	 Map<TipoHabitacion, Long> contarReservasPorTipo();
}
