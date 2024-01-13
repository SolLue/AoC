package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Coordinate;
import utility.Property;

public class DayTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day2.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>(); 
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		char[][] keypad = {
				{ '1', '2', '3' },
				{ '4', '5', '6' },
				{ '7', '8', '9' }
		};

		String code = "";
		int x = 1; int y = 1; 
		for (String s : input) {
			int[] arr = getCode(s, keypad, x, y);
			x = arr[0];
			y = arr[1];
			code += keypad[x][y] + "";
		}

		System.out.println("Day Two, Part One: " + code);	

		char[][] starkeypad = {
				{ '-', '-', '1', '-', '-' },
				{ '-', '2', '3', '4', '-' },
				{ '5', '6', '7', '8', '9' },
				{ '-', 'A', 'B', 'C', '-' },
				{ '-', '-', 'D', '-', '-' }
		};

		code = "";
		x = 2; y = 0; 
		for (String s : input) {
			int[] arr = getCode(s, starkeypad, x, y);
			x = arr[0];
			y = arr[1];
			code += starkeypad[x][y] + "";
		}

		System.out.println("Day Two, Part Two: " + code);
	}

	static int[] getCode(String input, char[][]keypad, int x, int y) {
		Coordinate current = new Coordinate(x, y);

		for (int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == 'U') {
				if(current.getX() > 0 && keypad[current.getX() - 1][current.getY()] != '-') {
					current.decreaseX();
				}
			} else if(input.charAt(i) == 'D') {
				if(current.getX() < keypad[current.getX()].length - 1 && keypad[current.getX() + 1][current.getY()] != '-') {
					current.increaseX();
				}
			} else if(input.charAt(i) == 'R') {
				if(current.getY() < keypad.length - 1 && keypad[current.getX()][current.getY() + 1] != '-') {
					current.increaseY();
				}
			} else if(input.charAt(i) == 'L') {
				if(current.getY() > 0 && keypad[current.getX()][current.getY() - 1] != '-') {
					current.decreaseY();
				}
			}			
		}

		int[] arr = new int[] {current.getX(), current.getY()};
		return arr; 
	}
}
