package org.example;

public class ELdenException extends RuntimeException {
    public ELdenException(Long id) {
        super("No existe el SinLuz con el id:" + id);
    }
}


