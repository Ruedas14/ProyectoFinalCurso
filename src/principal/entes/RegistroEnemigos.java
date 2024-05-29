package principal.entes;

public class RegistroEnemigos {

	/**
	 * count de enemigos
	 * @param idEnemigo
	 * @return
	 */
	public static Enemigo getEnemigo(final int idEnemigo) {
	    Enemigo enemigo = null;
	    switch (idEnemigo) {
	        case 1:
	            enemigo = new Enemigo(idEnemigo, "Zombie", 10, 1, "/sonidos/rugidoZombie.wav");
	            
	            break;
	    }
	    return enemigo;
	}
}
