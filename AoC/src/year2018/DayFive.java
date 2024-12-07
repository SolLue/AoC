package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		long startTime = System.currentTimeMillis(); 

		boolean reactionsFound = true; 
		while (reactionsFound) {
			boolean again = false; 
			for(int i = 0; i < input.length() - 1; i++) {
				char one = input.charAt(i);
				char two = input.charAt(i + 1);

				if (Character.isUpperCase(one) && Character.isLowerCase(two)) {
					char test = Character.toUpperCase(two);
					if (one == test) {
						again = true; 
					}
				} else if (Character.isUpperCase(two) && Character.isLowerCase(one)) {
					char test = Character.toUpperCase(one);
					if (two == test) {
						again = true;
					}
				}
				if (again) {
					input = input.substring(0, i) + input.substring(i + 2);
					break;
				}
			}
			if (!again)
				reactionsFound = false; 
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Five, Part One: " + input.length());
		System.out.println("Time in ms " + (stopTime - startTime));




		stopTime = System.currentTimeMillis();
		System.out.println("Day Five, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}


}
