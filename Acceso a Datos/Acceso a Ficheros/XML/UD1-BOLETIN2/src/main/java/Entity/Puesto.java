package Entity;

public class Puesto {

    private String id;
    private String nombrePuesto;
    private String departamento;
    private int salario;

    public Puesto(String nombrePuesto, int salario) {
        this.nombrePuesto = nombrePuesto;
        this.salario = salario;
    }

    public Puesto(String nombrePuesto, String departamento, int salario) {
        this(nombrePuesto, salario);
        this.departamento = departamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Puesto [id=" + id + ", nombrePuesto=" + nombrePuesto + ", departamento=" + departamento
                + ", salario=" + salario + "]";
    }
}