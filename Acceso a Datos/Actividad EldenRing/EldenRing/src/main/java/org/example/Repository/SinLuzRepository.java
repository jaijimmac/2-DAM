package org.example.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ELdenException;
import org.example.Entity.Encuentros;
import org.example.Entity.SinLuz;
import org.example.GestorEldenRing;
import org.example.Service.EncuentroService;

import java.util.*;

public class SinLuzRepository {

    private static final Logger logger = LogManager.getLogger(SinLuzRepository.class);
    private Collection<SinLuz> listadoSinLuz;

    private EncuentroService encuentroService;

    public SinLuzRepository(){
        super();
        this.listadoSinLuz = new ArrayList<>();
    }

    public Collection<SinLuz> getListaSinLuz() {

        return this.listadoSinLuz
                .stream()
                .sorted()
                .toList();
    }

    public SinLuz obtenerSinLuz(Long id) {
        return this.listadoSinLuz
                .stream()
                .filter(n -> Objects.equals(n.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ELdenException(id));
    }

    public SinLuz agregarSinLuz(SinLuz sinLuz){

        this.listadoSinLuz.add(sinLuz);
        return sinLuz;
    }

    public void agregarEncuentro(Long idSin, Encuentros encuentros) {

        try {
            SinLuz sinLuz = obtenerSinLuz(idSin);
            sinLuz.getEncuentros().add(encuentros);
            editarSinLuz(sinLuz);

        } catch (ELdenException e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    public SinLuz editarSinLuz(SinLuz sinLuz){

        this.listadoSinLuz.add(sinLuz);
        return sinLuz;
    }

    public void eliminarSinLuz(Long id){

        SinLuz sinLuz = obtenerSinLuz(id);
        this.listadoSinLuz.remove(sinLuz);
    }
}
