package principal.herramientas;

import java.awt.Point;

public class CalculaDistancia {
	public static double getDistanciaEntrePuntos(final Point pl, final Point p2) {
		return Math.sqrt(Math.pow((p2.getX() - pl.getX()), 2) + Math.pow((p2.getY() - pl.getY()), 2));
	}
}
