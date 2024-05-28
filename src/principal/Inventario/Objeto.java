package principal.Inventario;


import java.awt.Rectangle;

import principal.sprites.Sprite;

public abstract class Objeto {
	
	/**pruebas rutas
	 * public static HojaSprites hojaObjetos = new HojaSprites(Constantes.RUTA_OBJETOS, Constantes.LADO_SPRITE, false);
	 */
	
	protected final int id;
	protected final String nombre;
	protected final String descripcion;
	
	protected int cantidad;
	protected int cantidadMaxima;
	
	protected Rectangle posicionMenu;
	protected Rectangle posicionFlotante;
	
	/**
	 * constructor objetos
	 * @param id
	 * @param nombre
	 * @param descripcion
	 */
	public Objeto (final int id, final String nombre, final String descripcion) {
		this.id = id;
		this.nombre=nombre;
		this.descripcion= descripcion;
		
		cantidad = 0;
		cantidadMaxima = 99;
		
		posicionMenu = new Rectangle(0, 0, 0, 0);
		posicionFlotante = new Rectangle(0, 0, 0, 0);
	}
	
	/**
	 * constructor
	 * @param id
	 * @param nombre
	 * @param descripcion
	 * @param cantidad
	 */
	public Objeto(final int id, final String nombre, final String descripcion, final int cantidad) {
		this(id, nombre, descripcion);
		if(cantidad <= cantidadMaxima) {
			this.cantidad = cantidad;
		}
	}
	
	public abstract Sprite getSprite();
	
	/**
	 * para incremento de la cantidad de dicho objeto
	 * @param incremento
	 * @return
	 */
	public boolean incrementaCantidad(final int incremento) {
		boolean incrementado = false;
		
		if(cantidad + incremento <= cantidadMaxima) {
			cantidad += incremento;
			incrementado = true;
		}
		return incrementado;
	}
	
	/**
	 * para reduccion de cantidad
	 * @param reduccion
	 * @return
	 */
	public boolean reducirCantidad(final int reduccion) {
		boolean reducido = false;
		
		if(cantidad - reduccion >= 0) {
			cantidad -= reduccion;
			reducido = true;
		}
		return reducido;
	}
	
	/**
	 * para convertir el metodo cantidad en publico
	 * @return
	 */
	public int getCantidad() {
		return cantidad;
	}
	
	/**
	 * para convertir el metodo id en publico
	 * @return
	 */
	public int getID() {
		return id;
	}
	
	public String getNombre() {
		return nombre; 
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Rectangle getPosicionMenu() {
		return posicionMenu;
	}
	
	public Rectangle getPosicionFlotante() {
		return posicionFlotante;
	}
	
	
	public void establecerPosicionMenu(final Rectangle posicionMenu) {
		this.posicionMenu = posicionMenu;
	}
	
	public void establecerPosicionFlotante(final Rectangle posicionFlotante) {
		this.posicionFlotante = posicionFlotante;
	}
	
	
}
