interface DispositivoElectronico {
    nombre: string;
    encendido: boolean;

    encender(): void;
    apagar(): void;
}

class Laptop implements DispositivoElectronico {
    nombre: string;
    encendido: boolean;
    modelo: string;

    constructor(nombre: string, modelo: string) {
        this.nombre = nombre;
        this.modelo = modelo;
        this.encendido = false;
    }

    encender(): void {
        this.encendido = true;
        console.log(`${this.nombre} está encendida.`);
    }

    apagar(): void {
        this.encendido = false;
        console.log(`${this.nombre} está apagada.`);
    }

    abrirPrograma(programa: string): void {
        if (this.encendido) {
            console.log(`Abriendo ${programa} en la laptop ${this.modelo}.`);
        } else {
            console.log("No se puede abrir un programa. La laptop está apagada.");
        }
    }
}

class Smartphone implements DispositivoElectronico {
    nombre: string;
    encendido: boolean;
    marca: string;

    constructor(nombre: string, marca: string) {
        this.nombre = nombre;
        this.marca = marca;
        this.encendido = false;
    }

    encender(): void {
        this.encendido = true;
        console.log(`${this.nombre} está encendido.`);
    }

    apagar(): void {
        this.encendido = false;
        console.log(`${this.nombre} está apagado.`);
    }

    llamar(contacto: string): void {
        if (this.encendido) {
            console.log(`Llamando a ${contacto} desde el ${this.marca}.`);
        } else {
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
