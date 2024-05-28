package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.control.Raton;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
import principal.maquinaestado.GestorEstados;

/**
 * Se dibujara la superficie del videojuego
 */
public class SuperficieDibujo extends Canvas {

	/**
	 * Atributos
	 */
	private static final long serialVersionUID = -6227038142688953660L;
	
	private int alto;
	private int ancho;
	
	private Raton raton;
	
	/**
	 * Constructor
	 * @param ancho
	 * @param alto
	 */
	public SuperficieDibujo(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;
		
		this.raton = new Raton(this);

		/**
		 * No fuerce el repintado de canvas
		 */
		setIgnoreRepaint(true);
		
		/**
		 * Cursor
		 */
		setCursor(raton.getCursor());
		
		setPreferredSize(new Dimension(ancho, alto));
		addKeyListener(GestorControles.teclado);
		addMouseListener(raton);
		setFocusable(true);
		requestFocus();
	}
	
	public void actualizar() {
		raton.actualizar(this);
	}
	
	public void dibujar(final GestorEstados ge) {
		final BufferStrategy buffer = getBufferStrategy();
		
		if(buffer == null) {
			createBufferStrategy(4);
			return;
		}
		
		/**
		 * Objeto que se va a dibujar dentro del buffer
		 */
		final Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		
		/**
		 * reiniciar el contador de obgetos por frame
		 */
		DibujoDebug.reiniciarContadorObjetos();
		
		g.setFont(Constantes.FUENTE_PIXEL);
		DibujoDebug.dibujarRectangleRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA, Color.black);
		
		/**
		 * Para escalar la imagen como si fuera pantallaCompleta
		 */
		if(Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y != 1.0) {
			g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);
		}
		
		ge.dibujar(g);
		
		DibujoDebug.dibujarString(g, "FPS: " + GestorPrincipal.getFPS() +" "+ "APS: " + GestorPrincipal.getAPS(), 5, 12, Color.orange );
		
		DatosDebug.enviarDato("Escala X: " + Constantes.FACTOR_ESCALADO_X);
		DatosDebug.enviarDato("Escala Y: " + Constantes.FACTOR_ESCALADO_Y);
		DatosDebug.enviarDato("OPF: " + DibujoDebug.getContadorObjetos());
	
		if(GestorControles.teclado.debug) {
			DatosDebug.dibujarDatos(g);
		} else {
			/**
			 * para no sobre cargar de datos almacenado repedimanete la memoria
			 */
			DatosDebug.vaciarCahce();
		}
		
		/**
		 * Pintar entre actualizaciones de la pantalla
		 */
		Toolkit.getDefaultToolkit().sync();
		
		/**
		 * para destruir los objetos graficos para liberear el espacio
		 */
		g.dispose();
		
		buffer.show();
	}
	
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
	
	public Raton getRaton() {
		return raton;
	}
}
