package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DaySix {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day6.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		String[] corrected = getErrorCorrectedMessage(input);
		System.out.println("Day Six, Part One: " + corrected[0]);
		
		System.out.println("Day Six, Part Two: " + corrected[1]);
	}

	static String[] getErrorCorrectedMessage(List<String> input) {
		String code1 = ""; 
		String code2 = "";
		String[] code = new String[2];
		
		for(int i = 0; i < input.get(0).length(); i++) {
			Map<Character, Integer> allCharas = new HashMap<Character, Integer>();
			for (String string : input) {
				if(allCharas.containsKey(string.charAt(i))) {
					allCharas.put(string.charAt(i), allCharas.get(string.charAt(i)) + 1);
				} else
					allCharas.put(string.charAt(i), 1);
			}
			Map.Entry<Character, Integer> max = allCharas.entrySet().stream().max(Map.Entry.comparingByValue()).get();
			code1 += max.getKey();
			Map.Entry<Character, Integer> min = allCharas.entrySet().stream().min(Map.Entry.comparingByValue()).get();
			code2 += min.getKey();
		}
		code[0] = code1; 
		code[1] = code2; 
		return code; 
	}
}
