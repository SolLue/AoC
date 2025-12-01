package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayOne {

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day1.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		
		int password = 0; 
		int position = 50;

		for (String string : input) {
			int amount = Integer.parseInt(string.substring(1));
			if(string.startsWith("L"))
				amount *= -1; 
				
			position = (position + amount) % 100;
		
			if(position == 0)	
			    password++; 	
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day One, Part One: " + password);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		password = 0; 
		position = 50;

		for (String string : input) {
			int amount = Integer.parseInt(string.substring(1));
			boolean goLeft = string.startsWith("L");
			
			for (int i = 0; i < amount; i++) {
				if(goLeft)
					position -= 1;
				else
					position += 1;

				position %= 100;
				if(position < 0)
					position += 100; 
				if(position == 0)
					password++;
			}	
		}
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day One, Part Two: " + password);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
}
