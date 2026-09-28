package service;

import java.util.Set;

import ENUM.Estado;
import Entity.Pedido;

public interface PedidoService {

	public Set<Pedido> obtenerPedidos();

	public Pedido crearPedido(Pedido pedido);
	
	public Pedido editarEstado(Long idPedido, Estado estado);
	
	public void eliminarPedido(Long idPedido);
	
}
