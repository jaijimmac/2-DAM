package org.example.Repository;

import org.example.Entity.Encuentros;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EncuentroRepository {
    private List<Encuentros> listadoEncuentros;

    public EncuentroRepository() {
        this.listadoEncuentros = new ArrayList<>();
    }

    public List<Encuentros> listadoEncuentros() {
        return this.listadoEncuentros;
    }

    public Encuentros obtenerEncuentro(Long id) {
        return this.listadoEncuentros
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public Encuentros agregarEncuentro(Encuentros encuentros) {
        this.listadoEncuentros.add(encuentros);
        return encuentros;
    }

    public Encuentros editarEncuentro(Encuentros encuentros) {
        this.listadoEncuentros.add(encuentros);
        return encuentros;
    }

    public void eliminarEncuentro(Encuentros encuentros) {
        this.listadoEncuentros.remove(encuentros);
    }
}
