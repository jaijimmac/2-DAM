package repository;

import java.util.*;

import javax.management.Notification;

import ENUM.Estado;
import ENUM.TipoMensaje;
import Entity.Pedido;
import Entity.inmpl.SMS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.INotificacion;
import Entity.inmpl.Email;
public class NotificacionRepository {
	
	private static final Logger logger = LogManager.getLogger(NotificacionRepository.class);

	private List<INotificacion> listaNoti;



	public NotificacionRepository() {
		super();
		this.listaNoti = new ArrayList<>();
	}

	public INotificacion obtenerNotificacion(Long idPedido){
		return listaNoti
				.stream()
				.filter(n -> Objects.equals(n.getPedido().getId(), idPedido))
				.findFirst()
				.orElse(null);
	}

	public void agregarNotificacion(Pedido pedido, TipoMensaje tipo) {

		INotificacion newNotificacion;
		switch (tipo){
			case EMAIL:
				newNotificacion = new Email("PEDIDO", "PEDIDO "  + pedido.getEstado(), pedido);

			case SMS:
				newNotificacion = new SMS(pedido.getCliente().getTelefono(), "PEDIDO "  + pedido.getEstado(), pedido);
            default:

				newNotificacion = new Email("PEDIDO", "PEDIDO " + pedido.getEstado(), pedido);
		}

		this.listaNoti.add(newNotificacion);
	}

	public void enviarNoti(Pedido pedido, Estado estado){
		INotificacion notificacion = obtenerNotificacion(pedido.getId());
		notificacion.setCuerpo("PEDIDO " + estado);
	}
}
