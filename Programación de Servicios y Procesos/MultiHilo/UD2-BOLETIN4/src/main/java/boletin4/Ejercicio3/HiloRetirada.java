package boletin4.Ejercicio3;

public class HiloRetirada implements Runnable{

	private Cuenta cuenta;
	private double retirada;

	public HiloRetirada(Cuenta cuenta, double retirada) {
		this.cuenta = cuenta;
		this.retirada = retirada;
	}
	
	@Override
	public void run() {
		try {
			this.cuenta.retirarDinero(this.retirada);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
