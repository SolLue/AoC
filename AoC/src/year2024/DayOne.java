package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayOne {

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day1.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Integer> left = new ArrayList<Integer>();
		List<Integer> right = new ArrayList<Integer>();
		String temp = br.readLine();
		while(temp != null) {
			String[] in = temp.split("\\s+");
			left.add(Integer.parseInt(in[0]));
			right.add(Integer.parseInt(in[1]));
			temp = br.readLine();
		}
		br.close();

		Collections.sort(left);
		Collections.sort(right);

		int distance = 0; 
		for (int i = 0; i < left.size(); i++) {
			distance += Math.abs(left.get(i) - right.get(i)); 
		}

		System.out.println("Day One, Part One: " + distance);

		
		Map<Integer, Integer> frequency = new HashMap<Integer, Integer>();
		for (int i = 0; i < left.size(); i++) {	

			int count = 0; 
			if (right.contains(left.get(i))) {	
				for (int j = 0; j < right.size(); j++) {

					if (left.get(i).equals(right.get(j))) {
						count++;
					}
				}
			}		
			if (!frequency.containsKey(left.get(i)))
				frequency.put(left.get(i), count);

			while (right.contains(left.get(i)))
				right.remove(left.get(i));
		}

		int similarity = 0; 
		for (int i = 0; i < left.size(); i++) {
			similarity += (frequency.get(left.get(i)) * left.get(i));
		}

		System.out.println("Day One, Part Two: " + similarity);

	}
}
