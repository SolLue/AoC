package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DaySixteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day16.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine().trim();
		br.close();

		long startTime = System.currentTimeMillis(); 
			
		int diskSize = 272;
		String current = input;
		while (current.length() < diskSize) {
			current = repeating(current);
		}
		String checkSum = checkSum(current, diskSize);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Sixteen, Part One: " + checkSum);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		diskSize = 35651584;
		current = input;
		while (current.length() < diskSize) {
			current = repeating(current);
		}
		checkSum = checkSum(current, diskSize);
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Sixteen, Part Two: " + checkSum);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static String repeating(String a) {
		StringBuilder sb = new StringBuilder();
		for (int i = a.length() - 1; i >= 0; i--) {
			sb.append(a.charAt(i) == '1' ? "0" : "1");
		}
		return a + "0" + sb.toString();		
	}

	
	static String checkSum(String in, int space) {
		StringBuilder sb = new StringBuilder();
		String current = in.substring(0, space); 
		do {
			sb.delete(0, sb.length());
			for (int i = 0; i < current.length(); i += 2) {
				sb.append(current.charAt(i) == current.charAt(i + 1) ? "1" : "0");
			}
			current = sb.toString();
		} while (current.length() % 2 == 0);
		return current; 
	}
} 
