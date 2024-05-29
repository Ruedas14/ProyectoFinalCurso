package principal.mapas;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.Inventario.ContenedorObjetos;
import principal.Inventario.armas.Desarmado;
import principal.control.GestorControles;
import principal.entes.Enemigo;
import principal.entes.RegistroEnemigos;
import principal.herramientas.CalculaDistancia;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Mapa {
	
	private String[] partes;
	
	private final int ancho;
	private final int alto;
	
	private final Point posicionInicial;
	private final Point puntoSalida;
	
	private Rectangle lugarSalida;
	
	private String cambioMapa;
	
	private final Sprite[] paleta;
	
	private  final boolean[] colisiones;
	
	public final ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();
	public  ArrayList<ContenedorObjetos> objetosMapas;
	public final ArrayList<Enemigo> enemigos;
	
	private final int[] sprites;
	
	private final int MARGEN_X =  Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE/2;
	private final int MARGEN_Y =  Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE/2;
	
	public Mapa(final String ruta) {
		
		String contenido = CargadorRecursos.leerArchivoTexto(ruta);
		
		/**
		 * Para comprobar los tipos de partes que hay en el documento de txt
		 */
		partes = contenido.split("\\*");
		
		/**
		 * Lectura del ancho y alto de dicho mapa
		 */
		ancho = Integer.parseInt(partes[0].trim());
		alto = Integer.parseInt(partes[1].trim());
		
		/**
		 * Lectura de la hoja de sprites utilizada
		 */
		String HojasUtilizadas = partes[2];
		String[] hojasSeparadas = HojasUtilizadas.split(",");
		
		/**
		 * Lectura de la paleta de esprites
		 */
		String paletaEntera = partes[3];
		String[] partesPaleta = paletaEntera.split("#");
		
		
		/**
		 * Asignacion Sprites
		 * Asignacion de la paleta los numeros de sprites que hay en existencia
		 */
		paleta = asignarSprites(partesPaleta, hojasSeparadas);
		
		
		/**
		 * Para la lectura de las colisiones
		 */
		String colisionesEnteras = partes[4];
		colisiones = extraerColisiones(colisionesEnteras);
		
		
		/**
		 * para la lectura de los sprites que se van a pintar
		 */
		String spritesEnteros = partes[5];
		String[] cadenasSprites = spritesEnteros.split(" ");
		
		
		sprites = extraerSprites(cadenasSprites);
		
		/**
		 * Para leer el nivel 6 del manual que es la posicion en la que empieza el personaje
		 */
		String posicion = partes[6];
		String[] posiciones = posicion.split("-");
		
		posicionInicial = new Point();
		posicionInicial.x = Integer.parseInt(posiciones[0]) * Constantes.LADO_SPRITE;
		posicionInicial.y = Integer.parseInt(posiciones[1]) * Constantes.LADO_SPRITE;
		
		/**
		 * Cambio de nivel, para configurar la lectura del num7 donde se declara en que tile del mapa actual carga el siguinete o pasa mapa
		 */
		String salidaMapa = partes[7];
		String[] configSalida = salidaMapa.split("-");
		
		puntoSalida = new Point();
		puntoSalida.x = Integer.parseInt(configSalida[0]);
		puntoSalida.y= Integer.parseInt(configSalida[1]);
		cambioMapa = configSalida[2];
		
		lugarSalida = new Rectangle(0, 0, 0, 0);
		
		String informacionObjetos = partes[8];
		objetosMapas = asignarObjetos(informacionObjetos);

		
		String informacionEnemigos = partes[9];
		enemigos = asignarEnemigos(informacionEnemigos);
		
		
	}
	
	/**
	 * para hacer lectura en el txt de la posicion de los enemigos y el tipo de enemigo
	 * @param informacionEnemigos
	 * @return
	 */
	private ArrayList<Enemigo> asignarEnemigos(final String informacionEnemigos){
		ArrayList<Enemigo> enemigos = new ArrayList<>();
		
		String[] informacionEnemigosSeparada = informacionEnemigos.split("#");
		for(int i = 0; i < informacionEnemigosSeparada.length; i++) {
			String[] informacionEnemigoActual = informacionEnemigosSeparada[i].split(":");
			String[] coordenadas = informacionEnemigoActual[0].split(",");
			String idEnemigo = informacionEnemigoActual[1];
			
			Enemigo enemigo = RegistroEnemigos.getEnemigo(Integer.parseInt(idEnemigo));
			enemigo.establecerPosicion(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
			enemigos.add(enemigo);
		}
		
		return enemigos;
	}
	
	/**
	 * para la signacion de los sprites
	 * @param partesPaleta
	 * @param hojasSeparadas
	 * @return
	 */
	private Sprite[] asignarSprites(final String[] partesPaleta, final String[] hojasSeparadas) {
		Sprite[] paleta = new Sprite[partesPaleta.length];
		HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + hojasSeparadas[0].trim() + ".png", Constantes.LADO_SPRITE, true);
		
		for (int i = 0; i < partesPaleta.length; i++) {
			String spriteTemporal = partesPaleta[i];
			String[] partesSprite = spriteTemporal.split("-");
			
			int indicePaleta = Integer.parseInt(partesSprite[0]);
			int indiceSpriteHoja = Integer.parseInt(partesSprite[2].trim());
			
			paleta[indicePaleta] = hoja.getSprite(indiceSpriteHoja);			
		}
		return paleta;
	}
	
	/**
	 * Para examinar todos los caracteres uno por uno que existen en el String de colisiones
	 * @param cadenaColisiones
	 * @return
	 */
	private boolean[] extraerColisiones(final String cadenaColisiones) {
		boolean[] colisiones = new boolean [cadenaColisiones.length()];
		
		for(int i = 0; i < cadenaColisiones.length(); i++) {
			/**
			 * Declaro las colisiones si es 0 no pueda pasar el jugador si es otro si
			 */
			if(cadenaColisiones.charAt(i) == '0') {
				colisiones[i] = false;
			} else {
				colisiones[i] = true;
			}
		}
		return colisiones; 
	}
	
	/**
	 * para poder leer del documento del mapa las posiciones y los objetos que reaparezcan 
	 * en dicha posicion.
	 * @param informacionObjetos
	 * @return
	 */
	private ArrayList<ContenedorObjetos> asignarObjetos(final String informacionObjetos){
		final ArrayList<ContenedorObjetos> objetos = new ArrayList<ContenedorObjetos>();
		
		String[] contenedoresObjetos = informacionObjetos.split("#");
		
		for(String contenedorIndividual : contenedoresObjetos) {
			final ArrayList<Integer> idObjetos = new ArrayList<Integer>();
			final ArrayList<Integer> cantidadObjetos = new ArrayList<Integer>();
			
			final String[] divisionInformacionObjetos = contenedorIndividual.split(":");
			final String[] coordenadas = divisionInformacionObjetos[0].split(",");
			final Point posicionContenedor = new Point(Integer.parseInt(coordenadas[0]), Integer.parseInt(coordenadas[1]));
			
			final String[] objetosCantidades = divisionInformacionObjetos[1].split("/");
			
			for(String objetoActual : objetosCantidades) {
				final String[] datosObjetoActual = objetoActual.split("-");
				
				idObjetos.add(Integer.parseInt(datosObjetoActual[0]));
				cantidadObjetos.add(Integer.parseInt(datosObjetoActual[1]));
			}
			
			final int[] idObjetoArray = new int [idObjetos.size()];
			final int[] cantidadObjetosArray = new int[cantidadObjetos.size()];
			
			for(int i = 0; i < idObjetoArray.length; i++) {
				idObjetoArray[i] = idObjetos.get(i);
				cantidadObjetosArray[i] = cantidadObjetos.get(i);
			}
			final ContenedorObjetos contenedor = new ContenedorObjetos(posicionContenedor, idObjetoArray, cantidadObjetosArray);
			objetos.add(contenedor);
		}
		
		return objetos;
	}
	
	/**
	 * Para rellenar nuestro arraylist de sprites con todos los Sprites en formato numerico
	 * @param cadenasSprites
	 * @return
	 */
	private int[] extraerSprites(final String[] cadenasSprites) {
		ArrayList<Integer> sprites = new ArrayList<Integer>();
		
		/**
		 * prueba rapida
		 */
		for(int i = 0; i < cadenasSprites.length; i++) {
			if(cadenasSprites[i].length() == 2) {
				sprites.add(Integer.parseInt(cadenasSprites[i]));
			} else {
				String uno = "";
				String dos = "";
				
				String error = cadenasSprites[i];
				
				uno+= error.charAt(0);
				uno+= error.charAt(1);
				
				dos+= error.charAt(0);
				dos+= error.charAt(1);
				
				sprites.add(Integer.parseInt(uno));
				sprites.add(Integer.parseInt(dos));
			}
		}
		/**
		 * se copia todo a esta array
		 */
		int[] vectorSprites = new int[sprites.size()];
		
		for (int i = 0; i < sprites.size(); i++) {
			vectorSprites[i] = sprites.get(i);
		}
		
		return vectorSprites;
	}
	
	public void actualizar() {
		actualizarZonaSalida();
	    actualizarArrayColision();
	    actualizarRecogidaObjetos();
	    actualizarAtaques();
	    
	    
	   
	    
	}
	
	/**
	 * Actualizar Ataques
	 */
	private void actualizarAtaques() {
		if(enemigos.isEmpty() || ElementosPrinci.jugador.getAlcanceActual().isEmpty() || ElementosPrinci.jugador.getAlmacenEquipo().getArma1() instanceof Desarmado ) {
			return;
		}
		if(GestorControles.teclado.atacando) {
			ArrayList<Enemigo> enemigosAlcanzados = new ArrayList<>();
			
			if(ElementosPrinci.jugador.getAlmacenEquipo().getArma1().esPenetrantes()) {
				for(Enemigo enemigo : enemigos) {
					if(ElementosPrinci.jugador.getAlcanceActual().get(0).intersects(enemigo.getArea())) {
						enemigosAlcanzados.add(enemigo);
					}
				}
			} else {
				Enemigo enemigoMasCercano = null;
				Double distanciaMasCercana = null;
				
				for(Enemigo enemigo : enemigos) {
					if(ElementosPrinci.jugador.getAlcanceActual().get(0).intersects(enemigo.getArea())) {
						Point puntoJugador =new Point(ElementosPrinci.jugador.getPosicionXInt() / 32, ElementosPrinci.jugador.getPosicionYInt() / 32 );
						Point puntoEnemigo = new Point((int) enemigo.getPosicionX(), (int) enemigo.getPosicionY());
						
						Double distanciaActual = CalculaDistancia.getDistanciaEntrePuntos(puntoJugador, puntoEnemigo);
						
						if(enemigoMasCercano == null) {
							enemigoMasCercano = enemigo;
							distanciaMasCercana = distanciaActual;
						} else if(distanciaActual < distanciaMasCercana) {
							enemigoMasCercano = enemigo;
							distanciaMasCercana = distanciaActual;
						}
					}
				}
				enemigosAlcanzados.add(enemigoMasCercano);
			}
			ElementosPrinci.jugador.getAlmacenEquipo().getArma1().atacar(enemigosAlcanzados);
		}
		Iterator<Enemigo> iterador = enemigos.iterator();
		
		while(iterador.hasNext()) {
			Enemigo enemigo = iterador.next();
			if(enemigo.getVidaActual() <= 0) {
				iterador.remove();
			}
		}
	}
	
	/**
	 * Para el area de colisiones de dentro del mapa
	 * @param posicionX
	 * @param posicionY
	 */
	private void actualizarArrayColision() {
		if(!areasColision.isEmpty()) {
			areasColision.clear();
		}
		/**
		 * Recorre array de boleanos y si hay valor true generara un rectangulo en esa zona del mapa 
		 */
		for(int y = 0; y < this.alto; y++) {
			for(int x = 0; x < this.ancho; x++) {
				int puntoX = x * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionXInt() + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionYInt() + MARGEN_Y;
				
				/**
				 * si encpntramos un objeto que antes tenia un uno y ahora es true por que es colisionable creamos un rectangulo nuevo
				 */
				if(colisiones[x + y * this.ancho]) {
					final Rectangle r = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE );
					areasColision.add(r);
				}
			}
		}
	}
	
	/**
	 * en esta zona le estoy dando el juego de cambio del mapa al colisiopnar con el sprite que quiero como cambio o salida del mapa
	 * @param posicionX
	 * @param posicionY
	 */
	private void actualizarZonaSalida() {
		int puntoX = ((int) puntoSalida.getX()) * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionXInt() + MARGEN_X;
		int puntoY = ((int) puntoSalida.getY()) * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionYInt() + MARGEN_Y;
		
		lugarSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}
	
	
	/**
	 * para comprobar si en el mapa existen objetos y ir recogiendo
	 */
	private void actualizarRecogidaObjetos() {
		if(!objetosMapas.isEmpty()) {
			final Rectangle areaJugador = new Rectangle(ElementosPrinci.jugador.getPosicionXInt(),
					ElementosPrinci.jugador.getPosicionYInt(), Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			for(int i = 0; i < objetosMapas.size(); i++) {
				final ContenedorObjetos contenedor = objetosMapas.get(i);
				
				final Rectangle posicionContenedor = new Rectangle(contenedor.getPosicion().x * Constantes.LADO_SPRITE,
						contenedor.getPosicion().y * Constantes.LADO_SPRITE,Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
				
				if(areaJugador.intersects(posicionContenedor) && GestorControles.teclado.recogiendo) {
					ElementosPrinci.inventario.recogerObjetos(contenedor);
					objetosMapas.remove(i);
				}
			}
		}
	}

	
	/**
	 * margenes colision jugador
	 * @param g
	 * @param posicionX
	 * @param posicionY
	 */
	public void dibujar(Graphics g) {
		for (int y = 0; y < this.alto; y++) {
			for(int x = 0; x < this.ancho; x++) {
				BufferedImage imagen = paleta[sprites[x + y * this.ancho]].getImagen();
				
				int puntoX = x * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionXInt() + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionYInt() + MARGEN_Y;
				
				DibujoDebug.dibujarImg(g, imagen, puntoX, puntoY);

				/**
				 * COLOR DE LOS RECTANGULOS (QUITARLO CUANDO SE QUITEN LOS RECTANGULOS O COMENTARLO)
				 * 
				g.setColor(Color.green);
				for (int r = 0; r < areasColision.size(); r++) {
					Rectangle area = areasColision.get(r);
					g.drawRect(area.x, area.y, area.width, area.height);
				}*/
			}
		}
		
		
		if(!objetosMapas.isEmpty()) {
			for(ContenedorObjetos contenedor : objetosMapas) {
				
				final int puntoX = contenedor.getPosicion().x * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionXInt() + MARGEN_X;
				final int puntoY = contenedor.getPosicion().y * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getPosicionYInt() + MARGEN_Y;
				contenedor.dibujar(g, puntoX, puntoY);
			}
		}
		
		/*if(!enemigos.isEmpty()) {
			for(Enemigo enemigo : enemigos) {
				final int puntoX = (int) enemigo.getPosicionX()  * Constantes.LADO_SPRITE- (int) ElementosPrinci.jugador.getPosicionX() + MARGEN_X;
				final int puntoY = (int) enemigo.getPosicionY()  * Constantes.LADO_SPRITE - (int) ElementosPrinci.jugador.getPosicionY() + MARGEN_Y;
				enemigo.dibujar(g, puntoX, puntoY);
			}
		}*/
	}
	
	/**
	 * Para declararalos bordes de las zonas solidas
	 * @param posicionX
	 * @param posicionY
	 * @param anchoJugador
	 * @param altoJugador
	 * @return
	 */
	public Rectangle getBordes(final int posicionX, final int posicionY) {
		int x = MARGEN_X - posicionX + ElementosPrinci.jugador.getAncho();
		int y = MARGEN_Y - posicionY + ElementosPrinci.jugador.getAlto();
		int ancho = this.ancho * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getAncho() * 2;
		int alto = this.alto * Constantes.LADO_SPRITE - ElementosPrinci.jugador.getAlto() * 2;
		
		return new Rectangle(x, y, ancho, alto);
	}

	public Point getPosicionInicial() {
		return posicionInicial;
	}
	
	public Point getPuntoSalida() {
		return puntoSalida;
	}
	
	public String getcambioMapa() {
		return cambioMapa;
	}
	
	public Rectangle getLugarSalida() {
		return lugarSalida;
	}
	
}

