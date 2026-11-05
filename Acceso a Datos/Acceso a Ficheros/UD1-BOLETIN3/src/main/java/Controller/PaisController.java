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
	
		/*listado.forEach((pais) -> {
			System.out.println(pais);
		});*/
		
		Optional<Pais> pais = ctr.service.obtenerPais("Argentina");
		
		System.out.println(pais.get().toString());
	
	}

}
