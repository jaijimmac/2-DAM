package Entity;

import java.util.Objects;

import ENUM.Estado;

public class Pedido {
	
	private Long id;
	
	private Cliente cliente;

	private double importe;
	
	private Estado estado;
	
	public Pedido() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, estado, id, Double.valueOf(importe));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(cliente, other.cliente) && estado == other.estado && Objects.equals(id, other.id)
				&& Double.doubleToLongBits(importe) == Double.doubleToLongBits(other.importe);
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", cliente=" + cliente + ", importe=" + importe + ", estado=" + estado + "]";
	}
	
	
	
}
	
