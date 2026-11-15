package com.tiendatech.model;

public class LineaPedido {

    private Producto producto;
    private int cantidad;
    private double precioUnitario; // precio en el momento de la compra

    public LineaPedido(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public double getImporte() {
        return cantidad * precioUnitario;
    }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    @Override
    public String toString() {
        return producto.getId() + "x" + cantidad;
    }
}
