package utility.PathFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utility.Coordinate;

public class GraphSquareWeighted {
	
	private int[][] twoDGrid; 
	private int[][] weights; 
		
	public GraphSquareWeighted(int h, int w, int[][] we) {
		this.twoDGrid = new int [h][];
		for(int i = 0; i < h; i++) {
			this.twoDGrid[i] = new int[w];
		}
		this.weights = we;
	}
	
	public int[][] getWeights() {
		return this.weights;
	}
	
	public boolean inBounds(int x, int y) {
		return (0 <= x && x < this.twoDGrid[0].length) && (0 <= y && y < this.twoDGrid.length);
	}

	public boolean inBounds(Coordinate c) {
		return (c.getX() >= 0 && c.getX() < this.twoDGrid[0].length) 
				&& (c.getY() >= 0 && c.getY() < this.twoDGrid.length);
	}

	// change the order of coordinates added here for different movement -> ugly paths 
	public Map<Coordinate, Integer> getNeighbours(Coordinate c) {
		List<Coordinate> temp = new ArrayList<Coordinate>(); 
		temp.add(new Coordinate(c.getX(), c.getY() - 1)); //N
		temp.add(new Coordinate(c.getX() + 1, c.getY())); //E
		temp.add(new Coordinate(c.getX() - 1, c.getY())); //W
		temp.add(new Coordinate(c.getX(), c.getY() + 1)); //S
		
		temp = temp.stream().filter(e -> inBounds(e)).collect(Collectors.toList());
		
		Map<Coordinate, Integer> w = new HashMap<Coordinate, Integer>();
		for (Coordinate co : temp) {
			w.put(co, weights[co.getX()][co.getY()]);
		}
		return w;
	}

	public int heuristic(Coordinate c1, Coordinate c2) {
		return Math.abs((c1.getX() - c2.getX()) - (c1.getY() - c2.getY()));
	}
}
