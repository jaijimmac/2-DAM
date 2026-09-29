package service;

import Entity.Cliente;

import java.util.List;
import java.util.Set;

public interface ClienteService {

	public List<Cliente> obtenerClientes();
	
	public Cliente crearCliente(Cliente cliente);
	
	
	

}
