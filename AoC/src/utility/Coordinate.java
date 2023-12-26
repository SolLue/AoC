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
	
    @Override
    public int hashCode() {
        return Integer.valueOf(this.x).hashCode() + Integer.valueOf(this.y).hashCode();
    }

    @Override
    public boolean equals(Object o) {
    	Coordinate c = (Coordinate) o;
    	if(this.x == c.x && this.y == c.y) {
    		return true; 
    	}
    	return false;
    }
    
    @Override
    public String toString() {
    	return "[" + this.x + ", " + this.y + "]";
    }
}
