package org.example.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SinLuz implements Comparable<SinLuz>{
    private Long id;
    private String nombre;
    private Collection<Encuentros> encuentros = new ArrayList<>();

    public SinLuz(){

    }

    public SinLuz(String nombre) {
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

    public Collection<Encuentros> getEncuentros() {
        return encuentros;
    }

    public void setEncuentros(Collection<Encuentros> encuentros) {
        this.encuentros = encuentros;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SinLuz sinLuz = (SinLuz) o;
        return Objects.equals(id, sinLuz.id) && Objects.equals(nombre, sinLuz.nombre) && Objects.equals(encuentros, sinLuz.encuentros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, encuentros);
    }

    @Override
    public String toString() {
        return "SinLuz{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", encuentros=" + encuentros +
                '}';
    }

    @Override
    public int compareTo(SinLuz o) {
        return this.nombre.compareTo(o.nombre);
    }
}
