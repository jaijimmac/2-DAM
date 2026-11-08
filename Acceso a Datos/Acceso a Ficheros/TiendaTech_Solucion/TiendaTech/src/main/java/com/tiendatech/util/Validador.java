package com.tiendatech.util;

import com.tiendatech.exception.DatosInvalidosException;
import com.tiendatech.model.Cliente;
import com.tiendatech.model.Producto;

import java.util.regex.Pattern;

/**
 * Validaciones compartidas. Las usan tanto los repositorios (al cargar ficheros)
 * como los servicios (al dar de alta), así la regla está escrita una sola vez.
 */
public final class Validador {

    private Validador() {}

    private static final Pattern EMAIL = Pattern.compile("^[\\w.-]+@[\\w-]+\\.[a-z]{2,}$");
    private static final Pattern TELEFONO = Pattern.compile("^\\d{9}$");

    public static void validarProducto(Producto p) throws DatosInvalidosException {
        if (vacio(p.getId()))        throw new DatosInvalidosException("falta el id");
        if (vacio(p.getNombre()))    throw new DatosInvalidosException("falta el nombre");
        if (vacio(p.getCategoria())) throw new DatosInvalidosException("falta la categoría");
        if (p.getPrecio() <= 0)      throw new DatosInvalidosException("precio no válido (" + p.getPrecio() + ")");
        if (p.getStock() < 0)        throw new DatosInvalidosException("stock negativo (" + p.getStock() + ")");
    }

    public static void validarCliente(Cliente c) throws DatosInvalidosException {
        if (vacio(c.getId()))     throw new DatosInvalidosException("falta el id");
        if (vacio(c.getNombre())) throw new DatosInvalidosException("falta el nombre");
        if (vacio(c.getEmail()))  throw new DatosInvalidosException("falta el email");
        if (!EMAIL.matcher(c.getEmail()).matches())
            throw new DatosInvalidosException("email no válido (" + c.getEmail() + ")");
        if (c.getFechaAlta() == null) throw new DatosInvalidosException("falta la fecha de alta");
    }

    public static void validarTelefono(String telefono) throws DatosInvalidosException {
        if (telefono == null || !TELEFONO.matcher(telefono).matches())
            throw new DatosInvalidosException("teléfono no válido (" + telefono + "): debe tener 9 dígitos");
    }

    public static boolean vacio(String s) {
        return s == null || s.isBlank();
    }
}
