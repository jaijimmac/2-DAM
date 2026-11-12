package org.example;

import java.util.ArrayList;
import java.util.List;

public class Persona {

    private Long id;
    private String nombre;
    private List<Double> listaNotas = new ArrayList<>();

    public Persona(){

    }

    public Persona(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Double> getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(List<Double> listaNotas) {
        this.listaNotas = listaNotas;
    }

    public void annadirNotas(Double nota){
        this.listaNotas.add(nota);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", listaNotas=" + listaNotas +
                '}';
    }
}
