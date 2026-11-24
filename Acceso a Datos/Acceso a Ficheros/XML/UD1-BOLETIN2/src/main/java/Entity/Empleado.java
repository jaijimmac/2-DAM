package Entity;

import java.util.ArrayList;
import java.util.List;

public class Empleado {

    private Long id;
    private String nombreApellidos;
    private int edad;
    private String empresa;
    private List<Puesto> puestos = new ArrayList<>();

    public Empleado(String nombreApellidos, String empresa, Puesto puesto) {
        this.nombreApellidos = nombreApellidos;
        this.empresa = empresa;
        addPuesto(puesto);
    }

    public Empleado(String nombreApellidos, int edad, String empresa, Puesto puesto) {
        this(nombreApellidos, empresa, puesto);
        this.edad = edad;
    }

    public void addPuesto(Puesto puesto) {
        if (puesto != null) {
            this.puestos.add(puesto);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<Puesto> puestos) {
        this.puestos = (puestos != null) ? puestos : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Empleado [id=" + id + ", nombreApellidos=" + nombreApellidos + ", edad=" + edad
                + ", empresa=" + empresa + ", puestos=" + puestos + "]";
    }
}