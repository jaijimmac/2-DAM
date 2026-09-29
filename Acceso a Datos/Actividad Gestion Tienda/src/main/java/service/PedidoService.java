package service;

import java.util.List;
import java.util.Set;

import ENUM.Estado;
import ENUM.TipoMensaje;
import Entity.Pedido;

public interface PedidoService {

	public List<Pedido> obtenerPedidos();

	public Pedido obtenerPedido (Long idPedido);

	public Pedido crearPedido(Pedido pedido, TipoMensaje tipo);
	
	public Pedido editarEstado(Long idPedido, Estado estado);
	
	public void eliminarPedido(Long idPedido);
	
}
