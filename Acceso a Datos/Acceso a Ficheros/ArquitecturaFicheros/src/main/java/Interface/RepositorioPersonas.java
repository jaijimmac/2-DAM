package Interface;

import Entity.Persona;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface RepositorioPersonas {

    public List<Persona> findAll() throws FileNotFoundException;
    public Optional<Persona> findByName(String name);
    public boolean existsByName(String name);
    public int count();
    public void save(Persona persona);
    public boolean deleteByName(String name);
}
