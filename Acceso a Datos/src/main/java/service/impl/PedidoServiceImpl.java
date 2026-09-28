package service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ENUM.Estado;
import Entity.INotificacion;
import Entity.Pedido;
import repository.PedidoRepository;
import service.PedidoService;

public class PedidoServiceImpl implements PedidoService{
	private static final Logger logger = LogManager.getLogger(INotificacion.class);
	
	private PedidoRepository pedidoRepo;

	@Override
	public Pedido ocrearPedido(Pedido pedido) {
		
		return pedidoRepo.crearPedido(pedido);
	}

	@Override
	public Pedido editarEstado(Long idPedido, Estado estado) {
		
		return pedidoRepo.editarEstado(idPedido, estado);
	}

	@Override
	public void eliminarPedido(Long idPedido) {
		
		pedidoRepo.eliminarPedido(idPedido);
		
		logger.debug("Pedido Eliminado");
	}

}
