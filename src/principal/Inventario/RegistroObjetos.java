package principal.Inventario;

import principal.Inventario.armas.Desarmado;
import principal.Inventario.armas.Pistola;
import principal.Inventario.consumibles.Consumible;

public class RegistroObjetos {
	
	
	/**
	 * para hacer una base de datos en la que se encuentren todos los objtos que puede coger el jugador
	 * @param idObjeto
	 * @return
	 */
	public static Objeto getObjeto(final int idObjeto) {
		
		Objeto objeto = null;
		
		switch (idObjeto) {
		/*0-99 objetos que se pueden consumir*/
			case 0:
				objeto = new Consumible(idObjeto, "Botiquin", "Mmm, creo que esto me servira");
				break;
			case 1:
				objeto = new Consumible(idObjeto, "Sandia", "");
				break;
			case 2:
				objeto = new Consumible(idObjeto, "Platanos", "Oro parece platano no es");
				break;
			case 3:
				objeto = new Consumible(idObjeto, "Kiwi", "");
				break;
			case 4:
				objeto = new Consumible(idObjeto, "Cerezas", "");
				break;
			case 5:
				objeto = new Consumible(idObjeto, "Piña", "");
				break;
		/*100-200 objetos de armas  con su daño*/
			case 100:
				objeto = new Pistola(idObjeto, "P-189" , "Algo anticuada pero letal", 2, 5, false, true, 0.4);
				break;
			case 200:
				objeto = new Desarmado(idObjeto, "Desarmado", "", 0, 0, false, false, 0);
				break;
		}
		return objeto;
	}
}
