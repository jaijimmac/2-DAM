package eje1.Controller;

import java.util.List;

import eje1.Entity.Autor;
import eje1.Entity.Libro;
import eje1.Service.LibroService;

public class LibroController {
	
	private static String RUTA = "src/main/resources/libros/librosAutores.json";
	private LibroService service = new LibroService(RUTA);
	
	public static void main(String[] args) {
		
		LibroController controller = new LibroController();
		
		List<Libro> libros = controller.service.listadoLibro();
		
		Autor nuevoAutor = new Autor("Jaime", "Jiménez", "España");
		Libro nuevoLibro = new Libro("9823649264","La biblia","Santa Ana", 97123, nuevoAutor);
		controller.service.annadirLibro(nuevoLibro);
		
		/*
		 libros.forEach(libro -> {
			System.out.println(libro.toString());
		});
		
		*/
		
		System.out.println(controller.service.buscarLibro("9788445002070"));
		controller.service.escribirCSV("E");
	}

}
