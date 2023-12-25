package utility;

public class Coordinate {
	private int x; 
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x; 
		this.y = y; 
	}
	
	public Coordinate move(Direction direction) {
		return new Coordinate(this.x + direction.x, this.y + direction.y);
	}
	
	public int manhattenDistance(Coordinate coord) {
		return Math.abs(this.x - coord.x) + Math.abs(this.y - coord.y);
	}
}
