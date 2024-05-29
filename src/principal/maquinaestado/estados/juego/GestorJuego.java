package principal.maquinaestado.estados.juego;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.ElementosPrinci;
import principal.entes.Enemigo;
import principal.entes.Jugador;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
import principal.interfaz_usuario.HUD;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;




/**
 * ejecutara el juego mientras exploramos con el jugador
 */
public class GestorJuego implements EstadoJuego {

	BufferedImage logo;
	/**
	 * menu de inventario inferior del jugador
	 */
	HUD menuHUD;
	public static Enemigo[] enemi;
	
	/**
	 * inicializar
	 */
	public GestorJuego() {
		iniciarMapa(Constantes.RUTA_MAPA);
		iniciarJugador();
		iniciarEnemigos();
		menuHUD = new HUD();
		logo = CargadorRecursos.cargarImagenCompatibleTranslucida(Constantes.RUTA_LOGOTIPO);
	}
	
	

	private void recargarJuego() {
		final String ruta = "/mapas/" + ElementosPrinci.mapa.getcambioMapa();
		ElementosPrinci.mapa = new Mapa(ruta);
		iniciarMapa(ruta);
		iniciarJugador();
		iniciarEnemigos();
	}
	
	/**
	 * Iniciamos el mapa antes que el jugadro
	 * @param ruta
	 */
	private void iniciarMapa(final String ruta) {
		ElementosPrinci.mapa = new Mapa(ruta);
	}
	
	/**
	 * Iniciamos despues del mapa el mapa
	 */
	private void iniciarJugador() {
		ElementosPrinci.jugador = new Jugador();
	}
	
	public static void iniciarEnemigos() {
	    enemi = new Enemigo[3];
	    for (int i = 0; i < enemi.length; i++) {
	        int x = (i + 1) * 100;  
	        int y = (i + 1) * 50;   
	        int ataque = 1;        
	        int vida = 100;         
	        
	        enemi[i] = new Enemigo(i, "Enemigo " + i, vida, ataque, "/sonidos/rugidoZombie.wav");
	        enemi[i].establecerPosicion(x, y);
	    }
	}
	
	public void actualizar() {
		if(ElementosPrinci.jugador.getLIMITE_ARRIBA().intersects(ElementosPrinci.mapa.getLugarSalida())) {
			recargarJuego();
		}
		
		ElementosPrinci.jugador.actualizar();
		ElementosPrinci.mapa.actualizar();
		for (Enemigo enemigo : enemi) {
		    enemigo.actualizar(ElementosPrinci.jugador);
		    System.out.println("Posición del enemigo: (" + enemigo.getPosicionX() + ", " + enemigo.getPosicionY() + ")");
		}
	}
	
	public void dibujar(Graphics g) {
		ElementosPrinci.mapa.dibujar(g);
		ElementosPrinci.jugador.dibujar(g);
		for (Enemigo enemigo : enemi) {
            int puntoX = (int) (enemigo.getPosicionX() * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionX() + Constantes.MARGEN_X);
            int puntoY = (int) (enemigo.getPosicionY() * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionY() + Constantes.MARGEN_Y);
            enemigo.dibujar(g, puntoX, puntoY);
        }
		menuHUD.dibujar(g);
		
		
		
		/**
		 * Configuracion del logo del juego en pantalla
		 */
		DibujoDebug.dibujarImg(g, logo, 655 - logo.getWidth() - 5,  90 - logo.getHeight() - 5);
		
		DatosDebug.enviarDato("X :" + ElementosPrinci.jugador.getPosicionXInt());
		DatosDebug.enviarDato("Y :" + ElementosPrinci.jugador.getPosicionYInt());
		DatosDebug.enviarDato("Siguiente nivel: " + ElementosPrinci.mapa.getcambioMapa());
		DatosDebug.enviarDato("Coordenada salida X: " + ElementosPrinci.mapa.getPuntoSalida().getX() + "Y: " + ElementosPrinci.mapa.getPuntoSalida().getY());
		
		/**
		 * Zona donde hay salto de nivel, (comentar para que no aparezca la zona y se tenga que buscar)
		 * DibujoDebug.dibujarRectangleRelleno(g, mapa.getLugarSalida().x, mapa.getLugarSalida().y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE, Color.blue);
		 */
		
	}

}
