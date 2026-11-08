package com.tiendatech.model;

import com.tiendatech.util.FechaUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pedido {

    private String id;
    private LocalDate fecha;
    private Cliente cliente;          // referencia al objeto, no solo el id
    private List<LineaPedido> lineas = new ArrayList<>();
    private EstadoPedido estado;

    public Pedido(String id, LocalDate fecha, Cliente cliente, List<LineaPedido> lineas,
                  EstadoPedido estado) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.lineas = lineas;
        this.estado = estado;
    }

    /** Suma de cantidad x precio, sin descuento ni IVA. */
    public double getSubtotal() {
        return lineas.stream().mapToDouble(LineaPedido::getImporte).sum();
    }

    public boolean contieneProducto(String idProducto) {
        return lineas.stream().anyMatch(l -> l.getProducto().getId().equals(idProducto));
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<LineaPedido> getLineas() { return lineas; }
    public void setLineas(List<LineaPedido> lineas) { this.lineas = lineas; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    @Override
    public String toString() {
        return String.format(Locale.US, "%-8s %s  %-5s %-28s %-10s subtotal: %8.2f €",
                id, FechaUtil.formatear(fecha), cliente.getId(), lineas, estado, getSubtotal());
    }
}
