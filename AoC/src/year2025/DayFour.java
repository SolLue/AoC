package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		
		char[][] grid = createGrid(input);
		
		int amount_available = 0; 
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == '@') {
					if (checkLessThanFour(grid, i, j)) {
						grid[i][j] = 'X';
						amount_available++; 
					}
				}
			}
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Four, Part One: " + amount_available);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis();
		
		grid = createGrid(input);
		
		int paper_removed = 0; 
		amount_available = 0; 
		boolean stop = false;
		while (!stop) {
			stop = true;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j] == '@') {
						if (checkLessThanFour(grid, i, j)) {
							grid[i][j] = 'X';
							amount_available++; 
							stop = false;
						}
					}
				}
			}
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j] == 'X')
						grid[i][j] = '.';
				}
			}
			paper_removed += amount_available;
			amount_available = 0; 
		}

		//for (char[] cs : grid) {
		//	for (char cs2 : cs) {
		//		System.out.print(cs2);
		//	}
		//	System.out.println();
		//}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Four, Part Two: " + paper_removed);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static char[][] createGrid(List<String> input) {
		char[][] grid = new char[input.size()][];
		
		for (int i = 0; i < input.size(); i++) 
			grid[i] = input.get(i).toCharArray();
		return grid; 
	}

	static boolean checkLessThanFour(char[][] grid, int i, int j) {
		int[][] directions = {
				{ -1, -1 },
				{ -1, 0 },
				{ -1, 1 },
				{ 0, -1 },
				{ 0, 1 },
				{ 1, 1 },
				{ 1, 0 },
				{ 1, -1 } };
		
		int hits = 0; 
				
		for (int[] d : directions) {
			int id = i + d[0];
			int jd = j + d[1];
			
			if(id >= 0 && jd >= 0 && id < grid.length && jd < grid[id].length) {
				if (grid[id][jd] == '@' || grid[id][jd] == 'X')
					hits++;
				if (hits >= 4)
					return false;
			}
		}
		if (hits < 4)
			return true;
		return false; 
	}
}
