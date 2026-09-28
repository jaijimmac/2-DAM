package repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import ENUM.Estado;
import Entity.Cliente;
import Entity.Pedido;
import service.PedidoService;

public class PedidoRepository {
	private Set<Pedido> listaPedidos;
	
	public PedidoRepository() {
		super();
		this.listaPedidos = new HashSet<Pedido>();
	}
	
	public Pedido obtenerPedido(Long idPedido) {
		if(idPedido == null) {
			
		}
		
		return (Pedido) this.listaPedidos.stream().filter(p -> p.getId() == idPedido);
		
	}

	public Pedido crearPedido(Pedido pedido) {
		
		Pedido newPedido = new Pedido(); 
		
		newPedido.setCliente(pedido.getCliente());
		newPedido.setEstado(Estado.PENDIENTE);
		newPedido.setImporte(pedido.getImporte());
		
		this.listaPedidos.add(newPedido);
		return newPedido;

	}
	
	public Pedido editarEstado(Long idPedido, Estado estado) {
		
		Pedido updatePedido = obtenerPedido(idPedido);
		
		updatePedido.setEstado(estado);
			
		return updatePedido;
	}
	
	public void eliminarPedido(Long idPedido) {
			
		Pedido pedido = obtenerPedido(idPedido);
		this.listaPedidos.remove(pedido);
			
		}
	
	}
