package org.example.Entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SinLuz {
    private Long id;
    private String nombre;
    private List<Encuentros> encuentros;

    public SinLuz(){

    }

    public SinLuz(Long id, String nombre, List<Encuentros> encuentros) {
        this.id = id;
        this.nombre = nombre;
        this.encuentros = encuentros;
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

    public List<Encuentros> getEncuentros() {
        return encuentros;
    }

    public void setEncuentros(List<Encuentros> encuentros) {
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
}
