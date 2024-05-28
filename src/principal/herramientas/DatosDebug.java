package principal.herramientas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class DatosDebug {
	/**
	 * Guardara todos nuestros datos
	 */
	private static ArrayList<String> datos = new ArrayList<String>();
	
	public static void enviarDato(final String dato) {
		datos.add(dato);
	}
	
	public static void vaciarCahce() {
		datos.clear();
	}
	
	public static void dibujarDatos(final Graphics g) {
		g.setColor(Color.white);
		
		for(int i = 0; i < datos.size(); i++) {
			DibujoDebug.dibujarString(g, datos.get(i), 5, 25 + i * 10);
		}
		
		datos.clear();
	}
	
}
