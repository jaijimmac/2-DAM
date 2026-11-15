package com.tiendatech.model;

public enum EstadoPedido {
    PENDIENTE, ENVIADO, ENTREGADO, CANCELADO;

    /**
     * Transiciones permitidas:
     *   PENDIENTE -> ENVIADO -> ENTREGADO
     *   PENDIENTE -> CANCELADO
     */
    public boolean puedePasarA(EstadoPedido nuevo) {
        switch (this) {
            case PENDIENTE: return nuevo == ENVIADO || nuevo == CANCELADO;
            case ENVIADO:   return nuevo == ENTREGADO;
            default:        return false; // ENTREGADO y CANCELADO son estados finales
        }
    }

    /** Solo cuentan como venta los pedidos enviados o entregados. */
    public boolean esVenta() {
        return this == ENVIADO || this == ENTREGADO;
    }
}
