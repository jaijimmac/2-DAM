package com.tiendatech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiendatech.exception.DatosInvalidosException;
import com.tiendatech.exception.EntidadNoEncontradaException;
import com.tiendatech.exception.OperacionNoPermitidaException;
import com.tiendatech.exception.StockInsuficienteException;
import com.tiendatech.model.Cliente;
import com.tiendatech.model.EstadoPedido;
import com.tiendatech.model.LineaPedido;
import com.tiendatech.model.Pedido;
import com.tiendatech.model.Producto;
import com.tiendatech.repository.ClienteRepository;
import com.tiendatech.repository.PedidoRepository;
import com.tiendatech.repository.ProductoRepository;
import com.tiendatech.util.Config;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);


    /** Desglose del importe de un pedido. */
    public record Totales(double subtotal, double descuento, double base, double iva, double total) {}

    private final PedidoRepository pedidoRepo;
    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;
    private final Config config;

    public PedidoService(PedidoRepository pedidoRepo, ProductoRepository productoRepo,
                         ClienteRepository clienteRepo, Config config) {
        this.pedidoRepo = pedidoRepo;
        this.productoRepo = productoRepo;
        this.clienteRepo = clienteRepo;
        this.config = config;
    }

    public List<Pedido> listar() {
        return pedidoRepo.findAll();
    }

    public List<Pedido> listarVentas() {
        return pedidoRepo.findAll().stream()
                .filter(p -> p.getEstado().esVenta())
                .collect(Collectors.toList());
    }

    public Pedido buscarPorId(String id) throws EntidadNoEncontradaException {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el pedido " + id));
    }

    // ===================== CREAR PEDIDO =====================

    /**
     * @param cantidades idProducto -> cantidad (usar LinkedHashMap para mantener el orden)
     */
    public Pedido crearPedido(String idCliente, Map<String, Integer> cantidades)
            throws EntidadNoEncontradaException, DatosInvalidosException,
                   StockInsuficienteException, IOException {

        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el cliente " + idCliente));

        if (cantidades == null || cantidades.isEmpty()) {
            throw new DatosInvalidosException("el pedido no tiene productos");
        }

        // 1ª pasada: comprobar TODO antes de tocar nada
        Map<Producto, Integer> lineas = new LinkedHashMap<>();
        List<String> sinStock = new ArrayList<>();
        for (Map.Entry<String, Integer> e : cantidades.entrySet()) {
            Producto p = productoRepo.findById(e.getKey())
                    .orElseThrow(() -> new EntidadNoEncontradaException("No existe el producto " + e.getKey()));
            int cantidad = e.getValue();
            if (cantidad <= 0) {
                throw new DatosInvalidosException("cantidad no válida para " + p.getId() + ": " + cantidad);
            }
            if (p.getStock() < cantidad) {
                sinStock.add(p.getId() + " (pedidas " + cantidad + ", hay " + p.getStock() + ")");
            }
            lineas.put(p, cantidad);
        }
        if (!sinStock.isEmpty()) {
            String msg = "Stock insuficiente para el pedido de " + idCliente + ": " + String.join(", ", sinStock);
            logger.error(msg);
            throw new StockInsuficienteException(msg);
        }

        // 2ª pasada: ahora sí, descontar stock y crear las líneas
        List<LineaPedido> lineasPedido = new ArrayList<>();
        for (Map.Entry<Producto, Integer> e : lineas.entrySet()) {
            Producto p = e.getKey();
            p.setStock(p.getStock() - e.getValue());
            lineasPedido.add(new LineaPedido(p, e.getValue(), p.getPrecio()));
            if (p.getStock() < config.getUmbralStockBajo()) {
                logger.warn("Stock bajo del producto " + p.getId() + " (quedan " + p.getStock() + " unidades)");
            }
        }

        Pedido pedido = new Pedido(pedidoRepo.siguienteId(), LocalDate.now(), cliente,
                lineasPedido, EstadoPedido.PENDIENTE);
        pedidoRepo.anadir(pedido);   // append en pedidos.txt
        productoRepo.guardar();      // el stock ha cambiado
        logger.info("Pedido " + pedido.getId() + " creado para " + idCliente);
        return pedido;
    }

    // ===================== CAMBIAR ESTADO =====================

    public void cambiarEstado(String idPedido, EstadoPedido nuevo)
            throws EntidadNoEncontradaException, OperacionNoPermitidaException, IOException {
        Pedido p = buscarPorId(idPedido);
        EstadoPedido actual = p.getEstado();

        if (!actual.puedePasarA(nuevo)) {
            String msg = "El pedido " + idPedido + " no puede pasar de " + actual + " a " + nuevo;
            logger.error(msg);
            throw new OperacionNoPermitidaException(msg);
        }

        if (nuevo == EstadoPedido.CANCELADO) {
            // Se devuelve el stock
            for (LineaPedido l : p.getLineas()) {
                Producto prod = l.getProducto();
                prod.setStock(prod.getStock() + l.getCantidad());
            }
            productoRepo.guardar();
        }

        p.setEstado(nuevo);
        pedidoRepo.guardarTodo();
        logger.info("Pedido " + idPedido + ": " + actual + " -> " + nuevo);
    }

    // ===================== IMPORTES =====================

    /** Descuento VIP sobre el subtotal, después IVA. Se redondea en cada paso (como en una factura). */
    public Totales calcularTotales(Pedido p) {
        double subtotal = redondear(p.getSubtotal());
        double descuento = p.getCliente().isVip() ? redondear(subtotal * config.getDescuentoVip()) : 0;
        double base = redondear(subtotal - descuento);
        double iva = redondear(base * config.getIva());
        double total = redondear(base + iva);
        return new Totales(subtotal, descuento, base, iva, total);
    }

    public static double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public Config getConfig() {
        return config;
    }
}
