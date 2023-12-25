package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DaySixteen {

	static char[][] copy;
	
	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day16.txt");
		Scanner scan = new Scanner(fr);
		List<String> in = new ArrayList<String>();

		while(scan.hasNextLine()) {
			in.add(scan.nextLine());
		}

		char[][] input = new char[in.size()][];
		copy = new char[in.size()][];
		for (int i = 0; i < in.size(); i++) {
			input[i] = in.get(i).toCharArray();
			copy[i] = ".".repeat(in.get(i).length()).toCharArray();
		}		
		scan.close();

		//part 1
		beam(input, "right", 0, 0);

		int energised = 0; 
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[i].length; j++) {
				if(copy[i][j] == '#')
					energised++; 
			}
		}
		System.out.println("Day Sixteen, Part One: " + energised);

		//part 2
		energised = 0; 
		//from top to bottom (down)
		for(int i = 0; i < input[0].length; i++) {
			input = resetBoard(in);
			beam(input, "down", 0, i);
			int e = 0; 
			for (int j = 0; j < copy.length; j++) {
				for (int k = 0; k < copy[j].length; k++) {
					if(copy[j][k] == '#')
						e++; 
				}
			}
			if(e > energised)
				energised = e;
		}		
		//from right to left (left)
		for(int i = 0; i < input.length; i++) {
			input = resetBoard(in);
			beam(input, "left", i, input[0].length - 1);
			int e = 0; 
			for (int j = 0; j < copy.length; j++) {
				for (int k = 0; k < copy[j].length; k++) {
					if(copy[j][k] == '#')
						e++; 
				}
			}
			if(e > energised)
				energised = e;
		}
		//from bottom to top (up)
		for(int i = 0; i < input[0].length; i++) {
			input = resetBoard(in);
			beam(input, "up", input.length - 1, i);
			int e = 0; 
			for (int j = 0; j < copy.length; j++) {
				for (int k = 0; k < copy[j].length; k++) {
					if(copy[j][k] == '#')
						e++; 
				}
			}
			if(e > energised)
				energised = e;
		}
		//from right to left (right) 
		for(int i = 0; i < input[0].length; i++) {
			input = resetBoard(in);
			beam(input, "right", i, 0);
			int e = 0; 
			for (int j = 0; j < copy.length; j++) {
				for (int k = 0; k < copy[j].length; k++) {
					if(copy[j][k] == '#')
						e++; 
				}
			}
			if(e > energised)
				energised = e;
		}

		System.out.println("Day Sixteen, Part Two: " + energised);
	}
	
	static char[][] resetBoard(List<String> input) {
		char[][] in = new char[input.size()][];
		copy = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			in[i] = input.get(i).toCharArray();
			copy[i] = ".".repeat(input.get(i).length()).toCharArray();
		}		
		return in; 
	}
	
	static void beam(char[][] in, String s, int i, int j) {
		if(s.equals("right")) {
			if(checkPath(in, i, j, 0, 1)) {
				beamRight(in, i, j);
			}
		}
		if(s.equals("left")) {
			if(checkPath(in, i, j, 0, -1)) {
				beamLeft(in, i, j);
			}
		}
		if(s.equals("up")) {
			if(checkPath(in, i, j, -1, 0)) {
				beamUp(in, i, j);
			}
		}
		if(s.equals("down")) {
			if(checkPath(in, i, j, 1, 0)) {
				beamDown(in, i, j);
			}
		}
	}

	static void beamUp(char[][] in, int row, int col) {
		boolean done = false; 
		int i = row; 
		while(i >= 0 && !done) {
			if(in[i][col] == '.') {
				in[i][col] = '^';
				copy[i][col] = '#';
			}
			if (in[i][col] == '|') { 	
				copy[i][col] = '#';
			}  
			if(in[i][col] == '/') {
				copy[i][col] = '#';
				if(checkPath(in, i, col + 1, 0, 1)) {
					beamRight(in, i, col + 1);
				}
				done = true; 
			}
			if(in[i][col] == '\\') {
				copy[i][col] = '#';
				if(checkPath(in, i, col - 1, 0, -1)) {
					beamLeft(in, i, col - 1);
				}
				done = true; 
			}
			if(in[i][col] == '-') {
				copy[i][col] = '#';
				beamSplitHor(in, i, col); 
				done = true; 
			}
			i--;
		}	
	}

	static void beamDown(char[][] in, int row, int col) {
		boolean done = false; 
		int i = row; 

		while(i < in.length && !done) {
			if(in[i][col] == '.') {
				in[i][col] = 'v'; 
				copy[i][col] = '#';
			}
			if (in[i][col] == '|') { 				
				copy[i][col] = '#';
			}  
			if(in[i][col] == '/') {
				copy[i][col] = '#';
				if(checkPath(in, i, col - 1, 0, -1)) {
					beamLeft(in, i, col - 1);
				}
				done = true;
			}
			if(in[i][col] == '\\' && col + 1 < in[row].length) {
				copy[i][col] = '#';
				if(checkPath(in, i, col + 1, 0, 1)) {
					beamRight(in, i, col + 1);
				}
				done = true; 
			}
			if(in[i][col] == '-') {
				copy[i][col] = '#';
				beamSplitHor(in, i, col);
				done = true;
			}
			i++;
		}	
	}

	static void beamRight(char[][] in, int row, int col) {
		boolean done = false; 
		int j = col;
		while(j < in[row].length && !done) {
			if(in[row][j] == '.') {
				in[row][j] = '>'; 				
				copy[row][j] = '#';
			} 
			if (in[row][j] == '-') { 				
				copy[row][j] = '#';
			}  
			if(in[row][j] == '/') {
				copy[row][j] = '#';
				if(checkPath(in, row - 1, j, -1, 0)) {
					beamUp(in, row - 1, j);
				}
				done = true; 
			}
			if(in[row][j] == '\\') {
				copy[row][j] = '#';
				if(checkPath(in, row + 1, j, 1, 0)) {
					beamDown(in, row + 1, j);
				}
				done = true; 
			}
			if(in[row][j] == '|') {
				copy[row][j] = '#';
				beamSplitVer(in, row, j);
				done = true; 
			}
			j++;
		}	
	}

	static void beamLeft(char[][] in, int row, int col) {
		boolean done = false; 
		int j = col; 
		while (j >= 0 && !done) {
			if(in[row][j] == '.') {
				in[row][j] = '<';
				copy[row][j] = '#';
			}
			if (in[row][j] == '-') { 				
				copy[row][j] = '#';
			} 
			else if(in[row][j] == '/') {
				copy[row][j] = '#';
				if(checkPath(in, row + 1, j, 1, 0)) {
					beamDown(in, row + 1, j);
				}
				done = true;
			}
			else if(in[row][j] == '\\') {
				copy[row][j] = '#';
				if(checkPath(in, row - 1, j, -1, 0)) {
					beamUp(in, row - 1, j);
				}
				done = true; 
			}
			else if(in[row][j] == '|') {
				copy[row][j] = '#';
				beamSplitVer(in, row, j);
				done = true; 
			}
			j--;
		}	
	}

	static void beamSplitHor(char[][] in, int row, int col) {
		if(col - 1 >= 0) {
			if(checkPath(in, row, col - 1, 0, -1)) {
				beamLeft(in, row, col - 1);
			}
		}
		if(col + 1 < in[row].length) {
			if(checkPath(in, row, col + 1, 0, 1)) {
				beamRight(in, row, col + 1);
			}
		}
	}

	static void beamSplitVer(char[][] in, int row, int col) {
		if(row - 1 >= 0) {
			if(checkPath(in, row - 1, col, -1, 0)) {
				beamUp(in, row - 1, col); 
			}
		}
		if(row + 1 < in.length) {
			if(checkPath(in, row + 1, col, 1, 0)) {
				beamDown(in, row + 1, col);
			}
		}
	}

	static boolean checkPath(char[][] in, int row, int col, int vert, int hor) {
		//up
		if(vert == -1) {
			if(row < 0)
				return false; 
			if(in[row][col] == '^')
				return false; 
		}
		//down
		else if(vert == 1) {
			if(row >= in.length)
				return false; 
			if(in[row][col] == 'v')
				return false;
		} 
		//right
		else if(hor == 1) {			
			if(col >= in[row].length)
				return false; 
			if(in[row][col] == '>')
				return false;
		} 
		//left
		else if(hor == -1) {
			if(col < 0)
				return false;
			if(in[row][col] == '<')
				return false;
		}
		return true;
	}
}