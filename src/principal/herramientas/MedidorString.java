package principal.herramientas;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class MedidorString {

	/**
	 * medir los pixeles de ancho
	 * @param g
	 * @param s
	 * @return
	 */
	public static int medirAnchoPixeles(final Graphics g, final String s) {
		
		FontMetrics fm = g.getFontMetrics();
		
		return fm.stringWidth(s);
	}
	
	/**
	 * medir los pixeles de alto
	 * @param g
	 * @param s
	 * @return
	 */
	public static int medirAltoPixeles(final Graphics g, final String s) {
		
		FontMetrics fm = g.getFontMetrics();
		
		return (int) fm.getLineMetrics(s, g).getHeight();
	}
	
}
