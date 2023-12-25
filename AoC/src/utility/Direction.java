package utility;

public enum Direction {
	N(0, -1),
	E(1, 0),
	S(0, 1),
	W(-1, 0);

	public int x; 
	public int y; 

	Direction(int x, int y) {
		this.x = x; 
		this.y = y; 
	}

	public Direction turnLeft() {
		if(this == N) return W;
		if(this == E) return N;
		if(this == S) return E;
		if(this == W) return S;
		return null;		
	}
	public Direction turnRight() {
		if(this == N) return E;
		if(this == E) return S;
		if(this == S) return W;
		if(this == W) return N;
		return null;		
	}

}
