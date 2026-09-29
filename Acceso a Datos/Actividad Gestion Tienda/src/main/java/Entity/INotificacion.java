package Entity;


public interface INotificacion {

	public Pedido getPedido();
	public void setCuerpo(String cuerpo);

	public void enviarNotificacion();
}
