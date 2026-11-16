package Entity;

public class Mascota implements Runnable{
	
	private String nombre;
	private int numeroAlimentado;
	
	public Mascota(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getNumeroAlimentado() {
		return numeroAlimentado;
	}



	public void setNumeroAlimentado(int numeroAlimentado) {
		this.numeroAlimentado = numeroAlimentado;
	}



	public void comer() {
		System.out.println(this.nombre + " ha empezado a comer.");
		
		try {
			Thread.sleep(1000 * this.numeroAlimentado);
			this.numeroAlimentado++;
			
			System.out.println(this.numeroAlimentado + " - "  + Thread.currentThread().getName() + " - " + this.nombre + " ha terminado de comer");
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		this.comer();
	}
	

}
