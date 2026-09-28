package repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.management.Notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.INotificacion;
import Entity.inmpl.Email;
public class NotificacionRepository {
	
	private static final Logger logger = LogManager.getLogger(NotificacionRepository.class);

	private Set<Email> listaNoti;
	
	public NotificacionRepository() {
		super();
		this.listaNoti = new HashSet<Email>();
	}

	public Email obtenerEmail(Long id) {
        if (id != null) {
            return (Email) this.listaNoti.stream().filter(n-> Objects.equals(n.getId(), id));
        }

		return null;
	}

	public void agregarNotificacion(Email n) {

		Email newNotificacion = new Email();
		
		newNotificacion.setAsunto(n.getAsunto());
		newNotificacion.setCuerpo(n.getCuerpo());
		newNotificacion.setPedido(n.getPedido());

		this.listaNoti.add(newNotificacion);
	}
	
	public void enviarEmail(Long idNoti) {

		Email noti = obtenerEmail(idNoti);

		logger.debug("Email enviado");
		logger.debug("Asunto " + noti.getAsunto());
		logger.debug("Cuerpo " + noti.getCuerpo());
		logger.debug("Nº pedido " + noti.getPedido().getId());
		logger.debug("Nombre Cliente" + noti.getPedido().getCliente().getNombre());
	}

	
	public void enviarSMS(Long idNoti) {

		Email noti = obtenerEmail(idNoti);
		
		logger.debug("SMS enviado");
		logger.debug("Asunto " + noti.getAsunto());
		logger.debug("Cuerpo " + noti.getCuerpo());
		logger.debug("Nº pedido " + noti.getPedido().getId());
		logger.debug("Nombre Cliente" + noti.getPedido().getCliente().getNombre());
	}
}
