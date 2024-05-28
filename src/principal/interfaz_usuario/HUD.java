package principal.interfaz_usuario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.entes.Jugador;
import principal.herramientas.DibujoDebug;

public class HUD {
	
	private Rectangle areaHUD;
	private Rectangle bordeAreaHUD;
	
	
	
	private Color Grisverdoso;
	
	/**
	 * Colores para la vida del personaje
	 */
	private Color rojoVida;
	private Color rojoEfectoVida;
	
	/**
	 * Colores para la estamina del personaje
	 */
	private Color verdeEstamina;
	private Color verdeEfectoEstamina;
	
	/**
	 * Colores para la fuerza del personaje
	 */
	private Color azulFuerza;
	private Color azulEfectoFuerza;
	
	/**
	 * Colores para la experiencia del personaje
	 */
	private Color anaranjadoEXP;
	private Color anaranjadoEfectoEXP;
	
	/**
	 * creacion de la hud principal del personaje
	 * 
	 */
	public HUD () {
		int altoHUD = 55;
		
		/**
		 * para definir el rectangulo que contendra las distintas partes de la hud del jugador
		 */
		areaHUD = new Rectangle(0, Constantes.ALTO_JUEGO - altoHUD, Constantes.ANCHO_JUEGO, altoHUD);
		bordeAreaHUD = new Rectangle(areaHUD.x, areaHUD.y -1, areaHUD.width, 1);
		
		/**
		 * Color personalizado creado para el inventario
		 */
		Grisverdoso = new Color(133, 133, 133  );
		
		/**
		 * Color de la vida
		 */
		rojoVida = new Color(255, 0, 0);
		rojoEfectoVida = new Color(180, 0, 0);
		
		/**
		 * Color de la Estamina
		 */
		verdeEstamina = new Color(212, 255, 0);
		verdeEfectoEstamina = new Color(193, 232, 2);
		
		/**
		 * Color de la fuerza
		 */
		azulFuerza = new Color(0, 208, 255);
		azulEfectoFuerza = new Color(0, 185, 227);
		
		/**
		 * Color de la experiencia
		 */
		anaranjadoEXP = new Color(255, 238, 194);
		anaranjadoEfectoEXP = new Color(230, 214, 174);
		
	}
	
	/**
	 * Llamar los metodos de dibujo para dibujarlos
	 * @param g
	 * 
	 */
	public void dibujar(final Graphics g) {
		dibujarAreaHUD(g);
		dibujarBarraVida(g);
		dibujarBarraFuerza(g);
		dibujarBarraEstamina(g);
		dibujarBarraEXP(g, 67);
		//dibujarCuadrosObjetos(g);
	}
	
	/**
	 * establecer las areas de la hud
	 * @param g
	 */
	private void dibujarAreaHUD(final Graphics g) {
		g.setColor(Grisverdoso);
	    g.fillRect(areaHUD.x, areaHUD.y, areaHUD.width, areaHUD.height);
	    DibujoDebug.dibujarRectangleContorno(g, bordeAreaHUD, Color.DARK_GRAY);
	}
	
	/**
	 * Para la barra de vida
	 * @param g
	 */
	private void dibujarBarraVida(final Graphics g) {
        final int medidaVertical = 4;
        final int anchoTotal = 100;
        final int ancho = anchoTotal * ElementosPrinci.jugador.getVidaActual() / Jugador.VIDA_MAX ; 

        DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical, ancho, medidaVertical, rojoVida);
        DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical * 2, ancho, medidaVertical, rojoEfectoVida);

        DibujoDebug.dibujarString(g, "PS:", areaHUD.x + 10, areaHUD.y +medidaVertical * 3, rojoEfectoVida );
        DibujoDebug.dibujarString(g, "" + ElementosPrinci.jugador.getVidaActual(), anchoTotal + 40, areaHUD.y +medidaVertical * 3, rojoVida);
    }
	
	
	/**
	 * Para la barra de estamina
	 * @param g
	 * @param estamina
	 */
	private void dibujarBarraEstamina(final Graphics g) {
		final int medidaVertical = 4;
		final int anchoTotal = 100;
		final int ancho = anchoTotal * ElementosPrinci.jugador.getEstamina() / Jugador.ESTAMINA_MAX;
		
		DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical * 4, ancho, medidaVertical, verdeEstamina);
		DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical * 5, ancho, medidaVertical, verdeEfectoEstamina);
		
		DibujoDebug.dibujarString(g, "EN:", areaHUD.x + 10, areaHUD.y +medidaVertical * 6, verdeEfectoEstamina );
		DibujoDebug.dibujarString(g, "" + ElementosPrinci.jugador.getEstamina(), anchoTotal + 40, areaHUD.y +medidaVertical * 6, verdeEstamina);
	}
	
	
	/**
	 * Para la barra de fuerza
	 * @param g
	 */
	private void dibujarBarraFuerza(final Graphics g) {
		final int medidaVertical = 4;
		final int anchoTotal = 100;
	
		DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical * 7, anchoTotal, medidaVertical, azulFuerza);
		DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical * 8, anchoTotal, medidaVertical, azulEfectoFuerza);
		
		DibujoDebug.dibujarString(g, "PW:", areaHUD.x + 10, areaHUD.y +medidaVertical * 9, azulEfectoFuerza );
		DibujoDebug.dibujarString(g, "250", anchoTotal + 40, areaHUD.y +medidaVertical * 9, azulFuerza);
	}
	
	/**
	 * Para la barra de experiencia
	 * @param g
	 * @param experiencia
	 */
	private void dibujarBarraEXP(final Graphics g, final int experiencia) {
		final int medidaVertical = 4;
		final int anchoTotal = 100;
		final int ancho = anchoTotal * experiencia / anchoTotal;
	
		DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical * 10, ancho, medidaVertical, anaranjadoEXP);
		DibujoDebug.dibujarRectangleRelleno(g, areaHUD.x + 34, areaHUD.y + medidaVertical * 11, ancho, medidaVertical, anaranjadoEfectoEXP);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "EXP:", areaHUD.x + 10, areaHUD.y +medidaVertical * 12);
		DibujoDebug.dibujarString(g, experiencia + " º/º", anchoTotal + 40, areaHUD.y +medidaVertical * 12);
	}
	
	/**
	 * Para las ranuras del inventario
	 * @param g
	 */
	/*private void dibujarCuadrosObjetos(final Graphics g) {
		final int anchoRanuras = 32;
		final int numeroRanuras = 10 ;
		final int espacioRanuras = 10;
		final int anchoTotal = anchoRanuras * numeroRanuras + espacioRanuras * numeroRanuras;
		final int xInicial = Constantes.ANCHO_JUEGO - anchoTotal;
		final int anchoRanuraEspacio = anchoRanuras + espacioRanuras;
		
		g.setColor(Color.white);
		
		for(int i = 0; i < numeroRanuras; i++) {
			int xActual = xInicial + anchoRanuraEspacio * i;
			
			Rectangle ranura = new Rectangle(xActual, areaHUD.y + 4, anchoRanuras, anchoRanuras); 
			DibujoDebug.dibujarRectangleRelleno(g, ranura);
			DibujoDebug.dibujarString(g, "" + i, xActual + 13, areaHUD.y + 50);
		}
		
	}*/
	
}
