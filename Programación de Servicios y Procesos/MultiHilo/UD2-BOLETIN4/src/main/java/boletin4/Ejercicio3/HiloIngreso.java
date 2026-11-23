package boletin4.Ejercicio3;

public class HiloIngreso implements Runnable{

	private Cuenta cuenta;
	private double ingreso;

	public HiloIngreso(Cuenta cuenta, double ingreso) {
		this.cuenta = cuenta;
		this.ingreso = ingreso; 
	}
	
	@Override
	public void run() {
		
		this.cuenta.ingresarDinero(this.ingreso);
	}
}
