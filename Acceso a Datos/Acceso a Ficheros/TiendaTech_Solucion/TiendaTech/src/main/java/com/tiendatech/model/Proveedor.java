package com.tiendatech.model;

/** Añadido en la versión 2 del modelo. */
public class Proveedor {

    private String nombre;
    private String pais;
    private String email;

    public Proveedor() {}

    public Proveedor(String nombre, String pais, String email) {
        this.nombre = nombre;
        this.pais = pais;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return nombre + " (" + pais + ")";
    }
}
