 package Repository;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.Cliente;
import Entity.Reserva;
import Entity.TipoHabitacion;
import Exception.DatosInvalidosException;
import Utiles.UtilesXML;

public class ClienteRepository implements IClienteRepository{
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteRepository.class);

	
	private UtilesXML utiles;
	private File fichero;
	private Set<Cliente> listadoClientes;
	
	public ClienteRepository(String ruta) {
		if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta es nula o está vacía.");
        }
		
		this.utiles = new UtilesXML();
		this.fichero = new File(ruta);
		this.listadoClientes = new TreeSet<Cliente>(leerArchivo());
	}
	
	@Override
	public Cliente addCliente(Cliente c, List<Reserva> reservas) {
	   
	    for (Cliente existente : this.listadoClientes) {
	        if (existente.getId().equals(c.getId())) {
	            throw new DatosInvalidosException("Ya existe un cliente con id " + c.getId());
	        }
	    }

	    if (reservas != null) {
	        for (Reserva r : reservas) {
	            try {
	                c.addReserva(r);
	            } catch (DatosInvalidosException e) {
	                LOGGER.error("Reserva descartada: " + e.getMessage());
	            }
	        }
	    }

	    this.listadoClientes.add(c);
	    utiles.escribirClientes(this.listadoClientes, "src/main/resources/cliente_generado.xml");
	    return c;
	}

	@Override
	public List<Cliente> ordenarPorReservasYNombre() {
		return this.listadoClientes
		        .stream()
		        .sorted(Comparator.comparing((Cliente c) -> c.getReservas().size()).reversed()
		                .thenComparing(Cliente::getNombreApellidos))
		        .toList();
	}

	@Override
	public double importeTotal(Cliente c) {
		double importeTotal = 0.0;
		Optional<Cliente> cliente = this.listadoClientes
				.stream()
				.filter(clie -> clie.getId().equals(c.getId()))
				.findFirst();
		
		if(!cliente.isPresent()) {
			throw new DatosInvalidosException("No se encuentra el cliente");
		}
		
		for(Reserva r : cliente.get().getReservas()) {
			importeTotal += (r.getPrecioNoche()* r.getNoches());
		}
		
		return importeTotal;
	}

	@Override
	public Optional<Cliente> clienteMayorGasto() {
	    if (this.listadoClientes.isEmpty()) {
	        throw new DatosInvalidosException("El listado de clientes está vacío");
	    }

	    Cliente mayor = this.listadoClientes.iterator().next();

	    for (Cliente c : this.listadoClientes) {
	        if (importeTotal(c) > importeTotal(mayor)) {
	            mayor = c;
	        }
	    }

	    return Optional.of(mayor);
	}

	@Override
	public List<Cliente> clientesConEntradaEnMes(int mes, int anio) {
		
		if (mes < 1 || mes > 12) {
	        throw new DatosInvalidosException("El mes debe estar entre 1 y 12.");
	    }
		
		List<Cliente> listado = new ArrayList<Cliente>();
		
		for (Cliente c : this.listadoClientes) {
	        for (Reserva r : c.getReservas()) {
	            LocalDate entrada = r.getFechaEntrada();
	            if (entrada.getMonthValue() == mes && entrada.getYear() == anio) {
	                listado.add(c);
	                break; 
	            }
	        }
	    }
		
		return listado;
	}

	@Override
	public Map<TipoHabitacion, Long> contarReservasPorTipo() {
		Map<TipoHabitacion, Long> listado = new TreeMap<>();
		
		for (TipoHabitacion t : TipoHabitacion.values()) {
	        long contador = 0;                      
	        for (Cliente c : this.listadoClientes) {
	            for (Reserva r : c.getReservas()) {
	                if (r.getTipoHabitacion() == t) {
	                    contador++;
	                }
	            }
	        }
	        
	        listado.put(t, contador);                
	    }
		
		
		return listado;
	}
	
	public Set<Cliente> leerArchivo() {
		return this.utiles.leerClientes(this.fichero);
	}


	private void validarCliente(Cliente c) {
		if (c == null) {
	        throw new DatosInvalidosException("El cliente no puede ser null.");
	    }

	    for (Cliente existente : this.listadoClientes) {
	        if (existente.getId() == c.getId()) {
	            throw new DatosInvalidosException("Ya existe un cliente con id " + c.getId());
	        }
	    }
	    
		if(c.getNombreApellidos() == null || c.getNombreApellidos().isBlank()) {
			throw new DatosInvalidosException("El nombre no puede ser null o estar vacío.");
		}
		
		if (c.getEdad() < 18 || c.getEdad() > 99) {
			throw new DatosInvalidosException("La edad tiene que estar entre 18 y 99 años.");
		}
		
		if (c.getEmail() == null || !c.getEmail().contains("@")) {
			throw new DatosInvalidosException("El email debe de contener un @");
		}
		
	}
	
	private void validarReserva(Reserva r) {
		
		if(r.getId()== null) {
			throw new DatosInvalidosException("El id no puede ser null");
		}
		
		if(r.getFechaEntrada() == null) {
			throw new DatosInvalidosException("La fecha de entrada no puede ser null");
		}
		
		if(r.getNoches() < 1 || r.getNoches() > 30) {
			throw new DatosInvalidosException("El numero de noches debe estar entre 1 y 30");
		}
		
		if (r.getPrecioNoche() <= 0) { 
	        throw new DatosInvalidosException("El precio por noche debe ser mayor que 0");
	    }
		
		TipoHabitacion tipo = r.getTipoHabitacion();
		boolean valido = false;

		for (TipoHabitacion t : TipoHabitacion.values()) {
		    if (t == tipo) {
		        valido = true;
		    }
		}

		if (!valido) {
		    throw new DatosInvalidosException("El tipo de habitación no es válido");
		}
		
	}

}
