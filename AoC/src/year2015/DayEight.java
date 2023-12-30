package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayEight {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day8.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		int sum = 0;
		int difference1 = 0; 
		int difference2 = 0; 

		for (String string : input) {
			sum += string.length();
			int count = 0; 
			int count2 = 0; 
			for(int i = 0; i < string.length(); i++) {
				if(string.charAt(i) == '\\') {
					if(string.charAt(i + 1) == 'x') {
						if(string.substring(i + 1, i + 4).matches("[x][0-9a-fA-F]+")) {
							i += 3;
							count++; 
						} 
					} else {
						i++; 
						count++; 
					}
				} else {
					count++;	
				}

			}
			for(int i = 0; i < string.length(); i++) {
				if(string.charAt(i) == '\"' || string.charAt(i) == '\\') {
					count2++;
				}
			}
			difference1 += count - 2;
			difference2 += count2 + 2; 
		}
		sum = sum - difference1; 
		System.out.println("Day Eight, Part One: " + sum);

		System.out.println("Day Eight, Part Two: " + difference2);
	}
}