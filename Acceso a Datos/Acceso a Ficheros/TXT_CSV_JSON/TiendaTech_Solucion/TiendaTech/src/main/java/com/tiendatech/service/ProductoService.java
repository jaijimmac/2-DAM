package com.tiendatech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiendatech.exception.DatosInvalidosException;
import com.tiendatech.exception.EntidadNoEncontradaException;
import com.tiendatech.exception.OperacionNoPermitidaException;
import com.tiendatech.model.EstadoPedido;
import com.tiendatech.model.Pedido;
import com.tiendatech.model.Producto;
import com.tiendatech.model.Proveedor;
import com.tiendatech.repository.PedidoRepository;
import com.tiendatech.repository.ProductoRepository;
import com.tiendatech.util.Validador;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);


    private final ProductoRepository productoRepo;
    private final PedidoRepository pedidoRepo;

    public ProductoService(ProductoRepository productoRepo, PedidoRepository pedidoRepo) {
        this.productoRepo = productoRepo;
        this.pedidoRepo = pedidoRepo;
    }

    /** Ordenados por categoría y, dentro de cada una, por precio. */
    public List<Producto> listarOrdenados() {
        return productoRepo.findAll().stream()
                .sorted(Comparator.comparing(Producto::getCategoria)
                        .thenComparingDouble(Producto::getPrecio))
                .collect(Collectors.toList());
    }

    public Producto buscarPorId(String id) throws EntidadNoEncontradaException {
        return productoRepo.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el producto " + id));
    }

    public List<Producto> buscarPorEtiqueta(String etiqueta) {
        return productoRepo.findAll().stream()
                .filter(p -> p.tieneEtiqueta(etiqueta))
                .collect(Collectors.toList());
    }

    public void anadir(Producto p) throws DatosInvalidosException, IOException {
        try {
            Validador.validarProducto(p);
            if (productoRepo.existe(p.getId())) {
                throw new DatosInvalidosException("ya existe un producto con id " + p.getId());
            }
        } catch (DatosInvalidosException e) {
            logger.error("No se ha podido dar de alta el producto "
                    + (p.getNombre() == null ? p.getId() : p.getNombre()) + ": " + e.getMessage());
            throw e;
        }
        productoRepo.save(p);
        productoRepo.guardar();
        logger.info("Producto " + p.getId() + " dado de alta correctamente");
    }

    /**
     * No se puede eliminar un producto que esté en un pedido PENDIENTE o ENVIADO.
     */
    public void eliminar(String id) throws EntidadNoEncontradaException, OperacionNoPermitidaException, IOException {
        buscarPorId(id);
        List<String> activos = pedidoRepo.findAll().stream()
                .filter(p -> p.contieneProducto(id))
                .filter(p -> p.getEstado() == EstadoPedido.PENDIENTE || p.getEstado() == EstadoPedido.ENVIADO)
                .map(Pedido::getId)
                .collect(Collectors.toList());

        if (!activos.isEmpty()) {
            String msg = "No se ha podido dar de baja el producto " + id
                    + ": aparece en pedidos activos " + activos;
            logger.error(msg);
            throw new OperacionNoPermitidaException(msg);
        }
        productoRepo.delete(id);
        productoRepo.guardar();
        logger.info("Producto " + id + " dado de baja");
    }

    public Producto reponerStock(String id, int unidades)
            throws EntidadNoEncontradaException, DatosInvalidosException, IOException {
        if (unidades <= 0) {
            throw new DatosInvalidosException("las unidades a reponer deben ser positivas");
        }
        Producto p = buscarPorId(id);
        p.setStock(p.getStock() + unidades);
        productoRepo.guardar();
        logger.info("Repuestas " + unidades + " uds. de " + id + " (stock: " + p.getStock() + ")");
        return p;
    }

    /** Versión 2. */
    public void asignarProveedor(String id, Proveedor proveedor) throws EntidadNoEncontradaException, IOException {
        Producto p = buscarPorId(id);
        p.setProveedor(proveedor);
        productoRepo.guardar();
        logger.info("Proveedor " + proveedor + " asignado a " + id);
    }
}
