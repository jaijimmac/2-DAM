package org.example;

public class ELdenException extends RuntimeException {

    public ELdenException(Long id) {
        super("No existe el SinLuz con el id: " + id);
    }

    public ELdenException(String nombreEncuentro) {
        super("El encuentro '" + nombreEncuentro + "' ya ha sido añadido.");
    }
}

