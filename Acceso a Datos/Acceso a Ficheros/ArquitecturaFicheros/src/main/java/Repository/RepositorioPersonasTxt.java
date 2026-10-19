package Repository;

import Entity.Persona;
import Interface.RepositorioPersonas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepositorioPersonasTxt implements RepositorioPersonas {

    private List<Persona> listadoPersonas;
    private File fichero;

    public RepositorioPersonasTxt(String ruta) throws IllegalArgumentException {
        if(ruta == null || ruta.isBlank()){
           throw  new IllegalArgumentException("La ruta es nula o esta vacía");
        }
        this.fichero = new File(ruta);
        this.listadoPersonas = new ArrayList<>();
    }

    @Override
    public List<Persona> findAll() {
        return null;
    }

    @Override
    public Persona findByName(String name) {
        return listadoPersonas.stream().filter(name.equals(Objects.));
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void save(Persona persona) {

    }

    @Override
    public boolean deleteByName(String name) {
        return false;
    }
}
