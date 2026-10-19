"use strict";
class Animal {
    constructor(nombre, edad, tipo) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipo = tipo;
    }
    getNombre() {
        return this.nombre;
    }
    setNombre(nombre) {
        this.nombre = nombre;
    }
    getEdad() {
        return this.edad;
    }
    setEdad(edad) {
        this.edad = edad;
    }
    getTipo() {
        return this.tipo;
    }
    setTipo(tipo) {
        this.tipo = tipo;
    }
    hacerRuido() {
        console.log("El animal hace un ruido.");
    }
}
class Perro extends Animal {
    constructor(nombre, edad, tipo, raza) {
        super(nombre, edad, tipo);
        this.raza = raza;
    }
    getRaza() {
        return this.raza;
    }
    setRaza(raza) {
        this.raza = raza;
    }
    ladrar() {
        console.log("Guau");
    }
    hacerRuido() {
        console.log("El perro ladra");
    }
}
class Pajaro extends Animal {
    constructor(nombre, edad, tipo, puedeVolar) {
        super(nombre, edad, tipo);
        this.puedeVolar = puedeVolar;
    }
    getPuedeVolar() {
        return this.puedeVolar;
    }
    setPuedeVolar(puedeVolar) {
        this.puedeVolar = puedeVolar;
    }
    volar() {
        if (this.puedeVolar) {
            console.log("El pájaro está volando.");
        }
        else {
            console.log("Este pájaro no puede volar.");
        }
    }
    hacerRuido() {
        console.log("El pájaro canta");
    }
}
const perro1 = new Perro("Rocky", 5, "Mamífero", "Labrador");
const pajaro1 = new Pajaro("Piolín", 2, "Ave", true);
console.log("Nombre:", perro1.getNombre());
console.log("Edad:", perro1.getEdad());
console.log("Tipo:", perro1.getTipo());
console.log("Raza:", perro1.getRaza());
perro1.hacerRuido();
perro1.ladrar();
console.log("-------------------");
console.log("Nombre:", pajaro1.getNombre());
console.log("Edad:", pajaro1.getEdad());
console.log("Tipo:", pajaro1.getTipo());
console.log("¿Puede volar?:", pajaro1.getPuedeVolar());
pajaro1.hacerRuido();
pajaro1.volar();
//# sourceMappingURL=ejercicio4.js.map