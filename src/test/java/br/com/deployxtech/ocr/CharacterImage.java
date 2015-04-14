/**
 * 
 */
package br.com.deployxtech.ocr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francisco Silva
 *
 */
public class CharacterImage {
	
	private List<Position> positions = new ArrayList<Position>();
	private Position fist;
	private BufferedImage image;
	private int with, height;
	private int initWith, initHeight;
	
	public CharacterImage(Position fist) {
		this.fist = fist;
		this.initWith = this.fist.getX();
		this.initHeight = this.fist.getY();
		this.add(fist);
	}
	
	public boolean contains(Position position) {
		return positions.contains(position);
	}
	
	public void add(Position position) {
		if (with < position.getX()+1) {
			with = position.getX()+1;
		}
		if (height < position.getY()+1) {
			height = position.getY()+1;
		}
		if (initHeight > position.getY()) {
			initHeight = position.getY();
		}
		positions.add(position);
	}
	
	public void add(CharacterImage character) {
		positions.addAll(character.getPositions());
		if (character.initWith < initWith) {
			initWith = character.initWith;
			fist = character.fist;
		}
		if (character.initHeight < initHeight) {
			initHeight = character.initHeight;
		}
		if (character.with > with) {
			with = character.with;
		}
		if (character.height > height) {
			height = character.height;
		}
	}
	
	public void addPosition(int x, int y) {
		add(new Position(x, y));
	}
	
	public boolean haveSibling(Position olter) {
		for (Position position: positions) {
			if (position.isSibling(olter)) {
				//System.out.printf("%s = %s\n", position, olter);
				return true;
			}
			//System.out.printf("%s != %s\n", position, olter);
		}
		return false;
	}
	
	public boolean isValid(int x, int y) {
		int with = image.getWidth(), height = image.getHeight();
		return x >= 0 && y >= 0 && with > x && height > y;
	}
	
	public BufferedImage newImage() {
		this.image = createImage(with-initWith, height-initHeight);
		int divergent = fist.getX();
		for (Position position: positions) {
			image.setRGB(position.getX() - divergent, position.getY() - initHeight, Color.BLACK.getRGB());
		}
		return this.image;
	}
	
	private BufferedImage createImage(int with, int height) {
		BufferedImage newImage = new BufferedImage(with, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = (Graphics2D) newImage.getGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, with, height);
		g2d.dispose();
		
		return newImage;
	}
	
	public List<Position> getPositions() {
		return positions;
	}
}