package boletin4.Ejercicio3;

public class Cuenta{
	
	private double saldo;
	
	public Cuenta(double saldo) {
		this.saldo = saldo;
	}
	
	public String getDineroCuenta() {
		return "Saldo actual: " + this.saldo;
	}

	
	public synchronized void ingresarDinero(double cantidad) {
		System.out.println("Se ingresa " + cantidad + "€");
		this.saldo += cantidad;
		System.out.println(getDineroCuenta());
	}
	
	public synchronized void retirarDinero(double cantidad) throws Exception {
		
		if(this.saldo < cantidad) {
			throw new Exception("No se puede retirar el dinero: Saldo insuficiente");
		} else {
			System.out.println("Se retira " + cantidad + "€");
			this.saldo -= cantidad;
			System.out.println(getDineroCuenta());
		}
	}

}
