package com.tiendatech.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Producto {

    private String id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private List<String> etiquetas = new ArrayList<>();
    private Proveedor proveedor; // v2: puede ser null

    public Producto() {}

    public Producto(String id, String nombre, String categoria, double precio, int stock,
                    List<String> etiquetas) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.etiquetas = etiquetas != null ? etiquetas : new ArrayList<>();
    }

    public boolean tieneEtiqueta(String etiqueta) {
        return etiquetas.stream().anyMatch(e -> e.equalsIgnoreCase(etiqueta));
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    @Override
    public String toString() {
        String texto = String.format(Locale.US, "%-5s %-22s %-15s %8.2f €  %4d uds.  %s",
                id, nombre, categoria, precio, stock, etiquetas);
        if (proveedor != null) {
            texto += "  Proveedor: " + proveedor;
        }
        return texto;
    }
}
