package org.example.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ELdenException;
import org.example.Entity.Encuentros;
import org.example.Entity.SinLuz;
import org.example.GestorEldenRing;
import org.example.Repository.EncuentroRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class EncuentroService {

    private static final Logger logger = LogManager.getLogger(EncuentroService.class);

    private final EncuentroRepository encuentroRepository;
    private final SinLuzService sinLuzService;

    private Long contador = 1L;

    public EncuentroService(SinLuzService sinLuzService) {

        this.encuentroRepository = new EncuentroRepository();
        this.sinLuzService = sinLuzService;
    }


    public Collection<Encuentros> listadoEncuentros() {
        return encuentroRepository.listadoEncuentros();
    }

    public Encuentros obtenerEncuentro(Long id) {
        return encuentroRepository.obtenerEncuentro(id);
    }

    public Encuentros agregarEncuentro(Long idSinLuz, Encuentros encuentros) {

        try {
            SinLuz sinLuz = sinLuzService.obtenerSinLuz(idSinLuz);

            if (Objects.isNull(sinLuz)) {
                return null;
            }

            boolean encontrado = sinLuz.getEncuentros()
                    .stream()
                    .anyMatch(e -> Objects.equals(
                            e.getNombre(),
                            encuentros.getNombre()
                    ));

            if (encontrado) {
                logger.error("Este encuentro ya pertenece a este SinLuz");
                return null;
            }
            Encuentros newEncuentro = new Encuentros();

            newEncuentro.setId(contador);
            contador++;

            newEncuentro.setNombre(encuentros.getNombre());
            newEncuentro.setFechaEncuentro(encuentros.getFechaEncuentro());
            newEncuentro.setDificultad(encuentros.getDificultad());
            newEncuentro.setNombreEnemigos(encuentros.getNombreEnemigos());

            encuentroRepository.agregarEncuentro(newEncuentro);

            sinLuzService.agregarEncuentro(idSinLuz, newEncuentro);

            return newEncuentro;

        } catch (ELdenException e) {
            logger.error("Error: {}", e.getMessage());
            return null;
        }
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
