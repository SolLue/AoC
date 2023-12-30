package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import utility.Property;

public class DayTen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day10.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		for(int i = 0; i < 40; i++) {
			String[] splitting = input.split("(?<=(.))(?!\\1)");

			input = lookandsay(splitting); 
		}
		
		System.out.println("Day Ten, Part One: " + input.length());
		
		for(int i = 0; i < 10; i++) {
			String[] splitting = input.split("(?<=(.))(?!\\1)");

			input = lookandsay(splitting); 
		}
		System.out.println("Day Ten, Part Two: " + input.length());
	}

	static String lookandsay(String[] in) {
		String res = ""; 
		for (String string : in) {
			int counter = string.length();
			res += String.valueOf(counter) + String.valueOf(string.charAt(0));
		}	
		return res;
	}
}