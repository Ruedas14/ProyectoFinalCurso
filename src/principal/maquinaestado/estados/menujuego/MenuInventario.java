package principal.maquinaestado.estados.menujuego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.Inventario.Objeto;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;

public class MenuInventario extends SeccMenu {	
	private final int margenGeneral = 8;
	
	private final EstructuraMenu em;

	public MenuInventario(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
		super(nombreSeccion, etiquetaMenu, em);
		
		this.em = em;

	}
	
	public void actualizar() {
		actualizarPosiMenu();
	}
	
	/**
	 * para actualizar posiciones de los objetos y equipables
	 */
	private void actualizarPosiMenu() {
		if(ElementosPrinci.inventario.getConsumibles().isEmpty()) {
			return;
		}
		for(int i = 0; i < ElementosPrinci.inventario.getConsumibles().size(); i++) {
			final Point puntoInicial = new Point (em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);
			
			final int lado = Constantes.LADO_SPRITE;
			
			int idActual = ElementosPrinci.inventario.getConsumibles().get(i).getID();
			
			ElementosPrinci.inventario.getObjeto(idActual).establecerPosicionMenu(new Rectangle(puntoInicial.x + i * (lado + margenGeneral), puntoInicial.y, lado, lado));
		}
	}

	public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {
		dibujarLimitePeso(g);
		dibujarElementosConsumibles(g, em);
		dibujarPg(g, em);
		
		/**
		 * para saber si el raton se encuentra encima del peso en el inventario para que genere dicho texto de info
		 */
		if(sd.getRaton().getRectanglePosicion().intersects(EscaladorElementos.escalarRectanguloArriba(barraPeso))) {
			GeneradorTooltip.dibujarTooltip(g, sd, "kg: " + ElementosPrinci.jugador.pesoActual + "/" + ElementosPrinci.jugador.limitePeso);
		}
	}
	
	/**
	 * se encarga de dibujar todos los objetos que se pueden Consumir
	 * @param g
	 * @param panelObjetos
	 * @param titularPanel
	 */
	private void dibujarElementosConsumibles(final Graphics g, EstructuraMenu em) {
		
		if(ElementosPrinci.inventario.getConsumibles().isEmpty()) {
			return;
		}
		
		final Point puntoInicial = new Point (em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);
		
		final int lado = Constantes.LADO_SPRITE;
		
		for(int i = 0; i < ElementosPrinci.inventario.getConsumibles().size(); i++) {
			
			int idActual = ElementosPrinci.inventario.getConsumibles().get(i).getID();
			Objeto objetoActual = ElementosPrinci.inventario.getObjeto(idActual);
			
			DibujoDebug.dibujarImg(g, objetoActual.getSprite().getImagen(), objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y );
			
			g.setColor(Color.black);
			DibujoDebug.dibujarRectangleRelleno(g, puntoInicial.x + i * (lado + margenGeneral) + lado - 12, puntoInicial.y + 32 - 8, 12, 8);
			
			String texto = "";
			
			g.setColor(Color.white);
			if(objetoActual.getCantidad() < 10) {
				 texto = "0" + objetoActual.getCantidad();
			} else {
				 texto = "" + objetoActual.getCantidad();
			}
			
			g.setFont(g.getFont().deriveFont(10f));
			DibujoDebug.dibujarString(g, texto, puntoInicial.x + i * (lado + margenGeneral) + lado - MedidorString.medirAnchoPixeles(g, texto),
												puntoInicial.y + 31);
		}
		g.setFont(g.getFont().deriveFont(12f));
	}
	
	/**
	 * en caso de que haya muchas slots de inventario para poder pasar a la siguinte hoja de inventartio
	 * @param g
	 * @param em
	 */
	public void dibujarPg(final Graphics g, EstructuraMenu em) {
		final int anchoBoton = 32;
		final int altoBoton = 16;
		
		final Rectangle anterior = new Rectangle (em.FONDO.x + em.FONDO.width - margenGeneral * 2 - anchoBoton * 2 - 4,
				em.FONDO.y + em.FONDO.height - margenGeneral - altoBoton, anchoBoton, altoBoton);
		
		final Rectangle siguiente = new Rectangle(anterior.x + anterior.width + margenGeneral, anterior.y, 
				anchoBoton, altoBoton);
		
		g.setColor(Constantes.COLOR_INTERFAZ);
		
		DibujoDebug.dibujarRectangleRelleno(g, anterior);
		DibujoDebug.dibujarRectangleRelleno(g, siguiente);
	}

	
}
