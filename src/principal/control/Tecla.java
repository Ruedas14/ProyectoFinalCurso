package principal.control;

public class Tecla {
	
	private boolean pulsada = false;
	private long ultimaPulsada = System.nanoTime();
	
	/**
	 * Para tener mejor control de las teclas y no haya retrasos de tiempos*/
	public void teclaPulsada() {
		pulsada = true;
		ultimaPulsada = System.nanoTime();
	}
	
	public void teclaLibre() {
		pulsada = false;
	}
	
	public boolean estaPulsada() {
		return pulsada;
	}
	
	public long getUltimaPulsada() {
		return ultimaPulsada;
	}
}
