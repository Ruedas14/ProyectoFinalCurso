package principal.maquinaestado.estados.menujuego;

import java.awt.Graphics;
import java.awt.Rectangle;

import principal.graficos.SuperficieDibujo;
import principal.maquinaestado.EstadoJuego;

public class GestorMenu implements EstadoJuego {
	
	private final SuperficieDibujo sd;

	private final EstructuraMenu estructuraMenu;
	
	private final SeccMenu[] secciones;
	
	private SeccMenu seccionActual;
	
	public GestorMenu (final SuperficieDibujo sd) {
		this.sd = sd;
		
		estructuraMenu = new EstructuraMenu();
		
		secciones = new SeccMenu[2];
		
		/**
		 * para la etiqueta del inventario
		 */
		final Rectangle etiquetaInventario = new Rectangle(estructuraMenu.BAN_LATERAL.x + estructuraMenu.MARGIN_HORIZONTAL_ETIQUETAS,
				estructuraMenu.BAN_LATERAL.y + estructuraMenu.MARGIN_VERTICAL_ETIQUETAS, estructuraMenu.ANCHO_ETIQUETAS, 
				estructuraMenu.ALTO_ETIQUETAS);
		
		secciones[0] = new MenuInventario("Inventario", etiquetaInventario, estructuraMenu);
		
		/**
		 * para la etiqueta del Equipo
		 */
		final Rectangle etiquetaEquipo = new Rectangle(estructuraMenu.BAN_LATERAL.x + estructuraMenu.MARGIN_HORIZONTAL_ETIQUETAS,
				etiquetaInventario.y + etiquetaInventario.height + estructuraMenu.MARGIN_VERTICAL_ETIQUETAS, estructuraMenu.ANCHO_ETIQUETAS,
				estructuraMenu.ALTO_ETIQUETAS);
		
		
		secciones[1] = new MenuEquipo("Equipamiento" , etiquetaEquipo, estructuraMenu);
		
		seccionActual = secciones[0];
	}
	
	public void actualizar() {
	    for(int i = 0; i < secciones.length; i++) {
	    	if (sd.getRaton().getClick() && sd.getRaton().getRectanglePosicion().intersects(secciones[i].getEtiquetaMenuEscalada())) {
	    		if(secciones[i] instanceof MenuEquipo) {
	    			MenuEquipo seccion = (MenuEquipo) secciones[i];
	    			if(seccion.objetoSeleccionado != null) {
	    				seccion.eliminarObjetoSeleccionado();
	    			}
	    		}
	    		
	    		seccionActual = secciones[i];
	    	}
	    }
	    seccionActual.actualizar();
	    
	    sd.getRaton().resetClick();
	}

	/**
	 * metodo de dibujo
	 */
	public void dibujar(final Graphics g) {
		estructuraMenu.dibujar(g);
		
		/**
		 * Para recorrer el array y dibujar etiquetas inactivas
		 */
	    for (int i = 0; i < secciones.length; i++) {
	    	
	        if (seccionActual == secciones[i]) {
	        	if(sd.getRaton().getRectanglePosicion().intersects(secciones[i].getEtiquetaMenuEscalada())) {
		    		secciones[i].dibujarEtiquetaActivaResaltada(g);
		    	}else {
		    		secciones[i].dibujarEtiquetaActiva(g);
		    	}
	        }else {
	        	if(sd.getRaton().getRectanglePosicion().intersects(secciones[i].getEtiquetaMenuEscalada())) {
		    		secciones[i].dibujarEtiquetaInactivaResaltada(g);
		    	}else {
		    		secciones[i].dibujarEtiquetaInactiva(g);
		    	}
	        }
		}
	    
	    seccionActual.dibujar(g, sd, estructuraMenu);
	}

}
