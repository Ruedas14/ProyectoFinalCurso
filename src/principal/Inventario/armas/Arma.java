package principal.Inventario.armas;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import principal.Constantes;
import principal.Inventario.Objeto;
import principal.entes.Enemigo;
import principal.entes.Jugador;
import principal.sonido.Sonido;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public abstract class Arma extends Objeto {
	
	public Sonido sonidoDisparo;
	
	public static HojaSprites hojaArmas = new HojaSprites(Constantes.RUTA_ARMAS, Constantes.LADO_SPRITE, false);
	
	protected int ataqueMin;
	protected int ataqueMax;
	protected boolean automatica;
	protected boolean penetrante;
	protected double ataquesPorSegundo;
	protected int actualizacionesParaSiguienteAtaque;
	
	/**
	 * constructores
	 * @param id
	 * @param nombre
	 * @param descripcion
	 */
	public Arma(int id, String nombre, String descripcion, int ataqueMin, int ataqueMax, final boolean automatica, final boolean penetrante, final double ataquesPorSegundo, final String rutaSonidoDisparo) {
		super(id, nombre, descripcion);
		
		this.ataqueMin = ataqueMin;
		this.ataqueMax = ataqueMax;
		this.automatica = automatica;
		this.penetrante = penetrante;
		this.ataquesPorSegundo = ataquesPorSegundo;
		this.actualizacionesParaSiguienteAtaque = 0;
		this.sonidoDisparo = new Sonido (rutaSonidoDisparo);
	}

	public abstract ArrayList <Rectangle> getAlcance(final Jugador jugador);
	
	public void actualizar() {
		if(actualizacionesParaSiguienteAtaque > 0) {
			actualizacionesParaSiguienteAtaque--;
		}
	}
	
	public void atacar(final ArrayList<Enemigo> enemigos) {
		if (actualizacionesParaSiguienteAtaque > 0) {
			return;
		}
		actualizacionesParaSiguienteAtaque = (int) (ataquesPorSegundo* 60);
		
		sonidoDisparo.reproducir();
		
		
		if(enemigos.isEmpty()) {
			return;
		}
		float ataqueActual = getAtaqueMedio();
		
		for(Enemigo enemigo : enemigos){
			enemigo.perderVida(ataqueActual);
		}
	}
	
	/**
	 * para definir el rango de id de los objetos tipo arma
	 */
	public Sprite getSprite() {
		return hojaArmas.getSprite(id - 100);
	}
	
	/**
	 * para el daño del arma del jugador
	 * @param ataqueMin
	 * @param ataqueMax
	 * @return
	 */
	protected int getAtaqueMedio() {
		Random r = new Random();
		return r.nextInt(ataqueMax - ataqueMin) + ataqueMin;
	}
	
	public boolean esAutomatica() {
		return automatica;
	}
	
	public boolean esPenetrantes() {
		return penetrante;
	}

}
