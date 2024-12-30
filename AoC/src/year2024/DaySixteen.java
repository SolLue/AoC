package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

import utility.Property;

public class DaySixteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day16.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		char[][] grid = new char[input.size()][];
		for(int i = 0; i < input.size(); i++) {
			grid[i] = input.get(i).toCharArray();
		}

		Coordinate start = null;
		Coordinate end = null;
		List<Coordinate> obstacles = new ArrayList<Coordinate>();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '#') {
					Coordinate c = new Coordinate(i, j, Direction.NONE, Integer.MAX_VALUE);
					obstacles.add(c);
				}
				if (grid[i][j] == 'E') 
					end = new Coordinate(i, j);
				if (grid[i][j] == 'S') 
					start = new Coordinate(i, j, Direction.EAST, 0);	
			}
		}

		Graph graph = new Graph(grid.length, grid[0].length, obstacles);

		int minimum = Integer.MAX_VALUE;

		Map<Coordinate, Integer> shortest = djikstra(graph, 0, start, end, minimum);

		List<Coordinate> ends = new ArrayList<Coordinate>();
		for (Coordinate coordinate : shortest.keySet()) {
			if (coordinate.x == end.x && coordinate.y == end.y) {
				ends.add(coordinate);
			}
		}

		Coordinate e = end;
		for (Coordinate coordinate : ends) {
			if(shortest.get(coordinate) < minimum) {
				minimum = shortest.get(coordinate);
				e = coordinate;
			}
		}

		Map<Coordinate, Integer> path = new HashMap<Coordinate, Integer>();
		Coordinate current = e; 
		path.put(current, shortest.get(current));
		while (current.prev != null) {
			current = current.prev;
			path.put(current, shortest.get(current));
		}	

		long stopTime = System.currentTimeMillis();

		System.out.println("Day Sixteen, Part One: " + minimum);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 	

		int count = 0;

		Map<Coordinate, Integer> neighbours = new HashMap<Coordinate, Integer>();
		for (Coordinate i : path.keySet()) {
			Coordinate neigh = graph.getNeighbours(i);
			List<Coordinate> rotation = graph.getRotation(i); 

			if (neigh != null && !path.containsKey(neigh)) {
				int cost = path.get(i) + 1;
				if(cost < minimum)
					neighbours.put(neigh, cost);
			}
			for (Coordinate rotate : rotation) {				
				if (!path.containsKey(rotate)) {
					int cost = path.get(i) + 1000;
					if(cost < minimum)
						neighbours.put(rotate, cost);
				} 
			}
		}

		for (Coordinate coord : neighbours.keySet()) {
			shortest = djikstra(graph, neighbours.get(coord), coord, end, minimum); 

			ends = new ArrayList<Coordinate>();
			for (Coordinate coordinate : shortest.keySet()) {
				if (coordinate.x == end.x && coordinate.y == end.y)
					ends.add(coordinate);
			}
			e = null;
			for (Coordinate coordinate : ends) {
				if(shortest.get(coordinate) == minimum)
					e = coordinate;
			}
			if (shortest.get(e) != null) {
				current = e; 
				path.put(current, shortest.get(current));

				while (current.prev != null) {
					current = current.prev;
					path.put(current, shortest.get(current));
				}	
			}
		}

		for (Coordinate i : path.keySet()) {
			grid[i.x][i.y] = 'ö';				
		}
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'ö')
					count++;
			}
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Sixteen, Part Two: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static Map<Coordinate, Integer> djikstra(Graph graph, int startCost, Coordinate start, Coordinate destination, int minimum) {
		Queue<Coordinate> queue = new PriorityQueue<Coordinate>();
		queue.add(start);
		Map<Coordinate, Integer> pathWithCost = new HashMap<Coordinate, Integer>();
		pathWithCost.put(start, startCost);
		start.prev = null;

		while(!queue.isEmpty()) {
			Coordinate current = queue.poll(); 
			if(current.cost < minimum) {
				if(current.x == destination.x && current.y == destination.y)
					return pathWithCost;

				Coordinate neighbours = graph.getNeighbours(current);
				List<Coordinate> rotation = graph.getRotation(current);

				if (neighbours != null) {
					int neighdistance = pathWithCost.get(current) + 1;
					if(!pathWithCost.containsKey(neighbours) || neighdistance < pathWithCost.get(neighbours)) {
						pathWithCost.put(neighbours, neighdistance);
						neighbours.cost = neighdistance;
						queue.add(neighbours);
						neighbours.prev = current;
					}
				}
				for(Coordinate next : rotation) {
					int newcost = pathWithCost.get(current) + 1000;
					if(!pathWithCost.containsKey(next) || newcost < pathWithCost.get(next)) {
						pathWithCost.put(next, newcost);
						queue.add(next);
						next.cost = newcost;
						next.prev = current;
					}
				}
			} 
		}
		return pathWithCost;	
	}

	static class Coordinate implements Comparable<Coordinate> {
		int x;
		int y;
		Direction direction = Direction.NONE; 
		Coordinate prev = null; 
		int cost; 

		Coordinate(int i, int j) {
			this.x = i;
			this.y = j;
		}
		Coordinate(int i, int j, Direction d) {
			this.x = i;
			this.y = j;
			this.direction = d; 
		}
		Coordinate(int i, int j, Direction d, int c) {
			this.x = i;
			this.y = j;
			this.direction = d;
		}

		int getX() {
			return this.x;
		}
		int getY() {
			return this.y;
		}

		public String toString() {
			return "[" + this.x + " / " + this.y + "] " + this.direction;
		}
		@Override
		public int hashCode() {
			return Objects.hash(direction, x, y);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			return direction == other.direction && x == other.x && y == other.y;
		}
		@Override
		public int compareTo(Coordinate o) {
			return this.cost - o.cost;
		}
	}

	static enum Direction { 
		EAST, WEST, SOUTH, NORTH, NONE
	}

	static class Graph {
		List<Coordinate> obstacle; 
		int[][] grid; 

		Graph(int h, int w, List<Coordinate> obstacles) {
			this.grid = new int[h][w];	
			this.obstacle = obstacles;
		}

		boolean inBounds(Coordinate c) {
			return (c.getX() >= 0 && c.getX() < this.grid.length) 
					&& (c.getY() >= 0 && c.getY() < this.grid[0].length);
		}

		boolean passable(Coordinate c) {
			Coordinate co = new Coordinate(c.x, c.y, Direction.NONE, Integer.MAX_VALUE);
			return !this.obstacle.contains(co);
		}

		List<Coordinate> getRotation(Coordinate c) {
			List<Coordinate> rotate = new ArrayList<Coordinate>();

			if(c.direction.equals(Direction.NORTH) || c.direction.equals(Direction.SOUTH)) {
				Coordinate e = new Coordinate(c.getX(), c.getY() + 1, Direction.EAST);
				if(inBounds(e) && passable(e))
					rotate.add(new Coordinate(c.x, c.y, Direction.EAST));
				Coordinate w = new Coordinate(c.getX(), c.getY() - 1, Direction.WEST);
				if(inBounds(w) && passable(w))
					rotate.add(new Coordinate(c.x, c.y, Direction.WEST));
			} else if(c.direction.equals(Direction.EAST) || c.direction.equals(Direction.WEST)) {
				Coordinate n = new Coordinate(c.getX() - 1, c.getY(), Direction.NORTH);
				if(inBounds(n) && passable(n))
					rotate.add(new Coordinate(c.x, c.y, Direction.NORTH));
				Coordinate s = new Coordinate(c.getX() + 1, c.getY(), Direction.SOUTH);
				if(inBounds(s) && passable(s))
					rotate.add(new Coordinate(c.x, c.y, Direction.SOUTH));
			}
			return rotate;
		}

		Coordinate getNeighbours(Coordinate c) {
			Coordinate temp = null; 

			if(c.direction.equals(Direction.NORTH)) {
				Coordinate n = new Coordinate(c.getX() - 1, c.getY(), Direction.NORTH);
				if(inBounds(n) && passable(n)) 
					temp = n; 		
			} else if(c.direction.equals(Direction.EAST)) {
				Coordinate e = new Coordinate(c.getX(), c.getY() + 1, Direction.EAST);
				if(inBounds(e) && passable(e))
					temp = e; 		
			} else if(c.direction.equals(Direction.SOUTH)) {
				Coordinate s = new Coordinate(c.getX() + 1, c.getY(), Direction.SOUTH);
				if(inBounds(s) && passable(s)) 
					temp = s; 		
			} else if(c.direction.equals(Direction.WEST)) {
				Coordinate w = new Coordinate(c.getX(), c.getY() - 1, Direction.WEST);
				if(inBounds(w) && passable(w)) 
					temp = w; 			
			}
			return temp;
		}
	}
}
