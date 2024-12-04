package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayOne {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day1.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Integer> instructions = new ArrayList<Integer>();
		String temp = br.readLine();
		while(temp != null) {
			instructions.add(Integer.parseInt(temp));			
			temp = br.readLine();
		}
		br.close();

		int frequency = 0; 
		for (Integer integer : instructions) {
			frequency += integer; 
		}
		System.out.println("Day One, Part One: " + frequency);

		Map<Integer, Integer> repeating = new HashMap<Integer, Integer>(); 
		boolean found = false; 
		frequency = 0; 
		repeating.put(frequency, 0);
		while(!found) {

			for (Integer integer : instructions) {
				frequency += integer;
				if(repeating.containsKey(frequency))
					found = true;
				else 
					repeating.put(frequency, 0);	
				
				if (found)
					break; 
			}
		}
		System.out.println("Day One, Part Two: " + frequency);
	}
}

