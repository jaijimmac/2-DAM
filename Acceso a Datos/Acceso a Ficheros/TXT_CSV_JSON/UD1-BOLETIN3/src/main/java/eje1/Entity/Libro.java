package eje1.Entity;

public class Libro {

	private String isbn; 
	private String titulo;
	private String editorial;
	private int numero_edicione;
	
	private Autor autor;
	
	public Libro(String isbn, String titulo, String editorial) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.editorial = editorial;
	}

	public Libro(String isbn, String titulo, String editorial, int numero_edicione, Autor autor) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.editorial = editorial;
		this.numero_edicione = numero_edicione;
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getNumero_edicione() {
		return numero_edicione;
	}
		

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public void setNumero_edicione(int numero_edicione) {
		this.numero_edicione = numero_edicione;
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Libro:\n");
	    sb.append("[-] ISBM: ").append(isbn).append("\n");
	    sb.append("[-] Título: ").append(titulo).append("\n");
	    sb.append("[-] Editorial: ").append(editorial).append("\n");
	    sb.append("[-] Nuúmero de Ediciones: ").append(numero_edicione).append("\n");
	    sb.append("[-] Autor: ").append(autor);
	    return sb.toString();
	}
}
