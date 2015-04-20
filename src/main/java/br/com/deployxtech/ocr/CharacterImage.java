/**
 * 
 */
package br.com.deployxtech.ocr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francisco Silva
 *
 */
public class CharacterImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Position> positions = new ArrayList<Position>();
	private Position fist;
	private BufferedImage image;
	private int with, height;
	private int initWith, initHeight;

	private double proximidade;

	private char character;

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

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
		if (initWith > position.getX()) {
			initWith = position.getX();
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

	public boolean isCompl(CharacterImage character) {
		return (positions.size()/3.5) > character.positions.size()
				&& character.initWith >= initWith && character.with <= with;
	}

	public boolean isSelf(CharacterImage character) {
		for (Position position: positions) {
			if (character.haveSibling(position)) {
				return true;
			}
		}
		return false;
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

	public List<Position> getPositionScale() {
		List<Position> positionsScale = new ArrayList<Position>();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
					positionsScale.add(new Position(x, y));
				}
			}
		}
		return positionsScale;
	}
	
	/*public void analisarProximidade(CharacterImage outer) {
		proximidade = 0;
		int np = 0;
		newImage();
		List<Position> positionsScale = getPositionScale();
		outer.newImageResize(getWith(), getHeight());
		List<Position> outerPositions = outer.getPositionScale();
		for (Position oScale: outerPositions) {
			if (positionsScale.contains(oScale)) {
				proximidade++;
			}
			else {
				np++;
			}
		}

		proximidade = ((proximidade*2)/(positionsScale.size()+outerPositions.size()))*100;
		//proximidade = (proximidade/np)*100;
	}*/

	public void analisarProximidade(CharacterImage outer) {
		proximidade = 0;
		newImageResize(outer.getWith(), outer.getHeight());
		List<Position> outerPositions = outer.getPositionScale();
		List<Position> positionsScale = getPositionScale();
		int dif = outerPositions.size() - positionsScale.size();		
		int np = 0;
		for (Position pScale: positionsScale) {
			if (outerPositions.contains(pScale)) {
				proximidade++;
			}
			else {
				//proximidade--;
				np++;
			}
		}

		proximidade = ((proximidade*2)/(positionsScale.size()+outerPositions.size()))*100;
		//proximidade = (proximidade/np)*100;
	}

	public BufferedImage newImageScale(int scale) {

		this.image = newImage();

		BufferedImage bi = new BufferedImage(scale * image.getWidth(null),
				scale * image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D grph = (Graphics2D) bi.getGraphics();
		grph.scale(scale, scale);

		grph.drawImage(image, 0, 0, null);
		grph.dispose();

		this.image = bi;
		return bi;
	}

	public  BufferedImage newImageResize(int width, int height) {
		this.image = newImage();
		int type=0;
		type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height,type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		this.image = resizedImage;
		return resizedImage;
	}

	public BufferedImage newImage() {
		this.image = createImage(with-initWith, height-initHeight);
		for (Position position: positions) {
			image.setRGB(position.getX() - initWith, position.getY() - initHeight, Color.BLACK.getRGB());
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

	public int getWith() {
		return with-initWith;
	}

	public int getHeight() {
		return height-initHeight;
	}

	public double getProximidade() {
		return proximidade;
	}
}