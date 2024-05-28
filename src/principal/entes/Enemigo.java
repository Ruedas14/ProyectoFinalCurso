package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.herramientas.CalculaDistancia;
import principal.herramientas.DibujoDebug;
import principal.sonido.Sonido;

public class Enemigo {

    private Sonido lastimado;

    private long duracionLastimado;
    private long lastimadoSiguiente = 0;

    private int idEnemigo;

    protected double posicionX;
    protected double posicionY;

    private Rectangle areaPosicional;
    
    private String nombre;
    private int vidaMaxima;
    private float vidaActual;
    private float ataque;

	private double velocidad = 0.5;

    /**
     * constructor con atributos del enemigo
     * @param idEnemigo
     * @param nombre
     * @param vidaMaxima
     */
    public Enemigo(final int idEnemigo, final String nombre, final int vidaMaxima, float ataque, final String rutaLastimado) {
        this.idEnemigo = idEnemigo;

        this.posicionX = 0;
        this.posicionY = 0;
        this.areaPosicional = new Rectangle((int) posicionX, (int) posicionY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
        

        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima; 
        
        this.ataque = ataque;

        this.lastimado = new Sonido(rutaLastimado);
        this.duracionLastimado = lastimado.getDuracion();
    }
    
   

    /**
     * Para Actualizar enemigo
     * @param jugador
     */
    public void actualizar(Jugador jugador) {
        if(lastimadoSiguiente > 0) {
            lastimadoSiguiente -= 1000000 / 60;
        }

        perseguirJugador(jugador);
        verificarColisionConJugador(jugador);
    }
    
    /**
     * inteligencia para que el enemigo persiga al jugador
     * @param jugador
     */
    public void perseguirJugador(Jugador jugador) {
        double jugadorX = jugador.getPosicionX();
        double jugadorY = jugador.getPosicionY();

        double deltaX = jugadorX - this.posicionX;
        double deltaY = jugadorY - this.posicionY;

        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distancia > 0) {
            this.posicionX += (deltaX / distancia) * velocidad;
            this.posicionY += (deltaY / distancia) * velocidad;
        }

        this.areaPosicional.setLocation((int) this.posicionX, (int) this.posicionY);
    }


    /**
     * metodo de dibujo del enemigo
     * @param g
     * @param puntoX
     * @param puntoY
     */
    public void dibujar(final Graphics g, final int puntoXJugador, final int puntoYJugador) {
        if (vidaActual <= 0) {
            return;
        }

        dibujarBarraVida(g, puntoXJugador, puntoYJugador);

        final int puntoDibujoX = (int) (posicionX - puntoXJugador);
        final int puntoDibujoY = (int) (posicionY - puntoYJugador);

        DibujoDebug.dibujarRectangleContorno(g, puntoDibujoX, puntoDibujoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
        dibujarDistancia(g, puntoXJugador, puntoYJugador);
    }

    /**
     * para mostrar vida del enemigo
     * @param g
     * @param puntoX
     * @param puntoY
     */
    private void dibujarBarraVida(final Graphics g, final int puntoX, final int puntoY) {
        g.setColor(Color.red);

        int anchoBarra = (int) ((Constantes.LADO_SPRITE * vidaActual) / vidaMaxima);
        DibujoDebug.dibujarRectangleRelleno(g, puntoX, puntoY - 5, anchoBarra, 2);
    }

    private void dibujarDistancia(final Graphics g, final int puntoX, final int puntoY) {
        Point puntoJugador = new Point((int) ElementosPrinci.jugador.getPosicionX() / Constantes.LADO_SPRITE,
                (int) ElementosPrinci.jugador.getPosicionY() / Constantes.LADO_SPRITE);

        Point puntoEnemigo = new Point((int) posicionX, (int) posicionY);
        Double distancia = CalculaDistancia.getDistanciaEntrePuntos(puntoJugador, puntoEnemigo);
        DibujoDebug.dibujarString(g, String.format("%.2f" ,distancia), puntoX, puntoY - 8);
    }
    
    /**
     * Método para verificar la colisión con el jugador y hacerle daño si colisionan.
     * @param jugador El jugador con el que se verifica la colisión.
     */
    public void verificarColisionConJugador(Jugador jugador) {
        Rectangle areaJugador = jugador.getArea();
        Rectangle areaEnemigo = getArea();

        if (areaJugador.intersects(areaEnemigo)) {
            jugador.reducirVida(ataque);
        }
    }
    
   

    /**
     * para saber en la posicion en la que se encuentra
     * @param posicionX
     * @param posicionY
     */
    public void establecerPosicion(final double posicionX, final double posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public int getIdEnemigo() {
        return idEnemigo;
    }

    public float getVidaActual() {
        return vidaActual;
    }

    public void perderVida(float ataqueRecibido) {
        if(lastimadoSiguiente <= 0) {
            lastimado.reproducir();
            lastimadoSiguiente = duracionLastimado;
        }

        if(vidaActual - ataqueRecibido < 0) {
            vidaActual = 0;
        } else {
            vidaActual -= ataqueRecibido;
        }
    }

    /**
     * Calcular las coordenadas de colisión en relación con la posición del jugador
     * @return
     */
    public Rectangle getArea() {
        final int puntoX = (int) (posicionX - ElementosPrinci.jugador.getPosicionX()) + Constantes.MARGEN_X;
        final int puntoY = (int) (posicionY - ElementosPrinci.jugador.getPosicionY()) + Constantes.MARGEN_Y;
        
        return new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
    }
    
    public String getNombre() {
        return nombre;
    }

    public Rectangle getAreaPosicional() {
        return areaPosicional;
    }

	public float getAtaque() {
		return ataque;
	}



	public void setPosicion(int nuevaPosX, int nuevaPosY) {
		this.posicionX = nuevaPosX;
	    this.posicionY = nuevaPosY;
		
	}

   
}