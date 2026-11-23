package boletin4.Ejercicio1;

public class ContadorVocalesTotales {
	
	private int numVocales;
	
	public int getNumVocales() {
		return this.numVocales;
	}
	
	 public synchronized void incrementarNumVocales() {
		this.numVocales++;
	}

}
