package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import utility.Property;

public class DaySeven {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day7.txt");
		BufferedReader br = new BufferedReader(fr);

		Map<Character, List<Character>> steps = new HashMap<Character, List<Character>>();
		String temp = br.readLine();
		while(temp != null) {
			String[] t = temp.split("must be finished before step ");
			char c1 = t[0].charAt(5);
			char c2 = t[1].charAt(0);
			if (!steps.containsKey(c1)) {
				List<Character> te = new ArrayList<Character>();
				te.add(c2);
				steps.put(c1, te);
			} else {
				List<Character> te = steps.get(c1);
				te.add(c2);
				steps.put(c1, te);				
			}
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		String output = "";
		int targetlength = steps.size();

		List<Character> chara = new ArrayList<Character>();
		for (Character c : steps.keySet()) {
			chara.addAll(steps.get(c));
		}
		for (Character c : steps.keySet()) {
			if(!chara.contains(c)) {
				output += c;
			}
		}

		Set<Character> available = new HashSet<Character>();
		while (output.length() - 1 != targetlength) {
			for (Character c : steps.keySet()) {
				if (output.contains(c + "")) {
					available.addAll(steps.get(c));
				}
			}
			List<Character> sigh = new ArrayList<Character>(available);
			Collections.sort(sigh);
			
			for (int i = 0; i < sigh.size(); i++) {
				String tem = sigh.get(i) + "";
				
				if(!output.contains(tem)) {
					output += tem;

			//		available.add(i);
					System.out.println(output);
				}
			}
		}



		long stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part One: " + output);
		System.out.println("Time in ms " + (stopTime - startTime));
		startTime = System.currentTimeMillis(); 







		stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
}