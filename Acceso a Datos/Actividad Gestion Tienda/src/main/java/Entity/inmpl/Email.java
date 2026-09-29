package Entity.inmpl;

import java.util.Objects;

import Entity.Cliente;
import Entity.INotificacion;
import Entity.Pedido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.NotificacionRepository;

public class Email implements INotificacion{

	private String asunto;

	private String cuerpo;

	private Pedido pedido;

	private static final Logger logger = LogManager.getLogger(Email.class);

	public Email() {}

	public Email(String asunto, String cuerpo, Pedido pedido) {
		this.asunto = asunto;
		this.cuerpo = cuerpo;
		this.pedido = pedido;
	}

	public String getAsunto() {
		return asunto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Email email = (Email) o;
		return Objects.equals(asunto, email.asunto) && Objects.equals(cuerpo, email.cuerpo) && Objects.equals(pedido, email.pedido);
	}

	@Override
	public int hashCode() {
		return Objects.hash(asunto, cuerpo, pedido);
	}

	@Override
	public String toString() {
		return "Email{" +
				"asunto='" + asunto + '\'' +
				", cuerpo='" + cuerpo + '\'' +
				", pedido=" + pedido +
				'}';
	}

	@Override
	public void enviarNotificacion() {
		logger.debug("Email enviado");
		logger.debug("Asunto " + this.getAsunto());
		logger.debug("Cuerpo " + this.getCuerpo());
		logger.debug("Nº pedido " + this.getPedido().getId());
		logger.debug("Nombre Cliente" + this.getPedido().getCliente().getNombre());
	}
}
