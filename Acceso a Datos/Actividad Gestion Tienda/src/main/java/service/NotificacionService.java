package service;

import ENUM.Estado;
import ENUM.TipoMensaje;
import Entity.INotificacion;
import Entity.Pedido;
import Entity.inmpl.Email;
import Entity.inmpl.SMS;

public interface NotificacionService {

    public INotificacion obtenerNotificacion(Long id);

    public void agregarNotificacines(Pedido pedido, TipoMensaje tipo);

    public void enviarNoti(Long id, Estado estado);
}
