package year2019;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.Property;

public class DayFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2019/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();
		String[] numbers = input.split("-");
		int minimum = Integer.parseInt(numbers[0]);
		int maximum = Integer.parseInt(numbers[1]);

		long startTime = System.currentTimeMillis(); 

		int hit = 0; 
		Pattern pattern = Pattern.compile("(.)\\1{1}");

		for (int i = minimum; i <= maximum; i++) {
			String test = i + ""; 
			Matcher matcher = pattern.matcher(test);
			boolean match = true;
			boolean doubleDigit = matcher.find();
			if (doubleDigit) {
				for (int j = 0; j < test.length() - 1; j++) {
					if(Integer.parseInt(test.charAt(j) + "") > Integer.parseInt(test.charAt(j + 1) + "")) {
						match = false;
						break;
					}
				}
				if (match)
					hit++;
			}
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Four, Part One: " + hit);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		hit = 0; 
		for (int i = minimum; i <= maximum; i++) {
			String test = i + ""; 
			boolean match = true;

			Matcher matcher = Pattern.compile("(.)\\1{1}").matcher(test);

			List<String> matches = new ArrayList<String>();
			while (matcher.find()) {
				matches.add(matcher.group());
			}

			if (matches.size() > 0) {
				boolean matchGroups = false; 
				for (int ii = 0; ii < matches.size(); ii++) {
					String matching = matches.get(ii).substring(0, 1);
					Pattern pat = Pattern.compile("(" + matching + ")\\1{2,}");

					Matcher mat = pat.matcher(test);
					if (!mat.find()) 
						matchGroups = true;
				}

				if (matchGroups) {
					for (int j = 0; j < test.length() - 1; j++) {
						if(Integer.parseInt(test.charAt(j) + "") > Integer.parseInt(test.charAt(j + 1) + "")) {
							match = false;
							break;
						}
					}
					if (match)
						hit++;
				}
			}
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Four, Part Two: " + hit);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
}
