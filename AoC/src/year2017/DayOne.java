package year2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayOne {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2017/input/day1.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();
		
		int sum = 0; 
		int sum2 = 0;
		for (int i = 0; i < input.length(); i++) {
			if(i == input.length() - 1) {
				if(input.charAt(i) == input.charAt(0)) {
					sum += Character.getNumericValue(input.charAt(i));
				}
			} else if(input.charAt(i) == input.charAt(i + 1)) {
				sum += Character.getNumericValue(input.charAt(i));
			}
			int halfwayRound = i + (input.length() / 2);
			if (halfwayRound >= input.length()) {
				halfwayRound -= input.length();
			}
			if(input.charAt(i) == input.charAt(halfwayRound)) {
				sum2 += Character.getNumericValue(input.charAt(i));
			}
		}
		
		System.out.println("Day One, Part One: " + sum);
		System.out.println("Day One, Part Two: " + sum2);
	}
}
