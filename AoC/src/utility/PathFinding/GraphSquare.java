package utility.PathFinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utility.Coordinate;

public class GraphSquare {

	private int[][] twoDGrid; 
	private List<Coordinate> obstacle; 
		
	public GraphSquare(int h, int w, List<Coordinate> o) {
		this.twoDGrid = new int [h][];
		for(int i = 0; i < h; i++) {
			this.twoDGrid[i] = new int[w];
		}
		this.obstacle = o;
	}
	
	public GraphSquare(int h, int w) {
		this.twoDGrid = new int [h][];
		for(int i = 0; i < h; i++) {
			this.twoDGrid[i] = new int[w];
		}
		this.obstacle = new ArrayList<Coordinate>();
	}
	public List<Coordinate> getObstacles() {
		return this.obstacle;
	}
	
	public boolean inBounds(int x, int y) {
		return (0 <= x && x < this.twoDGrid[0].length) && (0 <= y && y < this.twoDGrid.length);
	}

	public boolean inBounds(Coordinate c) {
		return (c.getX() >= 0 && c.getX() < this.twoDGrid[0].length) 
				&& (c.getY() >= 0 && c.getY() < this.twoDGrid.length);
	}
	
	public boolean passable(int x, int y) {
		Coordinate c = new Coordinate(x, y);
		return obstacle.contains(c);
	}

	public boolean passable(Coordinate c) {
		return !obstacle.contains(c);
	}
	
	// change the order of coordinates added here for different movement -> ugly paths 
	public List<Coordinate> getNeighbours(Coordinate c) {
		List<Coordinate> temp = new ArrayList<Coordinate>(); 
		temp.add(new Coordinate(c.getX(), c.getY() - 1)); //N
		temp.add(new Coordinate(c.getX() + 1, c.getY())); //E
		temp.add(new Coordinate(c.getX() - 1, c.getY())); //W
		temp.add(new Coordinate(c.getX(), c.getY() + 1)); //S
		
		temp = temp.stream().filter(e -> inBounds(e)).collect(Collectors.toList());
		temp = temp.stream().filter(e -> passable(e)).collect(Collectors.toList());

		return temp;
	}

}
