package principal.Inventario.armas;

import java.awt.Rectangle;
import java.util.ArrayList;

import principal.Constantes;
import principal.entes.Jugador;

public class Pistola extends Arma {
	
	/**
	 * constructor
	 * @param id
	 * @param nombre
	 * @param descripcion
	 * @param ataqueMin
	 * @param ataqueMax
	 */
	public Pistola(int id, String nombre, String descripcion, int ataqueMin, int ataqueMax, boolean automatica, boolean penetrante, double ataquesPorSegundo) {
		super(id, nombre, descripcion, ataqueMin, ataqueMax, automatica, penetrante, ataquesPorSegundo, "/sonidos/disparo.wav");
		
	}

	
	/**
	 * array, para saber en que posicion esta mirando el jugador y alcance del arma
	 */
	public ArrayList<Rectangle> getAlcance(final Jugador jugador) {
		final ArrayList<Rectangle> alcance = new ArrayList<>();
		
		final Rectangle alcance1 = new Rectangle();
		
		if(jugador.getDireccion() == 0 || jugador.getDireccion() == 1) {
			alcance1.width = 1;
			alcance1.height = 3 * Constantes.LADO_SPRITE;
			alcance1.x = Constantes.CENTRO_VENTANA_X;
			if(jugador.getDireccion() == 0) {
				alcance1.y = Constantes.CENTRO_VENTANA_Y - 9;
			} else {
				alcance1.y = Constantes.CENTRO_VENTANA_Y - 9 - alcance1.height;
			} 
		} else {
			alcance1.width = 3 * Constantes.LADO_SPRITE;
			alcance1.height = 1;
			
			alcance1.y = Constantes.CENTRO_VENTANA_Y - 6;
			
			if(jugador.getDireccion() == 3) {
				alcance1.x = Constantes.CENTRO_VENTANA_X - alcance1.width;
			} else {
				alcance1.x = Constantes.CENTRO_VENTANA_X;
			}
		}
		
		alcance.add(alcance1);
		
		return alcance;
	}

	
	
}
