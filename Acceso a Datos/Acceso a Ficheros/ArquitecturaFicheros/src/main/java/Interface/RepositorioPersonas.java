package Interface;

import Entity.Persona;

import java.util.List;

public interface RepositorioPersonas {

    public List<Persona> findAll();
    public Persona findByName(String name);
    public boolean existsByName(String name);
    public int count();
    public void save(Persona persona);
    public boolean deleteByName(String name);
}
