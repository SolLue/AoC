package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utility.Property;

public class DaySix {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day6.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Coordinate> coord = new ArrayList<Coordinate>();
		String temp = br.readLine();
		int maxX = 0; 
		int maxY = 0; 
		while(temp != null) {
			String[] t = temp.split(", ");
			int x = Integer.parseInt(t[0]);
			int y = Integer.parseInt(t[1]);
			coord.add(new Coordinate(x, y));
			if (x > maxX)
				maxX = x; 
			if (y > maxY)
				maxY = y;
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		
		int[][] grid = new int[maxX + 1][maxY];
		
		
		
		List<Integer> maxDistance = new ArrayList<Integer>();
		for (Coordinate co1 : coord) {
			for (Coordinate co2 : coord) {
				if(!co1.equals(co2)) {


				
				}
			}	
		}
		
		System.out.println(maxDistance);
		
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Six, Part One: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Six, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
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
