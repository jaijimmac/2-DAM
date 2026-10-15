class Employee{
    private nombre: string;
    private apellido: string;
    private edad: number;  
    private fechaInicio: Date
    private fechaFin: Date | null;
    private profesion: string;

    constructor(nombre: string, apellido: string, edad: number, fechaInicio: Date, fechaFin: Date | null, profesion: string ){
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.profesion = profesion;
    }


	public getNombre(): string {
		return this.nombre;
	}


	public setNombre(value: string) {
		this.nombre = value;
	}


	public getApellido(): string {
		return this.apellido;
	}


	public setApellido(value: string) {
		this.apellido = value;
	}
    
	public getEdad(): number {
		return this.edad;
	}

	public setEdad(value: number) {
		this.edad = value;
	}

    public getFechaInicio(): Date {
        return this.fechaInicio;
    }

    public setFechaInicio(value: Date){
        return this.fechaInicio = value;
    }

	public getFechaFin(): Date | null {
		return this.fechaFin;
	}

	public setFechaFin(value: Date ) {
		this.fechaFin = value;
	}

	public getProfesion(): string {
		return this.profesion;
	}

	public setProfesion(value: string) {
		this.profesion = value;
	}   
}


var empleado1: Employee;
var empleado2: Employee;

empleado1 = new Employee('Jaime', 'Jiménez', 23, new Date, null, 'Programador');
empleado2 = new Employee('Alba', 'García', 23, new Date, new Date,'Administrativa');

console.log(empleado1)
console.log(empleado2)