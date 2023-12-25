package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayOne {

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day1.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String input = br.readLine();
		br.close();
		
		int currentFloor = 0; 
		for (int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '(')
				currentFloor++;
			else if(input.charAt(i) == ')')
				currentFloor--;
		}
		System.out.println("Day One, Part One: " + currentFloor);
		
		int position = 0;
		currentFloor = 0; 
		for (int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '(')
				currentFloor++;
			else if(input.charAt(i) == ')')
				currentFloor--;
			if(currentFloor == -1) {
				position = i + 1; 
				break; 
			}
		}
		
		System.out.println("Day One, Part Two: " + position);

	}
}
