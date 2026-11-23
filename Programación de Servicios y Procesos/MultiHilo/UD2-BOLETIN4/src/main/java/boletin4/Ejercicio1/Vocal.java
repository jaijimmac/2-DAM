package boletin4.Ejercicio1;

public class Vocal implements Runnable{
	
	private char vocal;
	private String cadena;
	private ContadorVocalesTotales contador;
	
	public Vocal(char vocal, String cadena, ContadorVocalesTotales contador) {
		this.vocal = vocal;
		this.cadena = cadena;
		this.contador = contador;
	}


	@Override
	public void run() {
		for(int i = 0; i<cadena.length(); i++) {
			if(cadena.charAt(i) == this.vocal) {
				contador.incrementarNumVocales();
			}
		}
		
		System.out.println("El número de veces en la vocal '" + this.vocal + "': " + contador.getNumVocales());
	}

}
