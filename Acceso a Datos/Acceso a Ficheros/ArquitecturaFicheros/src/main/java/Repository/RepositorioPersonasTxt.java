package Repository;

import Entity.Persona;
import Interface.RepositorioPersonas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        if (listadoPersonas.isEmpty()){
            Scanner in = null;
            try {
                FileReader ficheroLectura = new FileReader(this.fichero);
                in = new Scanner(ficheroLectura);
                Persona persona = new Persona();

                while (in.hasNext()){
                    String valor = in.next();
                    List<Double> listaNotas = new ArrayList<>();
                    while (in.hasNextDouble()){
                        double valorNota = in.nextDouble();
                        listaNotas.add(valorNota);
                    }
                    persona.setNombre(valor);
                    persona.setListaNotas(listaNotas);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            return this.listadoPersonas;
        }

        return this.listadoPersonas;
    }

    @Override
    public Optional<Persona> findByName(String name) {
        return  this.listadoPersonas
                .stream()
                .filter(persona -> persona.getNombre().equals(name))
                .findFirst();


    }

    @Override
    public boolean existsByName(String name) {
        return this.listadoPersonas
                .stream()
                .anyMatch(persona -> persona.getNombre().equals(name));
    }

    @Override
    public int count() {
        return this.listadoPersonas.size();
    }

    @Override
    public void save(Persona persona) {
        Persona persona1 = new Persona();
        if (persona != null){
            persona1.setNombre(persona.getNombre());
            persona1.setNotaMin(persona.getNotaMin());
            persona1.setNotaMax(persona.getNotaMax());
            persona1.setListaNotas(persona.getListaNotas());

            this.listadoPersonas.add(persona1);
        }

        escribePersonas(this.listadoPersonas);
    }

    @Override
    public boolean deleteByName(String name) {
        Optional<Persona> persona = findByName(name);
        if (persona.isEmpty()){
            return false;
        }
        this.listadoPersonas.remove(persona.get());
        escribePersonas(this.listadoPersonas);
        return true;
    }

    public void escribePersonas(List<Persona> personas) {
        PrintWriter out = null;
        try {
            FileWriter ficheroSalida;
            ficheroSalida = new FileWriter(this.fichero, StandardCharsets.UTF_8);
            out = new PrintWriter(ficheroSalida);
            for (Persona p : personas) {
                out.printf(Locale.ENGLISH, "%-10s", p.getNombre());
                for (double nota : p.getListaNotas()) {
                    out.printf(Locale.ENGLISH, "%6.2f", nota);
                }
                out.println();
            }
        } catch (IOException e) {

        } finally {
            if (out != null)
                out.close();
        }
    }
}
