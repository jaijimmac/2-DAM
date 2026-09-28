package service;

import java.util.Set;

import ENUM.Estado;
import Entity.Pedido;

public interface PedidoService {

	public Pedido ocrearPedido(Pedido pedido);
	
	public Pedido editarEstado(Long idPedido, Estado estado);
	
	public void eliminarPedido(Long idPedido);
	
}
