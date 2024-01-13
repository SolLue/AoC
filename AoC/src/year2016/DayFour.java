package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import utility.Property;

public class DayFour {	
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String[]> input = new ArrayList<>(); 
		String t = br.readLine();
		while(t != null) {
			String[] op = t.split("\\[");
			op[1] = op[1].substring(0, op[1].length() - 1);
			input.add(op);
			t = br.readLine();
		}
		br.close();

		List<String[]> valid = getValid(input); 
		int sum = sumOfSectorId(valid);

		System.out.println("Day Four, Part One: " + sum);	
		
		int id = correctRoom(valid);
		System.out.println("Day Four, Part Two: " + id);
	}

	static int correctRoom(List<String[]> input) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		
		for (String[] s : input) {
			int id = Integer.parseInt(s[0].substring(s[0].lastIndexOf("-") + 1));
			String temp = s[0].substring(0, s[0].lastIndexOf("-"));

			
			String correctName = ""; 
			for (int i = 0; i < temp.length(); i++) {
				if(temp.charAt(i) == '-') {
					correctName += " ";
				} else {
					int index = alphabet.indexOf(temp.charAt(i));
					int shift = (id + index) % 26;
					char c = alphabet.charAt(shift);
					correctName += String.valueOf(c);
				}
			}
			if(correctName.contains("northpole")) {
				return id;
			}
		}
		
		return -1; 
	}
	
	static List<String[]> getValid(List<String[]> input) {
		List<String[]> validRooms = new ArrayList<String[]>();
		
		for (String[] s : input) {
			String temp = s[0].substring(0, s[0].lastIndexOf("-")).replace("-", "");

			Map<Character,Integer> common = new TreeMap<>();
			for(char c : temp.toCharArray()) {
				common.put(c, common.getOrDefault(c, 0) + 1);
			}
			List<CharFreq> forSorting = new ArrayList<>();			
			for (char c : common.keySet()) {
				CharFreq ch = new CharFreq(c, common.get(c));
				forSorting.add(ch);
			}
			
			Collections.sort(forSorting, (e1, e2) -> {
				return (e2.frequency - e1.frequency);
			});

			String compare = "";
			for(int i = 0; i < 5; i++) {
				compare += String.valueOf(forSorting.get(i).c);
			}

			if(compare.equals(s[1])) {
				validRooms.add(s);
			}
		}
		return validRooms;
	}
	
	static int sumOfSectorId(List<String[]> input) {
		int sum = 0; 
		for (String[] s : input) {
			int id = Integer.parseInt(s[0].substring(s[0].lastIndexOf("-") + 1));
			sum += id;
		}
		return sum; 
	}
	
	record CharFreq(char c, int frequency) {}
}
