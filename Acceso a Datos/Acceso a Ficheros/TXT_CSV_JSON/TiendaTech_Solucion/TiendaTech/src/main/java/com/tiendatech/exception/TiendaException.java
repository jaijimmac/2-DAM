package com.tiendatech.exception;

/** Clase base de todas las excepciones de negocio de la tienda. */
public class TiendaException extends Exception {
    public TiendaException(String mensaje) {
        super(mensaje);
    }
}
