package principal.herramientas;

import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;

public class EscaladorElementos {
	
	/**
	 * metodo para agrandar los elementos y no se vean tan pequeños
	 * @param r
	 * @return
	 */
	public static Rectangle escalarRectanguloArriba(final Rectangle r) {
		
		final Rectangle rr = new Rectangle((int)(r.x * Constantes.FACTOR_ESCALADO_X), 
				(int) (r.y * Constantes.FACTOR_ESCALADO_Y),
				(int) (r.width * Constantes.FACTOR_ESCALADO_X), 
				(int) (r.height * Constantes.FACTOR_ESCALADO_Y));
	
		return  rr;
	}
	
	/**
	 * Escaladoarriba
	 * @param p
	 * @return
	 */
	public static Point escalarPuntoArriba(final Point p) {
		final Point pr = new Point((int) (p.x * Constantes.FACTOR_ESCALADO_X), (int) (p.y * Constantes.FACTOR_ESCALADO_Y));
		
		return pr;
	}
	
	/**
	 * Escaladoabajo
	 * @param p
	 * @return
	 */
	public static Point escalaPuntoAbajo(final Point p) {
		final Point pr = new Point((int) (p.x / Constantes.FACTOR_ESCALADO_X), (int) (p.y / Constantes.FACTOR_ESCALADO_Y));
		
		return pr;
	}
	
	
}
