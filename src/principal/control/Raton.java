package principal.control;


import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DatosDebug;

public class Raton extends MouseAdapter {
    private final Cursor cursor;
    private final Point posicion;
    private boolean click;
    private boolean click2;

    /**
     * configuracion del puntero del raton y so respectiva localizacion
     * @param sd
     */
    public Raton(final SuperficieDibujo sd) {
        Toolkit configuracion = Toolkit.getDefaultToolkit();
        BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucida("/imagenes/iconos/iconoCursor.png");
        
        Constantes.LADO_CURSOR =icono.getWidth();
        
        Point punta = new Point(0, 0);
        this.cursor = configuracion.createCustomCursor(icono, punta, "Cursor Gaming");
        posicion = new Point();
        actualizarPosicion(sd);
        click = false;
        click2 = false;
    }

    public void actualizar(final SuperficieDibujo sd) {
        actualizarPosicion(sd);
    }

    /**
     * para poder mostrar la posicion del raton por pantalla pero en este caso se almacena en el debug
     * @param g
     */
    public void dibujar(final Graphics g) {
        DatosDebug.enviarDato("Mous X: " + posicion.getX());
        DatosDebug.enviarDato("Mous Y: " + posicion.getY());
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    /**
     * para ir actualizando las posiciones en la que se encuentra el raton 
     * @param sd
     */
    private void actualizarPosicion(final SuperficieDibujo sd) {
        final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(posicionInicial, sd);
        posicion.setLocation(posicionInicial.getX(), posicionInicial.getY());
    }

    public Point getPuntoPosicion() {
        return posicion;
    }

    public Rectangle getRectanglePosicion() {
        return new Rectangle(posicion.x, posicion.y, 1, 1);
    }

    /**
     * cuando presionamos
     * @param e
     */
   public void mousePressed(MouseEvent e) {
	   if(SwingUtilities.isLeftMouseButton(e)) {
		   click = true;
	   }else if (SwingUtilities.isRightMouseButton(e)) {
		   click2 = true;
	   }
   }
   
   /**
    * Cuando soltamos 
    * @param e
    */
   public void mouseReleased(MouseEvent e) {
	   if(SwingUtilities.isLeftMouseButton(e)) {
		   click = false;
	   }else if (SwingUtilities.isRightMouseButton(e)) {
		   click2 = false;
	   }
   }

    public boolean getClick() {
        return click;
    }
    
    public boolean getClick2() {
        return click2;
    }

    public void resetClick() {
        click = false;
    }
}
