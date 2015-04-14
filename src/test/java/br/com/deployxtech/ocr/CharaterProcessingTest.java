/**
 * 
 */
package br.com.deployxtech.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author francisco
 *
 */
public class CharaterProcessingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CharacterProcessing processing = new CharacterProcessing();
			List<CharacterImage> characters = processing.discover(ImageIO.read(new File("/home/francisco/Downloads/teste.png")));
			File dir = new File("/home/francisco/Downloads/test");
			
			for (File file: dir.listFiles()) {
				file.delete();
			}
			
			for (CharacterImage character: characters) {
				BufferedImage image = character.newImage();
				ImageIO.write(image, "png", dir.toPath().resolve(String.format("%s.png", dir.listFiles().length)).toFile());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
