package boletin4.Ejercicio1;

public class ContadorController {
	public static void main(String[] args) {
		
		ContadorVocalesTotales contador = new ContadorVocalesTotales();
		String cadena = "Cadena de prueba";
		
		Vocal v1 = new Vocal('a', cadena, contador);
		Vocal v2 = new Vocal('e', cadena, contador);
		Vocal v3 = new Vocal('i', cadena, contador);
		Vocal v4 = new Vocal('o', cadena, contador);
		Vocal v5 = new Vocal('u', cadena, contador);
		
		Thread t1 = new Thread(v1);
		Thread t2 = new Thread(v2);
		Thread t3 = new Thread(v3);
		Thread t4 = new Thread(v4);
		Thread t5 = new Thread(v5);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}	
}
