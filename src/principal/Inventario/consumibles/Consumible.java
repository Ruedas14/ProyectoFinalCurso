package principal.Inventario.consumibles;

import principal.Constantes;
import principal.Inventario.Objeto;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Consumible extends Objeto {
	
	public static HojaSprites hojaConsumibles = new HojaSprites(Constantes.RUTA_OBJETOS, Constantes.LADO_SPRITE, false);
	
	/**
	 * constructor
	 * @param id
	 * @param nombre
	 * @param descripcion
	 */
	public Consumible(int id, String nombre, String descripcion) {
		super(id, nombre, descripcion);
		
	}

	/**
	 * constructor
	 * @param id
	 * @param nombre
	 * @param descripcion
	 * @param cantidad
	 */
	public Consumible(int id, String nombre, String descripcion, int cantidad) {
		super(id, nombre, descripcion, cantidad);
		
	}

	
	public Sprite getSprite() {
		
		return hojaConsumibles.getSprite(id);
	}

	

}
