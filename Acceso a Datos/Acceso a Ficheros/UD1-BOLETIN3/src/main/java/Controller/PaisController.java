package Controller;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import Entity.Pais;
import Service.PaisService;

public class PaisController {
	
	private static String ruta = "src/main/resources/paises.csv";
	private final PaisService service = new PaisService(ruta);
	
	public static void main(String[] args) throws FileNotFoundException {
		
		PaisController ctr = new PaisController();
		List<Pais> listado = ctr.service.listadoPaises();
		
		Pais nuevo = new Pais("España", "Europa", 46000000, "Español", "Euro");
		ctr.service.annadirPais(nuevo);
		
		Pais actualizado = new Pais("Españ", "Europa", 46000000, "Español", "Euro");
		//ctr.service.modificarPais(actualizado);
		
		
		Optional<Pais> pais = ctr.service.obtenerPais("España");
		System.out.println(pais.get().toString());
	
	}

}
