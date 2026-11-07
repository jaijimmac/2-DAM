package eje2.Repository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import  eje2.Entity.Pais;

public class PaisRepository implements InterfaceRepo{
	
	private List<Pais> listadoPaises;
	private File fichero;
	
	public PaisRepository(String ruta)  throws IllegalArgumentException{
		 if(ruta == null || ruta.isEmpty()){
	           throw  new IllegalArgumentException("La ruta es nula o esta vacía");
	        }
		 this.fichero = new File(ruta);
	        this.listadoPaises = new ArrayList<>();
	}
	
	@Override
	public List<Pais> leerPaises() throws FileNotFoundException {
		this.listadoPaises.clear();
		Scanner in = null;
		try {
			FileReader ficheroLectura = new FileReader(this.fichero);
			in = new Scanner(ficheroLectura);
			in.nextLine();
			
			while(in.hasNextLine()) {
				String valor = in.nextLine();
				if (valor.isBlank()) continue;
				
				String[] lineas = valor.split(",");
				if (lineas.length < 5) {
	                System.err.println("Línea incompleta, se ignora: " + valor);
	                continue;
	            }
				
				List<String> idiomas = new ArrayList<String>(Arrays.asList(lineas[3].split(" ")));
				
				
				Pais pais = new Pais(lineas[0], lineas[1], Integer.parseInt(lineas[2]), idiomas, lineas[4]);
				this.listadoPaises.add(pais);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("No se encuentra el archivo o fichero: " + e);
		} catch (Exception e) {
			System.err.println("No se puede añadir un pais sin los campos" + e);
		}
		return this.listadoPaises;
	}


	@Override
	public List<Pais> listadoPaises() throws FileNotFoundException {
		if(this.listadoPaises.isEmpty()) {
			return leerPaises();
		}
		
		return this.listadoPaises;
	}

	@Override
	public Optional<Pais> obtenerPais(String nombre) {
		return Optional.of(this.listadoPaises
                .stream()
                .filter(pais -> pais.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("País no encontrado: " + nombre)));
	}

	@Override
	public Pais annadirPais(Pais pais) {
		Pais nuevoPais = new Pais(null, null);
		
		try {
			nuevoPais.setNombre(pais.getNombre());
			nuevoPais.setContinente(pais.getContinente());
			nuevoPais.setIdioma(pais.getIdioma());
			nuevoPais.setMoneda(pais.getMoneda());
			nuevoPais.setPoblacion(pais.getPoblacion());

			this.listadoPaises.add(nuevoPais);
			escribirPaises();
			
			return nuevoPais;
			
		} catch (IllegalArgumentException e) {
			System.err.println("Error al registrar: " + e.getMessage());
		}
		
		return null;
	}

	@Override
	public Pais modificarPais(Pais pais) {
		Optional<Pais> antiguo = obtenerPais(pais.getNombre());

		try {
			antiguo.get().setNombre(pais.getNombre());
			antiguo.get().setContinente(pais.getContinente());
			antiguo.get().setIdioma(pais.getIdioma());
			antiguo.get().setMoneda(pais.getMoneda());
			antiguo.get().setPoblacion(pais.getPoblacion());
			
			escribirPaises();
			return antiguo.get();
			
		} catch (IllegalArgumentException e) {
			System.err.println("Error al Modificar: " + e.getMessage());
		}
		
		return null;
	}

	@Override
	public void eliminarPais(String nombre) {
		Optional<Pais> pais = obtenerPais(nombre);
        pais.ifPresent(p -> this.listadoPaises.remove(p));
        escribirPaises();
	}
	
	@Override
	public void escribirPaises() {
		PrintWriter out = null;
        try {
            FileWriter ficheroSalida;
            ficheroSalida = new FileWriter(this.fichero, StandardCharsets.UTF_8);
            out = new PrintWriter(ficheroSalida);
            out.println("Nombre,Continente,Población,Idioma,Moneda");
            
            for (Pais p : listadoPaises) {
            	out.printf(Locale.ENGLISH, "%s,%s,%d,%s,%s%n",
            	        p.getNombre(),
            	        p.getContinente(),
            	        p.getPoblacion(),
            	        String.join(" ", p.getIdioma()),
            	        p.getMoneda());
            }
        } catch (IOException e) {

        } finally {
            if (out != null)
                out.close();
        }
	}

	@Override
	public Pais annadirIdioma(String nombrePais, String idioma) {
		Optional<Pais> pais = obtenerPais(nombrePais);
		List<String> idiomas = pais.get().getIdioma();
		
		try {
			idiomas.add(idioma);
			pais.get().setIdioma(idiomas);
			escribirPaises();
			
			return pais.get(); 
			
		} catch (IllegalArgumentException e) {
			System.err.println("Error al añadir: " + e.getMessage());
		}
		
		return null;
	}

	@Override
	public void calcularPaises(String nombre) {
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy").create();
		 
		List<Pais> listadoPaises = this.listadoPaises
				.stream()
				.filter(pais -> pais.getContinente().equalsIgnoreCase(nombre))
				.toList();
		
		if(!listadoPaises.isEmpty()) {
			String json = gson.toJson(listadoPaises);
			
			FileWriter fichero = null;
	        try {
	            fichero = new FileWriter("src/main/resources/" + nombre + ".json", true);
	            fichero.write(json);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fichero != null) {
	                try {
	                    fichero.close();
	                } catch (IOException e) {
	                    System.out.println("Error al escribir pais");
	                }
	            }
	        }
		}
	}
	
	

}
