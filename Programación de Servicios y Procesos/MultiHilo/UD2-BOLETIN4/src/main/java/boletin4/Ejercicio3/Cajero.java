package boletin4.Ejercicio3;

public class Cajero {
	public static void main(String[] args) {
		
		Cuenta cuenta = new Cuenta(0);
		
		HiloIngreso hilo1 = new HiloIngreso(cuenta, 20);
		HiloIngreso hilo2 = new HiloIngreso(cuenta, 30);
		HiloIngreso hilo3 = new HiloIngreso(cuenta, 40);
		HiloIngreso hilo4 = new HiloIngreso(cuenta, 100);
		HiloIngreso hilo5 = new HiloIngreso(cuenta, 10);
		
		HiloRetirada hilo6 = new HiloRetirada(cuenta, 10);
		HiloRetirada hilo7 = new HiloRetirada(cuenta, 55);
		HiloRetirada hilo8 = new HiloRetirada(cuenta, 56);
		HiloRetirada hilo9 = new HiloRetirada(cuenta, 54);
		HiloRetirada hilo10 = new HiloRetirada(cuenta, 45);
		
		Thread t1 = new Thread(hilo1);
		Thread t2 = new Thread(hilo2);
		Thread t3 = new Thread(hilo3);
		Thread t4 = new Thread(hilo4);
		Thread t5 = new Thread(hilo5);
		Thread t6 = new Thread(hilo6);
		Thread t7 = new Thread(hilo7);
		Thread t8= new Thread(hilo8);
		Thread t9 = new Thread(hilo9);
		Thread t10 = new Thread(hilo10);
		
		
		t10.start();
		t8.start();
		t5.start();
		t7.start();
		t3.start();
		t6.start();
		t4.start();
		t2.start();
		t1.start();
		t9.start();
		
	}
}
