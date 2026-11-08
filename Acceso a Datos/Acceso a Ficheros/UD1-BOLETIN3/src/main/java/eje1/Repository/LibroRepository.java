package eje1.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eje1.Entity.Libro;
import eje2.Entity.Pais;

public class LibroRepository implements ILibroRepository {
	
	private static final Logger logger = LogManager.getLogger(LibroRepository.class);
	
	private List<Libro> listaLibros;
	private File fichero;
	
	public LibroRepository(String ruta) {
		
		if(ruta == null || ruta.isEmpty()){
	           throw  new IllegalArgumentException("La ruta es nula o esta vacía");
	           }
		this.fichero = new File(ruta);
        this.listaLibros = new ArrayList<>();
	}
	
	@Override
	public List<Libro> listadoLibro() {
		if(this.listaLibros.isEmpty()) {
			leerLibros();
		}
	
		return this.listaLibros;
	}

	@Override
	public Optional<Libro> buscarLibro(String isbn) {
		return this.listaLibros
				.stream()
				.filter(libro -> libro.getIsbn().equals(isbn))
				.findFirst();
	}

	@Override
	public Libro annadirLibro(Libro libro) {
		Libro nuevo = new Libro(null, null, null);
		
		try {
			nuevo.setTitulo(libro.getTitulo());
			nuevo.setIsbn(libro.getIsbn());
			nuevo.setEditorial(libro.getEditorial());
			nuevo.setNumero_edicione(libro.getNumero_edicione());
			nuevo.setAutor(libro.getAutor());
			
			this.listaLibros.add(nuevo);
			escribirLibros();

		} catch (IllegalArgumentException e) {
			System.err.println("Error al registrar: " + e.getMessage());
		}
		return nuevo;
	}

	@Override
	public Libro editarLibro(Libro libro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Libro eliminarLibro(Long isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leerLibros(){
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy").create();
	
		try {
			FileReader ficheroLectura = new FileReader(this.fichero);
			Libro[] libros = gson.fromJson(ficheroLectura, Libro[].class);
			List<Libro> librosFiltrados = new ArrayList<Libro>(Arrays.asList(libros));
			
			librosFiltrados.removeIf(libro -> {
			    boolean erroneo = libro.getTitulo() == null || libro.getIsbn() == null || libro.getEditorial() == null;
			    if (erroneo) registrarLibroErroneo(libro);
			    return erroneo;
			});
			
			this.listaLibros = librosFiltrados;
			
		} catch (FileNotFoundException e) {
			logger.error("No se encuentra el archivo o fichero");
		} catch (Exception e) {
			logger.error("No se puede añadir un pais sin los campos correspondientes");
		}
	}

	@Override
	public void escribirLibros() {
		// TODO Auto-generated method stub
		
	}
	
	private void registrarLibroErroneo(Libro libro) {
		try (FileWriter fw = new FileWriter("src/main/resources/libros/libros_erroneos.txt", true)) {
	        fw.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
	                + " - Libro erróneo: " + libro + System.lineSeparator());
	        
	    } catch (IOException e) {
	        logger.error("No se pudo escribir en libros_erroneos.txt", e);
	    }
		
	}

	@Override
	public void escribirCSV(String pais) {
		PrintWriter out = null;
		
		List<Libro> libros = this.listaLibros
				.stream()
				.filter(libro -> libro.getAutor().getPais().equalsIgnoreCase(pais))
				.toList();
		
		
		try {
            FileWriter ficheroSalida;
            ficheroSalida = new FileWriter("src/main/resources/libros/" + pais + ".csv", StandardCharsets.UTF_8);
            out = new PrintWriter(ficheroSalida);
            if(libros.isEmpty()) {
            	out.println("No hay libros en este Pais");
            }else {
            	out.println("ISBN, Título, Autor");
                out.println("");
                for (Libro l : libros) {
                	out.printf(Locale.FRENCH, "%s, %s, %s%n",
                	        l.getIsbn(),
                	        l.getTitulo(),
                	        String.join(" ", l.getAutor().getNombre(), l.getAutor().getApellidos()));
                	        
                }
            }
            
        } catch (IOException e) {

        } finally {
            if (out != null)
                out.close();
        }
	}
	
	
}
