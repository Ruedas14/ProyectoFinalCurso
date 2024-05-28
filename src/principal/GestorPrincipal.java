package principal;

import principal.control.GestorControles;
import principal.graficos.SuperficieDibujo;
import principal.graficos.Ventana;
import principal.maquinaestado.GestorEstados;
import principal.sonido.Sonido;


public class GestorPrincipal {
	private boolean enFuncionamiento = false;
	private String titulo;
	private int ancho;
	private int alto;
	
	public static SuperficieDibujo sd;
	private Ventana ventana;
	private GestorEstados ge;
	
	private static int fps = 0;
	private static int aps = 0;
	
	
	private Sonido musica = new Sonido("/sonidos/musica.wav");
	
	private GestorPrincipal(final String titulo, final int ancho, final int alto) {
		this.titulo = titulo;
		this.ancho = ancho;
		this.alto = alto;
		
	}

	/**
	 * Para ejecutar el juego con su titulo y tamaño
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		
		
		GestorPrincipal gp = new GestorPrincipal("Sulvival-RG", Constantes.ANCHO_PANTALLA_COMPLETA, 
				Constantes.ALTO_PANTALLA_COMPLETA );
				
		gp.iniciarJuego();
		gp.iniciarBuclePrincipal();
	}
	
	public void reiniciarJuego() {
	    iniciarBuclePrincipal();
	}
	
	private void iniciarJuego() {
		enFuncionamiento = true;
		inizializar();
		musica.repetir();
		
	}
	
	private void inizializar() {
		sd = new SuperficieDibujo(ancho, alto);
		ventana = new Ventana(titulo, sd);
		ge = new GestorEstados(sd);
		
	}

	private void iniciarBuclePrincipal() {
		int actualizacionesAcumuladas = 0;
		int framesAcumulados = 0;
		/**
		 * declaro variables para utilizar en el temporizador escribiendo sus
		 * equivalencias y actualizaciones por segundo
		 */
		final int NS_por_Second = 1000000000;
		final int APS_Objetivo = 60;
		final double NS_por_Actualizacion = NS_por_Second / APS_Objetivo;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0;

		/**
		 * Metodo de actualizacion de bucle del videojuego una y otra vez
		 */
		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime();

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_por_Actualizacion;

			while (delta >= 1) {
				actualizar();
				actualizacionesAcumuladas++;
				delta--;
			}

			dibujar();
			framesAcumulados++;

			/**
			 * Para que se actualize el Contador cada segundo de aps y fps
			 */
			if (System.nanoTime() - referenciaContador > NS_por_Second) {
			/**muestra por terminal fps y aps 
			 * System.out.println("FPS :" + framesAcumulados + "APS: " + actualizacionesAcumuladas );
			 */

				aps = actualizacionesAcumuladas;
				fps = framesAcumulados;
				
				actualizacionesAcumuladas = 0;
				framesAcumulados = 0;
				referenciaContador = System.nanoTime();
			}

		}
	}

	/**
	 * ACTUALIZAR POR SEGUNDOS
	 */
	private void actualizar() {
		/**
		 * Para cambiar entre estados
		 */
		if(GestorControles.teclado.inventarioActivo) {
			ge.cambiarEstadoActual(1);
		}else {
			ge.cambiarEstadoActual(0);
		}
		
		ge.actualizar();
		sd.actualizar();
	}
	
	private void dibujar() {
		sd.dibujar(ge);
	}
	
	public static int getFPS() {
		return fps;
	}
	
	public static int getAPS() {
		return aps;
	}
	
}
