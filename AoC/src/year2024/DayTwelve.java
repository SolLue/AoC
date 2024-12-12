package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import utility.Property;

public class DayTwelve {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day12.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		char[][] garden = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			char[] te = input.get(i).toCharArray(); 
			garden[i] = te;
		}


		List<Region> regions = new ArrayList<Region>(); 
		boolean allRegionsFound = false; 
		while (!allRegionsFound) {
			int currenti = 0; 
			int currentj = 0; 
			char current = '.';

			for (int i = 0; i < garden.length; i++) {		
				for (int j = 0; j < garden[i].length; j++) {
					if (garden[i][j] != '.') {
						currenti = i; 
						currentj = j;
						current = garden[i][j];
						break;
					}
				}
				if(current != '.') {
					break;
				}
			}
			if (current != '.') {
				garden = findRegion(garden, current, currenti, currentj, regions);
			}
			if (current == '.') {
				allRegionsFound = true;
			}
		}	

		int fenceCost = 0; 
		for (Region region : regions) {
			region.calculateArea();
			region.calculatePerimeter();
			fenceCost += region.area * region.perimeter;
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Twelve, Part One: " + fenceCost);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		int fenceCostBulk = 0; 
		for (Region region : regions) {
			region.calculateSides();
			fenceCostBulk += region.area * region.sides;
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Twelve, Part Two: " + fenceCostBulk);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static char[][] findRegion(char[][] garden, char current, int x, int y, List<Region> regions) {
		char[][] copy = new char[garden.length][garden[0].length];
		for (int i = 0; i < garden.length; i++) {
			copy[i] = Arrays.copyOf(garden[i], garden[i].length);
		}

		Region region = new Region(current);
		region.addCoords(x, y);
		copy[x][y] = '.';

		int check = region.coords.size();
		boolean looping = true; 
		while (looping) {
			looping = loopThroughGarden(garden, copy, current, region);
			int check1 = region.coords.size();
			if(check1 - check > 0) {
				check = check1; 
			} else 
				break;
		}

		regions.add(region);
		return copy; 
	}

	static boolean loopThroughGarden(char[][] garden, char[][] copy, char current, Region region) {
		boolean foundMore = false;
		for(int i = 0; i < garden.length; i++) {
			for(int j = 0; j < garden[i].length; j++) {
				Coordinate check = new Coordinate(i, j);
				if(garden[i][j] == current && coordinateBordering(region, check)) {
					region.area = region.area++;
					region.addCoords(i, j);
					copy[i][j] = '.';
				} else if(garden[i][j] == current) {
					foundMore = true;
				}
			}
		}		
		return foundMore;
	}

	static boolean coordinateBordering(Region region, Coordinate check) {
		int[] idir = new int[] {0, -1, 0, 1 };
		int[] jdir = new int[] {-1, 0, 1, 0 };
		boolean neighbour = false;
		for (int x = 0; x < idir.length; x++) {
			Coordinate test = new Coordinate(check.x + idir[x], check.y + jdir[x]);
			if(region.coords.contains(test)) {
				neighbour = true;
				break;
			} 
		}
		return neighbour;
	}

	static class Region {
		char chara; 
		int perimeter; 
		int area; 
		List<Coordinate> coords; 
		int sides;

		Region(char c) {
			this.chara = c; 
			this.perimeter = 0; 
			this.area = 0; 
			this.coords = new ArrayList<Coordinate>();
			this.sides = 0; 
		}

		void calculateArea() {
			this.area = coords.size();
		}
		void calculatePerimeter() {
			this.perimeter = this.area * 4;

			for(int i = 0; i < this.coords.size(); i++) {
				for(int j = 0; j < this.coords.size(); j++) {
					if(this.coords.get(i) != this.coords.get(j)) {
						if (coords.get(i).coordinateBordering(coords.get(j))) { 
							this.perimeter--;
						}
					}
				}
			}
		}

		void calculateSides() {
			int corners = 0;
			if (this.area == 1) {
				this.sides = 4; 
			} else {
				for(int i = 0; i < this.coords.size(); i++) {
					int corner = getCorners(this.coords.get(i));
					corners += corner;
				}
				this.sides = corners;
			}
		}

		int getCorners(Coordinate check) {
			int[] idir = new int[] {0, -1, 0, 1, -1, -1, 1, 1 };
			int[] jdir = new int[] {-1, 0, 1, 0, -1,  1, 1,-1 };
			List<Coordinate> neighbours = new ArrayList<Coordinate>();
			for (int x = 0; x < idir.length; x++) {
				Coordinate test = new Coordinate(check.x + idir[x], check.y + jdir[x]);
				if (this.coords.contains(test)) 
					neighbours.add(test);
			}
			int right = 0; int left = 0; 
			int up = 0; int upright = 0; 
			int upleft = 0; int down = 0; 
			int downleft = 0; int downright = 0; 

			for (Coordinate neigh : neighbours) {
				if (check.x == neigh.x) {
					if(check.y == neigh.y - 1)
						right = 1;
					if(check.y == neigh.y + 1)
						left = 1;
				}
				if (check.y == neigh.y) {
					if(check.x == neigh.x - 1)
						down = 1;
					if(check.x == neigh.x + 1)
						up = 1;
				}
				if (check.x - 1 == neigh.x) {
					if (check.y == neigh.y + 1)
						upleft = 1;
					if(check.y == neigh.y - 1)
						upright = 1;	
				}
				if (check.x + 1 == neigh.x) {
					if(check.y == neigh.y - 1) 
						downright = 1;
					if(check.y == neigh.y + 1) 
						downleft = 1;	
				}
			}

			int corner = 0; 
			if (right + up == 0) 
				corner++;
			else if ((right + up == 2) && upright == 0) 
				corner++;
			if (right + down == 0) 
				corner++;
			else if ((right + down == 2) && downright == 0) 
				corner++;
			if (left + up == 0) 
				corner++;
			else if ((left + up == 2) && upleft == 0) 
				corner++;
			if (left + down == 0)
				corner++;
			else if ((left + down == 2) && downleft == 0) 
				corner++;

			return corner;
		}

		void addCoords(int i, int j) {
			Coordinate c = new Coordinate(i, j);
			if(!this.coords.contains(c))
				this.coords.add(c);
		}
		public String toString() {
			return this.chara + " " + this.coords;
		}
	}
	static class Coordinate {
		int x;
		int y;

		Coordinate(int i, int j) {
			this.x = i;
			this.y = j;
		}

		public String toString() {
			return "[" + this.x + " / " + this.y + "]";
		}

		boolean coordinateBordering(Coordinate check) {
			boolean neighbour = false;
			if (this.x == check.x && (this.y == check.y - 1 || this.y == check.y + 1)) {
				neighbour = true;				
			} else if (this.y == check.y && (this.x == check.x - 1 || this.x == check.x + 1)) {
				neighbour = true;
			}
			return neighbour;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			Coordinate tmp = (Coordinate) o;
			if (this.x == tmp.x && this.y == tmp.y)
				return true;
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}
}
