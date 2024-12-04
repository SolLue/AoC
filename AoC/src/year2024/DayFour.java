package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		Letter[][] xmas = new Letter[input.size()][];
		for(int i = 0; i < input.size(); i++) {
			Letter[] inner = new Letter[input.get(i).length()];
			for(int j = 0; j < input.get(i).length(); j++) {
				Letter l = new Letter(input.get(i).charAt(j));
				inner[j] = l;
			}	
			xmas[i] = inner;
		}

		int[] directionx = { -1, -1, -1, 0, 1, 1,  1,  0 };
		int[] directiony = { -1,  0,  1, 1, 1, 0, -1, -1 };
		int countXmas = 0;
		boolean found = false;
		int lengthWord = "XMAS".length();
		for(int i = 0; i < xmas.length; i++) {
			for(int j = 0; j < xmas[i].length; j++) {
				for(int o = 0; o < directionx.length; o++) {
					found = findXmas(lengthWord, 0, xmas, i, j, directionx[o], directiony[o]);
					if(found) {
						countXmas++;
					}
				}				
			}
		}
		System.out.println("Day Four, Part One: " + countXmas);

		int countMasInx = 0;
		for(int i = 1; i < xmas.length - 1; i++) {
			for(int j = 1; j < xmas[i].length - 1; j++) {
				if(xmas[i][j].chara == 'A') {
					if(xmas[i - 1][j - 1].chara == 'M' && xmas[i + 1][j + 1].chara == 'S') {
						if(xmas[i - 1][j + 1].chara == 'M' && xmas[i + 1][j - 1].chara == 'S') 
							countMasInx++;
						else if (xmas[i - 1][j + 1].chara == 'S' && xmas[i + 1][j - 1].chara == 'M')
							countMasInx++;				
					} else if (xmas[i - 1][j - 1].chara == 'S' && xmas[i + 1][j + 1].chara == 'M') {
						if(xmas[i - 1][j + 1].chara == 'M' && xmas[i + 1][j - 1].chara == 'S') 
							countMasInx++;
						else if (xmas[i - 1][j + 1].chara == 'S' && xmas[i + 1][j - 1].chara == 'M')
							countMasInx++;	
					}
				}				
			}
		}
		System.out.println("Day Four, Part Two: " + countMasInx);
	}

	static boolean findXmas(int length, int temp, Letter[][] xmas, int i, int j, int xdir, int ydir) {
		if (temp == length)
			return true;
		if(i >= 0 && i < xmas.length && j >= 0 && j < xmas[0].length) {
			if(xmas[i][j].charAt(temp))
				return findXmas(length, temp + 1, xmas, i + xdir, j + ydir, xdir, ydir);
		}
		return false;
	}

	// Completely unnecessary - Wanted to do it differently in the beginning. Will still keep it. :)
	static class Letter {
		char chara;

		Letter(char c) {
			this.chara = c;
		}
		boolean charAt(int i) {
			if ("XMAS".charAt(i) == this.chara)
				return true;
			return false; 
		}
	}
}
