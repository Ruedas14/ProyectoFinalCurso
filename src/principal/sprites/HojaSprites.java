package principal.sprites;

import java.awt.image.BufferedImage;
import principal.herramientas.CargadorRecursos;

public class HojaSprites {
    
    final private int anchoHojaPixeles;
    final private int altoHojaPixeles;
    
    final private int anchoSprites;
    final private int altoSprites;
    
    final private Sprite[] sprites;
    
    /**
     * Constructor para hojas de sprites con sprites cuadrados.
     * 
     * @param ruta        Ruta de la imagen de la hoja de sprites.
     * @param tamañoSprites Tamaño de los sprites en píxeles.
     * @param hojaOpaca   Booleano que indica si la hoja es opaca o translúcida.
     */
    public HojaSprites(final String ruta, final int tamañoSprites, final boolean hojaOpaca) {
        final BufferedImage imagen;
        
        if (hojaOpaca) {
            imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
        }
        
        if (imagen != null && tamañoSprites > 0) {
            anchoHojaPixeles = imagen.getWidth();
            altoHojaPixeles = imagen.getHeight();
            
            anchoSprites = tamañoSprites;
            altoSprites = tamañoSprites;
            
            sprites = new Sprite[anchoHojaPixeles / tamañoSprites * altoHojaPixeles / tamañoSprites];
            
            rellenarSpritesDesdeImagen(imagen);
        } else {
            throw new IllegalArgumentException("La imagen es nula o el tamaño de los sprites es menor o igual a cero.");
        }
    }
    
    /**
     * Constructor para hojas de sprites con sprites no cuadrados.
     * 
     * @param ruta         Ruta de la imagen de la hoja de sprites.
     * @param anchoSprites Ancho de los sprites en píxeles.
     * @param altoSprites  Alto de los sprites en píxeles.
     * @param hojaOpaca    Booleano que indica si la hoja es opaca o translúcida.
     */
    public HojaSprites(final String ruta, final int anchoSprites, final int altoSprites, final boolean hojaOpaca) {
        final BufferedImage imagen;
        
        if(hojaOpaca) {
            imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
        }
        
        if (imagen != null && anchoSprites > 0 && altoSprites > 0) {
            anchoHojaPixeles = imagen.getWidth();
            altoHojaPixeles = imagen.getHeight();
            
            this.anchoSprites = anchoSprites;
            this.altoSprites = altoSprites;
            
            sprites = new Sprite[anchoHojaPixeles / anchoSprites * altoHojaPixeles / altoSprites];
            
            rellenarSpritesDesdeImagen(imagen);
        } else {
            throw new IllegalArgumentException("La imagen es nula o las dimensiones de los sprites son menores o iguales a cero.");
        }
    }
    
    /**
     * Relleno de sprites desde imagenes
     * @param imagen
     */
    private void rellenarSpritesDesdeImagen(final BufferedImage imagen) {
        for (int y = 0; y < altoHojaPixeles / altoSprites; y++) {
            for (int x = 0; x < anchoHojaPixeles / anchoSprites; x++) {
                final int posicionX = x * anchoSprites;
                final int posicionY = y * altoSprites;
                
                sprites[x + y * (anchoHojaPixeles / anchoSprites)] = new Sprite(imagen.getSubimage(posicionX, posicionY, anchoSprites, altoSprites));
            }
        }
    }
    
    public Sprite getSprite(final int indice) {
        if (indice >= 0 && indice < sprites.length) {
            return sprites[indice];
        } else {
            return null;
        }
    }
    
    public Sprite getSprite(final int x, final int y) {
        if (x >= 0 && x < anchoHojaPixeles / anchoSprites && y >= 0 && y < altoHojaPixeles / altoSprites) {
            return sprites[x + y * (anchoHojaPixeles / anchoSprites)];
        } else {
            return null;
        }
    }
}
