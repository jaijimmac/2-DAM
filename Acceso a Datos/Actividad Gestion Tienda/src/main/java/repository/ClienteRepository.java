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

	public Set<Cliente> obtenerClientes(){
		return this.listaClientes;
	}

	public Cliente crearCliente(Cliente cliente) {
		this.listaClientes.add(cliente);
		return cliente;
	}
}
