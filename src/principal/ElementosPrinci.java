package principal;

import principal.Inventario.Inventario;
import principal.entes.Enemigo;
import principal.entes.Jugador;
import principal.mapas.Mapa;

public class ElementosPrinci {
	
	public static Mapa mapa = new Mapa(Constantes.RUTA_MAPA);
	public static Jugador jugador = new Jugador();
	public static Inventario inventario = new Inventario();
	public static Enemigo[] enemi = new Enemigo[3];
	
	static {
        // Inicialización de los enemigos
        for (int i = 0; i < enemi.length; i++) {
            enemi[i] = new Enemigo(/* Parámetros de inicialización */);
        }
    }
	
	
	public static void reiniciar() {
		// Por ejemplo, podrías resetear la posición del jugador
	    ElementosPrinci.jugador.establecerPosicionX(ElementosPrinci.mapa.getPosicionInicial().getX());
	    ElementosPrinci.jugador.establecerPosicionY(ElementosPrinci.mapa.getPosicionInicial().getY());

	    // Resetear la vida y la estamina del jugador
	    ElementosPrinci.jugador.vidaActual = Jugador.VIDA_MAX;
	    ElementosPrinci.jugador.estamina = Jugador.ESTAMINA_MAX;	
	    
	    
	}

}
