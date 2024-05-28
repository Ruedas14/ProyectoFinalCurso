package principal.herramientas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class DibujoDebug {
	/**
	 * para contar los objetos que se dibujan
	 */
	private static int objetosDibujados = 0;
	
	/**
	 * para dibujar imagen
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void dibujarImg(final Graphics g, final BufferedImage img, final int x, final int y) {
		objetosDibujados++;
		g.drawImage(img, x, y, null);
	}
	
	/**
	 * dibujar imagen 
	 * @param g
	 * @param img
	 * @param p
	 */
	public static void dibujarImg(final Graphics g, final BufferedImage img, final Point p) {
		objetosDibujados++;
		g.drawImage(img, p.x, p.y, null);
	}
	
	/**
	 * dibujo de textos
	 * @param g
	 * @param s
	 * @param x
	 * @param y
	 */
	public static void dibujarString(final Graphics g, final String s, final int x, final int y) {
		objetosDibujados++;
		g.drawString(s, x, y);
	}
	
	/**
	 * dibujo de textos
	 * @param g
	 * @param s
	 * @param p
	 */
	public static void dibujarString(final Graphics g, final String s, final Point p) {
		objetosDibujados++;
		g.drawString(s, p.x, p.y);
	}
	
	/**
	 * dibujar con color
	 * @param g
	 * @param s
	 * @param x
	 * @param y
	 * @param c
	 */
	public static void dibujarString(final Graphics g, final String s, final int x, final int y, final Color c) {
		objetosDibujados++;
		g.setColor(c);
		g.drawString(s, x, y);
	}
	
	/**
	 * dibujar con color
	 * @param g
	 * @param s
	 * @param p
	 * @param c
	 */
	public static void dibujarString(final Graphics g, final String s, final Point p, final Color c) {
		objetosDibujados++;
		g.setColor(c);
		g.drawString(s, p.x, p.y);
	}
	
	/**
	 * dibujado de rectangulos
	 * @param g
	 * @param x
	 * @param y
	 * @param ancho
	 * @param alto
	 */
	public static void dibujarRectangleRelleno(final Graphics g, final int x, final int y, final int ancho, final int alto) {
		objetosDibujados++;
		g.fillRect(x, y, ancho, alto);
	}
	/**
	 * dibujado de rectangulos
	 * @param g
	 * @param r
	 */
	public static void dibujarRectangleRelleno(final Graphics g, final Rectangle r) {
		objetosDibujados++;
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	/**
	 * en caso de configurar color los rectangulos
	 * @param g
	 * @param x
	 * @param y
	 * @param ancho
	 * @param alto
	 * @param c
	 */
	public static void dibujarRectangleRelleno(final Graphics g, final int x, final int y, final int ancho, final int alto, final Color c) {
		objetosDibujados++;
		g.setColor(c);
		g.fillRect(x, y, ancho, alto);
	}
	
	/**
	 * en caso de configurar color los rectangulos
	 * @param g
	 * @param r
	 * @param c
	 */
	public static void dibujarRectangleRelleno(final Graphics g, final Rectangle r, final Color c) {
		objetosDibujados++;
		g.setColor(c);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	/**
	 * para los cuadrados de vacio del mapa
	 * @param g
	 * @param x
	 * @param y
	 * @param ancho
	 * @param alto
	 */
	public static void dibujarRectangleContorno(final Graphics g, final int x, final int y, final int ancho, final int alto) {
		objetosDibujados++;
		g.drawRect(x, y, ancho, alto);
	}
	
	/**
	 * para los cuadrados de vacio del mapa
	 * @param g
	 * @param r
	 */
	public static void dibujarRectangleContorno(final Graphics g, final Rectangle r) {
		objetosDibujados++;
		g.drawRect(r.x, r.y, r.width, r.height);
	}
	/**
	 * en caso de configurar color los rectangulos
	 * @param g
	 * @param x
	 * @param y
	 * @param ancho
	 * @param alto
	 * @param c
	 */
	public static void dibujarRectangleContorno(final Graphics g, final int x, final int y, final int ancho, final int alto, final Color c) {
		objetosDibujados++;
		g.setColor(c);
		g.drawRect(x, y, ancho, alto);
	}
	
	/**
	 * en caso de configurar color los rectangulos
	 * @param g
	 * @param r
	 * @param c
	 */
	public static void dibujarRectangleContorno(final Graphics g, final Rectangle r, final Color c) {
		objetosDibujados++;
		g.setColor(c);
		g.drawRect(r.x, r.y, r.width, r.height);
	}
	
	public static int getContadorObjetos() {
		return objetosDibujados;
	}
	
	public static void reiniciarContadorObjetos() {
		objetosDibujados = 0;
	}
}
