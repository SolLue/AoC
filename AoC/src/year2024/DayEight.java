package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import utility.Property;

public class DayEight {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day8.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<char[][]> grids = resetGrids(input);
		char[][] grid = grids.get(0);
		char[][] antigrid = grids.get(1);

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] != '.') {
					findAntiNodes(grid, i, j, antigrid);
				}
			}	
		}
		
		int distinct = findDistinct(antigrid);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Eight, Part One: " + distinct);
		System.out.println("Time in ms " + (stopTime - startTime));
		startTime = System.currentTimeMillis(); 

		grid = grids.get(0);
		antigrid = grids.get(1);

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] != '.') {
					findAntiNodesNoDistance(grid, i, j, antigrid);
				}
			}	
		}

		distinct = findDistinct(antigrid);

		stopTime = System.currentTimeMillis();
		System.out.println("Day Eight, Part Two: " + distinct);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static List<char[][]> resetGrids(List<String> input) {
		char[][] grid = new char[input.size()][];
		char[][] antigrid = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			grid[i] = input.get(i).toCharArray();
			antigrid[i] = new String(".".repeat(input.size())).toCharArray();
		}
		List<char[][]> temp = new ArrayList<char[][]>();
		temp.add(grid);
		temp.add(antigrid);
		return temp;
	}

	static int findDistinct(char[][] grid) {
		int distinct = 0; 
		for (char[] cs : grid) {
			for(char c : cs) {
				if (c == '#')
					distinct++;
			}
		}
		return distinct; 
	}
	
	static void findAntiNodesNoDistance(char[][] grid, int x, int y, char[][] antigrid) {
		List<Coordinate> list = findDistinctCoords(grid, x, y);
		
		if(list.size() != 0) {
			antigrid[x][y] = '#';
		}
		
		for (int i = 0; i < list.size(); i++) {
			Coordinate currentStart = list.get(i);
			for (int j = 0; j < list.size(); j++) {
				if(i != j) {
					int xDistance = currentStart.distanceX(list.get(j));
					int yDistance = currentStart.distanceY(list.get(j));
					int nodex = currentStart.y + yDistance;
					int nodey = currentStart.x + xDistance;
					while ((nodex >= 0 && nodex < antigrid[0].length) && (nodey >= 0 && nodey < antigrid.length)) {
						antigrid[nodex][nodey] = '#';
						nodex = nodex + yDistance;
						nodey = nodey + xDistance;	
					}	
				}	
			}
		}
	}

	static List<Coordinate> findDistinctCoords(char[][] grid, int x, int y) {
		char current = grid[x][y];
		Coordinate currentc = new Coordinate(x, y);
		Set<Coordinate> coord = new HashSet<Coordinate>();
		coord.add(currentc);

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] != '.') {
					if(current == grid[i][j]) {
						Coordinate c = new Coordinate(i, j);
						coord.add(c);
					}
				}
			}	
		}

		List<Coordinate> list = new ArrayList<Coordinate>();
		list.addAll(coord);
		return list; 
	}

	static void findAntiNodes(char[][] grid, int x, int y, char[][] antigrid) {
		List<Coordinate> list = findDistinctCoords(grid, x, y);

		for (int i = 0; i < list.size(); i++) {
			Coordinate currentStart = list.get(i);
			for (int j = 0; j < list.size(); j++) {
				if(i != j) {
					int xDistance = currentStart.distanceX(list.get(j));
					int yDistance = currentStart.distanceY(list.get(j));
					int nodex = currentStart.y + yDistance;
					int nodey = currentStart.x + xDistance;
					if((nodex >= 0 && nodex < antigrid[0].length) && (nodey >= 0 && nodey < antigrid.length)) {
						antigrid[nodex][nodey] = '#';
					}					
				}	
			}
		}
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
		int distanceX(Coordinate c) {
			return this.x - c.x;
		}
		int distanceY(Coordinate c) {
			return this.y - c.y;
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
