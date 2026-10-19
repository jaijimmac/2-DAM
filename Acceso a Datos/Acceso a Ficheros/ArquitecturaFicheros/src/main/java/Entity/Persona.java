package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Persona {

    private double notaMin;
    private double notaMax;
    private String nombre;
    private List<Double> listaNotas;

    public Persona(){}

    public Persona(double notaMin, double notaMax, String nombre) {
        this.notaMin = notaMin;
        this.notaMax = notaMax;
        this.nombre = nombre;
        this.listaNotas = new ArrayList<>();
    }

    public double getNotaMin() {
        return notaMin;
    }

    public void setNotaMin(double notaMin) {
        this.notaMin = notaMin;
    }

    public double getNotaMax() {
        return notaMax;
    }

    public void setNotaMax(double notaMax) {
        this.notaMax = notaMax;
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

    public List<Double> annadirNota(double nota){
        this.listaNotas.add(nota);
        return this.listaNotas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Double.compare(notaMin, persona.notaMin) == 0 && Double.compare(notaMax, persona.notaMax) == 0 && Objects.equals(nombre, persona.nombre) && Objects.equals(listaNotas, persona.listaNotas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notaMin, notaMax, nombre, listaNotas);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "notaMin=" + notaMin +
                ", notaMax=" + notaMax +
                ", nombre='" + nombre + '\'' +
                ", listaNotas=" + listaNotas +
                '}';
    }
}

