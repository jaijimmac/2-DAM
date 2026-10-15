"use strict";
class Employee {
    constructor(nombre, apellido, edad, fechaInicio, fechaFin, profesion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.profesion = profesion;
    }
    getNombre() {
        return this.nombre;
    }
    setNombre(value) {
        this.nombre = value;
    }
    getApellido() {
        return this.apellido;
    }
    setApellido(value) {
        this.apellido = value;
    }
    getEdad() {
        return this.edad;
    }
    setEdad(value) {
        this.edad = value;
    }
    getFechaInicio() {
        return this.fechaInicio;
    }
    setFechaInicio(value) {
        return this.fechaInicio = value;
    }
    getFechaFin() {
        return this.fechaFin;
    }
    setFechaFin(value) {
        this.fechaFin = value;
    }
    getProfesion() {
        return this.profesion;
    }
    setProfesion(value) {
        this.profesion = value;
    }
}
var empleado1;
var empleado2;
empleado1 = new Employee('Jaime', 'Jiménez', 23, new Date, null, 'Programador');
empleado2 = new Employee('Alba', 'García', 23, new Date, new Date, 'Administrativa');
console.log(empleado1);
console.log(empleado2);
//# sourceMappingURL=ejercicio3.js.map