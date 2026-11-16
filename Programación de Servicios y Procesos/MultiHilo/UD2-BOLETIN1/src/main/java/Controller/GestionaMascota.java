package Controller;

import java.util.ArrayList;
import java.util.List;

import Entity.Mascota;

public class GestionaMascota {
	public static void main(String[] args) {
		long t_fin_comienzo = System.currentTimeMillis();
		Mascota lulu = new Mascota("Lulú");
		List<Thread> hilos = new ArrayList<Thread>();
		
		for (int i = 0; i<100; i++) {
			Thread hilo = new Thread(lulu, "Cuidador " + i);
			hilo.start();
			hilos.add(hilo);
		}
		
		for(Thread hilo: hilos) {
			try {
				hilo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(lulu.getNumeroAlimentado());
		
		long t_fin = System.currentTimeMillis();
		double tiempoTarda = t_fin_comienzo;
		System.out.println(tiempoTarda + " ms");
	}

}
