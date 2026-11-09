package ejemplos;

public class Main {
	public static void main(String[] args) {
		
		HiloHijo h1 = new HiloHijo("Hijo 1");
		HiloHijo h2 = new HiloHijo("Hijo 2");
		
		h1.start();
		h2.start();
	}
}
