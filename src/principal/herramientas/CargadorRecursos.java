package principal.herramientas;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Para cargar la ruta de la imagen 
 */
public class CargadorRecursos {

	/**
	 * para declarar cuando una imagen es opaca
	 * @param ruta
	 * @return
	 */
	public static BufferedImage cargarImagenCompatibleOpaca(String ruta) {
	    try {
	        /**
	         * Cargamos la imagen utilizando getResourceAsStream() para manejar la carga de recursos de manera más robusta 
	         */
	        InputStream inputStream = CargadorRecursos.class.getResourceAsStream(ruta);
	        if (inputStream == null) {
	            throw new IOException("El recurso no se encuentra en la ruta especificada: " + ruta);
	        }
	        BufferedImage imagen = ImageIO.read(inputStream);

	        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	        BufferedImage imagenOpaca = gc.createCompatibleImage(imagen.getWidth(), imagen.getHeight(),Transparency.OPAQUE);
	        
	        Graphics2D g = imagenOpaca.createGraphics();
	        g.drawImage(imagen, 0, 0, null);
	        g.dispose();

	        return imagenOpaca;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/**
	 * Para declarar cuando una imagen es translucida
	 * @param ruta
	 * @return
	 */
	public static BufferedImage cargarImagenCompatibleTranslucida(final String ruta) {
	    try {
	        /**
	         * Cargamos la imagen utilizando getResourceAsStream() para manejar la carga de recursos de manera más robusta
	         */
	        InputStream inputStream = CargadorRecursos.class.getResourceAsStream(ruta);
	        if (inputStream == null) {
	            throw new IOException("El recurso no se encuentra en la ruta especificada: " + ruta);
	        }
	        BufferedImage imagen = ImageIO.read(inputStream);

	        /**
	         * Para obtener la configuración gráfica del monitor que estamos utilizando
	         */
	        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(), imagen.getHeight(), Transparency.TRANSLUCENT);

	        /**
	         * Dibujar en la imagen acelerada 
	         */
	        Graphics2D g = imagenAcelerada.createGraphics();
	        g.drawImage(imagen, 0, 0, null);
	        g.dispose();

	        return imagenAcelerada;
	    } catch (IOException e) {
	        System.out.println("Error al cargar la imagen desde la ruta: " + ruta);
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	/**
	 * para que sepa leer archivos de texto segun los numeros indicados para generar el mapa
	 * @param ruta
	 * @return
	 */
	public static String leerArchivoTexto(final String ruta) {
	    String contenido = "";
	    
	    try {
	        /**
	         * Cargamos el archivo de texto utilizando getResourceAsStream() para manejar la carga de recursos de manera robusta
	         */
	        InputStream entradaBytes = CargadorRecursos.class.getResourceAsStream(ruta);
	        
	        /**
	         *  Verificamos si la carga del recurso fue exitosa
	         */
	        if (entradaBytes != null) {
	            BufferedReader lector = new BufferedReader(new InputStreamReader(entradaBytes));
	            String linea;
	            
	            /**
	             * Leemos el contenido del archivo línea por línea
	             */
	            while ((linea = lector.readLine()) != null) {
	                contenido += linea;
	            }
	            
	            /**
	             * Cerramos el lector
	             */
	            lector.close();
	        } else {
	            throw new IOException("No se pudo cargar el recurso en la ruta especificada: " + ruta);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return contenido;
	}
	
	/**
	 * Para cargar la fuente de letras personalizada detro del videojuego
	 * @param ruta
	 * @return
	 */
	public static Font cargaFuente(final String ruta) {
	    try {
	        InputStream entradaBytes = CargadorRecursos.class.getResourceAsStream(ruta);
	        if (entradaBytes == null) {
	            throw new IOException("No se pudo cargar el recurso en la ruta especificada: " + ruta);
	        }
	        Font fuente = Font.createFont(Font.TRUETYPE_FONT, entradaBytes);
	        fuente = fuente.deriveFont(12f);
	        return fuente;
	    } catch (FontFormatException e) {
	        System.err.println("Error de formato de fuente: " + e.getMessage());
	        return null;
	    } catch (IOException e) {
	        System.err.println("Error al cargar la fuente desde la ruta: " + ruta);
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * para leer recursos de sonidos
	 * @param ruta
	 * @return
	 */
	public static Clip cargarSonido(final String ruta) {
        Clip clip = null;
        InputStream is = null;
        AudioInputStream ais = null;

        try {
            is = CargadorRecursos.class.getResourceAsStream(ruta);
            if (is == null) {
                throw new IOException("El recurso no se encuentra en la ruta especificada: " + ruta);
            }
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            
            /**
             * Obtener el formato original del audio
             */
            AudioFormat baseFormat = ais.getFormat();
            
            /**
             * Crear un formato que sea compatible con Clip
             */
            AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
            );

            /**
             * Obtener un flujo de audio en el nuevo formato
             */
            AudioInputStream decodedAis = AudioSystem.getAudioInputStream(decodedFormat, ais);
            DataLine.Info info = new DataLine.Info(Clip.class, decodedFormat);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(decodedAis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ais != null) {
                try {
                    ais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return clip;
    }
}
