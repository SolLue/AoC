package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayEighteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day18.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<String> field = new ArrayList<String>();
		field.add(input);
		int rows = 40;

		while (field.size() < rows) {
			char left = '.';
			char right = '.';
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < input.length(); i++) {
				boolean safe = false;
				if (i == 0)
					safe = isSafe(left, field.get(field.size() - 1).charAt(i), field.get(field.size() - 1).charAt(i + 1));
				else if (i == input.length() - 1)
					safe = isSafe(field.get(field.size() - 1).charAt(i - 1), field.get(field.size() - 1).charAt(i), right);
				else
					safe = isSafe(field.get(field.size() - 1).charAt(i - 1), field.get(field.size() - 1).charAt(i), field.get(field.size() - 1).charAt(i + 1));			
				
				if (safe)
					sb.append('.');
				else
					sb.append('^');
			}
			field.add(sb.toString());
			sb.delete(0, sb.length() - 1);
		
		}
        int count = (int) field.toString().chars().filter(c -> c == '.').count();

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Eighteen, Part One: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		field = new ArrayList<String>();
		field.add(input);
		rows = 400000;

		while (field.size() < rows) {
			char left = '.';
			char right = '.';
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < input.length(); i++) {
				boolean safe = false;
				if (i == 0)
					safe = isSafe(left, field.get(field.size() - 1).charAt(i), field.get(field.size() - 1).charAt(i + 1));
				else if (i == input.length() - 1)
					safe = isSafe(field.get(field.size() - 1).charAt(i - 1), field.get(field.size() - 1).charAt(i), right);
				else
					safe = isSafe(field.get(field.size() - 1).charAt(i - 1), field.get(field.size() - 1).charAt(i), field.get(field.size() - 1).charAt(i + 1));			
				if (safe)
					sb.append('.');
				else
					sb.append('^');
			}
			field.add(sb.toString());
			sb.delete(0, sb.length() - 1);
		
		}
        count = (int) field.toString().chars().filter(c -> c == '.').count();
        
		stopTime = System.currentTimeMillis();
		System.out.println("Day Eighteen, Part Two: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static boolean isSafe(char left, char center, char right) {
		if (left == '^' && center == '^' && right == '.') 
			return false; 
		if (center == '^' && right == '^' && left == '.') 
			return false;
		if (center == '.' && right == '.' && left == '^')
			return false;
		if (center == '.' && left == '.' && right == '^')
			return false;			
		return true; 
	}
}
