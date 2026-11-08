package Repository;

import java.util.List;
import java.util.Optional;

import Entity.Producto;

public interface IProductosRepository {

	public void leerArchivo();
	
	public List<Producto> findAll();
	public Optional<Producto> findBProductoById(Long id);
	public Optional<Producto> findBProductoByName(Long name);
	
	public Producto addProducto(Producto producto);
	public Producto updateProducto(Producto producto);
	public String delProducto(Long id);
	
	public void reponerStock(int cantidad);
}
