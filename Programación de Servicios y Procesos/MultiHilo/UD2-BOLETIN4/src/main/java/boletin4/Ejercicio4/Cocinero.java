package boletin4.Ejercicio4;

public class Cocinero implements Runnable{
	
	private Bandeja bandeja;
	private String nombre;
	
	public Cocinero(Bandeja bandeja, String nombre) {
		super();
		this.bandeja = bandeja;
		this.nombre = nombre;
	}



	@Override
	public void run() {
		System.out.println(this.nombre + " empieza a cocinar.");
		try {
			for(int i = 0; i < 12; i++) {
				Thread.currentThread().sleep(5000);
				System.out.println(this.nombre + " pone plato");
				bandeja.pongoPlato("Plato");
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
