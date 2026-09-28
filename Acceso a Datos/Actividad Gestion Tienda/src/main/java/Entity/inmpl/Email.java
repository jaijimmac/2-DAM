package Entity.inmpl;

import java.util.Objects;

import Entity.Cliente;
import Entity.INotificacion;
import Entity.Pedido;

public class Email {

	private Long id;

	private String asunto;

	private String cuerpo;

	private Pedido pedido;


	public Email() {

	}

	public Email(String asunto, String cuerpo, Pedido pedido) {
		this.asunto = asunto;
		this.cuerpo = cuerpo;
		this.pedido = pedido;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAsunto() {
		return asunto;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
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
	public int hashCode() {
		return Objects.hash(id, pedido);
	}


	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", pedido=" + pedido + "]";
	}
	
}
