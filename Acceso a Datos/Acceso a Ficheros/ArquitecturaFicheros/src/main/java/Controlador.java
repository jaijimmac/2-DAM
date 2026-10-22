import Entity.Persona;
import Repository.RepositorioPersonasTxt;
import Service.PersonaService;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Controlador {
    private final String ruta = "src/main/resources/Personas.txt";
    private final PersonaService personaService = new PersonaService(ruta);

    static void main(String[] args) throws FileNotFoundException {

        Controlador controlador = new Controlador();
        Persona persona = new Persona("Jaime");
        persona.annadirNota(6.5);
        persona.annadirNota(5.5);
        persona.annadirNota(3.5);
        persona.annadirNota(1.5);
        persona.annadirNota(7.5);

        Persona persona2 = new Persona("Pepe");
        persona2.annadirNota(6.5);
        persona2.annadirNota(5.5);
        persona2.annadirNota(3.5);
        persona2.annadirNota(1.5);
        persona2.annadirNota(7.5);

        controlador.personaService.save(persona);
        controlador.personaService.save(persona2);

        List<Persona> personas = controlador.personaService.findAll();

        System.out.println(controlador.personaService.existsByName("Jaime"));
        System.out.println(controlador.personaService.findByName("Jaime").toString());

        controlador.personaService.deleteByName("Jaime");
    }
}
