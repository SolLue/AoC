package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import utility.Property;

public class DayOne {

	static int sum;

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2023/input/day1.txt");
		Scanner scan = new Scanner(fr);

		sum = 0;
		boolean firstfound = false;

		while (scan.hasNextLine()) {
			String temp = scan.nextLine();
			char firstDigit = 0;
			char lastDigit = 0;

			for (int i = 0; i < temp.length(); i++) {
				if (Character.isDigit(temp.charAt(i))) {
					if (!firstfound) {
						firstDigit = temp.charAt(i);
						lastDigit = temp.charAt(i);
						firstfound = true;
					} else {
						lastDigit = temp.charAt(i);
					}
				}
			}
			firstfound = false;

			sumUp(firstDigit + "" + lastDigit);

		}
		System.out.println("Day One, Part One: " + sum);
		scan.close();

		fr = new FileReader(folder + "/year2023/input/day1.txt");
		Scanner scan1 = new Scanner(fr);

		Map<String, String> numbers = new HashMap<String, String>();
		numbers.put("one", "1");
		numbers.put("two", "2");
		numbers.put("three", "3");
		numbers.put("four", "4");
		numbers.put("five", "5");
		numbers.put("six", "6");
		numbers.put("seven", "7");
		numbers.put("eight", "8");
		numbers.put("nine", "9");

		sum = 0;

		firstfound = false;
		while (scan1.hasNextLine()) {
			String temp = scan1.nextLine();

			temp = replaceWord(temp);

			char firstDigit = 0;
			char lastDigit = 0;

			for (int i = 0; i < temp.length(); i++) {
				if (Character.isDigit(temp.charAt(i))) {
					if (!firstfound) {
						firstDigit = temp.charAt(i);
						lastDigit = temp.charAt(i);
						firstfound = true;
					} else {
						lastDigit = temp.charAt(i);
					}
				}
			}
			firstfound = false;

			sumUp(firstDigit + "" + lastDigit);

		}

		System.out.println("Day One, Part Two: " + sum);
		scan1.close();

	}

	public static void sumUp(String text) {
		int parsing = Integer.parseInt(text);
		sum += parsing;
	}

	public static String replaceWord(String text) {
		text = text.replaceAll("one", "o1e");
		text = text.replaceAll("two", "t2o");
		text = text.replaceAll("three", "t3e");
		text = text.replaceAll("four", "f4r");
		text = text.replaceAll("five", "f5e");
		text = text.replaceAll("six", "s6x");
		text = text.replaceAll("seven", "s7n");
		text = text.replaceAll("eight", "e8t");
		text = text.replaceAll("nine", "n9e");
		
		return text;
	}

}
