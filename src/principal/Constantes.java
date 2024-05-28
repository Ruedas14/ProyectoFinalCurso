package principal;

import java.awt.Color;
import java.awt.Font;

import principal.herramientas.CargadorRecursos;

public class Constantes {
	
	/**
	 * se declaran variables generales del propio videojugo 
	 */
	public static final int LADO_SPRITE = 32;
	public static int LADO_CURSOR = 0;
	
	public static int ANCHO_JUEGO = 640;//1280
	public static int ALTO_JUEGO = 360;//720
	
	public static int ANCHO_PANTALLA_COMPLETA = 1280;// 1290
	public static int ALTO_PANTALLA_COMPLETA = 720;//1080
	
	public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA / (double) ANCHO_JUEGO;
	public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA / (double) ALTO_JUEGO;
	
	public static int CENTRO_VENTANA_X = ANCHO_JUEGO /2;
	public static int CENTRO_VENTANA_Y = ALTO_JUEGO /2;
	
	public static int MARGEN_X = ANCHO_JUEGO / 2 - LADO_SPRITE / 2;
	public static int MARGEN_Y = ALTO_JUEGO / 2 - LADO_SPRITE / 2;
	
	public static String RUTA_MAPA ="/mapas/prueba.ad";
	public static String RUTA_ICONO_RATO ="/imagenes/iconos/iconoCursor.png";
	public static String RUTA_PERSONAJE ="/imagenes/hojasPersonajes/jugador.png";
	public static String RUTA_PERSONAJE_ARMADO ="/imagenes/hojasPersonajes/jugador_arma.png";
	public static String RUTA_ENEMIGO ="/imagenes/hojasEnemigos/";
	public static String RUTA_ICONO_VENTANA ="/imagenes/icono/iconoVentana.png";
	public static String RUTA_LOGOTIPO ="/imagenes/iconos/iconoSFondo.png";
	public static String RUTA_OBJETOS = "/imagenes/hojasObjetos/Healt.png";
	public static String RUTA_ARMAS = "/imagenes/hojasObjetos/armas.png";
	
	/**ubicacion del ttf*/
	public static Font FUENTE_PIXEL = CargadorRecursos.cargaFuente("/fuentes/Pixel.ttf");
	
	/**
	 * se puede cambiar el color de toda la interfaz del inventario
	 */
	public static final Color COLOR_INTERFAZ = new Color(0x32D8BF);

	
	
	
	
}
