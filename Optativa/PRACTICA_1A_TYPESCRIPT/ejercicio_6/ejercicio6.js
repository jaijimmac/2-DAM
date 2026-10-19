"use strict";
class Almacen {
    constructor() {
        this.elementos = [];
    }
    agregarElemento(elemento) {
        this.elementos.push(elemento);
    }
    obtenerElemento(posicion) {
        return this.elementos[posicion];
    }
    listarElementos() {
        return this.elementos;
    }
}
const almacenNumeros = new Almacen();
almacenNumeros.agregarElemento(10);
almacenNumeros.agregarElemento(20);
almacenNumeros.agregarElemento(30);
console.log("Números almacenados:", almacenNumeros.listarElementos());
console.log("Elemento en la posición 1:", almacenNumeros.obtenerElemento(1));
const almacenTextos = new Almacen();
almacenTextos.agregarElemento("Hola");
almacenTextos.agregarElemento("Mundo");
almacenTextos.agregarElemento("TypeScript");
console.log("Textos almacenados:", almacenTextos.listarElementos());
console.log("Elemento en la posición 0:", almacenTextos.obtenerElemento(0));
const almacenPersonas = new Almacen();
almacenPersonas.agregarElemento({ nombre: "Ana", edad: 25 });
almacenPersonas.agregarElemento({ nombre: "Carlos", edad: 30 });
console.log("Personas almacenadas:", almacenPersonas.listarElementos());
console.log("Persona en la posición 1:", almacenPersonas.obtenerElemento(1));
//# sourceMappingURL=ejercicio6.js.map