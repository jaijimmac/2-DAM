package Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.Empleado;
import Entity.Puesto;
import Service.EmpleadoService;

public class GestionController {

    private static final Logger LOGGER = LogManager.getLogger(GestionController.class);

    public static final String RUTA = "src/main/resources/empleados.xml";
    public static final String RUTA_100 = "src/main/resources/empleados_100.xml";

    public static void main(String[] args) {

        EmpleadoService empleadoService = new EmpleadoService(RUTA);

        if (empleadoService.findAll().isEmpty()) {
            Puesto p1 = new Puesto("Desarrollador", "Desarrollo", 20505);
            Empleado e1 = new Empleado("Jaime Jiménez", 23, "SOL-X", p1);
            e1.addPuesto(new Puesto("Responsable de equipo", "Desarrollo", 3000));

            Puesto p2 = new Puesto("Diseñadora UX/UI", "Diseño", 24000);
            Empleado e2 = new Empleado("Lucía Fernández", 27, "SOL-X", p2);

            Puesto p3 = new Puesto("Administrador de Sistemas", "Sistemas", 28500);
            Empleado e3 = new Empleado("Carlos Ruiz Martín", 35, "TechNova", p3);

            Puesto p4 = new Puesto("Analista de Datos", "Business Intelligence", 31000);
            Empleado e4 = new Empleado("Marta Gómez Sánchez", 30, "DataCorp", p4);

            Puesto p5 = new Puesto("Jefe de Proyecto", "Gestión", 42000);
            Empleado e5 = new Empleado("Antonio López Vega", 44, "SOL-X", p5);

            List<Empleado> nuevos = List.of(e1, e2, e3, e4, e5);
            for (Empleado e : nuevos) {
                empleadoService.addEmpleado(e);
            }
        }

        EmpleadoService lectura = new EmpleadoService(RUTA);
        LOGGER.info("Empleados leídos de empleados.xml:");
        lectura.findAll().forEach(e -> LOGGER.info("  " + e));

        EmpleadoService service100 = new EmpleadoService(RUTA_100);
        LOGGER.info("Total cargados de empleados_100.xml: " + service100.findAll().size());
    }
}