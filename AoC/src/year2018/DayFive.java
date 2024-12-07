package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		long startTime = System.currentTimeMillis(); 
		String partOne = reactions(input);
		long stopTime = System.currentTimeMillis();

		System.out.println("Day Five, Part One: " + partOne.length());
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		String test = input.toUpperCase();
		List<Character> distinct = test.chars()
									.distinct()
									.mapToObj(c -> (char) c)
									.collect(Collectors.toList());

		int min = Integer.MAX_VALUE;
		for (Character character : distinct) {
			String temp = input.replaceAll(String.valueOf(character), "");
			temp = temp.replaceAll(String.valueOf(Character.toLowerCase(character)), "");
			
			String check = reactions(temp);
			if (check.length() < min)
				min = check.length();
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Five, Part Two: " + min);
		System.out.println("Time in ms " + (stopTime - startTime));
	}


	static String reactions(String input) {
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
		return input;
	}
}
