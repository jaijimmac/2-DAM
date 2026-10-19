"use strict";
class Laptop {
    constructor(nombre, modelo) {
        this.nombre = nombre;
        this.modelo = modelo;
        this.encendido = false;
    }
    encender() {
        this.encendido = true;
        console.log(`${this.nombre} está encendida.`);
    }
    apagar() {
        this.encendido = false;
        console.log(`${this.nombre} está apagada.`);
    }
    abrirPrograma(programa) {
        if (this.encendido) {
            console.log(`Abriendo ${programa} en la laptop ${this.modelo}.`);
        }
        else {
            console.log("No se puede abrir un programa. La laptop está apagada.");
        }
    }
}
class Smartphone {
    constructor(nombre, marca) {
        this.nombre = nombre;
        this.marca = marca;
        this.encendido = false;
    }
    encender() {
        this.encendido = true;
        console.log(`${this.nombre} está encendido.`);
    }
    apagar() {
        this.encendido = false;
        console.log(`${this.nombre} está apagado.`);
    }
    llamar(contacto) {
        if (this.encendido) {
            console.log(`Llamando a ${contacto} desde el ${this.marca}.`);
        }
        else {
            console.log("No se puede llamar. El smartphone está apagado.");
        }
    }
}
const laptop1 = new Laptop("Mi portátil", "HP Pavilion");
const smartphone1 = new Smartphone("Mi móvil", "Samsung");
laptop1.encender();
laptop1.abrirPrograma("Visual Studio Code");
laptop1.apagar();
console.log("------------------------");
smartphone1.encender();
smartphone1.llamar("Carlos");
smartphone1.apagar();
//# sourceMappingURL=ejercicio5.js.map