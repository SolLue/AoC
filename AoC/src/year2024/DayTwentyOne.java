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

public class DayTwentyOne {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day21.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		NumKeypad num = new NumKeypad();
		Graph graphNum = new Graph(num);
		List<Keypad> directionkey = createKeyPads(2);
		List<Graph> graphDirection = createGraphs(2, directionkey);

		Map<Integer, String> output = new HashMap<Integer, String>(); 

		for (String code : input) {
			int number = getNumber(code); 
			String codeEn = ""; 

			codeEn = getCodePath(graphNum, num, code);
			for (int i = 0; i < graphDirection.size(); i++) {
				codeEn = getCodePath(graphDirection.get(i), directionkey.get(i), codeEn);
			}
			output.put(number, codeEn);
		}

		long result = 0; 
		for (Integer i : output.keySet()) {
			result += i * output.get(i).length();
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyOne, Part One: " + result);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		Map<Path, String> memory = createMemory();
		Map<Integer, Long> outputCount = new HashMap<Integer, Long>(); 

		num = new NumKeypad();
		graphNum = new Graph(num);
		for (String code : input) {
			int number = getNumber(code); 

			String codeEn = ""; 
			codeEn = getCodePath(graphNum, num, code);

			long length = 0; 

			Map<String, Long> cache = new HashMap<String, Long>(); 

			String[] splitToA = codeEn.split("(?<=A)");
			for (String string : splitToA) {
				cache.put(string, cache.getOrDefault(string, 0L) + 1L);				
			}	

			for (int q = 0; q < 25; q++) {
				Map<String, Long> subCache = new HashMap<String, Long>();
				for (String co : cache.keySet()) {

					char[] arr = ("A" + co).toCharArray();
					if (arr.length > 1) {
						for (int i = 0; i < arr.length - 1; i++) {
							Path p = new Path(arr[i], arr[i + 1]);
							String instructions = memory.get(p);
							long amount = cache.get(co);
							subCache.put(instructions, subCache.getOrDefault(instructions, 0L) + amount);
						}
					}
				}
				cache = new HashMap<String, Long>(subCache);
			}
			for (String s : cache.keySet()) {
				length += (s.length() * cache.get(s));
			}
			outputCount.put(number, length);
		}
		result = 0;
		for (Integer i : outputCount.keySet()) {
			result += i * outputCount.get(i); 
		}
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyOne, Part Two: " + result);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static Map<Path, String> createMemory() {
		Map<Path, String> memory = new HashMap<Path, String>();
		DirecKeypad d = new DirecKeypad();
		Graph g = new Graph(d);

		char[] c1 = new char[] {'<', '>', 'v', '^', 'A'};
		char[] c2 = new char[] {'<', '>', 'v', '^', 'A'};

		for (char cii : c1) {
			String s = ""; 
			for (char cjj : c2) {
				Coordinate path = pathfinding(g, d.getCoordinate(cii), d.getDestination(cjj));
				s = translate(path);
				Path p = new Path(cii, cjj);
				memory.put(p, s);
			}
		}
		return memory; 
	}

	static List<Keypad> createKeyPads(int part) {
		List<Keypad> pads = new ArrayList<Keypad>();
		for (int i = 0; i < part; i++) {
			Keypad di = new DirecKeypad();
			pads.add(di);
		}
		return pads;
	}

	static String getCodePath(Graph graphNum, Keypad num, String code) {
		String out = ""; 
		char[] codeArr = code.toCharArray(); 

		for (char chara : codeArr) {
			Coordinate destination = num.getDestination(chara);
			Coordinate path = pathfinding(graphNum, num.getCurrentCoord(), destination);
			String save = translate(path);

			out += save;
			num.setCurrentCoord(destination);
			num.currentDirection(Direction.NONE);
		}
		return out;
	}

	static List<Graph> createGraphs(int part, List<Keypad> pads) {
		List<Graph> graphDirection = new ArrayList<Graph>();

		for (int i = 0; i < part; i++) {
			Graph graphdirection = new Graph(pads.get(i));
			graphDirection.add(graphdirection);
		}
		return graphDirection;
	}

	static int getNumber(String code) {
		String str = code.replaceAll("[^-?0-9]+", ""); 
		return Integer.parseInt(str);
	}

	static String translate(Coordinate path) {
		String out = ""; 
		Coordinate current = path;
		while(current.prev != null) {
			Coordinate previous = current.prev;
			if (previous.x < current.x && previous.y == current.y)
				out += "v";
			else if (previous.x > current.x && previous.y == current.y)
				out += "^";
			else if (previous.x == current.x && previous.y < current.y)
				out += ">";
			else if (previous.x == current.x && previous.y > current.y)
				out += "<";
			current = current.prev;
		}

		StringBuilder sb = new StringBuilder();
		return sb.append(out).reverse().toString() + "A";
	}

	static Coordinate pathfinding(Graph graph, Coordinate start, Coordinate destination) {
		Queue<Coordinate> queue = new PriorityQueue<Coordinate>(); 
		queue.add(start);

		Map<Coordinate, Integer> pathCost = new HashMap<Coordinate, Integer>(); 
		pathCost.put(start, 0);
		start.prev = null;
		start.cost = 0; 

		Coordinate current = null;
		while(!queue.isEmpty()) {
			current = queue.poll();

			if(current.x == destination.x && current.y == destination.y) 
				return current;

			List<Coordinate> neighbours = graph.getNeighbours(current);
			for(Coordinate next : neighbours) {
				int priority = 0; 
				if(current.direction.equals(Direction.NONE)) {
					if (next.direction.equals(Direction.WEST)) {
						priority = 2; 			
					} else if (next.direction.equals(Direction.NORTH)) {
						priority = 4; 			
					} else if (next.direction.equals(Direction.SOUTH)) {
						priority = 4; 			
					} else if (next.direction.equals(Direction.EAST)) {
						priority = 5; 			
					}
				} else {
					if (current.direction.equals(next.direction)) {
						priority = 1; 
					} else
						priority = 7;
				}
				
				int newcost = pathCost.get(current) + priority;
				if(!pathCost.containsKey(next) || newcost < pathCost.get(next)) {

					pathCost.put(next, newcost);
					next.cost = newcost;
					next.prev = current;
					queue.add(next);
				}
			}
		}
		return current;
	}

	static class Path {
		char start; 
		char end; 
		Path(char s, char e) {
			this.start = s; 
			this.end = e; 
		}
		@Override
		public int hashCode() {
			return Objects.hash(end, start);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Path other = (Path) obj;
			return end == other.end && start == other.start;
		}
		public String toString() {
			return this.start + " - " + this.end;
		}
	}

	static interface Keypad {
		char getCurrent();
		Coordinate getCoordinate(char c);
		void setForNext(Coordinate destination);
		void currentDirection(Direction none);
		void setCurrentCoord(Coordinate destination);
		Coordinate getCurrentCoord();
		char getCharacter(Coordinate c);
		int getHeight();
		int getWidth();
		Coordinate getDestination(char c);
	}

	static class NumKeypad implements Keypad {
		Coordinate current;
		char[][] possible = new char[][] { 
			{'7', '8', '9'}, 
			{'4', '5', '6'}, 
			{'1', '2', '3'}, 
			{'X', '0', 'A'} 
		}; 		

		NumKeypad() {
			this.current = new Coordinate(3, 2); 
		}

		@Override
		public Coordinate getDestination(char c) {
			for (int i = 0; i < possible.length; i++) {
				for (int ii = 0; ii < possible[0].length; ii++) {
					if (possible[i][ii] == c)
						return new Coordinate(i, ii);
				}
			}
			return null;
		}

		public char getCurrent() {
			return this.possible[current.x][current.y];
		}
		public Coordinate getCurrentCoord() {
			return this.current;
		}

		@Override
		public char getCharacter(Coordinate c) {
			return this.possible[c.x][c.y];
		}

		@Override
		public int getHeight() {
			return this.possible.length;
		}

		@Override
		public int getWidth() {
			return this.possible[0].length;
		}

		@Override
		public void currentDirection(Direction d) {
			this.current.direction = d;
		}

		@Override
		public void setCurrentCoord(Coordinate destination) {
			this.current = destination;
		}

		@Override
		public void setForNext(Coordinate destination) {
			this.setCurrentCoord(destination);
			this.currentDirection(Direction.NONE);
		}

		@Override
		public Coordinate getCoordinate(char c) {
			for (int i = 0; i < possible.length; i++) {
				for (int j = 0; j < possible.length; j++) {
					if (possible[i][j] == c)
						return new Coordinate(i, j);
				}	
			}
			return null; 
		}
	}

	static class Coordinate implements Comparable<Coordinate> {
		int x;
		int y;
		Coordinate prev = null;
		int cost; 
		Direction direction = Direction.NONE; 

		Coordinate(int i, int j) {
			this.x = i;
			this.y = j;
		}
		Coordinate(int i, int j, Direction d) {
			this.x = i;
			this.y = j;
			this.direction = d; 
		}
		public String toString() {
			return this.x + "/" + this.y + " " + this.direction;
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

	static class DirecKeypad implements Keypad {
		Coordinate current;
		char[][] possible = new char[][] { 
			{'X', '^', 'A'}, 
			{'<', 'v', '>'} 
		}; 		

		DirecKeypad() {
			this.current = new Coordinate(0, 2); 
		}
		@Override
		public Coordinate getCoordinate(char c) {
			for (int i = 0; i < possible.length; i++) {
				for (int j = 0; j < possible[0].length; j++) {
					if (possible[i][j] == c)
						return new Coordinate(i, j);
				}	
			}
			return null; 
		}

		public char getCurrent() {
			return this.possible[this.current.x][this.current.y];
		}
		@Override
		public Coordinate getCurrentCoord() {
			return this.current;
		}
		@Override
		public char getCharacter(Coordinate c) {
			return this.possible[c.x][c.y];
		}
		@Override
		public int getHeight() {
			return this.possible.length;
		}

		@Override
		public int getWidth() {
			return this.possible[0].length;
		}
		@Override
		public Coordinate getDestination(char c) {
			for (int i = 0; i < possible.length; i++) {
				for (int ii = 0; ii < possible[0].length; ii++) {
					if (possible[i][ii] == c)
						return new Coordinate(i, ii);
				}
			}
			return null;
		}

		@Override
		public void currentDirection(Direction d) {
			this.current.direction = d;
		}
		@Override
		public void setCurrentCoord(Coordinate destination) {
			this.current = destination;
		}

		@Override
		public void setForNext(Coordinate destination) {
			this.setCurrentCoord(destination);
			this.currentDirection(Direction.NONE);
		}
	}

	static enum Direction { 
		EAST, WEST, SOUTH, NORTH, NONE
	}

	static class Graph {
		Keypad key;
		char[][] grid;

		Graph(Keypad key) {
			this.grid = new char[key.getWidth()][key.getHeight()];
			this.key = key;
		}
		boolean inPassable(Coordinate c) {
			return this.key.getCharacter(c) != 'X';
		}

		boolean inBounds(Coordinate c) {
			return (c.x >= 0 && c.x < this.grid[0].length) && (c.y >= 0 && c.y < this.grid.length);
		}

		List<Coordinate> getNeighbours(Coordinate c) {
			List<Coordinate> temp = new ArrayList<Coordinate>();
			temp.add(new Coordinate(c.x, c.y - 1, Direction.WEST));
			temp.add(new Coordinate(c.x, c.y + 1, Direction.EAST));
			temp.add(new Coordinate(c.x + 1, c.y, Direction.SOUTH));
			temp.add(new Coordinate(c.x - 1, c.y, Direction.NORTH));
			temp = temp.stream().filter(co -> inBounds(co)).collect(Collectors.toList());
			temp = temp.stream().filter(co -> inPassable(co)).collect(Collectors.toList());
			return temp;		
		}
	}
}
