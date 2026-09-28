package Entity;

import java.util.Objects;

public interface INotificacion {
	
	public void notificar(Cliente cliente, String asunto, String mensaje);
	
}
