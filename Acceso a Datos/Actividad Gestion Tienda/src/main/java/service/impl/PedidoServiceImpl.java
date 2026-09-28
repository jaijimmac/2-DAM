package service.impl;

import Entity.inmpl.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ENUM.Estado;
import Entity.INotificacion;
import Entity.Pedido;
import repository.PedidoRepository;
import service.PedidoService;

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
	public Set<Pedido> obtenerPedidos() {
		return pedidoRepo.obtenerPedidos();
	}

	@Override
	public Pedido crearPedido(Pedido pedido) {
		Pedido newPedido = new Pedido();
		newPedido.setId(contadorId);
		contadorId++;
		newPedido.setCliente(pedido.getCliente());
		newPedido.setEstado(Estado.PENDIENTE);
		newPedido.setImporte(pedido.getImporte());

		return pedidoRepo.crearPedido(newPedido);
	}

	@Override
	public Pedido editarEstado(Long idPedido, Estado estado) {
		Pedido pedido = pedidoRepo.obtenerPedido(idPedido);

		pedidoRepo.editarEstado(pedido, estado);

		if (estado == Estado.CONFIRMADO) {
			Email email = new Email("Pedido", "Pedido enviado", pedido);
			notificacionService.agregarNotificacines(email);
			notificacionService.enviarEmail(idPedido);
		}

		return pedido;
	}


	@Override
	public void eliminarPedido(Long idPedido) {
		pedidoRepo.eliminarPedido(idPedido);
		logger.debug("Pedido Eliminado");
	}

}
