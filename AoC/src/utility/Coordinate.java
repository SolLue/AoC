package utility;

public class Coordinate {
	private int x; 
	private int y;

	public Coordinate(int x, int y) {
		this.x = x; 
		this.y = y;		
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void increaseX() {
		this.x++; 
	}
	public void increaseY() {
		this.y++; 
	}
	public void decreaseX() {
		this.x--; 
	}
	public void decreaseY() {
		this.y--; 
	}	

	public void setX(int x) {
		this.x = x; 
	}

	public void setY(int y) {
		this.y = y; 
	}

	public Coordinate move(Direction direction) {
		return new Coordinate(this.x + direction.x, this.y + direction.y);
	}
	public Coordinate moveX(Direction direction, int amountx) {
		return new Coordinate(this.x + (direction.x * amountx), this.y + direction.y);
	}
	public Coordinate moveY(Direction direction, int amounty) {
		return new Coordinate(this.x + direction.x, this.y + (direction.y * amounty));
	}

	@Override
	public int hashCode() {
		int tmp = (this.y + ((this.x + 1) / 2));
		return this.x + (tmp * tmp);
		//    	return Integer.valueOf(this.x).hashCode() + Integer.valueOf(this.y).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		Coordinate c = (Coordinate) o;
		if(this.x == c.getX() && this.y == c.getY()) {
			return true; 
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}
}
