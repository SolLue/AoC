package year2019;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayOne {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2019/input/day1.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Integer> input = new ArrayList<Integer>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(Integer.parseInt(temp));			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		int amount = 0; 
		for (Integer mass : input) {
			amount += calculateFuel(mass);
		}		
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day One, Part One: " + amount);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		int amount2 = 0; 
		for (Integer mass : input) {
			amount2 += calculateFuelRec(mass, 0);
		}
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day One, Part Two: " + amount2);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static int calculateFuel(int mass) {
		return Math.floorDiv(mass, 3) - 2;
	}	
	
	static int calculateFuelRec(int mass, int fuel) {
		mass = Math.floorDiv(mass, 3) - 2;	
		if (mass > 0) {
			fuel += mass;
			return calculateFuelRec(mass, fuel);
		}
		return fuel;
	}
}
