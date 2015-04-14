/**
 * 
 */
package br.com.deployxtech.ocr;

/**
 * @author Francisco Silva
 *
 */
public class Position {
	
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			Position outer = (Position)obj;
			return x == outer.x && y == outer.y; 
		}
		else {
			return false;
		}
	}
	
	public boolean isSibling(Position position) {
		return (position.x+1 == x && position.y+1 == y)
			|| (position.x+1 == x && position.y == y)
			|| (position.x+1 == x && position.y-1 == y)
			|| (position.x == x && position.y+1 == y)
			|| (position.x == x && position.y-1 == y)
			|| (position.x-1 == x && position.y+1 == y)
			|| (position.x-1 == x && position.y == y)
			|| (position.x-1 == x && position.y-1 == y);
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", x, y);
	}
}