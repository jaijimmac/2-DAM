package com.guia.ficheros.model;

import java.util.ArrayList;
import java.util.List;

public class Pais {

    private String nombre;
    private String continente;
    private long poblacion;          // long: con int, 8.000.000.000 no cabe (máx. ~2.147 millones)
    private List<String> idiomas;
    private String moneda;           // puede ser null

    public Pais() {
        this.idiomas = new ArrayList<>();
    }

    public Pais(String nombre, String continente, long poblacion, List<String> idiomas, String moneda) {
        this.nombre = nombre;
        this.continente = continente;
        this.poblacion = poblacion;
        this.idiomas = idiomas != null ? idiomas : new ArrayList<>();
        this.moneda = moneda;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContinente() { return continente; }
    public void setContinente(String continente) { this.continente = continente; }

    public long getPoblacion() { return poblacion; }
    public void setPoblacion(long poblacion) { this.poblacion = poblacion; }

    public List<String> getIdiomas() { return idiomas; }
    public void setIdiomas(List<String> idiomas) { this.idiomas = idiomas; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    @Override
    public String toString() {
        return String.format("%-22s %-9s %,15d  %-30s %s",
                nombre, continente, poblacion, idiomas, moneda == null ? "(sin moneda)" : moneda);
    }
}
