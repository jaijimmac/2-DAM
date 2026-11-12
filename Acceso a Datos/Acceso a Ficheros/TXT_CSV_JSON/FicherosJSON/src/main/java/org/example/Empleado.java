package org.example;

public class Empleado {

    private String identificador;
    private String nombreApellido;
    private int edad;
    private String empresa;
    private Domicilio domicilio;

    public Empleado(){

    }

    public Empleado(String identificador, String nombreApellido, int edad, String empresa, Domicilio domicilio) {
        this.identificador = identificador;
        this.nombreApellido = nombreApellido;
        this.edad = edad;
        this.empresa = empresa;
        this.domicilio = domicilio;
    }

    public String getIdentificador() {

        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
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

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "identificador='" + identificador + '\'' +
                ", nombreApellido='" + nombreApellido + '\'' +
                ", edad=" + edad +
                ", empresa='" + empresa + '\'' +
                '}';
    }
}
