package com.tiendatech.model;

import com.tiendatech.util.FechaUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fechaAlta;
    private Direccion direccion;            // puede ser null
    private List<String> telefonos = new ArrayList<>();
    private boolean vip;

    public Cliente() {}

    public Cliente(String id, String nombre, String apellidos, String email, LocalDate fechaAlta,
                   Direccion direccion, List<String> telefonos, boolean vip) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaAlta = fechaAlta;
        this.direccion = direccion;
        this.telefonos = telefonos != null ? telefonos : new ArrayList<>();
        this.vip = vip;
    }

    public String getNombreCompleto() {
        return apellidos == null || apellidos.isBlank() ? nombre : nombre + " " + apellidos;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public List<String> getTelefonos() { return telefonos; }
    public void setTelefonos(List<String> telefonos) { this.telefonos = telefonos; }

    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }

    @Override
    public String toString() {
        return String.format("%-5s %-22s %-25s alta: %s  %s  tlf: %s  %s",
                id, getNombreCompleto(), email, FechaUtil.formatear(fechaAlta),
                direccion != null ? direccion.getCiudad() : "(sin dirección)",
                telefonos, vip ? "★ VIP" : "");
    }
}
