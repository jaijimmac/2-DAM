package Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Entity.Criatura;
import Entity.Habitat;
import Entity.TipoCriatura;
import Exception.CriaturasFantasticasException;

public class CriaturaRepository implements ICriaturaRepository{
	
	private static final Logger logger = LogManager.getLogger(CriaturaRepository.class);

	private Set<Criatura> listadoCriaturas;
	private File fichero;

	public CriaturaRepository(String ruta) {
		if(ruta == null || ruta.isEmpty()){
	      throw  new IllegalArgumentException("La ruta es nula o esta vacía");
		}    
		
		this.fichero = new File(ruta);
		this.listadoCriaturas = new TreeSet<Criatura>();
	}

	@Override
	public Collection<Criatura> findAll() {
		if(listadoCriaturas.isEmpty()) {
			leerArchivo();
		}else {
			logger.error("Archivo vacío");
		}
		
		return this.listadoCriaturas; 
	}

	@Override
	public Optional<Criatura> findById(String nombre) {
		String mensaje = "No existe niguna criatura con ese nombre: " + nombre;
		
		Optional<Criatura> criatura = this.listadoCriaturas
				.stream()
				.filter(c -> c.getNombre().equalsIgnoreCase(nombre))
				.findFirst();
		
		if(!criatura.isPresent()) {
			new CriaturasFantasticasException(mensaje);
		}
		
		return criatura;
	}

	@Override
	public Criatura addCriatura(Criatura criatura) {
		String mensaje = "Criatura registrada con ese nombre";
		
		boolean criaturaBusqueda = this.listadoCriaturas
				.stream()
				.anyMatch(c -> c.getNombre().equalsIgnoreCase(criatura.getNombre()));
		
		if(criaturaBusqueda) {
			new CriaturasFantasticasException(mensaje);
		}
		
		Criatura nueva = new Criatura(null);
		
		try {
			nueva.setNombre(criatura.getNombre());
			nueva.setTipo(criatura.getTipo());
			nueva.setPeligrosidad(criatura.getPeligrosidad());
			nueva.setMagia(criatura.getMagia());
			nueva.setInmortal(criatura.isInmortal());
			nueva.setHabitat(criatura.getHabitat());
			
			listadoCriaturas.add(nueva);
			
		} catch (Exception e) {
			logger.error("Error al introducir datos");
		}
		
		return nueva;
	}

	@Override
	public List<Criatura> obtenerCriaturaDeTipo(TipoCriatura tipo) {
		return this.listadoCriaturas
				.stream()
				.filter(c -> c.getTipo() == tipo)
				.sorted(Comparator
						.comparing(Criatura::getNombre, String.CASE_INSENSITIVE_ORDER)
						.thenComparing(Criatura::getTipo))
				.collect(Collectors.toList());
	}

	@Override
	public void exportarJSON(Collection<Criatura> criaturas, String nombreFichero) {
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		
		String json = gson.toJson(criaturas);

		try (FileWriter fichero = new FileWriter("src/main/resources/" + nombreFichero + ".json", false)) {
			fichero.write(json);
		} catch (IOException e) {
			new CriaturasFantasticasException("Error al escribir el fichero " + nombreFichero + ": " + e.getMessage());
		}
	}
	
	@Override
	public String leerArchivo() {
		Scanner in = null;
		
		try {
			FileReader ficheroLectura = new FileReader(this.fichero);
			in = new Scanner(ficheroLectura);
			in.nextLine();
			int contadorLineasError = 0;
			
			while(in.hasNextLine()) {

				String linea = in.nextLine();
				try {
					String[] lineaDividida = linea.split(";");
					
					if(lineaDividida[0].isEmpty()) {
						contadorLineasError++;
						new CriaturasFantasticasException("No se puede añadir por falta del campo nombre: " + linea);
						continue;
					}
					
					TipoCriatura tipo = null;
					
					switch (lineaDividida[1]) {
					case "BESTIA":
						tipo = TipoCriatura.BESTIA;
						break;
					
					case "SER":
						
						tipo = TipoCriatura.SER;
						break;
						
					case "ESPIRITU":
						tipo = TipoCriatura.ESPIRITU;
						break;

					default: 
						contadorLineasError++;
						new CriaturasFantasticasException("No se puede añadir, error en el Tipo: " + linea);
						continue;
					}
					
					Habitat habitat = new Habitat();
					habitat.setRegion(lineaDividida[5]);
					habitat.setClima(lineaDividida[6]);
					
					int peligrosidad = Integer.parseInt(lineaDividida[3]);
					int magia = Integer.parseInt(lineaDividida[4]);
					
					if(peligrosidad <0 || peligrosidad > 100) {
						continue;
					}
					
					if(magia <0 || magia > 100) {
						continue;
					}
					
					Criatura criatura = new Criatura(lineaDividida[0],tipo, Boolean.parseBoolean(lineaDividida[2]),peligrosidad, magia, habitat);
					this.listadoCriaturas.add(criatura);
					
				} catch (Exception e) {
					new CriaturasFantasticasException(e.getMessage());
				}
			}
			
			String mensaje = "Número de cargas incorrectas: " + contadorLineasError;
			logger.info(mensaje);
			return mensaje;

		} catch (Exception e) {
			new CriaturasFantasticasException(e.getMessage());
		}
		return null; 
	}
	
	
}