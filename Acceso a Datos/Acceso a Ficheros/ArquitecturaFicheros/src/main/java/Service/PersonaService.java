package Service;

import Entity.Persona;
import Repository.RepositorioPersonasTxt;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public class PersonaService {

    private RepositorioPersonasTxt repo;

    public PersonaService(String ruta) {
        this.repo = new RepositorioPersonasTxt(ruta);
    }


    public List<Persona> findAll() throws FileNotFoundException{
        return repo.findAll();
    }

    public Optional<Persona> findByName(String name){
        return repo.findByName(name);
    }

    public boolean existsByName(String name){
        return repo.existsByName(name);
    }

    public int count(){
        return repo.count();
    }

    public void save(Persona persona){
        repo.save(persona);
    }

    public boolean deleteByName(String name){
        return repo.deleteByName(name);
    }
}
