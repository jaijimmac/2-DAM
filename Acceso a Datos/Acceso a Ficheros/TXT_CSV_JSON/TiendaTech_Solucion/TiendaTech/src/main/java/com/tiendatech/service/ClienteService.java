package com.tiendatech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiendatech.exception.DatosInvalidosException;
import com.tiendatech.exception.EntidadNoEncontradaException;
import com.tiendatech.model.Cliente;
import com.tiendatech.repository.ClienteRepository;
import com.tiendatech.util.Validador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);


    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public List<Cliente> listar() {
        return clienteRepo.findAll();
    }

    public Cliente buscarPorId(String id) throws EntidadNoEncontradaException {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el cliente " + id));
    }

    /** El id se genera automáticamente y, si no viene fecha de alta, se pone la de hoy. */
    public Cliente anadir(Cliente c) throws DatosInvalidosException, IOException {
        c.setId(clienteRepo.siguienteId());
        if (c.getFechaAlta() == null) {
            c.setFechaAlta(LocalDate.now());
        }
        if (c.getTelefonos() == null) {
            c.setTelefonos(new ArrayList<>());
        }
        try {
            Validador.validarCliente(c);
            for (String t : c.getTelefonos()) {
                Validador.validarTelefono(t);
            }
        } catch (DatosInvalidosException e) {
            logger.error("No se ha podido dar de alta al cliente " + c.getNombreCompleto()
                    + ": " + e.getMessage());
            throw e;
        }
        clienteRepo.save(c);
        clienteRepo.guardar();
        logger.info("Cliente " + c.getId() + " dado de alta correctamente");
        return c;
    }

    public void anadirTelefono(String idCliente, String telefono)
            throws EntidadNoEncontradaException, DatosInvalidosException, IOException {
        Cliente c = buscarPorId(idCliente);
        try {
            Validador.validarTelefono(telefono);
            if (c.getTelefonos().contains(telefono)) {
                throw new DatosInvalidosException("el teléfono " + telefono + " ya está registrado");
            }
        } catch (DatosInvalidosException e) {
            logger.error("No se ha podido añadir el teléfono a " + idCliente + ": " + e.getMessage());
            throw e;
        }
        c.getTelefonos().add(telefono);
        clienteRepo.guardar();
        logger.info("Teléfono " + telefono + " añadido a " + idCliente);
    }

    public void hacerVip(String idCliente) throws EntidadNoEncontradaException, IOException {
        Cliente c = buscarPorId(idCliente);
        if (c.isVip()) {
            logger.warn("El cliente " + idCliente + " ya era VIP");
            return;
        }
        c.setVip(true);
        clienteRepo.guardar();
        logger.info("Cliente " + idCliente + " ahora es VIP");
    }
}
