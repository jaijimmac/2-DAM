package service;

import Entity.Cliente;

import java.util.Set;

public interface ClienteService {

	public Set<Cliente> obtenerClientes();
	
	public Cliente crearCliente(Cliente cliente);
	
	
	

}
