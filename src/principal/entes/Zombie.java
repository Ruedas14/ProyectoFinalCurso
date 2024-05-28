package principal.entes;

import java.awt.Graphics;

import principal.Constantes;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

public class Zombie extends Enemigo{

	private static HojaSprites hojaZombie;
	
	/**
	 * constructor
	 * @param idEnemigo
	 * @param nombre
	 * @param vidaMaxima
	 */
	public Zombie(int idEnemigo, String nombre, int vidaMaxima, float ataque, final String rutaLastimado) {
		super(idEnemigo, nombre, vidaMaxima, ataque ,rutaLastimado);

		if (hojaZombie == null) {
			hojaZombie = new HojaSprites(Constantes.RUTA_ENEMIGO + idEnemigo + ".png", Constantes.LADO_SPRITE, false);
		}
	}
	
	/**
	 * metodo para pintar por pantalla
	 */
	public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
		DibujoDebug.dibujarImg(g, hojaZombie.getSprite(0).getImagen(), puntoX, puntoY);
		super.dibujar(g, puntoX, puntoY);
	}
	
}
