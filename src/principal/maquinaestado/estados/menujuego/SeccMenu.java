package principal.maquinaestado.estados.menujuego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;

public abstract class SeccMenu {
	protected final String nombreSeccion;
	protected final Rectangle etiquetaMenu;
	protected final EstructuraMenu em;
	
	protected final int margenGeneral = 8;
	protected final Rectangle barraPeso;
	
	public SeccMenu (final String nombreSeccion, final Rectangle etiquetaMenu, final EstructuraMenu em) {
		this.nombreSeccion = nombreSeccion;
		this.etiquetaMenu = etiquetaMenu;
		this.em = em;
		
		int anchoBarra = 100;
		
		barraPeso = new Rectangle(Constantes.ANCHO_JUEGO - anchoBarra - 12, em.BAN_SUPERIOR.height + margenGeneral,
				ElementosPrinci.jugador.limitePeso, 8);	
	}
	
	public abstract void actualizar ();
	public abstract void dibujar(final Graphics g, final SuperficieDibujo sd, final EstructuraMenu em);
	
	/**
	 * Dibujar una etiqueta inactiva para cuando no se utiliza
	 * @param g
	 */
	public void dibujarEtiquetaInactiva(final Graphics g) {
		DibujoDebug.dibujarRectangleRelleno(g, etiquetaMenu, Color.white);
		DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	/**
	 * Para saber que opcion esta marcada de las etiquetas
	 * @param g
	 */
	public void dibujarEtiquetaActiva(final Graphics g) {
		
		/**
		 * Cambiar el color de fondo del rectángulo activo a naranja claro
		 */
	    DibujoDebug.dibujarRectangleRelleno(g, etiquetaMenu, Color.white);
	    
	    final Rectangle marcaDeActivo = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
	    DibujoDebug.dibujarRectangleRelleno(g, marcaDeActivo, new Color(0xff5600));
	    
	    DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	public void dibujarEtiquetaInactivaResaltada(final Graphics g) {
		DibujoDebug.dibujarRectangleRelleno(g, etiquetaMenu, new Color(255, 179, 141));
		
		DibujoDebug.dibujarRectangleRelleno(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10,
				etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10), new Color(0x2a2a2a));
		
		DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	
	public void dibujarEtiquetaActivaResaltada(final Graphics g) {
		DibujoDebug.dibujarRectangleRelleno(g, etiquetaMenu, Color.white);
		
		final Rectangle marcaDeActivo = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
		DibujoDebug.dibujarRectangleRelleno(g, marcaDeActivo, new Color(0xff5600));
		
		DibujoDebug.dibujarRectangleRelleno(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10,
				etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10), new Color(0x2a2a2a));
		
		DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	protected void dibujarLimitePeso(final Graphics g) {
		final Rectangle contenidoBarra = new Rectangle(barraPeso.x + 1, barraPeso.y +1, barraPeso.width / (ElementosPrinci.jugador.limitePeso / ElementosPrinci.jugador.pesoActual), barraPeso.height - 2);
		
		DibujoDebug.dibujarString(g, "KG", barraPeso.x - 20, barraPeso.y + 7, Color.black);
		DibujoDebug.dibujarRectangleRelleno(g, barraPeso, Color.gray);
		DibujoDebug.dibujarRectangleRelleno(g, contenidoBarra, Constantes.COLOR_INTERFAZ);
	}
	
	
	/**
	 * para saber como se llama cada seccion del menu
	 * @return
	 */
	public String obtenerNombreSelecionado() {
		return nombreSeccion;
	}
	
	public Rectangle getEtiquetaMenu() {
		return etiquetaMenu;
	}
	
	/**
	 * ajustar que los botornes no esten descuadrados
	 * @return
	 */
	public Rectangle getEtiquetaMenuEscalada() {
		final Rectangle etiquetaEscalada = new Rectangle( (int)(etiquetaMenu.x * Constantes.FACTOR_ESCALADO_X), 
				(int)(etiquetaMenu.y * Constantes.FACTOR_ESCALADO_Y), (int)(etiquetaMenu.width * Constantes.FACTOR_ESCALADO_X),
				(int)(etiquetaMenu.height * Constantes.FACTOR_ESCALADO_Y));
		return etiquetaEscalada;
	}
}
