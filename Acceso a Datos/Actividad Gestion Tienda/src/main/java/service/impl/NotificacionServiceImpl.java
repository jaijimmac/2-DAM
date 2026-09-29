package service.impl;

import ENUM.Estado;
import ENUM.TipoMensaje;
import Entity.INotificacion;
import Entity.Pedido;
import Entity.inmpl.Email;
import Entity.inmpl.SMS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.NotificacionRepository;
import service.NotificacionService;

import java.util.Optional;

public class NotificacionServiceImpl implements NotificacionService {

    private static final Logger logger = LogManager.getLogger(NotificacionServiceImpl.class);
    private NotificacionRepository notificacionRepository;
    private PedidoServiceImpl pedidoService; ;

    public NotificacionServiceImpl() {
        this.notificacionRepository = new NotificacionRepository();
        this.pedidoService = new PedidoServiceImpl();
    }


    @Override
    public INotificacion obtenerNotificacion(Long id) {
        return notificacionRepository.obtenerNotificacion(id);
    }

    @Override
    public void agregarNotificacines(Pedido pedido, TipoMensaje tipo) {
        notificacionRepository.agregarNotificacion(pedido, tipo);
    }

    @Override
    public void enviarNoti(Long id, Estado estado) {
        Pedido pedido = pedidoService.obtenerPedido(id);
        notificacionRepository.enviarNoti(pedido, estado);
    }


}
