package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

public class Enemigo {

    private double posicionX;
    private double posicionY;

    private int direccion;

    private double velocidad = 0.3;

    private boolean enMovimiento;

    private HojaSprites hs;

    private BufferedImage imagenActual;

    private final int ANCHO_ENEMIGO = 16;
    private final int ALTO_ENEMIGO = 16;

    private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_ENEMIGO / 2,
            Constantes.CENTRO_VENTANA_Y, ANCHO_ENEMIGO, 1);
    private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_ENEMIGO / 2,
            Constantes.CENTRO_VENTANA_Y + ALTO_ENEMIGO, ANCHO_ENEMIGO, 1);
    private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_ENEMIGO / 2,
            Constantes.CENTRO_VENTANA_Y, 1, ALTO_ENEMIGO);
    private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_ENEMIGO / 2,
            Constantes.CENTRO_VENTANA_Y, 1, ALTO_ENEMIGO);

    private int animacion;
    private int estado;

    public static final int VIDA_MAX = 100;
    public float vidaActual = 100;
    private boolean estaVivo = true;
    
    private double ataque = 0.5;
    private boolean lastimado = false;
    private int duracionLastimado = 10; // Duración del estado lastimado
    private int contadorLastimado = 0;

    public Enemigo() {
        posicionX = posicionX;
        posicionY = posicionY;

        direccion = 0;

        enMovimiento = false;

        hs = new HojaSprites(Constantes.RUTA_ENEMIGO, Constantes.LADO_SPRITE, false);

        imagenActual = hs.getSprite(0).getImagen();

        animacion = 0;
        estado = 0;
    }

    public Enemigo(int i, String string, int vida, int ataque2, String string2) {
        this();
        
    }

    public void actualizar(Jugador jugador) {
        cambiarAnimacionEstado();
        enMovimiento = false;
        moverHaciaJugador(jugador);
        animar();
        atacarJugador(jugador);
        gestionarEstadoLastimado();
        seguirJugador(jugador);
    }

    private void atacarJugador(Jugador jugador) {
        if (colisionaConJugador(jugador)) {
            jugador.reducirVida(ataque);
        }
    }

    public boolean colisionaConJugador(Jugador jugador) {
        Rectangle areaEnemigo = getArea();
        Rectangle areaJugador = jugador.getArea();
        return areaEnemigo.intersects(areaJugador);
    }

    private void moverHaciaJugador(Jugador jugador) {
        double jugadorX = jugador.getPosicionX();
        double jugadorY = jugador.getPosicionY();

        double deltaX = jugadorX - posicionX;
        double deltaY = jugadorY - posicionY;

        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double velocidadX = (deltaX / distancia) * velocidad;
        double velocidadY = (deltaY / distancia) * velocidad;

        mover((int) velocidadX, (int) velocidadY);
    }

    private void mover(final int velocidadX, final int velocidadY) {
        if (lastimado) return;

        enMovimiento = true;

        cambiarDireccion(velocidadX, velocidadY);

        if (!fueraMapa(velocidadX, velocidadY)) {
            if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
                posicionX += velocidadX * velocidad;
            }
            if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
                posicionX += velocidadX * velocidad;
            }
            if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
                posicionY += velocidadY * velocidad;
            }
            if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
                posicionY += velocidadY * velocidad;
            }
        }
    }
    public void seguirJugador(Jugador jugador) {
        double velocidad = 0.5; // Velocidad de movimiento del enemigo
        
        double jugadorX = jugador.getPosicionX();
        double jugadorY = jugador.getPosicionY();

        double dx = jugadorX - posicionX;
        double dy = jugadorY - posicionY;

        double distancia = Math.sqrt(dx * dx + dy * dy);

        // Normalizar la dirección
        dx /= distancia;
        dy /= distancia;

        // Mover el enemigo hacia el jugador
        posicionX += dx * velocidad;
        posicionY += dy * velocidad;
    }

    private boolean enColisionArriba(int velocidadY) {
        for (int r = 0; r < ElementosPrinci.mapa.areasColision.size(); r++) {
            final Rectangle area = ElementosPrinci.mapa.areasColision.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) velocidad + 3 * (int) velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (LIMITE_ARRIBA.intersects(areaFutura)) {
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

            if (LIMITE_ABAJO.intersects(areaFutura)) {
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

            if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
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

            if (LIMITE_DERECHA.intersects(areaFutura)) {
                return true;
            }
        }

        return false;
    }

    private boolean fueraMapa(final int velocidadX, final int velocidadY) {
        int posicionFuturaX = (int) posicionX + velocidadX * (int)velocidad;
        int posicionFuturaY = (int) posicionY + velocidadY * (int)velocidad;

        final Rectangle bordesMapa = ElementosPrinci.mapa.getBordes(posicionFuturaX, posicionFuturaY);

        final boolean fuera;

        if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa) || LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)) {
            fuera = false;
        } else {
            fuera = true;
        }

        return fuera;
    }

    private void cambiarDireccion(final int velocidadX, final int velocidadY) {
        if(velocidadX == -1) {
            direccion = 3;
        } else if (velocidadX == 1) {
            direccion = 2;
        }

        if(velocidadY == -1) {
            direccion = 1;
        } else if (velocidadY == 1) {
            direccion = 0;
        }
    }

    private void cambiarAnimacionEstado() {
        if(animacion < 30) {
            animacion++;
        } else {
            animacion = 0;
        }

        if(animacion < 15) {
            estado = 1;
        } else {
            estado = 2;
        }
    }

    private void animar() {
        if(!enMovimiento) {
            estado = 0;
            animacion = 0;
        }

        imagenActual = hs.getSprite(direccion, estado).getImagen();
    }

    public void reducirVida(float cantidad) {
        vidaActual -= cantidad;
        if (vidaActual <= 0) {
            vidaActual = 0;
            // Acciones a realizar cuando el enemigo muere
        }
        lastimado = true;
        contadorLastimado = duracionLastimado;
    }

    private void gestionarEstadoLastimado() {
        if (lastimado) {
            contadorLastimado--;
            if (contadorLastimado <= 0) {
                lastimado = false;
            }
        }
    }
    
    public void perderVida(float ataqueActual) {
    	vidaActual -= ataqueActual;
        
        if (vidaActual <= 0) {
            vidaActual = 0;
            estaVivo = false; 
        }
		
	}

    public void dibujar(Graphics g,int puntoX, int puntoY) {
    	if (estaVivo) {
            puntoX = (int) (posicionX - ElementosPrinci.jugador.getPosicionX() + Constantes.CENTRO_VENTANA_X - ANCHO_ENEMIGO / 2);
            puntoY = (int) (posicionY - ElementosPrinci.jugador.getPosicionY() + Constantes.CENTRO_VENTANA_Y - ALTO_ENEMIGO / 2);

            // Dibujar imagen del enemigo
            DibujoDebug.dibujarImg(g, imagenActual, puntoX, puntoY);
            
            Rectangle areaColision = getArea();
            g.setColor(Color.BLUE);
            g.fillRect(areaColision.x, areaColision.y, areaColision.width, areaColision.height);

            // Dibujar barra de vida
            int barraVidaX = puntoX;
            int barraVidaY = puntoY - 10; // Distancia desde la parte superior del enemigo
            int anchoBarraVida = (int) ((vidaActual / VIDA_MAX) * ANCHO_ENEMIGO); // Calcula el ancho proporcional de la barra de vida
            int alturaBarraVida = 3;
            
            // Dibujar el contorno de la barra de vida
            g.setColor(Color.BLACK);
            g.drawRect(barraVidaX, barraVidaY, ANCHO_ENEMIGO, alturaBarraVida);
            
            // Dibujar la barra de vida propiamente dicha
            g.setColor(Color.RED);
            g.fillRect(barraVidaX, barraVidaY, anchoBarraVida, alturaBarraVida);
        }
    }
    
    public void recibirDanio(float cantidadDanio) {
        vidaActual -= cantidadDanio;
        if (vidaActual <= 0) {
            vidaActual = 0;
            estaVivo = false; 
        }
    }

    public Rectangle getArea() {
    	int x = (int) (posicionX - ANCHO_ENEMIGO / 2);
        int y = (int) (posicionY - ALTO_ENEMIGO / 2);
        
        // Creamos y retornamos el rectángulo de colisión
        return new Rectangle(x, y, ANCHO_ENEMIGO, ALTO_ENEMIGO);
    }

    public double getPosicionX() {
        return posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public void establecerPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }

	public int getVidaActual() {
		return (int) vidaActual;
	}

	public Rectangle getAreaPosicional() {
		 return new Rectangle((int) posicionX, (int) posicionY, ANCHO_ENEMIGO, ALTO_ENEMIGO);
	}

	public void establecerPosicion(double posicionX, double posicionY) {
		this.posicionX = posicionX;	
		this.posicionY = posicionY;
	}
		
	public void establecerPosicionX(double posicionX) {
		this.posicionX = posicionX;		
	}

	public void establecerPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}

	public double getAtaque() {
		return ataque;
	}
}
