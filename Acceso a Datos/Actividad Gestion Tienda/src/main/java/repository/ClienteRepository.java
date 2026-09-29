package repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entity.Cliente;

public class ClienteRepository {
	
	private List<Cliente> listaClientes;
	
	public ClienteRepository() {
		super();
		this.listaClientes = new ArrayList<>();
	}

	public List<Cliente> obtenerClientes(){
		return this.listaClientes;
	}

	public Cliente crearCliente(Cliente cliente) {
		this.listaClientes.add(cliente);
		return cliente;
	}
}
