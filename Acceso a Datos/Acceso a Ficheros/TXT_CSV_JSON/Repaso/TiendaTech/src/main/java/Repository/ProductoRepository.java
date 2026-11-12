package Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Entity.Producto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductoRepository implements IProductosRepository{
	
	private List<Producto> listaProductos;
	private File fichero;
	
	 private static final Logger logger = LoggerFactory.getLogger(ProductoRepository.class);
	
	public ProductoRepository(String ruta) {
		if(ruta == null || ruta.isEmpty()){
			throw  new IllegalArgumentException("La ruta es nula o esta vacía");
	    }
		
		this.fichero = new File(ruta);
        this.listaProductos = new ArrayList<>();
	}

	@Override
	public void leerArchivo() {
		
	}

	@Override
	public List<Producto> findAll() {
		if(listaProductos.isEmpty()) {
			 leerArchivo();
		}
		
		return this.listaProductos;
	}

	@Override
	public Optional<Producto> findBProductoById(Long id) {
		return this.listaProductos
				.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();
	}

	@Override
	public Optional<Producto> findBProductoByName(Long nombre) {
		return this.listaProductos
				.stream()
				.filter(p -> p.getNombre().equals(nombre))
				.findFirst();
	}

	@Override
	public Producto addProducto(Producto producto) {
		Producto nuevo = new Producto(null, null, null);
		try {
			nuevo.setId(producto.getId());
			nuevo.setNombre(producto.getNombre());
			nuevo.setCategoria(producto.getCategoria());
			nuevo.setPrecio(producto.getPrecio());
			nuevo.setStock(producto.getStock());
			nuevo.setEtiquetas(producto.getEtiquetas());
			
			this.listaProductos.add(nuevo);
			
			

		} catch (IllegalArgumentException e) {
			String motivo = "Faltan datos";
			logger.error("Producto {} descartado: {}", producto.getId(), motivo);
		}
		
		return nuevo;
	}

	@Override
	public Producto updateProducto(Producto producto) {
		Optional<Producto> modificado = findBProductoById(producto.getId());
		try {
			modificado.get().setId(producto.getId());
			modificado.get().setNombre(producto.getNombre());
			modificado.get().setCategoria(producto.getCategoria());
			modificado.get().setPrecio(producto.getPrecio());
			modificado.get().setStock(producto.getStock());
			modificado.get().setEtiquetas(producto.getEtiquetas());

		} catch (IllegalArgumentException e) {
			String motivo = "Faltan datos";
			logger.error("Producto {} descartado: {}", producto.getId(), motivo);
		}
		
		return modificado.get();
	}

	@Override
	public String delProducto(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reponerStock(int cantidad) {
		// TODO Auto-generated method stub
		
	}

}
