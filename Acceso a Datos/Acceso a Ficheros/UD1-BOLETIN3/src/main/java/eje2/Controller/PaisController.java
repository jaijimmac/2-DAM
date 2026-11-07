package eje2.Controller;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import  eje2.Entity.Pais;
import  eje2.Service.PaisService;

public class PaisController {
	
	private static String ruta = "src/main/resources/paises.csv";
	private final PaisService service = new PaisService(ruta);
	
	public static void main(String[] args) throws FileNotFoundException {
		
		PaisController ctr = new PaisController();
		List<Pais> listado = ctr.service.listadoPaises();
		
		
		 /*
		  listado.forEach(pais -> {
			System.out.println(pais);
		})
		 */
		
		String[] idiomasTabla = {"Español", "Portugés", "Inglés"};
		List<String> idiomas = Arrays.asList(idiomasTabla);
		
		//Pais nuevo = new Pais("Portugal", "Europa",1393000000, idiomas, "Euro");
		//Pais nuevoPrueba = ctr.service.annadirPais(nuevo);
		
		Pais mofidicado = new Pais("Portugal", "Europ",2093000000, idiomas, "Euro");
		Pais mofidicadoPrueba = ctr.service.modificarPais(mofidicado);
		
		Optional<Pais> pais = ctr.service.obtenerPais("Portugal");
		//System.out.println(pais.get().toString());
		
		ctr.service.calcularPaises("América");
	}
}
