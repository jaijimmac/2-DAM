package org.example.Service;

import org.example.ELdenException;
import org.example.Entity.Encuentros;
import org.example.Entity.SinLuz;
import org.example.Repository.SinLuzRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SinLuzService {
    private final SinLuzRepository sinLuzRepository;
    private Long contador = 1L;

    public SinLuzService(){
        super();
        sinLuzRepository = new SinLuzRepository();
    }

    public Collection<SinLuz> getListaSinLuz() {

        return sinLuzRepository.getListaSinLuz();
    }

    public SinLuz obtenerSinLuz(Long id) {

        try {
            return sinLuzRepository.obtenerSinLuz(id);
        } catch (ELdenException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public SinLuz agregarSinLuz(SinLuz sinLuz) {
        try {

            SinLuz sinLuz1 = new SinLuz();
            sinLuz1.setId(contador);
            contador++;
            sinLuz1.setNombre(sinLuz.getNombre());
            sinLuzRepository.agregarSinLuz(sinLuz1);
            return sinLuz1;
        } catch (ELdenException e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public void agregarEncuentro(Long idSin, Encuentros encuentros) {
        sinLuzRepository.agregarEncuentro(idSin, encuentros);
    }

    public SinLuz editarSinLuz(SinLuz sinLuz){

        SinLuz sinLuz1 = new SinLuz();
        sinLuz1.setNombre(sinLuz.getNombre());
        sinLuzRepository.agregarSinLuz(sinLuz1);
        return sinLuz1;
    }

    public void eliminarSinLuz(Long id){
        sinLuzRepository.eliminarSinLuz(id);
    }
}
