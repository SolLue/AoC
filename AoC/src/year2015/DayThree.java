package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import utility.Coordinate;
import utility.Property;

public class DayThree {
	
	static Map<Coordinate, Integer> visited; 
	
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();
		
		Coordinate start = new Coordinate(0, 0);
		
		visited = new HashMap<Coordinate, Integer>();
		visited.put(start, 1);
			
		fillMapPart1(input);
		System.out.println("Day Three, Part One: " + visited.size());

		visited.clear();

		visited.put(start, 2);		
		fillMapPart2(visited, input);
			
		System.out.println("Day Three, Part Two: " + visited.size());
	
	}
	
	static void fillMapPart1(String input) {
		int currentx = 0; 
		int currenty = 0; 
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '^') {
				currenty--;
				Coordinate current = new Coordinate(currentx, currenty);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			} else if(input.charAt(i) == '>') {
				currentx++;
				Coordinate current = new Coordinate(currentx, currenty);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			} else if(input.charAt(i) == 'v') {
				currenty++;
				Coordinate current = new Coordinate(currentx, currenty);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			} else if(input.charAt(i) == '<') {
				currentx--;
				Coordinate current = new Coordinate(currentx, currenty);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			}	
		}
	}

	
	static void fillMapPart2(Map<Coordinate, Integer> visited, String input) {
		int currentxS = 0; 
		int currentyS = 0; 
		int currentxR = 0; 
		int currentyR = 0; 

		for(int i = 0; i < input.length(); i++) {
			if(i % 2 == 0) {
				int[] temp = move(input.charAt(i), currentxS, currentyS);
				currentxS = temp[0];
				currentyS = temp[1];
			} else {
				int[] temp = move(input.charAt(i), currentxR, currentyR);
				currentxR = temp[0];
				currentyR = temp[1];	
			}
		}
	}

	static int[] move(char c, int x, int y) {
			if(c == '^') {
				y--;
				Coordinate current = new Coordinate(x, y);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			} else if(c == '>') {
				x++;
				Coordinate current = new Coordinate(x, y);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			} else if(c == 'v') {
				y++;
				Coordinate current = new Coordinate(x, y);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			} else if(c == '<') {
				x--;
				Coordinate current = new Coordinate(x, y);
				if(visited.containsKey(current)) {
					int v = visited.get(current) + 1;
					visited.put(current, v);
				} else
					visited.put(current, 1);
			}	
			
		return new int[] {x, y}; 
	} 
	
}
