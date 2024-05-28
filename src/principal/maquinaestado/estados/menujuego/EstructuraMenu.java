package principal.maquinaestado.estados.menujuego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constantes;
import principal.herramientas.DibujoDebug;

public class EstructuraMenu {
	
	public final Color COLOR_BAN_SUPERIOR;
	public final Color COLOR_BAN_LATERAL;
	public final Color COLOR_FONDO;
	
	public final Rectangle BAN_SUPERIOR;
	public final Rectangle BAN_LATERAL;
	public final Rectangle FONDO;
	
	public final int MARGIN_HORIZONTAL_ETIQUETAS;
	public final int MARGIN_VERTICAL_ETIQUETAS;
	public final int ANCHO_ETIQUETAS;
	public final int ALTO_ETIQUETAS;
	
	public EstructuraMenu() {
		COLOR_BAN_SUPERIOR = Constantes.COLOR_INTERFAZ;
		COLOR_BAN_LATERAL = Color.DARK_GRAY;
		COLOR_FONDO = Color.white;
		
		/**
		 * para inicializarlos
		 */
		BAN_SUPERIOR = new Rectangle(0, 0, Constantes.ANCHO_JUEGO, 20);
		BAN_LATERAL = new Rectangle(0, BAN_SUPERIOR.height, 140, Constantes.ALTO_JUEGO - BAN_SUPERIOR.height);
		FONDO = new Rectangle(BAN_LATERAL.x + BAN_LATERAL.width, BAN_LATERAL.y, 
				Constantes.ANCHO_JUEGO - BAN_LATERAL.width, Constantes.ALTO_JUEGO - BAN_SUPERIOR.height);
		
		/**
		 * Definir valores
		 */
		MARGIN_HORIZONTAL_ETIQUETAS = 20;
		MARGIN_VERTICAL_ETIQUETAS = 20;
		ANCHO_ETIQUETAS = 100;
		ALTO_ETIQUETAS = 20;
	}
	
	public void actualizar() {
		
	}
	
	/**
	 * Dibujando fondo del menu
	 * @param g
	 */
	public void dibujar(final Graphics g) {
		
		DibujoDebug.dibujarRectangleRelleno(g, BAN_SUPERIOR, COLOR_BAN_SUPERIOR);
		DibujoDebug.dibujarRectangleRelleno(g, BAN_LATERAL, COLOR_BAN_LATERAL);
		DibujoDebug.dibujarRectangleRelleno(g, FONDO, COLOR_FONDO);
	}
}
