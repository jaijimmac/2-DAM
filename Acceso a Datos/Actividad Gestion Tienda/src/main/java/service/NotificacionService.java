package service;

import Entity.inmpl.Email;

public interface NotificacionService {

    public void agregarNotificacines(Email email);

    public void enviarEmail(Long idEmail);

    public void enviarSMS(Long idSMS);
}
