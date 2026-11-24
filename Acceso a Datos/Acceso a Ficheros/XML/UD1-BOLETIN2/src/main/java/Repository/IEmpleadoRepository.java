package Repository;

import java.util.List;
import java.util.Optional;

import Entity.Empleado;

public interface IEmpleadoRepository {

    List<Empleado> findAll();

    Optional<Empleado> findById(Long id);

    Empleado addEmpleado(Empleado empleado);

    Empleado modiEmpleado(Empleado empleado);

    void delEmpleado(Long id);
}