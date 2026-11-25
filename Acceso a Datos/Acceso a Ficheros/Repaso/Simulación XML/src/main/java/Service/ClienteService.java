package Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.ls.LSResourceResolver;

import Entity.Cliente;
import Entity.Reserva;
import Entity.TipoHabitacion;
import Exception.DatosInvalidosException;
import Repository.ClienteRepository;
import Utiles.UtilesXML;

public class ClienteService implements IClienteService{
	
	private static final Logger LOGGER = LogManager.getLogger(UtilesXML.class);
	private ClienteRepository repo;
	
	public ClienteService(String ruta) {
	
		this.repo = new ClienteRepository(ruta);
	}
	
	@Override
	public Cliente addCliente(Cliente c, List<Reserva> reservas) {
		try {
			repo.addCliente(c, reservas);
			LOGGER.info("Cliente " + c.getNombreApellidos() + " añadido con exito");
			
		} catch (DatosInvalidosException e) {
			LOGGER.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public List<Cliente> ordenarPorReservasYNombre() {
		try {
			return repo.ordenarPorReservasYNombre();
			
		} catch(DatosInvalidosException e) {
			LOGGER.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public double importeTotal(Cliente c) {
		try {
			return repo.importeTotal(c);
			
		} catch(DatosInvalidosException e) {
			LOGGER.error(e.getMessage());
		}
		
		return 0;
	}

	@Override
	public Optional<Cliente> clienteMayorGasto() {
		try {
			return repo.clienteMayorGasto();
			
		} catch(DatosInvalidosException e) {
			LOGGER.error(e.getMessage());
		}
		
		return null;
	}

	@Override
	public List<Cliente> clientesConEntradaEnMes(int mes, int anio) {
		try {
			return repo.clientesConEntradaEnMes(mes, anio);
		
		} catch(DatosInvalidosException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Map<TipoHabitacion, Long> contarReservasPorTipo() {
		try {
			return repo.contarReservasPorTipo();
		
		} catch(DatosInvalidosException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	

}
