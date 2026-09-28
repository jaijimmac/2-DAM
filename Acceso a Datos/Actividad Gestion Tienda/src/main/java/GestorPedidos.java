import ENUM.Estado;
import Entity.Cliente;
import Entity.Pedido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.NotificacionService;
import service.impl.ClienteServiceImpl;
import service.impl.NotificacionServiceImpl;
import service.impl.PedidoServiceImpl;

import java.util.Set;

public class GestorPedidos {

	private static final Logger logger = LogManager.getLogger(GestorPedidos.class);

	private static ClienteServiceImpl clienteService = new ClienteServiceImpl();

	private static PedidoServiceImpl pedidoService = new PedidoServiceImpl();

	public static void main(String[] args) {

		Cliente newCliente1 = new Cliente("prueba@gmail.com","Jaime","604254345");
		Cliente newCliente2 = new Cliente("prueba2@gmail.com","Pedro","5758493746");

		Cliente c1 = clienteService.crearCliente(newCliente1);
		Cliente c2 = clienteService.crearCliente(newCliente2);

		Pedido newPedido1 = new Pedido(c1,24.5);
		Pedido newPedido2 = new Pedido(c1,33.0);
		Pedido newPedido3 = new Pedido(c2,10.0);

		Pedido p1 = pedidoService.crearPedido(newPedido1);
		Pedido p2 = pedidoService.crearPedido(newPedido2);
		Pedido p3 = pedidoService.crearPedido(newPedido3);


		Set<Cliente> listadoClientes = clienteService.obtenerClientes();
		logger.debug("");
		logger.debug("Listado de Clientes");
		logger.debug("___________________");
		for (Cliente cliente : listadoClientes) {
			logger.debug(cliente.toString());
		}
		logger.debug("");
		logger.debug("");
		logger.debug("");

		Set<Pedido> listadoPedidos = pedidoService.obtenerPedidos();

		logger.debug("Listado de Pedidos");
		logger.debug("___________________");
		for (Pedido pedido: listadoPedidos) {
			logger.debug(pedido.toString());
		}

		pedidoService.editarEstado(p1.getId(),Estado.CONFIRMADO);
		pedidoService.editarEstado(p2.getId(),Estado.CONFIRMADO);
		pedidoService.editarEstado(p3.getId(),Estado.CANCELADO);

		logger.debug("Listado de Pedidos Actualizados");
		logger.debug("___________________");
		for (Pedido pedido: listadoPedidos) {
			logger.debug(pedido.toString());
		}
	}
}

