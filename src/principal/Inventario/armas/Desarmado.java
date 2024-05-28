package principal.Inventario.armas;

import java.awt.Rectangle;
import java.util.ArrayList;

import principal.entes.Jugador;

public class Desarmado extends Arma {
	
	/**
	 * constructor
	 * @param id
	 * @param nombre
	 * @param descripcion
	 * @param ataqueMin
	 * @param ataqueMax
	 */
	public Desarmado(int id, String nombre, String descripcion, int ataqueMin, int ataqueMax, boolean automatica, boolean penetrante, double ataquesPorSegundo) {
		super(id, nombre, descripcion, ataqueMin, ataqueMax, automatica, penetrante, ataquesPorSegundo, "/sonidos/golpe.wav");
		
	}
	
	public ArrayList<Rectangle> getAlcance(final Jugador jugador) {	
		return null;
	}

	

}
