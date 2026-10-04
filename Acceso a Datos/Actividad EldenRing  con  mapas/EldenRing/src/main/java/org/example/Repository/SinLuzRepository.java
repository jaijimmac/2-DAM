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

    private  final Map<Long, SinLuz> mapaSinLuz;

    private EncuentroService encuentroService;

    public SinLuzRepository(){
        super();
        this.mapaSinLuz = new TreeMap<>();
    }

    public Collection<SinLuz> getListaSinLuz() {

        return this.mapaSinLuz.values() ;
    }

    public SinLuz obtenerSinLuz(Long id) {
        SinLuz sinLuz = this.mapaSinLuz.get(id);
        if (sinLuz == null) {
            throw   new ELdenException(id);
        }

        return sinLuz;
    }

    public SinLuz agregarSinLuz(SinLuz sinLuz){

        this.mapaSinLuz.put(sinLuz.getId(),sinLuz);
        return sinLuz;
    }

    public void agregarEncuentro(Long idSin, Encuentros encuentros) {

        try {
            obtenerSinLuz(idSin).getEncuentros().add(encuentros);
        } catch (ELdenException e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    public SinLuz editarSinLuz(SinLuz sinLuz){

        this.mapaSinLuz.put(sinLuz.getId(),sinLuz);
        return sinLuz;
    }

    public void eliminarSinLuz(Long id){

        SinLuz sinLuz = obtenerSinLuz(id);
        this.mapaSinLuz.remove(id);
    }
}
