package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DaySeven {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day7.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.nanoTime();

		char[][] grid = createGrid(input);

		int amount = 0;

		for (int i = 1; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i - 1][j] == 'S')
					grid[i][j] = '|';
				if (grid[i - 1][j] == '|') {
					if (grid[i][j] == '.')
						grid[i][j] = '|';
					else if (grid[i][j] == '^') {
						amount++;
						if (j - 1 >= 0)
							grid[i][j - 1] = '|';
						if (j + 1 <= grid[i].length)
							grid[i][j + 1] = '|';
					}
				}
			}
		}

		long stopTime = System.nanoTime();
		System.out.println("Day Seven, Part One: " + amount);
		System.out.println("Time in microsec " + (stopTime - startTime) / 1000);

		startTime = System.nanoTime();

		grid = createGrid(input);
		long[][] timelines = new long[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			timelines[i] = new long[input.get(i).length()];
		}

		for (int i = 1; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i - 1][j] == 'S') {
					grid[i][j] = '|';
					timelines[i][j] = 1;
				}
				if (grid[i - 1][j] == '|') {
					if (grid[i][j] == '.') 
						grid[i][j] = '|';
					else if (grid[i][j] == '^') {
						amount++;
						if (j - 1 >= 0) {
							grid[i][j - 1] = '|';
							timelines[i][j - 1] += timelines[i - 1][j];
						}
						if (j + 1 <= grid[i].length) {
							grid[i][j + 1] = '|';
							timelines[i][j + 1] += timelines[i - 1][j];
						}
					}
					timelines[i][j] += timelines[i - 1][j];
				}
			}
		}

		long amount_timelines = 0;
		for (int i = 0; i < grid[0].length; i++) {
			amount_timelines += timelines[grid.length - 1][i];
		}

		stopTime = System.nanoTime();
		System.out.println("Day Seven, Part Two: " + amount_timelines);
		System.out.println("Time in  microsec " + (stopTime - startTime) / 1000);
	}

	static char[][] createGrid(List<String> input) {
		char[][] grid = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			grid[i] = input.get(i).toCharArray();
		}
		return grid;
	}
}
