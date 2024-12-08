package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import utility.Property;

public class DaySix {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day6.txt");
		BufferedReader br = new BufferedReader(fr);

		char[] alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".toCharArray();
		Map<Coordinate, Character> coord = new HashMap<Coordinate, Character>();
		String temp = br.readLine();
		int maxX = 0; 
		int maxY = 0; 
		int l = 0; 
		while(temp != null) {
			String[] t = temp.split(", ");
			int x = Integer.parseInt(t[1]);
			int y = Integer.parseInt(t[0]);
			coord.put(new Coordinate(x, y), Character.valueOf(alphabet[l]));
			if (x > maxX)
				maxX = x; 
			if (y > maxY)
				maxY = y;
			l++;
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		char[][] grid = resetGrid(maxX, maxY, coord);			
		int max = findMaximumArea(grid, coord);



		long stopTime = System.currentTimeMillis();
		System.out.println("Day Six, Part One: " + max);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis();
		grid = resetGrid(maxX, maxY, coord);
		max = safestRegion(grid, coord);

		stopTime = System.currentTimeMillis();
		System.out.println("Day Six, Part Two: " + max);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static int safestRegion(char[][] grid, Map<Coordinate, Character> coord) {
		int max = 0; 
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				Coordinate current = new Coordinate(i, j);
				int overallDistance = 0;
				for (Coordinate c : coord.keySet()) {
					if(!c.equals(current)) {
						int distance = c.manhatten(current);
						overallDistance += distance; 
					}
				}
				if (overallDistance < 10000)
					max++;
			}	
		}
		return max; 
	}

	static int findMaximumArea(char[][] grid, Map<Coordinate, Character>coord) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				Coordinate current = new Coordinate(i, j);
				if(!coord.containsKey(current)) {
					Map<Coordinate, Integer> distanceTemp = new HashMap<Coordinate, Integer>();
					for (Coordinate c : coord.keySet()) {
						if(!c.equals(current)) {
							int distance = c.manhatten(current);
							distanceTemp.put(c, distance);
						}
					}
					int smallest = Integer.MAX_VALUE;
					for (Coordinate c : distanceTemp.keySet()) {
						if(distanceTemp.get(c) < smallest) {
							smallest = distanceTemp.get(c);
							grid[i][j] = coord.get(c); 
						} else if(distanceTemp.get(c) == smallest) {
							grid[i][j] = '.';
						} 
					}
				}
			}	
		}

		Set<Character> infinite = new HashSet<Character>();
		String t1 = new String(grid[0]);
		t1 += new String(grid[grid.length - 1]);
		for (int i = 0; i < t1.length(); i++) {
			infinite.add(t1.charAt(i));
		}

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if(j == 0 || j == grid[0].length - 1) {
					infinite.add(grid[i][j]);
				}
			}
		}

		infinite.remove('.');		
		Map<Character, Integer> count = new HashMap<Character, Integer>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != '.') {
					if (infinite.contains(grid[i][j])) {
						grid[i][j] = '-';
					} else {
						if (count.containsKey(grid[i][j])) {
							int t = count.get(grid[i][j]) + 1;	
							count.put(grid[i][j], t);
						} else {
							count.put(grid[i][j], 1);		
						}
					}
				}
			}
		}
		int max = 0; 
		for (Character character : count.keySet()) {
			if (count.get(character) > max)
				max = count.get(character);
		}
		return max; 
	}

	static char[][] resetGrid(int maxX, int maxY, Map<Coordinate, Character> coord) {
		maxX += 1;
		maxY += 2;

		char[][] grid = new char[maxX][maxY];
		for (int i = 0; i < maxX; i++) {
			for (int j = 0; j < maxY; j++) {
				grid[i][j] = '.';
			}	
		}

		for (Coordinate t : coord.keySet()) {
			grid[t.y][t.x] = coord.get(t);
		}
		return grid; 
	}

	static class Coordinate {
		int x; 
		int y; 
		Coordinate(int i, int j) {
			this.x = j; 
			this.y = i;
		}
		public String toString() {
			return "[" + this.x + " / " + this.y + "]";
		}
		int manhatten(Coordinate c) {
			return (Math.abs(this.x - c.x) + Math.abs(this.y - c.y));
		}
		@Override
		public boolean equals(Object o) {
			if(this == o)
				return true;
			if(o == null)
				return false;
			if(!(o instanceof Coordinate))
				return false;
			Coordinate tmp = (Coordinate) o;

			return this.x == tmp.x && this.y == tmp.y;
		}
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}
}
