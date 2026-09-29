package org.example.Service;

import org.example.ELdenException;
import org.example.Entity.SinLuz;
import org.example.Repository.SinLuzRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SinLuzService {
    private final SinLuzRepository sinLuzRepository;
    private Long contador = 1L;

    public SinLuzService(){
        super();
        sinLuzRepository = new SinLuzRepository();
    }

    public List<SinLuz> getListaSinLuz() {
        return sinLuzRepository.getListaSinLuz();
    }

    public SinLuz obtenerSinLuz(Long id) {
        return sinLuzRepository.obtenerSinLuz(id);
    }

    public SinLuz agregarSinLuz(SinLuz sinLuz){

        SinLuz sinLuz1 = new SinLuz();
        sinLuz1.setId(contador);
        contador++;

        sinLuz1.setNombre(sinLuz.getNombre());

        sinLuzRepository.agregarSinLuz(sinLuz);
        return sinLuz1;
    }

    public SinLuz editarSinLuz(SinLuz sinLuz){
        SinLuz sinLuz1 = new SinLuz();

        sinLuz1.setNombre(sinLuz.getNombre());

        sinLuzRepository.agregarSinLuz(sinLuz);
        return sinLuz1;
    }

    public void eliminarSinLuz(Long id){
        sinLuzRepository.eliminarSinLuz(id);
    }
}
