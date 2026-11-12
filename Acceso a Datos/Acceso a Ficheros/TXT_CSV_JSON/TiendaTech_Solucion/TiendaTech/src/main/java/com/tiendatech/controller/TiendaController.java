package com.tiendatech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiendatech.exception.TiendaException;
import com.tiendatech.model.Cliente;
import com.tiendatech.model.Direccion;
import com.tiendatech.model.EstadoPedido;
import com.tiendatech.model.Pedido;
import com.tiendatech.model.Producto;
import com.tiendatech.model.Proveedor;
import com.tiendatech.repository.ClienteRepository;
import com.tiendatech.repository.PedidoRepository;
import com.tiendatech.repository.ProductoRepository;
import com.tiendatech.service.ClienteService;
import com.tiendatech.service.InformeService;
import com.tiendatech.service.PedidoService;
import com.tiendatech.service.ProductoService;
import com.tiendatech.util.BackupUtil;
import com.tiendatech.util.Config;
import com.tiendatech.util.Rutas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Controlador: prueba TODAS las funcionalidades del enunciado.
 * Captura las excepciones y muestra mensajes limpios (sin stack traces).
 */
public class TiendaController {

    private static final Logger logger = LoggerFactory.getLogger(TiendaController.class);

    // Interfaz funcional que permite lanzar excepciones dentro de una lambda
    @FunctionalInterface
    interface Accion {
        void ejecutar() throws Exception;
    }

    private static final String MARCA_INICIO = "========== INICIO DE EJECUCIÓN ==========";

    private final Config config;
    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;
    private final PedidoRepository pedidoRepo;
    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private final InformeService informeService;

    public TiendaController() {
        config = new Config(Rutas.CONFIG);
        productoRepo = new ProductoRepository(Rutas.PRODUCTOS);
        clienteRepo = new ClienteRepository(Rutas.CLIENTES);
        pedidoRepo = new PedidoRepository(Rutas.PEDIDOS, clienteRepo, productoRepo);
        productoService = new ProductoService(productoRepo, pedidoRepo);
        clienteService = new ClienteService(clienteRepo);
        pedidoService = new PedidoService(pedidoRepo, productoRepo, clienteRepo, config);
        informeService = new InformeService(pedidoService, productoRepo, clienteRepo, Rutas.RESOURCES);
    }

    public static void main(String[] args) throws IOException {
        // SOLO PARA LA DEMO: deja los datos como al principio para que cada ejecución dé lo mismo
        resetDatos();
        new TiendaController().ejecutarDemo();
    }

    public void ejecutarDemo() {
        logger.info(MARCA_INICIO);
        cargarDatos();
        pruebasProductos();
        pruebasClientes();
        pruebasPedidos();
        pruebasInformes();
        pruebasBackup();
        pruebasVersion2();
        titulo("FIN. Revisa resources/ (tienda.log, facturas/, backup/ e informes)");
    }

    // ============================================================
    // 3. CARGA
    // ============================================================
    private void cargarDatos() {
        titulo("3. CARGA DE DATOS (productos -> clientes -> pedidos)");
        ejecutar("Cargar ficheros", () -> {
            productoRepo.cargar();   // el orden importa:
            clienteRepo.cargar();    // los pedidos necesitan productos y clientes
            pedidoRepo.cargar();
        });

        System.out.println("\nProductos cargados: " + productoRepo.findAll().size());
        productoRepo.findAll().forEach(p -> System.out.println("  " + p));
        System.out.println("\nClientes cargados: " + clienteRepo.findAll().size());
        clienteRepo.findAll().forEach(c -> System.out.println("  " + c));
        System.out.println("\nPedidos cargados: " + pedidoRepo.findAll().size());
        pedidoRepo.findAll().forEach(p -> System.out.println("  " + p));

        System.out.println("\nErrores de carga registrados con logger.error en " + Rutas.LOG + ":");
        mostrarErroresDeEstaEjecucion();
    }

    // ============================================================
    // 4. PRODUCTOS
    // ============================================================
    private void pruebasProductos() {
        titulo("4. GESTIÓN DE PRODUCTOS");

        subtitulo("Listado por categoría y precio");
        productoService.listarOrdenados().forEach(p -> System.out.println("  " + p));

        subtitulo("Buscar P009 y P099");
        ejecutar("Buscar P009", () -> System.out.println("  " + productoService.buscarPorId("P009")));
        ejecutar("Buscar P099", () -> System.out.println("  " + productoService.buscarPorId("P099")));

        subtitulo("Buscar por etiqueta 'usb'");
        productoService.buscarPorEtiqueta("usb").forEach(p -> System.out.println("  " + p));

        subtitulo("Añadir productos");
        ejecutar("Añadir P012 (válido)", () -> productoService.anadir(new Producto("P012", "Alfombrilla XL",
                "Periféricos", 19.90, 50, new ArrayList<>(List.of("gaming")))));
        ejecutar("Añadir P013 con precio 0", () -> productoService.anadir(new Producto("P013", "Regalo",
                "Varios", 0, 10, null)));
        ejecutar("Añadir P001 repetido", () -> productoService.anadir(new Producto("P001", "Otro teclado",
                "Periféricos", 50, 10, null)));

        subtitulo("Reponer stock");
        ejecutar("Reponer 5 uds. de P003", () -> System.out.println("  " + productoService.reponerStock("P003", 5)));
        ejecutar("Reponer -3 uds. de P003", () -> productoService.reponerStock("P003", -3));

        subtitulo("Eliminar P010 (está en PED-003, PENDIENTE)");
        ejecutar("Eliminar P010", () -> productoService.eliminar("P010"));
    }

    // ============================================================
    // 5. CLIENTES
    // ============================================================
    private void pruebasClientes() {
        titulo("5. GESTIÓN DE CLIENTES");

        ejecutar("Añadir cliente válido", () -> {
            Cliente c = clienteService.anadir(new Cliente(null, "Elena", "Castro Díaz", "elena.castro@mail.com",
                    null, new Direccion("Calle Mayor 5", "Madrid", "28013"),
                    new ArrayList<>(List.of("633445566")), false));
            System.out.println("  " + c);
        });
        ejecutar("Añadir cliente con email inválido", () -> clienteService.anadir(new Cliente(null, "Pedro",
                "Ruiz", "pedro@@mail", null, null, null, false)));

        ejecutar("Añadir teléfono 600112233 a C004", () -> clienteService.anadirTelefono("C004", "600112233"));
        ejecutar("Añadir teléfono 123 a C004", () -> clienteService.anadirTelefono("C004", "123"));
        ejecutar("Añadir otra vez 600112233 a C004", () -> clienteService.anadirTelefono("C004", "600112233"));
        ejecutar("Hacer VIP a C004", () -> clienteService.hacerVip("C004"));
        ejecutar("Hacer VIP a C099", () -> clienteService.hacerVip("C099"));

        ejecutar("Ver C004", () -> System.out.println("  " + clienteService.buscarPorId("C004")));
    }

    // ============================================================
    // 6. PEDIDOS
    // ============================================================
    private void pruebasPedidos() {
        titulo("6. GESTIÓN DE PEDIDOS");

        subtitulo("Crear pedidos");
        ejecutar("Pedido de 1 ud. de P011 (stock 0)", () -> pedidoService.crearPedido("C006", Map.of("P011", 1)));

        ejecutar("Pedido con P002x1 y P011x1 (no debe descontar P002)", () -> {
            Map<String, Integer> lineas = new LinkedHashMap<>();
            lineas.put("P002", 1);
            lineas.put("P011", 1);
            pedidoService.crearPedido("C006", lineas);
        });
        ejecutar("Comprobar stock de P002 (debe seguir en 60)",
                () -> System.out.println("  " + productoService.buscarPorId("P002")));

        ejecutar("Pedido de cliente inexistente", () -> pedidoService.crearPedido("C099", Map.of("P001", 1)));

        ejecutar("Pedido válido C006: 4 x P004 (deja stock bajo)", () -> {
            Pedido p = pedidoService.crearPedido("C006", Map.of("P004", 4));
            System.out.println("  Creado: " + p);
        });

        subtitulo("Cambios de estado");
        ejecutar("PED-001 ENTREGADO -> PENDIENTE", () -> pedidoService.cambiarEstado("PED-001", EstadoPedido.PENDIENTE));
        ejecutar("PED-002 ENVIADO -> ENTREGADO", () -> pedidoService.cambiarEstado("PED-002", EstadoPedido.ENTREGADO));
        ejecutar("PED-009 PENDIENTE -> ENTREGADO (se salta ENVIADO)",
                () -> pedidoService.cambiarEstado("PED-009", EstadoPedido.ENTREGADO));
        ejecutar("Cancelar PED-003 (devuelve stock)", () -> {
            pedidoService.cambiarEstado("PED-003", EstadoPedido.CANCELADO);
            System.out.println("  " + productoService.buscarPorId("P010"));
            System.out.println("  " + productoService.buscarPorId("P006"));
        });

        subtitulo("Pedidos tras los cambios");
        pedidoService.listar().forEach(p -> System.out.println("  " + p));
    }

    // ============================================================
    // 7. INFORMES
    // ============================================================
    private void pruebasInformes() {
        titulo("7. FACTURAS E INFORMES");

        ejecutar("Factura PED-001", () -> mostrarFichero(informeService.generarFactura("PED-001")));
        ejecutar("Factura PED-008", () -> mostrarFichero(informeService.generarFactura("PED-008")));
        ejecutar("Factura PED-007 (cancelado)", () -> informeService.generarFactura("PED-007"));
        ejecutar("Factura PED-099", () -> informeService.generarFactura("PED-099"));

        ejecutar("Ventas por categoría", () -> mostrarFichero(informeService.ventasPorCategoria()));
        ejecutar("Informe de clientes", () -> mostrarFichero(informeService.informeClientes()));
        ejecutar("Stock bajo (< " + config.getUmbralStockBajo() + ")",
                () -> mostrarFichero(informeService.stockBajo(config.getUmbralStockBajo())));

        subtitulo("Top 3 productos más vendidos");
        informeService.topVendidos(3).forEach(e ->
                System.out.println("  " + e.getKey().getNombre() + " -> " + e.getValue() + " uds."));

        ejecutar("Pedidos de Madrid", () -> mostrarFichero(informeService.pedidosPorCiudad("Madrid")));

        subtitulo("Eliminar P001 (solo está en pedidos ENTREGADO)");
        ejecutar("Eliminar P001", () -> productoService.eliminar("P001"));
    }

    // ============================================================
    // 9. VERSIÓN 2
    // ============================================================
    private void pruebasVersion2() {
        titulo("9. VERSIÓN 2: PROVEEDORES");

        ejecutar("Migrar productos.csv -> productos_v2.csv", () -> {
            int n = ProductoRepository.migrarAV2(Rutas.PRODUCTOS, Rutas.PRODUCTOS_V2);
            System.out.println("  " + n + " líneas migradas");
        });

        // Trabajamos sobre el CSV v2 con su propio repositorio
        ProductoRepository repoV2 = new ProductoRepository(Rutas.PRODUCTOS_V2);
        ProductoService servicioV2 = new ProductoService(repoV2, pedidoRepo);
        InformeService informesV2 = new InformeService(pedidoService, repoV2, clienteRepo, Rutas.RESOURCES);

        ejecutar("Cargar productos_v2.csv (detecta el formato por la cabecera)", repoV2::cargar);
        ejecutar("Asignar proveedores", () -> {
            servicioV2.asignarProveedor("P002", new Proveedor("Logitech", "Suiza", "ventas@logitech.ch"));
            servicioV2.asignarProveedor("P004", new Proveedor("Asus", "Taiwán", "b2b@asus.com"));
            servicioV2.asignarProveedor("P010", new Proveedor("Kingston", "Taiwán", "pedidos@kingston.tw"));
        });
        ejecutar("Volver a cargar el v2 para comprobar que se lee bien", () -> {
            repoV2.cargar();
            repoV2.findAll().forEach(p -> System.out.println("  " + p));
        });
        ejecutar("Productos de Taiwán", () -> mostrarFichero(informesV2.productosPorPais("Taiwán")));
    }

    // ============================================================
    // 8. BACKUPS
    // ============================================================
    private void pruebasBackup() {
        titulo("8. COPIAS DE SEGURIDAD");
        ejecutar("Listar copias", () -> {
            try (Stream<Path> copias = Files.list(Rutas.BACKUP)) {
                copias.sorted().forEach(p -> System.out.println("  " + p.getFileName()));
            }
        });
        ejecutar("Restaurar clientes.json desde la última copia", () -> {
            int antes = clienteRepo.findAll().size();
            BackupUtil.restaurarUltimaCopia(Rutas.CLIENTES)
                    .ifPresent(p -> System.out.println("  Restaurado desde " + p.getFileName()));
            clienteRepo.cargar();
            System.out.println("  Clientes antes: " + antes + " / después: " + clienteRepo.findAll().size());
        });
    }

    // ============================================================
    // UTILIDADES DE CONSOLA
    // ============================================================

    /** Ejecuta una acción y traduce las excepciones a mensajes para el usuario. */
    private void ejecutar(String descripcion, Accion accion) {
        System.out.println("\n> " + descripcion);
        try {
            accion.ejecutar();
            System.out.println("  OK");
        } catch (TiendaException e) {
            System.out.println("  ERROR: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error de fichero en '{}'", descripcion, e);
            System.out.println("  ERROR DE FICHERO: " + e.getMessage());
        } catch (Exception e) {
            // Los errores no previstos van al log CON la traza completa, y al usuario un mensaje limpio
            logger.error("Error inesperado en '{}'", descripcion, e);
            System.out.println("  ERROR INESPERADO: " + e.getMessage() + " (detalles en el log)");
        }
    }

    private void mostrarFichero(Path fichero) throws IOException {
        System.out.println("  --- " + fichero + " ---");
        Files.readAllLines(fichero, StandardCharsets.UTF_8).forEach(l -> System.out.println("  " + l));
    }

    /** El log se acumula entre ejecuciones (append): mostramos solo lo posterior a la última marca. */
    private void mostrarErroresDeEstaEjecucion() {
        try {
            List<String> lineas = Files.readAllLines(Rutas.LOG, StandardCharsets.UTF_8);
            int desde = 0;
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).contains(MARCA_INICIO)) desde = i;
            }
            lineas.subList(desde, lineas.size()).stream()
                    .filter(l -> l.contains("[ERROR]"))
                    .forEach(l -> System.out.println("  " + l));
        } catch (IOException e) {
            System.out.println("  (no se pudo leer el log)");
        }
    }

    private static void titulo(String texto) {
        System.out.println("\n" + "=".repeat(70) + "\n  " + texto + "\n" + "=".repeat(70));
    }

    private static void subtitulo(String texto) {
        System.out.println("\n--- " + texto + " ---");
    }

    /**
     * SOLO PARA LA DEMO: copia los ficheros de resources/originales a resources
     * y borra facturas y backups, para que cada ejecución empiece igual.
     */
    private static void resetDatos() throws IOException {
        for (String nombre : List.of("productos.csv", "clientes.json", "pedidos.txt")) {
            Files.copy(Rutas.ORIGINALES.resolve(nombre), Rutas.RESOURCES.resolve(nombre),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        // El log NO se borra: Logback lo tiene abierto y en Windows no se podría borrar.
        // Además, el log debe acumularse (append) entre ejecuciones.
        for (Path carpeta : List.of(Rutas.BACKUP, Rutas.FACTURAS)) {
            if (Files.exists(carpeta)) {
                try (Stream<Path> s = Files.walk(carpeta)) {
                    for (Path p : s.sorted(Comparator.reverseOrder()).toList()) {
                        Files.delete(p);
                    }
                }
            }
        }
    }
}
