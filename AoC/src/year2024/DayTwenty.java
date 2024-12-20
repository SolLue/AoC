package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import utility.Property;

public class DayTwenty {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day20.txt");
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
		for (int i = 0; i < input.size(); i++) {
			grid[i] = input.get(i).toCharArray();
		}
		List<Position> obstacles = new ArrayList<Position>();
		Position start = null;
		Position end = null; 
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] == 'S')
					start = new Position(i, j);
				if(grid[i][j] == 'E')
					end = new Position(i, j);
				if(grid[i][j] == '#') {
					Position o = new Position(i, j);
					obstacles.add(o);
				}
			}
		}

		Graph graph = new Graph(grid.length, grid[0].length, obstacles);
		Map<Position, Integer> noCheatRaceStart = djikstra(graph, start, end);
		Map<Position, Integer> noCheatRaceEnd = djikstra(graph, end, start);
		List<Entry<Position, Integer>> orderedStart = new ArrayList<Entry<Position, Integer>>(noCheatRaceStart.entrySet());
		orderedStart.sort(Entry.comparingByValue());
		List<Entry<Position, Integer>> orderedEnd = new ArrayList<Entry<Position, Integer>>(noCheatRaceEnd.entrySet());
		orderedEnd.sort(Entry.comparingByValue());

		int count = countPathWithCheat(2, start, end, noCheatRaceStart, noCheatRaceEnd, grid);

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Twenty, Part One: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		count = countPathWithCheat(20, start, end, noCheatRaceStart, noCheatRaceEnd, grid);

		stopTime = System.currentTimeMillis();
		System.out.println("Day Twenty, Part Two: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static int countPathWithCheat(int cheat, Position start, Position end, Map<Position, Integer> noCheatRaceStart,
			Map<Position, Integer> noCheatRaceEnd, char[][] grid) {
		int count = 0; 
		int current = noCheatRaceEnd.get(start);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] != '#') {
					Set<Position> all = new HashSet<>();
					Position p = new Position(i, j); 
					all.add(p);
					for(int k = 0; k < cheat; k++) {
						Set<Position> newNeigh = new HashSet<>(all);
						for(Position c: all){
							newNeigh.addAll(getNeighbours(c.x, c.y, grid));
						}
						all = newNeigh;
					}
					for (Position c : all) {
						if (grid[c.x][c.y] != '#' && 
							(noCheatRaceStart.getOrDefault(p, 0) + 
							noCheatRaceEnd.getOrDefault(c, 0) +
							Math.abs(c.x - i) + Math.abs(c.y - j)) <= current - 100) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}
	
	static List<Position> getNeighbours(int x, int y, char[][] grid) {
		Position c = new Position(x, y);
		List<Position> temp = new ArrayList<Position>(); 
		temp.add(new Position(c.x, c.y - 1));  // N
		temp.add(new Position(c.x + 1, c.y));  // E
		temp.add(new Position(c.x - 1, c.y));  // W
		temp.add(new Position(c.x, c.y + 1));  // S

		temp = temp.stream().filter(e -> inBounds(e, grid)).collect(Collectors.toList());
		return temp;		
	}

	static boolean inBounds(Position c, char[][] grid) {
		return (c.x >= 0 && c.x < grid[0].length) 
				&& (c.y >= 0 && c.y < grid.length);
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
