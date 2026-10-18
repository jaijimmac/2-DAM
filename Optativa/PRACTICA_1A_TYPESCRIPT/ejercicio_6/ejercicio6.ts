class Almacen<T> {
    private elementos: T[] = [];

    public agregarElemento(elemento: T): void {
        this.elementos.push(elemento);
    }

    public obtenerElemento(posicion: number): T | undefined {
        return this.elementos[posicion];
    }

    public listarElementos(): T[] {
        return this.elementos;
    }
}


const almacenNumeros = new Almacen<number>();

almacenNumeros.agregarElemento(10);
almacenNumeros.agregarElemento(20);
almacenNumeros.agregarElemento(30);

console.log("Números almacenados:", almacenNumeros.listarElementos());
console.log("Elemento en la posición 1:", almacenNumeros.obtenerElemento(1));


const almacenTextos = new Almacen<string>();

almacenTextos.agregarElemento("Hola");
almacenTextos.agregarElemento("Mundo");
almacenTextos.agregarElemento("TypeScript");

console.log("Textos almacenados:", almacenTextos.listarElementos());
console.log("Elemento en la posición 0:", almacenTextos.obtenerElemento(0));


type Persona = {
    nombre: string;
    edad: number;
};

const almacenPersonas = new Almacen<Persona>();

almacenPersonas.agregarElemento({ nombre: "Ana", edad: 25 });
almacenPersonas.agregarElemento({ nombre: "Carlos", edad: 30 });

console.log("Personas almacenadas:", almacenPersonas.listarElementos());
console.log("Persona en la posición 1:", almacenPersonas.obtenerElemento(1));
