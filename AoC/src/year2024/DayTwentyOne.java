package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
		DirecKeypad direct1 = new DirecKeypad();
		String output = ""; 
		for (String code : input) {
			char[] codeArr = code.toCharArray();
			for (char chara : codeArr) {
				if (num.getCurrent() == chara)
					output += "A";
				else {
					
				}
			}
			
			
		}
		
		
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Twentyone, Part One: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Twentyone, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static String djikstra(NumKeypad num, char destination) {
		Queue<Coordinate> queue = new PriorityQueue<Coordinate>();
		queue.add(num.getCurrentCoord());

		List<Coordinate> cameFrom = new ArrayList<Coordinate>(); 
		Coordinate start = num.getCurrentCoord();
		cameFrom.add(start);
		Map<Coordinate, String> costSoFar = new HashMap<Coordinate, String>();
		costSoFar.put(start, "");

		while(!queue.isEmpty()) {
			Coordinate current = queue.poll();

			if(start.equals(destination))
				return costSoFar.get(destination); 

		//	for(Coordinate next : num.getNeighbours(current).keySet()) {
			//	int newcost = costSoFar.get(current) + num.getNeighbours(current).get(next);
			//	if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
			//		costSoFar.put(next, newcost);
			//		queue.add(next);
			//		cameFrom.add(next);
			//	}
		//	}
		}
		return costSoFar.get(start);	
	}
	
	static class NumKeypad {
		Coordinate current;
		char[][] possible = new char[][] { {'7', '8', '9'}, 
									       {'4', '5', '6'}, 
									       {'1', '2', '3'}, 
									       {'X', '0', 'A'} }; 		
		NumKeypad() {
			this.current = new Coordinate(3, 2); 
		}
		
		char getCurrent() {
			return this.possible[current.y][current.y];
		}
		Coordinate getCurrentCoord() {
			return this.current;
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
			return this.x + "/" + this.y ;
		}

		@Override
		public int hashCode() {
			return Integer.valueOf(this.x).hashCode() + Integer.valueOf(this.y).hashCode();
		}

		@Override
		public boolean equals(Object o) {
			Coordinate c = (Coordinate) o;
			if(this.x == c.x && this.y == c.y) {
				return true; 
			}
			return false;
		}
	}
	
	static class DirecKeypad {
		int x;
		int y;
		char[][] possible = new char[][] { {'X', '^', 'A'}, 
									       {'<', 'v', '>'} }; 		
		DirecKeypad() {
			this.x = 0;
			this.y = 2; 
		}
		
		char getCurrent() {
			return this.possible[x][y];
		}
	}
}
