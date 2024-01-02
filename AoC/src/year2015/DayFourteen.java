package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayFourteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day14.txt");
		BufferedReader br = new BufferedReader(fr);

		String temp = br.readLine();
		List<String> input = new ArrayList<String>();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		Map<String, Integer> reindeerRaces = race(input);
		System.out.println("Day Fourteen, Part One: " + Collections.max(reindeerRaces.values()));

		Map<String, Integer> awards = awards(input);
		System.out.println("Day Fourteen, Part Two: " + Collections.max(awards.values()));

	}

	static Map<String, Integer> race(List<String> input) {
		Map<String, Integer> reindeerRaces = new HashMap<String, Integer>();

		for (String string : input) {
			String[] options = string.split(" ");
			String reindeer = options[0].trim();
			int kmpersec = Integer.parseInt(options[3].trim());
			int secondracing = Integer.parseInt(options[6].trim());
			int secondbreak = Integer.parseInt(options[13].trim());

			int distance = 0;
			boolean rest = false;
			int total = 2503;

			for (int i = 0; i < total; i++) {
				if(!rest) {
					if(i + secondracing < total) {
						distance += (kmpersec * secondracing);
						i += secondracing - 1;
						rest = true;
					} else {
						int r = total - i;
						distance += (kmpersec * r);
						i += secondracing - 1;
						rest = true;	
					}
				} else if(rest) {
					i += secondbreak - 1;
					rest = false;
				}
			}
			reindeerRaces.put(reindeer, distance);
		}
		return reindeerRaces;
	}

	static Map<String, Integer> awards(List<String> input) {
		Map<String, Integer> awards = new HashMap<String, Integer>();
		List<Reindeer> currentDistance = new ArrayList<Reindeer>();
		
		for (String string : input) {
			String[] options = string.split(" ");
			Reindeer r = new Reindeer(options[0].trim());
			r.kmpersec = Integer.parseInt(options[3].trim());
			r.secondracing = Integer.parseInt(options[6].trim());
			r.secondbreak = Integer.parseInt(options[13].trim());
			currentDistance.add(r);
			awards.put(options[0].trim(), 0);
		}
		
		int total = 2503;
		for (int i = 0; i < total; i++) {
			for (Reindeer rei : currentDistance) {
				if(!rei.rest) {
					rei.distance += rei.kmpersec;
					rei.counterRace++;
					if(rei.counterRace == rei.secondracing) {
						rei.counterBreak = 0;
						rei.rest = true;
					}
				} else if(rei.rest) {
					rei.counterBreak++;
					if(rei.counterBreak == rei.secondbreak) {
						rei.rest = false;
						rei.counterRace = 0;
					}
				}
			}
			List<String> maxValues = maxValues(currentDistance); 
			for (String string : maxValues) {
				awards.put(string, awards.get(string) + 1);
			}
		}		
		return awards;
	}
	
	static List<String> maxValues(List<Reindeer> currentDistances) {
		List<String> max = new ArrayList<>();
		int temp = 0;
	    for (Reindeer rei : currentDistances) {
	        if (rei.distance > temp) {
	            max.clear();
	            max.add(rei.name);
	            temp = rei.distance;
	        } else if (rei.distance == temp) {
	            max.add(rei.name);
	        }
	    }
		return max; 
	}
	
	static class Reindeer {
		String name; 
		boolean rest = false; 
		int kmpersec = 0;
		int secondracing = 0;
		int secondbreak = 0;
	
		int counterRace = 0; 
		int counterBreak = 0;
		
		int distance; 
		
		Reindeer(String s) {
			this.name = s; 
		}
	}
}
