package boletin4.Ejercicio4;

public class Restaurante {
	public static void main(String[] args) {
		
		Bandeja bandeja = new Bandeja();
		Thread cocineroThread = new Thread(new Cocinero(bandeja, "Paco"));
		
		Thread cocineroThread2 = new Thread(new Cocinero(bandeja, "Jesus"));
		
		
		Thread comensal1 = new Thread(new Comensal("Comensal 1", bandeja));
		Thread comensal2 = new Thread(new Comensal("Comensal 2", bandeja));
		Thread comensal3 = new Thread(new Comensal("Comensal 3", bandeja));
		
		cocineroThread.start();
		cocineroThread2.start();
		
		comensal1.start();
		comensal2.start();
		comensal3.start();
	}
}
