package service.impl;

import ENUM.TipoMensaje;
import Entity.inmpl.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ENUM.Estado;
import Entity.INotificacion;
import Entity.Pedido;
import repository.PedidoRepository;
import service.PedidoService;

import java.util.List;
import java.util.Set;

public class PedidoServiceImpl implements PedidoService{
	private static final Logger logger = LogManager.getLogger(INotificacion.class);
	
	private final PedidoRepository pedidoRepo;
	private final NotificacionServiceImpl notificacionService;

	private Long contadorId = 1L;

    public PedidoServiceImpl() {
        this.notificacionService = new NotificacionServiceImpl();
        this.pedidoRepo = new PedidoRepository();
    }

	@Override
	public List<Pedido> obtenerPedidos() {
		return pedidoRepo.obtenerPedidos();
	}

	@Override
	public Pedido obtenerPedido(Long idPedido) {
		return pedidoRepo.obtenerPedido(idPedido);
	}

	@Override
	public Pedido crearPedido(Pedido pedido, TipoMensaje tipo) {
		Pedido newPedido = new Pedido();
		newPedido.setId(contadorId);
		contadorId++;
		newPedido.setCliente(pedido.getCliente());
		newPedido.setEstado(Estado.PENDIENTE);
		newPedido.setImporte(pedido.getImporte());

		notificacionService.agregarNotificacines(pedido, tipo);

		return pedidoRepo.crearPedido(newPedido);
	}

	@Override
	public Pedido editarEstado(Long idPedido, Estado estado) {
		Pedido pedido = pedidoRepo.obtenerPedido(idPedido);

		pedidoRepo.editarEstado(pedido, estado);
		notificacionService.enviarNoti(idPedido, estado);
		return pedido;
	}


	@Override
	public void eliminarPedido(Long idPedido) {
		pedidoRepo.eliminarPedido(idPedido);
		logger.debug("Pedido Eliminado");
	}

}
