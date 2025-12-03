package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		int total_joltage = 0; 
		
		for (String string : input) {
			char[] chartemp = string.toCharArray();
			String together = "";
			int lastIndex = 0;
			while (together.length() != 2) {
				int maxCheck = 1 - together.length();
				lastIndex = getNextBiggest(chartemp, lastIndex, maxCheck);
				together += chartemp[lastIndex];
				lastIndex++;
			}
			total_joltage += Integer.parseInt(together);
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Three, Part One: " + total_joltage);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		long total_joltageL = 0; 
		
		for (String string : input) {
			char[] chartemp = string.toCharArray();
			String together = "";
			int lastIndex = 0;
			while (together.length() != 12) {
				int maxCheck = 12 - together.length() - 1;
				lastIndex = getNextBiggest(chartemp, lastIndex, maxCheck);
				together += chartemp[lastIndex];
				lastIndex++;
			}
			total_joltageL += Long.parseLong(together);
		}
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Three, Part Two: " + total_joltageL);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static int getNextBiggest(char[] chartemp, int lastIndex, int maxCheck) {
		int biggest = 0;
		int biggest_index = 0;
		for (int i = lastIndex; i < chartemp.length - maxCheck; i++) {
			if(Integer.parseInt(String.valueOf(chartemp[i])) > biggest) {
				biggest = Integer.parseInt(String.valueOf(chartemp[i]));
				biggest_index = i;
			}
		}
		return biggest_index; 
	}
}
