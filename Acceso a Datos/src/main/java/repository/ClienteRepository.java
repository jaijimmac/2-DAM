package repository;

import java.awt.List;
import java.util.HashSet;
import java.util.Set;

import Entity.Cliente;

public class ClienteRepository {
	
	private Set<Cliente> listaClientes;
	
	public ClienteRepository() {
		super();
		this.listaClientes = new HashSet<Cliente>();
	}

	public Cliente crearCliente(Cliente cliente) {
		
		Cliente newCliente = new Cliente(); 
		
		newCliente.setEmail(cliente.getEmail());
		newCliente.setNombre(cliente.getNombre());
		newCliente.setTelefono(cliente.getTelefono());
		
		this.listaClientes.add(newCliente);
		
		return newCliente;

	}
}
