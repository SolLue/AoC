package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayNineteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day19.txt");
		BufferedReader br = new BufferedReader(fr);

		String t = br.readLine();
		List<String> input = new ArrayList<String>();
		while (t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		String original = ""; 
		List<String[]> in = new ArrayList<String[]>();

		for (int i = 0; i < input.size(); i++) {
			if(input.get(i).contains("=>")) {
				String[] options = input.get(i).split(" => ");
				String[] temp = {options[0], options[1]};
				in.add(temp);
			} else if(!input.get(i).isBlank()) 
				original = input.get(i);
		}

		List<String> perm = new ArrayList<String>();

		for (String[] string : in) {
			int pos = 0;
			while ((pos = original.indexOf(string[0], pos)) >= 0) {
				perm.add(replace(original, string[0], string[1], pos));
				pos += string[0].length();
			}
		}

		long count = perm.stream().distinct().count();

		System.out.println("Day Nineteen, Part One: " + count);
	     
		int count1 = 0;
	        while(!original.equals("e")) {
	            for (String[] string : in) {
	                if (original.contains(string[1])) {
	                    original = replace(original, string[1], string[0], original.lastIndexOf(string[1]));
	                    count1++;
	                }
	            }
	        }
		
		System.out.println("Day Nineteen, Part Two: " + count1);
	}

	static String replace(String s, String in, String out, int position) {
		return s.substring(0, position) + out + s.substring(position + in.length());
	}

}
