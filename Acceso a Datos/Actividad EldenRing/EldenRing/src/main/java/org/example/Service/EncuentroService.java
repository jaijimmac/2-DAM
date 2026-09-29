package org.example.Service;

import org.example.Entity.Encuentros;
import org.example.Repository.EncuentroRepository;

import java.util.List;
import java.util.Objects;

public class EncuentroService {

    private final EncuentroRepository encuentroRepository;

    private Long contador = 1L;

    public EncuentroService() {
        this.encuentroRepository = new EncuentroRepository();
    }

    public List<Encuentros> listadoEncuentros() {
        return encuentroRepository.listadoEncuentros();
    }

    public Encuentros obtenerEncuentro(Long id) {
        return encuentroRepository.obtenerEncuentro(id);
    }

    public Encuentros agregarEncuentro(Encuentros encuentros) {
        Encuentros newEncuentro = new Encuentros();
        newEncuentro.setId(contador);
        contador++;
        newEncuentro.setNombre(encuentros.getNombre());
        newEncuentro.setFechaEncuentro(encuentros.getFechaEncuentro());
        newEncuentro.setDificultad(encuentros.getDificultad());
        newEncuentro.setNombreEnemigos(encuentros.getNombreEnemigos());

        encuentroRepository.agregarEncuentro(newEncuentro);

        return newEncuentro;
    }

    public Encuentros editarEncuentro(Encuentros encuentros) {
        Encuentros newEncuentro = new Encuentros();
        newEncuentro.setNombre(encuentros.getNombre());
        newEncuentro.setFechaEncuentro(encuentros.getFechaEncuentro());
        newEncuentro.setDificultad(encuentros.getDificultad());
        newEncuentro.setNombreEnemigos(encuentros.getNombreEnemigos());

        encuentroRepository.agregarEncuentro(newEncuentro);

        return newEncuentro;
    }

    public void eliminarEncuentro(Long id) {
        Encuentros encuentros = obtenerEncuentro(id);
        encuentroRepository.eliminarEncuentro(encuentros);
    }

}
