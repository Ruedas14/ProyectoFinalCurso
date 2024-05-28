package principal.Inventario;

import java.util.ArrayList;

import principal.Constantes;
import principal.Inventario.armas.Arma;
import principal.Inventario.consumibles.Consumible;
import principal.sprites.HojaSprites;

public class Inventario {
	
	private final HojaSprites hojaObjetos;
	public final ArrayList<Objeto> objetos;
	
	/**
	 * se declaran todos los objetops que dispondra en el inventario el personaje
	 */
	public Inventario() {
		hojaObjetos = new HojaSprites(Constantes.RUTA_OBJETOS, Constantes.LADO_SPRITE, false);
		objetos = new ArrayList<Objeto>();

		
		/**
		 * para darte item automaticamente
		 * en este caso coges 2 botiquines
		 * incrementarObjeto(RegistroObjetos.objetos[0], 2);
		 */
		
	}
	
	/**
	 * para la recoloeccion de objetos en el mapa 
	 * @param co
	 */
	public void recogerObjetos(final ContenedorObjetos co) {
		for(Objeto objeto:co.getObjetos()) {
			if(objetoExiste(objeto)) {
				incrementarObjeto(objeto, objeto.getCantidad());
			} else {
				objetos.add(objeto);
			}
		}
	}
	
	/**
	 * Para que cuando se consiga un objeto el valor numerico vaya aumentando 
	 * @param objeto
	 * @param cantidad
	 * @return
	 */
	public boolean incrementarObjeto(final Objeto objeto, final int cantidad) {
		boolean incrementado = false;
		
		for(Objeto objetoActual : objetos) {
			if(objetoActual.getID() == objeto.getID()) {
				objetoActual.incrementaCantidad(cantidad);
				incrementado = true;
				break;
			}
		}
		return incrementado;
	}
	
	/**
	 * para saber si esta el objeto ya en el inventario para que no lo duplique si no que se incremente en el inventario
	 * @param objeto
	 * @return
	 */
	public boolean objetoExiste(final Objeto objeto) {
		boolean existe = false;
		
		for(Objeto objetoActual : objetos) {
			if(objetoActual.getID() == objeto.getID()) {
				existe = true;
				break;
			}
		}
		return existe;
	}
	
	/**
	 * para poder referenciar la division de objetos en el apartado del equipo del personaje
	 * @return
	 */
	public ArrayList<Objeto> getConsumibles(){
		ArrayList<Objeto> consumibles = new ArrayList<Objeto>();
		
		
		for(Objeto objeto : objetos) {
			if (objeto instanceof Consumible) {
				consumibles.add(objeto);
			}
		}
		return consumibles;
	}
	
	public ArrayList<Objeto> getArmas(){
		ArrayList<Objeto> armas = new ArrayList<Objeto>();
		
		for(Objeto objeto : objetos) {
			if(objeto instanceof Arma) {
				armas.add(objeto);
			}
		}
		return armas;
	}
	
	public Objeto getObjeto(final int id) {
		for(Objeto objetoActual : objetos) {
			if(objetoActual.getID() == id) {
				return objetoActual;
			}
		}
		return null;
	}
	
	
}
