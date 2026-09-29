package org.example.Repository;

import org.example.ELdenException;
import org.example.Entity.Encuentros;
import org.example.Entity.SinLuz;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SinLuzRepository {

    private List<SinLuz> listadoSinLuz;

    public SinLuzRepository(){
        super();
        this.listadoSinLuz = new ArrayList<>();
    }

    public List<SinLuz> getListaSinLuz() {
        return this.listadoSinLuz;
    }

    public SinLuz obtenerSinLuz(Long id) {
        return this.listadoSinLuz
                .stream()
                .filter(n -> Objects.equals(n.getId(), id))
                .findFirst()
                .orElseThrow(()-> new ELdenException(id));
    }

    public SinLuz agregarSinLuz(SinLuz sinLuz){
        this.listadoSinLuz.add(sinLuz);
        return sinLuz;
    }

    public void agregarEncuentro(Long idSin, Encuentros encuentros) {
        SinLuz sinLuz = obtenerSinLuz(idSin);
        List<Encuentros> listaE = new ArrayList<>();
        listaE = sinLuz.getEncuentros();
        listaE.add(encuentros);
        sinLuz.setEncuentros(listaE);

        editarSinLuz(sinLuz);
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
