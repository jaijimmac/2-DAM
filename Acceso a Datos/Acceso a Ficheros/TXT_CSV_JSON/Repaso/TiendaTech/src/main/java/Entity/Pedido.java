package Entity;

import java.time.LocalDate;
import java.util.List;

public class Pedido {

	private Long id;
	private LocalDate fecha;
	private Cliente cliente;
	private List<LineaPedido> lineaPedidos;
	private EstadoPedido estado;
	
	public Pedido() {}

	public Pedido(Long id, LocalDate fecha, Cliente cliente, List<LineaPedido> lineaPedidos, EstadoPedido estado) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.cliente = cliente;
		this.lineaPedidos = lineaPedidos;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<LineaPedido> getLineaPedidos() {
		return lineaPedidos;
	}

	public void setLineaPedidos(List<LineaPedido> lineaPedidos) {
		this.lineaPedidos = lineaPedidos;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Producto:\n");
	    sb.append("[-] ID: ").append(id).append("\n");
	    sb.append("[-] Fecha: ").append(fecha).append("\n");
	    sb.append("[-] Clinte: ").append(cliente).append("\n");
	    sb.append("[-] Linea Pedido: ").append(lineaPedidos).append("\n");
	    sb.append("[-] Estado: ").append(estado).append("\n");
	
	    return sb.toString();
	}
	
}
