package year2019;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2019/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		List<List<String>> wires = new ArrayList<List<String>>();
		String temp = br.readLine();
		while(temp != null) {
			List<String> t = new ArrayList<String>();
			String[] w = temp.split(",");
			for(int i = 0; i < w.length; i++) {
				t.add(w[i]);
			}
			wires.add(t);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		Coordinate start = new Coordinate(0, 0);
		Map<Coordinate, Integer> firstWire = grabAllCoordinates(wires.get(0), start);
		Map<Coordinate, Integer> secondWire = grabAllCoordinates(wires.get(1), start);

		Set<Coordinate> cross = new HashSet<Coordinate>(); 
		for (Coordinate coordinate : firstWire.keySet()) {
			if(secondWire.containsKey(coordinate))
				cross.add(coordinate);
		}

		int minimum = Integer.MAX_VALUE;
		for (Coordinate coordinate : cross) {
			int manhatten = coordinate.manhatten(start);
			if (manhatten < minimum && manhatten > 0) {
				minimum = manhatten;
			}
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Three, Part One: " + minimum);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		int stepFirst = 0;
		int stepSecond = 0; 
		int minimumStep = Integer.MAX_VALUE;
		for (Coordinate coordinate : cross) {
			int steps = firstWire.get(coordinate) + secondWire.get(coordinate);
			if (steps < minimumStep && steps > 0) {
				minimumStep = steps;
				stepFirst = firstWire.get(coordinate);
				stepSecond = secondWire.get(coordinate);
			}
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Three, Part Two: " + (stepFirst + stepSecond));
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static Map<Coordinate, Integer> grabAllCoordinates(List<String> wire, Coordinate start) {
		Map<Coordinate, Integer> coords = new HashMap<Coordinate, Integer>();
		Coordinate current = start; 
		coords.put(current, 0);
		int currentsteps = 0; 
		for (String string : wire) {
			char direction = string.charAt(0);
			int steps = Integer.parseInt(string.substring(1));
			for (int i = 0; i < steps; i++) {
				Coordinate next = null;
				if (direction == 'U') {
					next = new Coordinate(current.x, current.y - 1);
				} else if (direction == 'R') {
					next = new Coordinate(current.x + 1, current.y);
				} else if (direction == 'L') {
					next = new Coordinate(current.x - 1, current.y);
				} else if (direction == 'D') {
					next = new Coordinate(current.x, current.y + 1);
				}
				currentsteps++;
				current = next; 
				if (!coords.containsKey(current))  
					coords.put(current, currentsteps);
			}
		}
		return coords; 
	}

	static class Coordinate {
		int x; 
		int y; 
		Coordinate(int x, int y) {
			this.x = x; 
			this.y = y; 
		}
		int manhatten(Coordinate c) {
			return Math.abs(this.x - c.x) + Math.abs(this.y - c.y);
		}
		@Override
		public String toString() {
			return x + "/ " + y;
		}
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
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
			return x == other.x && y == other.y;
		}
	}
}
