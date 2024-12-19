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
import java.util.stream.Collectors;

import utility.Property;

public class DayEighteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day18.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();

		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<Position> position = new ArrayList<Position>();
		for (int i = 0; i < input.size(); i++) {
			String[] t = input.get(i).split(",");
			Position p = new Position(Integer.parseInt(t[1]), Integer.parseInt(t[0]));
			position.add(p);
		}

		Position start = new Position(0, 0);
		Position end = new Position(70, 70);
		List<Position> currentObstacles = new ArrayList<Position>();
		for(int i = 0; i < 1024; i++) {
			currentObstacles.add(position.get(i));
		}

		Graph graph = new Graph(71, 71, currentObstacles);					
		Map<Position, Integer> path = djikstra(graph, start, end);		

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Eighteen, Part One: " + path.get(end));
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		boolean ok = true;
		int obs = currentObstacles.size();
		while (ok) {
			for(int i = obs; i < obs + 1; i++) {
				currentObstacles.add(position.get(i));
			}
			graph = new Graph(71, 71, currentObstacles);	
			path = djikstra(graph, start, end);
			if (path.get(end) == null)
				ok = false;
			else
				obs++;
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Eighteen, Part Two: " + " index " + obs 
				+ " coord (flip back): " + currentObstacles.get(obs));
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static Map<Position, Integer> djikstra(Graph g, Position start, Position destination) {
		Queue<Position> queue = new PriorityQueue<Position>();
		queue.add(start);

		List<Position> path = new ArrayList<Position>(); 
		path.add(start);
		Map<Position, Integer> costSoFar = new HashMap<Position, Integer>();
		costSoFar.put(start, 0);

		while(!queue.isEmpty()) {
			Position current = queue.poll();

			if(start.equals(destination)) {
				return costSoFar;
			}

			for(Position next : g.getNeighbours(current)) {
				int newcost = costSoFar.get(current) + 1;
				if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
					costSoFar.put(next, newcost);
					queue.add(next);
					path.add(next);
				}
			}
		}
		return costSoFar;	
	}

	static class Position implements Comparable<Position> {
		int x;
		int y;

		Position(int i, int j) {
			this.x = i;
			this.y = j;
		}

		public String toString() {
			return "[" + this.x + " / " + this.y + "]";
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			Position tmp = (Position) o;
			if (this.x == tmp.x && this.y == tmp.y)
				return true;
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public int compareTo(Position o) {
			return Integer.compare(o.x - this.x, o.y - this.y);
		}
	}

	static class Graph {

		int[][] grid; 
		List<Position> obstacle; 

		Graph(int h, int w, List<Position> o) {
			this.grid = new int [h][];
			for(int i = 0; i < h; i++) {
				this.grid[i] = new int[w];
			}
			this.obstacle = o;
		}

		Graph(int h, int w) {
			this.grid = new int [h][];
			for(int i = 0; i < h; i++) {
				this.grid[i] = new int[w];
			}
			this.obstacle = new ArrayList<Position>();
		}

		List<Position> getObstacles() {
			return this.obstacle;
		}

		boolean inBounds(int x, int y) {
			return (0 <= x && x < this.grid[0].length) && (0 <= y && y < this.grid.length);
		}

		boolean inBounds(Position c) {
			return (c.x >= 0 && c.x < this.grid[0].length) 
					&& (c.y >= 0 && c.y < this.grid.length);
		}

		boolean passable(int x, int y) {
			Position c = new Position(x, y);
			return this.obstacle.contains(c);
		}

		boolean passable(Position c) {
			return !this.obstacle.contains(c);
		}

		List<Position> getNeighbours(Position c) {
			List<Position> temp = new ArrayList<Position>(); 
			temp.add(new Position(c.x, c.y - 1));  // N
			temp.add(new Position(c.x + 1, c.y));  // E
			temp.add(new Position(c.x - 1, c.y));  // W
			temp.add(new Position(c.x, c.y + 1));  // S

			temp = temp.stream().filter(e -> inBounds(e)).collect(Collectors.toList());
			temp = temp.stream().filter(e -> passable(e)).collect(Collectors.toList());

			return temp;
		}
	}
}
