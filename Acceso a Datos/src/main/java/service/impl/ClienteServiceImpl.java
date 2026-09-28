package service.impl;

import Entity.Cliente;
import repository.ClienteRepository;
import service.ClienteService;

public class ClienteServiceImpl implements ClienteService {
	
	private ClienteRepository clienteRepo;

	@Override
	public Cliente crearCliente(Cliente cliente) {
		return clienteRepo.crearCliente(cliente);
	}

}
