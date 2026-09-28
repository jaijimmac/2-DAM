package Entity.inmpl;

import java.util.Objects;

import Entity.Cliente;
import Entity.INotificacion;

public class SMS implements INotificacion {
	
	private String telefono;
	
	private String mensaje;
	
	public SMS() {}
	
	
	

	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}




	@Override
	public int hashCode() {
		return Objects.hash(mensaje, telefono);
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
		return Objects.equals(mensaje, other.mensaje) && Objects.equals(telefono, other.telefono);
	}

	@Override
	public String toString() {
		return "SMS [telefono=" + telefono + ", mensaje=" + mensaje + "]";
	}


	@Override
	public void notificar(Cliente cliente, String asunto, String mensaje) {
		// TODO Auto-generated method stub
		
	}

}
