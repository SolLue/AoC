package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayTen {

	static int steps = 0; 
	static char[][] input;

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day10.txt");
		Scanner scan = new Scanner(fr);

		List<String> t = new ArrayList<String>(); 

		while(scan.hasNextLine()) {
			t.add(scan.nextLine());
		}
		scan.close();

		input = new char[t.size()][];
		Position start = null;

		for(int i = 0; i < t.size(); i++) {
			input[i] = t.get(i).toCharArray();
			if(t.get(i).contains("S")) {
				int x = t.get(i).indexOf("S");
				start = new Position(x, i); 
			}	
		}

		List<String> pipeCoords = new ArrayList<>();

		boolean foundstart = false; 
		Position current = new Position(start.x, start.y);
		Position previous = new Position(current.x - 1, current.y - 1); 

		//changing S to valid character 
		String up = "";
		String down = ""; 
		String right = ""; 
		String left = ""; 

		if(input[start.y - 1][start.x] == '|' || input[start.y - 1][start.x] == '7' ||
				input[start.y - 1][start.x] == 'F') {
			System.out.println("upwards");
			//S can be | 7 L J 
			up = "| 7 L J";
		} 
		if(input[start.y + 1][start.x] == '|' || input[start.y + 1][start.x] == 'L' ||
				input[start.y + 1][start.x] == 'J') {
			//S can be | 7 F
			down = "| L F";
		} 
		if(input[start.y][start.x - 1] == '-' || input[start.y][start.x - 1] == 'L' ||
				input[start.y][start.x - 1] == 'F') {
			//S can be - J
			left = "- J 7";
		} 
		if(input[start.y][start.x + 1] == '-' || input[start.y][start.x + 1] == 'J' ||
				input[start.y][start.x - 1] == '7') {
			//S can be - L
			right = "- L F"; 
		} 
		//needs 2 connections
		if(!up.isBlank() && !down.isBlank()) {
			input[start.y][start.x] = '|';
		} else if(!left.isBlank() && !right.isBlank()) {
			input[start.y][start.x] = '-';
		} else if(!down.isBlank() && !right.isBlank()) {
			input[start.y][start.x] = 'F';
		} else if(!down.isBlank() && !left.isBlank()) {
			input[start.y][start.x] = '7';
		} else if(!up.isBlank() && !right.isBlank()) {
			input[start.y][start.x] = 'L';
		} else if(!up.isBlank() && !left.isBlank()) {
			input[start.y][start.x] = 'J';
		}

		pipeCoords.add(start.y + " " + start.x);

		while(!foundstart) {
			//north 
			if( (previous.y != current.y - 1) &&
					(input[current.y][current.x] == '|' || input[current.y][current.x] == 'L' ||
					input[current.y][current.x] == 'J')) {
				previous.y = current.y;
				previous.x = current.x; 
				current.move(0, -1);
				pipeCoords.add(current.y + " " + current.x);
				if(current.x == start.x && current.y == start.y) {
					foundstart = true;
				}
			}		
			//east
			else if((previous.x != current.x + 1) && 
					(input[current.y][current.x] == '-' || input[current.y][current.x] == 'F' 
					|| input[current.y][current.x] == 'L')) {
				previous.x = current.x;
				previous.y = current.y;
				current.move(1, 0);
				pipeCoords.add(current.y + " " + current.x);
				if(current.y == start.y && current.x == start.x) {
					foundstart = true; 
				}
			}
			//south
			else if((previous.y != current.y + 1) && (input[current.y][current.x] == '|' 
					|| input[current.y][current.x] == '7' || input[current.y][current.x] == 'F')) {
				previous.y = current.y; 
				previous.x = current.x; 
				current.move(0, 1);
				pipeCoords.add(current.y + " " + current.x);
				if(current.y == start.y && current.x == start.x) {
					foundstart = true; 
				}
			}
			//west
			else if((previous.x != current.x - 1) && 
					(input[current.y][current.x] == '-' || input[current.y][current.x] == '7'
					|| input[current.y][current.x] == 'J')) {
				previous.x = current.x; 
				previous.y = current.y;
				current.move(-1, 0);
				pipeCoords.add(current.y + " " + current.x);
				if(current.y == start.y && current.x == start.x) {
					foundstart = true; 
				}
			}
		}

		System.out.println("Day Ten, Part One: " + steps / 2);

		int enclosed = 0; 

		for(int i = 1; i < input.length; i++) {
			int count = 0; 
			for(int j = 1; j < input[i].length; j++) {
				if(pipeCoords.contains(i + " " + j)) {
					if(input[i][j] == 'J' || input[i][j] == '|' ||input[i][j] == 'L') {
						count++;
					}
				} else if(count % 2 == 1) {
					enclosed++;
				}
			}
		}
		System.out.println("Day Ten, Part Two: " + enclosed);

	}

	static class Position {
		int x; 
		int y; 
		Position() {}
		Position(int x, int y) {
			this.x = x; 
			this.y = y; 
		}
		int getX() {
			return this.x;
		}
		int getY() {
			return this.y; 
		}
		void move(int x, int y) {
			this.x += x; 
			this.y += y; 
			steps++;
		}

	}
}
