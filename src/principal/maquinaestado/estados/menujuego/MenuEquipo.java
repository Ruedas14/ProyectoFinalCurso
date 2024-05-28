package principal.maquinaestado.estados.menujuego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrinci;
import principal.GestorPrincipal;
import principal.Inventario.Objeto;
import principal.Inventario.armas.Arma;
import principal.Inventario.armas.Desarmado;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;

public class MenuEquipo extends SeccMenu {

	/**
	 * Rectangulos para pintar en inventario equipo
	 */
	final Rectangle panelObjetos = new Rectangle(em.FONDO.x + margenGeneral, barraPeso.y + barraPeso.height + margenGeneral, 248, 
			Constantes.ALTO_JUEGO - barraPeso.y - barraPeso.height - margenGeneral * 2);
	
	final Rectangle titulosObjetos = new Rectangle(panelObjetos.x, panelObjetos.y, panelObjetos.width, 24);
	
	final Rectangle panelEquipo = new Rectangle(panelObjetos.x + panelObjetos.width + margenGeneral, panelObjetos.y, 88, panelObjetos.height);
	
	final Rectangle titulosEquipo = new Rectangle(panelEquipo.x, panelEquipo.y, panelEquipo.width, 24);
	
	final Rectangle panelAtributos = new Rectangle(panelEquipo.x + panelEquipo.width + margenGeneral, panelObjetos.y, 132, panelEquipo.height);
	
	final Rectangle titulosAtributos = new Rectangle(panelAtributos.x, panelAtributos.y, panelAtributos.width, 24);
	
	final Rectangle etiquetaArma = new Rectangle(titulosEquipo.x + margenGeneral, titulosEquipo.y + titulosEquipo.height + margenGeneral,
			titulosEquipo.width - margenGeneral * 2, margenGeneral * 2 + MedidorString.medirAltoPixeles(GestorPrincipal.sd.getGraphics(), "Arma"));
	
	final Rectangle contenedorArma = new Rectangle(etiquetaArma.x + 1, etiquetaArma.y + etiquetaArma.height,
			etiquetaArma.width - 2, Constantes.LADO_SPRITE);
	
	Objeto objetoSeleccionado = null;
	
	/**
	 * constructor
	 * @param nombreSeccion
	 * @param etiquetaMenu
	 * @param em
	 */
	public MenuEquipo(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
		super(nombreSeccion, etiquetaMenu, em);		
	}

	public void actualizar() {
		actualizarPosiMenu();
		actualizarSeleccionRaton();
		actualizarObjetoSeleccionado();
	}
	
	/**
	 * para actualizar posiciones de los objetos y equipables
	 */
	private void actualizarPosiMenu() {
		if(ElementosPrinci.inventario.getArmas().isEmpty()) {
			return;
		}
		for(int i = 0; i < ElementosPrinci.inventario.getArmas().size(); i++) {
			final Point puntoInicial = new Point(titulosObjetos.x + margenGeneral, titulosObjetos.y + titulosObjetos.height + margenGeneral);
			
			final int lado = Constantes.LADO_SPRITE;
			
			int idActual = ElementosPrinci.inventario.getArmas().get(i).getID();
			
			ElementosPrinci.inventario.getObjeto(idActual).establecerPosicionMenu(new Rectangle(puntoInicial.x + i * (lado + margenGeneral), puntoInicial.y, lado, lado));
		}
	}

	/**
	 * para saber la ubicacion del raton en el equipo y seleccion en el cuadrante Arma
	 */
	private void actualizarSeleccionRaton() {
		Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getRectanglePosicion();
		
		if(posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(panelObjetos))) {
			if(ElementosPrinci.inventario.getArmas().isEmpty()) {
				return;
			}
			
			for(Objeto objeto : ElementosPrinci.inventario.getArmas()) {
				if(GestorPrincipal.sd.getRaton().getClick() && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(objeto.getPosicionMenu()))) {
					objetoSeleccionado = objeto;
				}
			}
		} else if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(panelEquipo))){
			if(objetoSeleccionado != null && objetoSeleccionado instanceof Arma && GestorPrincipal.sd.getRaton().getClick() && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(contenedorArma))) {
				ElementosPrinci.jugador.getAlmacenEquipo().cambiarArma1((Arma)objetoSeleccionado);
				objetoSeleccionado = null;
			}
			
		} else if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(panelAtributos))){
			
		}
	}
	
	/**
	 * Si se selecciono el objeto que se quede con la funcion de arrastrando
	 */
	private void actualizarObjetoSeleccionado() {
		if(objetoSeleccionado != null) {
			if(GestorPrincipal.sd.getRaton().getClick2()) {
				objetoSeleccionado = null;
				return;
			}
			Point posicionRaton = EscaladorElementos.escalaPuntoAbajo(GestorPrincipal.sd.getRaton().getPuntoPosicion());
			objetoSeleccionado.establecerPosicionFlotante(new Rectangle(posicionRaton.x, posicionRaton.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));
			
		}
	}

	/**
	 * se declara la forma de dibujar los rectangulos y sus titulos
	 */
	public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {
		dibujarLimitePeso(g);
		
		dibujarPaneles(g);
		
		
		if(sd.getRaton().getRectanglePosicion().intersects(EscaladorElementos.escalarRectanguloArriba(barraPeso))) {
			GeneradorTooltip.dibujarTooltip(g, sd, "kg: " + ElementosPrinci.jugador.pesoActual + "/" + ElementosPrinci.jugador.limitePeso);
		}
	}
	
	/**
	 * para implementar la creacion de los rectangulos
	 * @param g
	 */
	private void dibujarPaneles(final Graphics g) {
		dibujarPanelObjetos(g, panelObjetos, titulosObjetos, "Equipamientos");
		dibujarPanelEquipo(g, panelEquipo, titulosEquipo, "Equipo");
		dibujarPanelAtributos(g, panelAtributos, titulosAtributos, "Atributos");
	}
	
	/**
	 * para enmascarar los metodos de dibujar los paneles 
	 * @param g
	 * @param panel
	 * @param titularPanel
	 * @param nombrePanel
	 */
	private void dibujarPanelObjetos(final Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel) {
		dibujarPanel(g, panel, titularPanel, nombrePanel);
		dibujarElementosEquipables(g, panel, titularPanel);
	}
	
	/**
	 * se encarga de dibujar todos los objetos que se pueden equipar
	 * @param g
	 * @param panelObjetos
	 * @param titularPanel
	 */
	private void dibujarElementosEquipables(final Graphics g, final Rectangle panelObjetos, final Rectangle titularPanel) {
		
		if(ElementosPrinci.inventario.getArmas().isEmpty()) {
			return;
		}
		
		final Point  puntoInicial = new Point(titularPanel.x + margenGeneral, titularPanel.y + titularPanel.height + margenGeneral);
		
		final int lado = Constantes.LADO_SPRITE;
		
		for(int i = 0; i < ElementosPrinci.inventario.getArmas().size(); i++) {
			
			int idActual = ElementosPrinci.inventario.getArmas().get(i).getID();
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
		
		if(objetoSeleccionado != null) {
			DibujoDebug.dibujarImg(g, objetoSeleccionado.getSprite().getImagen(), new Point(objetoSeleccionado.getPosicionFlotante().x, 
					objetoSeleccionado.getPosicionFlotante().y));
		}
	}
	/**
	 * 
	 * @param g
	 * @param panel
	 * @param titularPanel
	 * @param nombrePanel
	 */
	private void dibujarPanelEquipo(final Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel) {
		dibujarPanel(g, panel, titularPanel, nombrePanel);
		
		g.setColor(Constantes.COLOR_INTERFAZ);
		
		DibujoDebug.dibujarRectangleRelleno(g, etiquetaArma);
		DibujoDebug.dibujarRectangleContorno(g, contenedorArma);
		
		if(!(ElementosPrinci.jugador.getAlmacenEquipo().getArma1() instanceof Desarmado)) {
			Point coordenadaImagen = new Point(contenedorArma.x + contenedorArma.width / 2 - Constantes.LADO_SPRITE / 2,
					contenedorArma.y);
			
			DibujoDebug.dibujarImg(g, ElementosPrinci.jugador.getAlmacenEquipo().getArma1().getSprite().getImagen(),
					coordenadaImagen);
		}
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "Arma", new Point(etiquetaArma.x + etiquetaArma.width / 2 - MedidorString.medirAnchoPixeles(g, "Arma") / 2,
														etiquetaArma.y + etiquetaArma.height / 2 + MedidorString.medirAltoPixeles(g, "Arma") / 2));
	}
	private void dibujarPanelAtributos(final Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel) {
		dibujarPanel(g, panel, titularPanel, nombrePanel);
	}
	
	/**
	 * para optimizacion del codigo y no se repitan las cosas
	 * @param g
	 * @param panel
	 * @param titularPanel
	 * @param nombrePanel
	 */
	private void dibujarPanel(final Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel) {
		g.setColor(Constantes.COLOR_INTERFAZ);
		DibujoDebug.dibujarRectangleContorno(g, panel);
		DibujoDebug.dibujarRectangleRelleno(g, titularPanel);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, nombrePanel, new Point(titularPanel.x + titularPanel.width / 2 - MedidorString.medirAnchoPixeles(g, nombrePanel) / 2, 
				titularPanel.y + titularPanel.height - MedidorString.medirAltoPixeles(g, nombrePanel) / 2));
		
		g.setColor(Constantes.COLOR_INTERFAZ);
	}
	
	public Objeto getObjetoSeleccionado() {
		return objetoSeleccionado;
	}
	
	public void eliminarObjetoSeleccionado() {
		objetoSeleccionado = null;
	}
	

}
