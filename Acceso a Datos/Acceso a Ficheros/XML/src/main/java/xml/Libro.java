package xml;

public class Libro {

	private Long id;
	private String categoria;
	private String titulo;
	private String autor;
	private Editorial editorial;
	
	public Libro() {}
	

	public Libro(Long id, String categoria, String titulo, String autor, Editorial editorial) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	


	public Editorial getEditorial() {
		return editorial;
	}


	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}


	@Override
	public String toString() {
		return "Libro [id=" + id + ", categoria=" + categoria + ", titulo=" + titulo + ", autor=" + autor
				+ ", editorial=" + editorial + "]";
	}


	
	
}
