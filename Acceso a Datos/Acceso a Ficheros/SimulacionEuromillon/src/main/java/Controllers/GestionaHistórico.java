package Controllers;

import java.util.List;

import Entity.Combinaciones;
import Service.CombinacionesService;

public class GestionaHistórico {
	
	private static final String RUTA = "src/main/resources/Euromillones 2004 a 2023.csv";
	private final CombinacionesService service = new CombinacionesService(RUTA);
	
	public static void main(String[] args) {
		
		GestionaHistórico gs = new GestionaHistórico();
		
		gs.service.leerCSV();
		
		List<Combinaciones> listado = gs.service.listar();
		
		System.out.println(gs.service.calcularNumeroEestrellas());
		System.out.println(gs.service.numeroCombinaciones());
		System.out.println(gs.service.combinacionFrecuente());
		
		
		/*
		for(int i = 0; i <= 3; i++) {
			System.out.println(listado.get(i));
		}
		
		System.out.println(listado.size());
		*/
	}

}
