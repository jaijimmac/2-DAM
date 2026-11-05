package Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Entity.Pais;

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
		Scanner in = null;
		try {
			FileReader ficheroLectura = new FileReader(this.fichero);
			in = new Scanner(ficheroLectura);
			String cabecera = in.nextLine();
			
			while(in.hasNextLine()) {
				String valor = in.nextLine();
				String[] lineas = valor.split(",");
				
				for(String linea: lineas) {
					if(linea.trim().equals("")) {
						throw new Exception();
					}
				}
				
				Pais pais = new Pais(lineas[0],lineas[1],Integer.parseInt(lineas[2]),lineas[3],lineas[4]);
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
	public void escribirPaises() {
		// TODO Auto-generated method stub
		
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
		
		return this.listadoPaises
			.stream()
			.filter(pais -> pais.getNombre().equals(nombre))
			.findFirst();
	}

	@Override
	public Pais annadirPais(Pais pais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pais modificarPais(Pais pais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarPais(String nombre) {
		// TODO Auto-generated method stub
		
	}

}
