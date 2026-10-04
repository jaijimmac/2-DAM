package org.example.Entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Encuentros {
    private Long id;
    private String nombre;
    private LocalDate fechaEncuentro;
    private int dificultad;
    private List<String> nombreEnemigos;

    public Encuentros() {

    }

    public Encuentros( String nombre, LocalDate fechaEncuentro, int dificultad, List<String> nombreEnemigos) {
        this.nombre = nombre;
        this.fechaEncuentro = fechaEncuentro;
        this.dificultad = dificultad;
        this.nombreEnemigos = nombreEnemigos;
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

    public LocalDate getFechaEncuentro() {
        return fechaEncuentro;
    }

    public void setFechaEncuentro(LocalDate fechaEncuentro) {
        this.fechaEncuentro = fechaEncuentro;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public List<String> getNombreEnemigos() {
        return nombreEnemigos;
    }

    public void setNombreEnemigos(List<String> nombreEnemigos) {
        this.nombreEnemigos = nombreEnemigos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Encuentros that = (Encuentros) o;
        return dificultad == that.dificultad && Objects.equals(nombre, that.nombre) && Objects.equals(fechaEncuentro, that.fechaEncuentro) && Objects.equals(nombreEnemigos, that.nombreEnemigos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, fechaEncuentro, dificultad, nombreEnemigos);
    }

    @Override
    public String toString() {
        return "Encuentros{" +
                "nombre='" + nombre + '\'' +
                ", fechaEncuentro=" + fechaEncuentro +
                ", dificultad=" + dificultad +
                ", nombreEnemigos=" + nombreEnemigos +
                '}';
    }
}
