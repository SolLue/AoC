package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DaySeventeen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day17.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 


		
		
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Seventeen, Part One: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Seventeen, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
}
