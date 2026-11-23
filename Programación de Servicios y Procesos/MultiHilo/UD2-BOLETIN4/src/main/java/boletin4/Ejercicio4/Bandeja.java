package boletin4.Ejercicio4;

import java.util.ArrayList;
import java.util.List;

public class Bandeja {
	private int capacidad;
	List<String> bandejaPlatos;
	
	
	public Bandeja() {
		this.capacidad = 3;
		this.bandejaPlatos = new ArrayList<String>();
	}
	
	public synchronized void pongoPlato(String plato) throws InterruptedException{
		
		while(bandejaPlatos.size() == capacidad) {
			System.out.println("Bandeja de platos llena");
			wait();
		} 
		
			System.out.println("Añade plato a bandeja: " + plato);
			this.bandejaPlatos.add(plato);
			notifyAll();
	}
	
	public synchronized void quitoPlato() throws InterruptedException{
		
		while(this.bandejaPlatos.isEmpty()) {
			System.out.println("Bandeja de platos vacía");
			wait();
		}
		
		System.out.println("Quita plato a bandeja: ");
		this.bandejaPlatos.remove(0);
		notifyAll();
	}
}
