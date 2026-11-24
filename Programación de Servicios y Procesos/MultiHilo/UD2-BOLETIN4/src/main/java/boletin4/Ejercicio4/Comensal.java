package boletin4.Ejercicio4;

public class Comensal implements Runnable{
	private Bandeja bandeja;
	private String nombre;
	
	public Comensal(String nombre, Bandeja bandeja) {
		this.nombre = nombre;
		this.bandeja = bandeja;
	}

	@Override
	public void run() {
		
		try {
			
			for(int i = 0; i < 3; i++) {
				bandeja.quitoPlato();
				System.out.println(this.nombre + " coge un plato");
				System.out.println(this.nombre + " ha empezado a comer.");
				Thread.currentThread().sleep(20000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
