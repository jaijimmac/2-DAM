class Animal {
    private nombre: string;
    private edad: number;
    private tipo: string;


    constructor(nombre: string, edad: number, tipo: string) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipo = tipo;
    }


    public getNombre(): string {
        return this.nombre;
    }

    public setNombre(nombre: string) {
        this.nombre = nombre;
    }

    public getEdad(): number {
        return this.edad;
    }

    public setEdad(edad: number) {
        this.edad = edad;
    }

    public getTipo(): string {
        return this.tipo;
    }

    public setTipo(tipo: string) {
        this.tipo = tipo;
    }


    public hacerRuido(): void {
        console.log("El animal hace un ruido.");
    }

}


class Perro extends Animal {

    private raza: string;

    constructor(nombre: string, edad: number, tipo: string, raza: string) {
        super(nombre, edad, tipo); this.raza = raza;
    }

    public getRaza(): string {
        return this.raza;
    }
    public setRaza(raza: string): void {
        this.raza = raza;
    }

    public ladrar(): void {
        console.log("Guau");
    }

    public hacerRuido(): void {
        console.log("El perro ladra");
    }
}

class Pajaro extends Animal {
    private puedeVolar: boolean;

    constructor(nombre: string, edad: number, tipo: string, puedeVolar: boolean) {
        super(nombre, edad, tipo); this.puedeVolar = puedeVolar;
    }

    public getPuedeVolar(): boolean {
        return this.puedeVolar;
    }

    public setPuedeVolar(puedeVolar: boolean): void {
        this.puedeVolar = puedeVolar;
    }

    public volar(): void {

        if (this.puedeVolar) {
            console.log("El pájaro está volando.");
        } else { console.log("Este pájaro no puede volar."); }
    }

    public hacerRuido(): void {
        console.log("El pájaro canta");
    }
}


const perro1 = new Perro("Rocky", 5, "Mamífero", "Labrador");
const pajaro1 = new Pajaro("Piolín", 2, "Ave", true);

console.log("Nombre:", perro1.getNombre());
console.log("Edad:", perro1.getEdad());
console.log("Tipo:", perro1.getTipo());
console.log("Raza:", perro1.getRaza());
perro1.hacerRuido(); perro1.ladrar();
console.log("-------------------");
console.log("Nombre:", pajaro1.getNombre());
console.log("Edad:", pajaro1.getEdad());
console.log("Tipo:", pajaro1.getTipo());
console.log("¿Puede volar?:", pajaro1.getPuedeVolar());
pajaro1.hacerRuido();
pajaro1.volar();