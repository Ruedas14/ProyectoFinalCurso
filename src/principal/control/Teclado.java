package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.Clip;

import principal.herramientas.CargadorRecursos;

public class Teclado implements KeyListener {

	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla izquierda = new Tecla();
	public Tecla derecha = new Tecla();

	public boolean recogiendo = false;
	public boolean correr = false;
	public boolean debug = false;
	public boolean inventarioActivo = false;
	public boolean atacando = false;
	
	/**
	 * se declaron los tipos de casos segun la tecla que se presione en cada caso
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			arriba.teclaPulsada();
			break;
		case KeyEvent.VK_S:
			abajo.teclaPulsada();
			break;
		case KeyEvent.VK_A:
			izquierda.teclaPulsada();
			break;
		case KeyEvent.VK_D:
			derecha.teclaPulsada();
			break;
		case KeyEvent.VK_E:
			recogiendo = true;
			break;
		case KeyEvent.VK_SHIFT:
			correr = true;
			break;
		case KeyEvent.VK_F12:
			debug = !debug;
			break;
		case KeyEvent.VK_I:
			inventarioActivo = !inventarioActivo;
			break;
		case KeyEvent.VK_SPACE:
			atacando = true;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}	
	}
	
	/**
	 * Cuando la tecla despues de ser pulsada se queda libre
	 */
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			arriba.teclaLibre();
			break;
		case KeyEvent.VK_S:
			abajo.teclaLibre();
			break;
		case KeyEvent.VK_A:
			izquierda.teclaLibre();
			break;
		case KeyEvent.VK_D:
			derecha.teclaLibre();
			break;
		case KeyEvent.VK_E:
			recogiendo = false;
			break;
		case KeyEvent.VK_SHIFT:
			correr = false;
			break;
		case KeyEvent.VK_SPACE:
			atacando = false;
			break;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	
	
}
