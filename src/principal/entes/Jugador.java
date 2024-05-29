package principal.entes;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.Inventario.RegistroObjetos;
import principal.Inventario.armas.Arma;
import principal.Inventario.armas.Desarmado;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;


public class Jugador {
	
	private double posicionX;
	private double posicionY;
	
	private int direccion;
	
	private double velocidad = 1;
	
	private boolean enMovimiento;
	
	private HojaSprites hs;
	
	private BufferedImage imagenActual;
	
	private final int ANCHO_JUGADOR = 16;
	private final int ALTO_JUGADOR = 16;
	
	
	/**
	 * Ajuste de los resctangulos de colision para que se detecte dicha colision
	 * */
	private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2, 
			Constantes.CENTRO_VENTANA_Y,ANCHO_JUGADOR, 1);
	private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2, 
			Constantes.CENTRO_VENTANA_Y + ALTO_JUGADOR, ANCHO_JUGADOR, 1);
	private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2, 
			Constantes.CENTRO_VENTANA_Y, 1, ALTO_JUGADOR);
	private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_JUGADOR / 2, 
			Constantes.CENTRO_VENTANA_Y, 1, ALTO_JUGADOR);
	
	
	private int animacion;
	private int estado;
	
	/**
	 * CANTIDAD DE RESISTENCIA DEL JUGADOR MAXIMA
	 * */
	public static final int VIDA_MAX = 200;
    public float vidaActual = 200;
    
	public static final int ESTAMINA_MAX = 600;
	public int estamina = 600;
	private int recuperacion = 0;
	private final int RECUPERACION_MAXIMA = 200;
	private boolean recuperado = true;
	
	public int limitePeso = 70;
	public int pesoActual = 30;
	
	private AlmacenEquipo ae;
	
	private ArrayList<Rectangle> alcanceActual;
	
	
	public Jugador() {
		posicionX = ElementosPrinci.mapa.getPosicionInicial().getX();
		posicionY = ElementosPrinci.mapa.getPosicionInicial().getY();

		direccion = 0;
		
		enMovimiento = false;
		
		hs = new HojaSprites(Constantes.RUTA_PERSONAJE, Constantes.LADO_SPRITE, false);
		
		imagenActual = hs.getSprite(0).getImagen();
		
		animacion = 0;
		estado = 0;
		
		ae = new AlmacenEquipo((Arma) RegistroObjetos.getObjeto(200));
		
		alcanceActual = new ArrayList<>();
		
		 
	}
	
	public boolean colisionaConEnemigo(Enemigo enemigo) {
	    Rectangle areaJugador = new Rectangle((int) posicionX, (int) posicionY, ANCHO_JUGADOR, ALTO_JUGADOR);
	    Rectangle areaEnemigo = enemigo.getAreaPosicional();
	    return areaJugador.intersects(areaEnemigo);
	}
	
	/**
	 * OPTIMIZACION DEL METODO DE MOVIMIENTO de tal manera para que tenga mejor 
	 * configuracionsobre las teclas pulsadas y los cambios de movimiento del personaje
	  */
	public void actualizar() {
		cambiarHojaSprites();
		gestionarVelocidadResistencia();
		cambiarAnimacionEstado();
		enMovimiento = false;
		determinarDireccion();
		animar();
		actualizarArmas();
		
		if (ElementosPrinci.enemi != null) {
		    for (Enemigo enemigo : ElementosPrinci.enemi) {
		        if (colisionaConEnemigo(enemigo)) {
		            reducirVida(enemigo.getAtaque());
		        }
		    }
		}
	}
	
	private void actualizarArmas() {
		if(ae.getArma1() instanceof Desarmado) {
			return;
		}
		calcularAlcanceAtaque();
		ae.getArma1().actualizar();
		
	}
	
	/**
	 * alcance del arma
	 */
	private void calcularAlcanceAtaque() {
		if(ae.getArma1() instanceof Desarmado) {
			return;
		}
		
		alcanceActual = ae.getArma1().getAlcance(this);
	}
	
	private void cambiarHojaSprites() {
		if (ae.getArma1() instanceof Arma && !(ae.getArma1() instanceof Desarmado)) {
			hs = new HojaSprites(Constantes.RUTA_PERSONAJE_ARMADO, Constantes.LADO_SPRITE, false);
		}
	}
	
	/**
	 * Metodo para declarar que en 100 actualizaciones es cuando el jugador recupera estamina o resistencia para correr
	 * */
	private void gestionarVelocidadResistencia() {
		if(GestorControles.teclado.correr && estamina > 0) {
			velocidad = 2;
			recuperado = false;
			recuperacion = 0;
		}else {
			velocidad = 1;
			if(!recuperado && recuperacion < RECUPERACION_MAXIMA) {
				recuperacion++;
			}
			if(recuperacion == RECUPERACION_MAXIMA && estamina < 600) {
				estamina++;
			}
		}
	}
	
	
	/**
	 * Establecer el cambio animacion
	 * */
	private void cambiarAnimacionEstado() {
		if(animacion < 30) {
			animacion++;
		}else {
			animacion = 0;
		}
		
		if(animacion < 15) {
			estado = 1;
		}else {
			estado = 2;
		}
		
	}
	
	
	private void determinarDireccion () {
		final int velocidadX = evaluarVelocidadX();
		final int velocidadY = evaluarVelocidadY();
		
		if(velocidadX == 0 && velocidadY == 0) {
			return;
		}
		
		if((velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)) {
			mover(velocidadX, velocidadY);
			
			/**
			 * para eliminar el bug de movimiento diagonal
			 */
		}else {
			/**
			 * izquierda y arriba
			 */
			if(velocidadX == -1 && velocidadY == -1) {
				if(GestorControles.teclado.izquierda.getUltimaPulsada() > GestorControles.teclado.arriba.getUltimaPulsada()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
			/**
			 * izquierda y abajo
			 */
			if(velocidadX == -1 && velocidadY == 1) {
				if(GestorControles.teclado.izquierda.getUltimaPulsada() > GestorControles.teclado.abajo.getUltimaPulsada()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
				/**
				 * derecha y arriba
				 */
				if(velocidadX == 1 && velocidadY == -1) {
					if(GestorControles.teclado.derecha.getUltimaPulsada() > GestorControles.teclado.arriba.getUltimaPulsada()) {
						mover(velocidadX, 0);
					} else {
						mover(0, velocidadY);
					}
				}
				/**
				 * derecha y abajo
				 */
				if(velocidadX == 1 && velocidadY == 1) {
					if(GestorControles.teclado.derecha.getUltimaPulsada() > GestorControles.teclado.abajo.getUltimaPulsada()) {
						mover(velocidadX, 0);
					} else {
						mover(0, velocidadY);
					}
				}
			}
		}
	}
	
	
	private int evaluarVelocidadX() {
		int velocidadX = 0;
		
		if(GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
			velocidadX = -1;
		} else if (GestorControles.teclado.derecha.estaPulsada() && !GestorControles.teclado.izquierda.estaPulsada()){
			velocidadX = 1;
		}
		return velocidadX;
	}
	
	private int evaluarVelocidadY() {
		int velocidadY = 0;
		
		if(GestorControles.teclado.arriba.estaPulsada() && !GestorControles.teclado.abajo.estaPulsada()) {
			velocidadY = -1;
		} else if (GestorControles.teclado.abajo.estaPulsada() && !GestorControles.teclado.arriba.estaPulsada()){
			velocidadY = 1;
		}
		return velocidadY;
	}
	
	
	/**
	 * Declaracion de variables, para convertir en true el movimiento y animarlo
	 * @param velocidadX
	 * @param velocidadY
	 */
	private void mover(final int velocidadX, final int velocidadY) {
		enMovimiento = true;
		
		cambiarDireccion(velocidadX, velocidadY);
		
		if (!fueraMapa(velocidadX, velocidadY)) {
			if(velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
				posicionX += velocidadX * velocidad;
				restarEstamina();
				
			}
			if(velocidadX == 1 && !enColisionDerecha(velocidadX)) {
				posicionX += velocidadX * velocidad;
				restarEstamina();
	
			}
			if(velocidadY == -1 && !enColisionArriba(velocidadY)) {
				posicionY += velocidadY * velocidad;
				restarEstamina();
			
			}
			if(velocidadY == 1 && !enColisionAbajo(velocidadY)) {
				posicionY += velocidadY * velocidad;
				restarEstamina();
				
			}
		}	
	}
	
	/**
	 *  Método para reducir la vida del jugador
	 * @param cantidad
	 */
    public void reducirVida(double ataque) {
        vidaActual -= ataque;
        if (vidaActual <= 0) {
            vidaActual = 0; 
            reiniciarJuego();
        }
    }
	
	/**
	 * Si se mantiene el sift del teclado se va gastando su resistencia
	 */
	private void restarEstamina() {
		if(GestorControles.teclado.correr && estamina > 0) {
			estamina--;
		}
	}
	
	private void reiniciarJuego() {
	    ElementosPrinci.reiniciar();
	}
	
	
	
	
	/**
	 * EN COLISIONES
	 * @param velocidadY
	 * @return
	 */
	private boolean enColisionArriba(int velocidadY) {
		for (int r = 0; r < ElementosPrinci.mapa.areasColision.size(); r++) {
			final Rectangle area = ElementosPrinci.mapa.areasColision.get(r);
			
			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad + 3 * (int) velocidad;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_ARRIBA.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	private boolean enColisionAbajo(int velocidadY) {
		for (int r = 0; r < ElementosPrinci.mapa.areasColision.size(); r++) {
			final Rectangle area = ElementosPrinci.mapa.areasColision.get(r);
			
			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 3 * (int) velocidad;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_ABAJO.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	private boolean enColisionIzquierda(int velocidadX) {
		for (int r = 0; r < ElementosPrinci.mapa.areasColision.size(); r++) {
			final Rectangle area = ElementosPrinci.mapa.areasColision.get(r);
			
			int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
			int origenY = area.y;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_IZQUIERDA.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	private boolean enColisionDerecha(int velocidadX) {
		for (int r = 0; r < ElementosPrinci.mapa.areasColision.size(); r++) {
			final Rectangle area = ElementosPrinci.mapa.areasColision.get(r);
			
			int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;
			int origenY = area.y;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_DERECHA.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void disparar(List<Enemigo> enemigos, float cantidad) {
        for (Enemigo enemigo : enemigos) {
            if (colisionaConEnemigo(enemigo)) {
                enemigo.reducirVida(cantidad);
                return; // Solo reducimos la vida de un enemigo a la vez
            }
        }
    }
	
	
	
	/**
	 * Configuracion de salida del mapa para que el jugador no se pueda salir
	 * @param velocidadX
	 * @param velocidadY
	 * @return
	 */
	private boolean fueraMapa(final int velocidadX, final int velocidadY) {
		
		int posicionFuturaX = (int) posicionX + velocidadX * (int)velocidad;
		int posicionFuturaY = (int) posicionY + velocidadY * (int)velocidad;
		
		
		final Rectangle bordesMapa = ElementosPrinci.mapa.getBordes(posicionFuturaX, posicionFuturaY);
		
		final boolean fuera;
		
		/**
		 * Para saber si estamos fuera del mapa
		 */
		if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa) || LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)) {
			fuera = false;
		}else {
			fuera = true;
		}
		
		return fuera;
	}
	

	/**
	 * Para cmbiar en la direccion en la que esta mirando
	 * @param velocidadX
	 * @param velocidadY
	 */
	private void cambiarDireccion(final int velocidadX, final int velocidadY) {
		if(velocidadX == -1) {
			direccion = 3;
		}else if (velocidadX == 1) {
			direccion = 2;
		}
		
		if(velocidadY == -1) {
			direccion = 1;
		}else if (velocidadY == 1) {
			direccion = 0;
		}
	}
	
	
	private void animar() {
		if(!enMovimiento) {
			estado = 0;
			animacion = 0;
		}
		
		imagenActual = hs.getSprite(direccion, estado).getImagen();
	}
	
	
	
	public void dibujar(Graphics g) {
		/**
		 * para darnos el centro de la pantalla
		 */
		final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		
		
		DibujoDebug.dibujarImg(g, imagenActual, centroX, centroY);
		
		if(!alcanceActual.isEmpty()) {
			g.setColor(Color.orange);
			dibujarAlcance(g);
		}
		
		/**
		 * Rectangulos para saber la colision del jugador, se puede comentar, solo es para testear
		 * 
		g.setColor(Color.green);
		g.drawRect(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height );
		g.drawRect(LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height );
		g.drawRect(LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height );
		g.drawRect(LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height );*/
		
	}
	
	private void dibujarAlcance(final Graphics g) {
		DibujoDebug.dibujarRectangleRelleno(g, alcanceActual.get(0));
	}
	
	public Rectangle getArea() {
        return new Rectangle((int) posicionX, (int) posicionY, ANCHO_JUGADOR, ALTO_JUGADOR);
    }
	

	public void establecerPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}
	
	public void establecerPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}
	
	public double getPosicionX() {
		return posicionX;
	}
	
	public double getPosicionY() {
		return posicionY;
	}
	
	public int getPosicionXInt() {
		return (int) posicionX;
	}
	
	public int getPosicionYInt() {
		return (int) posicionY;
	}
	
	public int getAncho() {
		return ANCHO_JUGADOR;
	}
	
	public int getAlto() {
		return ALTO_JUGADOR;
	}
	
	public int getVidaActual() {
        return (int) vidaActual;
    }
	
	public boolean estaMuerto() {
	    return vidaActual <= 0;
	}
	
	public int getEstamina() {
		return estamina;
	}
	
	public Rectangle getLIMITE_ARRIBA() {
		return LIMITE_ARRIBA;
	}
	
	public AlmacenEquipo getAlmacenEquipo() {
		return ae;
	}
	
	public int getDireccion() {
		return direccion;
	}
	public ArrayList<Rectangle> getAlcanceActual(){
		return alcanceActual;
	}
}

