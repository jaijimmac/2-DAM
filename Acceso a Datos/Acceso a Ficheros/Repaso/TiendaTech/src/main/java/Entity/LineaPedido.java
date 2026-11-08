package Entity;

public class LineaPedido {

	private Producto producto;
	private int cantidad;
	private double precioUnitario;

	
	public LineaPedido() {}


	public LineaPedido(Producto producto, int cantidad, double precioUnitario) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public double getPrecioUnitario() {
		return precioUnitario;
	}


	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Linea Pedido:\n");
	    sb.append("[-] Producto: ").append(producto).append("\n");
	    sb.append("[-] Cantidad: ").append(cantidad).append("\n");
	    sb.append("[-] Precio: ").append(precioUnitario).append("\n");
	    
	    return sb.toString();
	}
}
