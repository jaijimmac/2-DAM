package com.tiendatech.exception;

/** Para operaciones que los datos permiten pero las reglas de negocio no
 *  (transiciones de estado, borrar un producto con pedidos activos...). */
public class OperacionNoPermitidaException extends TiendaException {
    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
