package service.impl;

import Entity.Cliente;
import repository.ClienteRepository;
import service.ClienteService;

import java.util.Set;

public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepo;

	public ClienteServiceImpl() {
		this.clienteRepo = new ClienteRepository();
	}

	@Override
	public Set<Cliente> obtenerClientes() {
		return clienteRepo.obtenerClientes();
	}

	@Override
	public Cliente crearCliente(Cliente cliente) {

		Cliente newCliente = new Cliente();
		newCliente.setEmail(cliente.getEmail());
		newCliente.setNombre(cliente.getNombre());
		newCliente.setTelefono(cliente.getTelefono());

		return clienteRepo.crearCliente(newCliente);
	}
}
