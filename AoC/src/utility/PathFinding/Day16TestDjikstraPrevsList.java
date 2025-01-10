package utility.PathFinding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import utility.Property;

public class Day16TestDjikstraPrevsList {
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
		Map<Coordinate, Integer> pathCost = djikstra(graph, start, end);

		int minimum = pathCost.get(destination);
		
		long stopTime = System.currentTimeMillis();

		System.out.println("Day Sixteen, Part One: " + minimum);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 	

		Set<Coordinate> path = new HashSet<Coordinate>();
		Queue<Coordinate> q = new PriorityQueue<Coordinate>();
		Set<Coordinate> visited = new HashSet<Coordinate>();

		q.add(destination);
		while(!q.isEmpty()) {
			Coordinate current = q.poll();
			visited.add(current);
			path.add(current);
			
			if(!current.prevs.isEmpty()) {
				System.out.println(current.prevs);
				for (Coordinate coordinate : current.prevs) {
					if (!visited.contains(coordinate)) {
						if(pathCost.get(coordinate) < minimum)
							q.add(coordinate);
					}
				}
			}
			current = current.prev;
		}
		
		for (Coordinate i : path) {
			grid[i.x][i.y] = 'ö';				
		}

		int count = 0; 
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] == 'ö')
					count++;
	//			System.out.print(grid[i][j]);
			}
		//	System.out.println();
		}




		stopTime = System.currentTimeMillis();
		System.out.println("Day Sixteen, Part Two - again: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));


	}

	static Coordinate destination = null;
	static Map<Coordinate, Integer> djikstra(Graph graph, Coordinate start, Coordinate end) {
		Queue<Coordinate> queue = new PriorityQueue<Coordinate>();
		queue.add(start);
		Map<Coordinate, Integer> pathWithCost = new HashMap<Coordinate, Integer>();
		pathWithCost.put(start, 0);
		start.prev = null;

		while(!queue.isEmpty()) {
			Coordinate current = queue.poll(); 

			Coordinate nextNeigh = graph.getNeighbours(current);
			List<Coordinate> rotation = graph.getRotation(current);

			if (current.x == end.x && current.y == end.y)
				destination = current;
			if (nextNeigh != null) {
				int nextcost = pathWithCost.get(current) + 1;
				if(!pathWithCost.containsKey(nextNeigh) || nextcost <= pathWithCost.get(nextNeigh)) {
					pathWithCost.put(nextNeigh, nextcost);
					queue.add(nextNeigh);
					nextNeigh.cost = nextcost;
					nextNeigh.prev = current;
					nextNeigh.prevs.add(current);
				}
			}
			for(Coordinate next : rotation) {
				int newcost = pathWithCost.get(current) + 1000;
				if(!pathWithCost.containsKey(next) || newcost <= pathWithCost.get(next)) { 
					pathWithCost.put(next, newcost);
					queue.add(next);
					next.cost = newcost; 
					next.prev = current;
					next.prevs.add(current);
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
		Set<Coordinate> prevs;
 
		Coordinate(int i, int j) {
			this.x = i;
			this.y = j;
			this.prevs = new HashSet<Coordinate>();
		}
		Coordinate(int i, int j, Direction d) {
			this.x = i;
			this.y = j;
			this.direction = d; 
			this.prevs = new HashSet<Coordinate>();
		}
		Coordinate(int i, int j, Direction d, int c) {
			this.x = i;
			this.y = j;
			this.direction = d;
			this.prevs = new HashSet<Coordinate>();
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
		List<Coordinate> allCoords;

		Graph(int h, int w, List<Coordinate> obstacles) {
			this.grid = new int[h][w];	
			this.obstacle = obstacles;
			this.allCoords = new ArrayList<Coordinate>();
			establishGraph();
		}
		
		Coordinate getCoordinate(Coordinate c) {
			for (Coordinate coordinate : this.allCoords) {
				if (coordinate.equals(c))
					return coordinate;
			}
			return null;
		}
	
		void establishGraph() {
			for(int i = 0; i < this.grid.length; i++) {
				for(int j = 0; j < this.grid[0].length; j++) {
					Coordinate c = new Coordinate(i, j);
					if (passable(c)) {
						Coordinate n = new Coordinate(c.getX(), c.getY(), Direction.NORTH);
						Coordinate e = new Coordinate(c.getX(), c.getY(), Direction.EAST);
						Coordinate s = new Coordinate(c.getX(), c.getY(), Direction.SOUTH);
						Coordinate w = new Coordinate(c.getX(), c.getY(), Direction.WEST);
						this.allCoords.add(n);
						this.allCoords.add(e);
						this.allCoords.add(s);
						this.allCoords.add(w);
					}
				}	
			}
		}
		
		boolean inBounds(Coordinate c) {
			return (c.getX() >= 0 && c.getX() < this.grid.length) && (c.getY() >= 0 && c.getY() < this.grid[0].length);
		}
		boolean passable(Coordinate c) {
			Coordinate co = new Coordinate(c.x, c.y, Direction.NONE, Integer.MAX_VALUE);
			return !this.obstacle.contains(co);
		}

		List<Coordinate> getRotation(Coordinate c, Map<Coordinate, Integer> path) {
			List<Coordinate> rotate = new ArrayList<Coordinate>();
			if(c.direction.equals(Direction.NORTH)) {
				Coordinate e = new Coordinate(c.getX(), c.getY() + 1, Direction.EAST);
				if(inBounds(e) && passable(e) && !path.containsKey(e)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.WEST)))
						rotate.add(getCoordinate(c.x, c.y, Direction.EAST)); 
				}
				Coordinate w = new Coordinate(c.getX(), c.getY() - 1, Direction.WEST);
				if(inBounds(w) && passable(w) && !path.containsKey(w)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.EAST)))
						rotate.add(getCoordinate(c.x, c.y, Direction.WEST)); 
				}
			} else if(c.direction.equals(Direction.SOUTH)) {
				Coordinate e = new Coordinate(c.getX(), c.getY() + 1, Direction.EAST);
				if(inBounds(e) && passable(e) && !path.containsKey(e)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.WEST)))
						rotate.add(getCoordinate(c.x, c.y, Direction.EAST)); 
				}
				Coordinate w = new Coordinate(c.getX(), c.getY() - 1, Direction.WEST);
				if(inBounds(w) && passable(w) && !path.containsKey(w)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.EAST)))
						rotate.add(getCoordinate(c.x, c.y, Direction.WEST)); 
				}
			} else if(c.direction.equals(Direction.EAST)) {
				Coordinate n = new Coordinate(c.getX() - 1, c.getY(), Direction.NORTH);
				if(inBounds(n) && passable(n) && !path.containsKey(n)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.SOUTH)))
						rotate.add(getCoordinate(c.x, c.y, Direction.NORTH));
				}
				Coordinate s = new Coordinate(c.getX() + 1, c.getY(), Direction.SOUTH);
				if(inBounds(s) && passable(s) && !path.containsKey(s)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.NORTH)))
						rotate.add(getCoordinate(c.x, c.y, Direction.SOUTH));
				}
			}  else if(c.direction.equals(Direction.WEST)) {
				Coordinate n = new Coordinate(c.getX() - 1, c.getY(), Direction.NORTH);
				if(inBounds(n) && passable(n) && !path.containsKey(n)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.SOUTH)))
						rotate.add(getCoordinate(c.x, c.y, Direction.NORTH));
				}
				Coordinate s = new Coordinate(c.getX() + 1, c.getY(), Direction.SOUTH);
				if(inBounds(s) && passable(s) && !path.containsKey(s)) {
					if(!path.containsKey(new Coordinate(c.x, c.y, Direction.NORTH)))
						rotate.add(getCoordinate(c.x, c.y, Direction.SOUTH));
				}
			}
			return rotate;

		}

		Coordinate getCoordinate(int x, int y, Direction dir) {
			Coordinate c = new Coordinate(x, y, dir);
			for (Coordinate coordinate : this.allCoords) {
				if (coordinate.equals(c))
					return coordinate;
			}
			return null;
		}

		List<Coordinate> getRotation(Coordinate c) {
			List<Coordinate> rotate = new ArrayList<Coordinate>();

			if(c.direction.equals(Direction.NORTH) || c.direction.equals(Direction.SOUTH)) {
				Coordinate e = new Coordinate(c.getX(), c.getY() + 1, Direction.EAST);
				if(inBounds(e) && passable(e))
					rotate.add(getCoordinate(c.x, c.y, Direction.EAST));
				Coordinate w = new Coordinate(c.getX(), c.getY() - 1, Direction.WEST);
				if(inBounds(w) && passable(w))
					rotate.add(getCoordinate(c.x, c.y, Direction.WEST));
			} else if(c.direction.equals(Direction.EAST) || c.direction.equals(Direction.WEST)) {
				Coordinate n = new Coordinate(c.getX() - 1, c.getY(), Direction.NORTH);
				if(inBounds(n) && passable(n))
					rotate.add(getCoordinate(c.x, c.y, Direction.NORTH));
				Coordinate s = new Coordinate(c.getX() + 1, c.getY(), Direction.SOUTH);
				if(inBounds(s) && passable(s))
					rotate.add(getCoordinate(c.x, c.y, Direction.SOUTH));
			}
			return rotate;
		}

		Coordinate getNeighbours(Coordinate c) {
			if(c.direction.equals(Direction.NORTH)) {
				Coordinate n = new Coordinate(c.getX() - 1, c.getY(), Direction.NORTH);
				if(inBounds(n) && passable(n)) 
					return getCoordinate(n);  		
			} else if(c.direction.equals(Direction.EAST)) {
				Coordinate e = new Coordinate(c.getX(), c.getY() + 1, Direction.EAST);
				if(inBounds(e) && passable(e))
					return getCoordinate(e); 		
			} else if(c.direction.equals(Direction.SOUTH)) {
				Coordinate s = new Coordinate(c.getX() + 1, c.getY(), Direction.SOUTH);
				if(inBounds(s) && passable(s)) 
					return getCoordinate(s); 		
			} else if(c.direction.equals(Direction.WEST)) {
				Coordinate w = new Coordinate(c.getX(), c.getY() - 1, Direction.WEST);
				if(inBounds(w) && passable(w)) 
					return getCoordinate(w);  			
			}
			return null;
		}
	}
}
