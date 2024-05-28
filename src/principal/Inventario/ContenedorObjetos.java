package principal.Inventario;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;

public class ContenedorObjetos {
	
	private static final BufferedImage sprite = CargadorRecursos.cargarImagenCompatibleTranslucida("/imagenes/bolsa.png");

	private Point posicion;
	private Objeto[] objetos;
	
	/**
	 * contenedor de los objetos
	 * @param posicion
	 * @param objetos
	 * @param cantidades
	 */
	public ContenedorObjetos(final Point posicion, final int[] objetos, final int[] cantidades) {
	    this.posicion = posicion;
	    this.objetos = new Objeto[objetos.length];
	    
	    for(int i = 0; i < objetos.length; i++) {
	        this.objetos[i] = RegistroObjetos.getObjeto(objetos[i]);
	        if (this.objetos[i] != null) {
	            this.objetos[i].incrementaCantidad(cantidades[i]);
	        } else {
	            System.err.println("Error: Objeto con id " + objetos[i] + " es null.");
	        }
	    }
	}

	
	/**
	 * Metodo de dibujo
	 * @param g
	 * @param puntoX
	 * @param puntoY
	 */
	public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
		DibujoDebug.dibujarImg(g, sprite, puntoX, puntoY);
	}
	
	public Point getPosicion() {
		return posicion;
	}
	
	public Objeto[] getObjetos() {
		return objetos;
	}
}
