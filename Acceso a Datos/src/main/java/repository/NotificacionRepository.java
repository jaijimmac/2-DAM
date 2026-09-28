package repository;

import java.util.HashSet;
import java.util.Set;

import javax.management.Notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.INotificacion;
import Entity.Pedido;
public class NotificacionRepository {
	
	private static final Logger logger = LogManager.getLogger(INotificacion.class);

	
	private Set<INotificacion> listaNoti;
	
	public NotificacionRepository() {
		super();
		this.listaNoti = new HashSet<INotificacion>();
	}
	
	public INotificacion obtenerNotificacion(Long id) {
	if(id == null) {
				
			}
		INotificacion noti = (INotificacion) this.listaNoti.stream().filter(n -> n.getId() == id);
		
		return noti;
	}
		
	
	
	public void agregarNotificacion(INotificacion n) {
		
		INotificacion newNotificacion = new INotificacion();
		
		newNotificacion.setAsunto(n.getAsunto());
		newNotificacion.setCuerpo(n.getCuerpo());
		newNotificacion.setPedido(n.getPedido());
		
		
		this.listaNoti.add(newNotificacion);
		
	}
	
	public void enviarEmail(Long idNoti) {
		
		INotificacion noti = obtenerNotificacion(idNoti);
		
		logger.debug("Email enviado");
		logger.debug("Asunto " + noti.getAsunto());
		logger.debug("Cuerpo " + noti.getCuerpo());
		logger.debug("Nº pedido " + noti.getPedido().getId());
		logger.debug("Nombre Cliente" + noti.getPedido().getCliente().getNombre());
		
	}

	
	public void enviarSMS(Long idNoti) {
		INotificacion noti = obtenerNotificacion(idNoti);
		
		logger.debug("SMS enviado");
		logger.debug("Asunto " + noti.getAsunto());
		logger.debug("Cuerpo " + noti.getCuerpo());
		logger.debug("Nº pedido " + noti.getPedido().getId());
		logger.debug("Nombre Cliente" + noti.getPedido().getCliente().getNombre());
	}
}
