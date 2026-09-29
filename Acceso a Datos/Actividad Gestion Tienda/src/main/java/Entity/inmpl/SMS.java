package Entity.inmpl;

import java.util.Objects;

import Entity.Cliente;
import Entity.INotificacion;
import Entity.Pedido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.NotificacionRepository;

public class SMS implements INotificacion {
	
	private String telefono;
	
	private String cuerpo;

	private Pedido pedido;

	private static final Logger logger = LogManager.getLogger(SMS.class);


	public SMS() {}

	public SMS(String telefono, String cuerpo, Pedido pedido) {
		this.telefono = telefono;
		this.cuerpo = cuerpo;
		this.pedido = pedido;
	}

	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCuerpo() {
		return cuerpo;
	}


	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}


	@Override
	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cuerpo, telefono);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SMS other = (SMS) obj;
		return Objects.equals(cuerpo, other.cuerpo) && Objects.equals(telefono, other.telefono);
	}

	@Override
	public String toString() {
		return "SMS [telefono=" + telefono + ", mensaje=" + cuerpo + "]";
	}



	@Override
	public void enviarNotificacion() {
		logger.debug("SMS enviado");
		logger.debug("Nº Telefono " + this.getTelefono());
		logger.debug("Mensaje " + this.getCuerpo());
	}
}
