package org.example.Repository;

import org.example.Entity.Encuentros;

import java.util.*;

public class EncuentroRepository {
    private final Map<Long, Encuentros> mapaEncuentros = new TreeMap<>();


    public Collection<Encuentros> listadoEncuentros() {

        return this.mapaEncuentros.values();
    }

    public Encuentros obtenerEncuentro(Long id) {
        return this.mapaEncuentros.get(id);
    }

    public Encuentros agregarEncuentro(Encuentros encuentros) {
       this.mapaEncuentros.put(encuentros.getId(), encuentros);
        return encuentros;
    }

    public Encuentros editarEncuentro(Encuentros encuentros) {
        this.mapaEncuentros.put(encuentros.getId(), encuentros);
        return encuentros;
    }

    public void eliminarEncuentro(Encuentros encuentros) {

        this.mapaEncuentros.remove(encuentros.getId());
    }
}
