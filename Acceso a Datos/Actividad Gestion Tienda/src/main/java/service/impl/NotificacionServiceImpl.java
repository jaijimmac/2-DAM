package service.impl;

import Entity.inmpl.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.NotificacionRepository;
import service.NotificacionService;

public class NotificacionServiceImpl implements NotificacionService {

    private static final Logger logger = LogManager.getLogger(NotificacionServiceImpl.class);
    private NotificacionRepository notificacionRepository;

    public NotificacionServiceImpl() {
        this.notificacionRepository = new NotificacionRepository();
    }

    @Override
    public void agregarNotificacines(Email email) {
        notificacionRepository.agregarNotificacion(email);
    }

    @Override
    public void enviarEmail(Long idEmail) {
        notificacionRepository.enviarEmail(idEmail);
    }

    @Override
    public void enviarSMS(Long idSMS) {
        notificacionRepository.enviarSMS(idSMS);
    }
}
