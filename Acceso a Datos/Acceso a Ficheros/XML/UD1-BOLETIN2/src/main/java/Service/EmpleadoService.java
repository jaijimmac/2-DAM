package Service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.Empleado;
import Entity.Puesto;
import Exceptions.EmpleadoException;
import Repository.EmpleadoRepository;

public class EmpleadoService implements IEmpleadoService {

    private static final Logger LOGGER = LogManager.getLogger(EmpleadoService.class);

    private final EmpleadoRepository repo;

    public EmpleadoService(String ruta) {
        if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta es nula o está vacía.");
        }
        this.repo = new EmpleadoRepository(ruta);
    }

    @Override
    public List<Empleado> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Empleado> findById(Long id) {
        try {
            validarId(id);
            return repo.findById(id);
        } catch (EmpleadoException e) {
            LOGGER.warn("Error al buscar: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Empleado addEmpleado(Empleado empleado) {
        try {
            validarEmpleado(empleado);
            Empleado nuevo = repo.addEmpleado(empleado);
            LOGGER.info("Empleado añadido con id " + nuevo.getId());
            return nuevo;
        } catch (EmpleadoException e) {
            LOGGER.warn("Error al añadir: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Empleado modiEmpleado(Empleado empleado) {
        try {
            if (empleado == null) {
                throw new EmpleadoException("El empleado no puede ser null.");
            }
            validarId(empleado.getId());
            validarEmpleado(empleado);
            if (!repo.findById(empleado.getId()).isPresent()) {
                throw new EmpleadoException("No existe ningún empleado con id " + empleado.getId() + ".");
            }
            Empleado modificado = repo.modiEmpleado(empleado);
            LOGGER.info("Empleado modificado con id " + modificado.getId());
            return modificado;
        } catch (EmpleadoException e) {
            LOGGER.warn("Error al modificar: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delEmpleado(Long id) {
        try {
            validarId(id);
            if (!repo.findById(id).isPresent()) {
                throw new EmpleadoException("No existe ningún empleado con id " + id + ".");
            }
            repo.delEmpleado(id);
            LOGGER.info("Empleado borrado con id " + id);
        } catch (EmpleadoException e) {
            LOGGER.warn("Error al borrar: " + e.getMessage());
        }
    }


    private void validarId(Long id) {
        if (id == null) {
            throw new EmpleadoException("El id no puede ser null.");
        }
        if (id <= 0) {
            throw new EmpleadoException("El id debe ser mayor que 0.");
        }
    }

    private void validarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new EmpleadoException("El empleado no puede ser null.");
        }
        if (empleado.getNombreApellidos() == null || empleado.getNombreApellidos().isBlank()) {
            throw new EmpleadoException("El nombre y apellidos no pueden estar vacíos.");
        }
        if (empleado.getEdad() < 0) {
            throw new EmpleadoException("La edad no puede ser negativa.");
        }
        if (empleado.getEmpresa() == null || empleado.getEmpresa().isBlank()) {
            throw new EmpleadoException("La empresa no puede estar vacía.");
        }
        if (empleado.getPuestos().isEmpty()) {
            throw new EmpleadoException("El empleado debe tener al menos un puesto.");
        }
        for (Puesto p : empleado.getPuestos()) {
            validarPuesto(p);
        }
    }

    private void validarPuesto(Puesto puesto) {
        if (puesto.getNombrePuesto() == null || puesto.getNombrePuesto().isBlank()) {
            throw new EmpleadoException("El nombre del puesto no puede estar vacío.");
        }
        if (puesto.getSalario() <= 0) {
            throw new EmpleadoException("El salario debe ser mayor que 0.");
        }
    }
}