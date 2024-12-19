package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayNineteen {
	static Map<String, Long> memory = new HashMap<String, Long>();
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day19.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> towels = new ArrayList<String>();
		List<String> designs = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			if(!temp.isBlank()) {
				if (temp.contains(",")) {
					String[] arr = temp.split(", ");
					for (String string : arr) {
						towels.add(string);
					}
				} else {
					designs.add(temp);
				}
			}
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		int possible = 0;
		for (String design : designs) {
			checkPossible(towels, design);
		}
		for (String design : designs) {
			if (memory.containsKey(design)) {
				if (memory.get(design) > 0)
					possible++;
			}
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Nineteen, Part One: " + possible);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		long amount = 0;
		for (String design : designs) {
			if (memory.containsKey(design)) {
				if (memory.get(design) > 0)
					amount += memory.get(design);
			}
		}
		stopTime = System.currentTimeMillis();
		System.out.println("Day Nineteen, Part Two: " + amount);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	
	
	static long checkPossible(List<String> towels, String design) {
		if (memory.containsKey(design))
			return memory.get(design);
		long count = 0L;
		for (String towel : towels) {
			if (design.equals(towel))
				count++;
			else if (design.startsWith(towel)) 
				count += checkPossible(towels, design.substring(towel.length()));
		}
		memory.put(design, count);
		return count;
	}
}
