package repository;

import java.util.*;
import java.util.stream.Stream;

import ENUM.Estado;
import Entity.Cliente;
import Entity.Pedido;
import service.PedidoService;

public class PedidoRepository {
	private List<Pedido> listaPedidos;

	
	public PedidoRepository() {
		super();
		this.listaPedidos = new ArrayList<>();
	}

	public List<Pedido> obtenerPedidos() {
		return this.listaPedidos;
	}

	public Pedido obtenerPedido(Long idPedido) {
		return this.listaPedidos.stream()
				.filter(p -> Objects.equals(p.getId(), idPedido))
				.findFirst()
				.orElse(null);
	}


	public Pedido crearPedido(Pedido pedido) {
		this.listaPedidos.add(pedido);
		return pedido;
	}
	
	public Pedido editarEstado(Pedido updatePedido, Estado estado) {
		updatePedido.setEstado(estado);
		return updatePedido;
	}
	
	public void eliminarPedido(Long idPedido) {
		Pedido pedido = obtenerPedido(idPedido);
		this.listaPedidos.remove(pedido);
		}
	}
