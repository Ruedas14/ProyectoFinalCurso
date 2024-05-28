package principal.entes;

import principal.Inventario.armas.Arma;

public class AlmacenEquipo {

	private Arma arma;
	
	
	public AlmacenEquipo(final Arma arma1) {
		this.arma = arma1;
	}
	
	public Arma getArma1() {
		return arma;
	}
	
	public void cambiarArma1(final Arma arma1) {
		this.arma = arma1;
	}
}
