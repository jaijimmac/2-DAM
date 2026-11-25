package Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.Empleado;
import Entity.Puesto;
import Exceptions.EmpleadoException;
import Utilles.UtillesXMLDom;

public class EmpleadoRepository implements IEmpleadoRepository {

    private static final Logger LOGGER = LogManager.getLogger(EmpleadoRepository.class);

    private final File fichero;
    private final UtillesXMLDom utilXml;
    private List<Empleado> listadoEmpleados;

    private long siguienteIdEmpleado = 1;
    private long siguienteIdPuesto = 1;

    public EmpleadoRepository(String ruta) {
        this.fichero = new File(ruta);
        this.utilXml = new UtillesXMLDom();
        this.listadoEmpleados = leerArchivo();
        calcularSiguientesIds();
    }

    @Override
    public List<Empleado> findAll() {
        return new ArrayList<>(this.listadoEmpleados);
    }

    @Override
    public Optional<Empleado> findById(Long id) {
        return this.listadoEmpleados
                .stream()
                .filter(e -> e.getId() != null && e.getId().equals(id))
                .findFirst();
    }

    @Override
    public Empleado addEmpleado(Empleado empleado) {
        Empleado nuevo = new Empleado(empleado.getNombreApellidos(), empleado.getEdad(),
                empleado.getEmpresa(), null);
        nuevo.setId(siguienteIdEmpleado++);

        for (Puesto p : empleado.getPuestos()) {
            nuevo.addPuesto(copiarPuesto(p, generarIdPuesto()));
        }

        this.listadoEmpleados.add(nuevo);
        escribirFichero();
        return nuevo;
    }
   

    @Override
    public Empleado modiEmpleado(Empleado empleado) {
        Optional<Empleado> antiguo = findById(empleado.getId());

        if (antiguo.isPresent()) {
            Empleado e = antiguo.get();
            e.setNombreApellidos(empleado.getNombreApellidos());
            e.setEdad(empleado.getEdad());
            e.setEmpresa(empleado.getEmpresa());

            List<Puesto> puestos = new ArrayList<>();
            for (Puesto p : empleado.getPuestos()) {
                String id = (p.getId() != null) ? p.getId() : generarIdPuesto();
                puestos.add(copiarPuesto(p, id));
            }
            e.setPuestos(puestos);

            escribirFichero();
            return e;
        }
        return null;
    }

    @Override
    public void delEmpleado(Long id) {
        if (this.listadoEmpleados.removeIf(e -> e.getId() != null && e.getId().equals(id))) {
            escribirFichero();
        }
    }

    private List<Empleado> leerArchivo() {
        try {
            return utilXml.leerEmpleados(this.fichero);
        } catch (EmpleadoException e) {
            LOGGER.error("No se pudo leer el fichero " + fichero.getName() + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void escribirFichero() {
        utilXml.escribirEmpleados(this.listadoEmpleados, this.fichero);
    }

    private Puesto copiarPuesto(Puesto p, String id) {
        Puesto copia = new Puesto(p.getNombrePuesto(), p.getDepartamento(), p.getSalario());
        copia.setId(id);
        return copia;
    }

    private String generarIdPuesto() {
        return "p" + siguienteIdPuesto++;
    }

    private void calcularSiguientesIds() {
        for (Empleado e : listadoEmpleados) {
            if (e.getId() != null && e.getId() >= siguienteIdEmpleado) {
                siguienteIdEmpleado = e.getId() + 1;
            }
            for (Puesto p : e.getPuestos()) {
                long numero = numeroDePuesto(p.getId());
                if (numero >= siguienteIdPuesto) {
                    siguienteIdPuesto = numero + 1;
                }
            }
        }
        for (Empleado e : listadoEmpleados) {
            for (Puesto p : e.getPuestos()) {
                if (p.getId() == null) {
                    p.setId(generarIdPuesto());
                }
            }
        }
    }

    private long numeroDePuesto(String id) {
        if (id != null && id.matches("p\\d+")) {
            return Long.parseLong(id.substring(1));
        }
        return -1;
    }
}