/**
 * 
 */
package br.com.deployxtech.ocr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author francisco
 *
 */
public class CharacterProcessing {
	
	public final static int WHITE = Color.WHITE.getRGB(), BLACK = Color.BLACK.getRGB();
	
	public List<CharacterImage> discover(BufferedImage image) {
		List<CharacterImage> characters = new ArrayList<CharacterImage>();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (WHITE != image.getRGB(x, y)) {
					boolean isNew = true;
					Position position = new Position(x, y);
					for (CharacterImage character: characters) {
						if (character.haveSibling(position)) {
							character.add(position);
							isNew = false;
							break;
						}
					}
					if (isNew) {
						characters.add(new CharacterImage(position));
					}
				}
			}
		}
		return characters;
	}

}
