package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayNine {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day9.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		String decomp = decompressv1(input);
		decomp = decomp.replaceAll("\\s+", "");
		System.out.println("Day Nine, Part One: " + decomp.length());

		long count = decompressv2(input);
		System.out.println("Day Nine, Part Two: " + count);	
	}

	static String decompressv1(String input) {
		String output = ""; 

		for (int i = 0; i < input.length(); i++) {
			if(input.charAt(i) != '(') {
				output += input.charAt(i);
			} else {
				String slength = input.substring(i);
				slength = slength.substring(0, slength.indexOf(")") + 1); 
				int length = Integer.parseInt(slength.substring(slength.indexOf("(") + 1, slength.indexOf("x")));
				int amount = Integer.parseInt(slength.substring(slength.indexOf("x") + 1, slength.length() - 1));

				int jump = slength.length();
				String temp = input.substring(i);
				temp = input.substring(i + jump);
				String torepeat = temp.substring(0, length);

				output += torepeat.repeat(amount);
				i += jump + length - 1;				 	
			}
		}
		return output;
	}

	static long decompressv2(String input) {
		long[] weights = new long[input.length()];
		long count = 0; 
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = 1; 
		}

		for (int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '(') {
				String slength = input.substring(i);
				slength = slength.substring(0, slength.indexOf(")") + 1);
				int length = Integer.parseInt(slength.substring(slength.indexOf("(") + 1, slength.indexOf("x")));
				int amount = Integer.parseInt(slength.substring(slength.indexOf("x") + 1, slength.length() - 1));

				int counter = i + slength.length(); 
				for(int j = 0; j < length; j++) {
					weights[counter] = weights[counter] * amount;
					counter++;
				}
				i += slength.length() - 1;
			} else {
				count += weights[i];
			}
		}
		return count;
	}
	
}
