package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DaySeven {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day7.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		int[] amount = tlsSslChecking(input);
		System.out.println("Day Seven, Part One: " + amount[0]);
		System.out.println("Day Seven, Part Two: " + amount[1]);
	}

	static int[] tlsSslChecking(List<String> input) {
		int countabba = 0; 
		int countaba = 0; 

		for (String string : input) {
			List<String> squares = new ArrayList<String>();
			List<String> normal = new ArrayList<String>();

			String temp = string;
			do {
				String first = temp.substring(0, temp.indexOf("["));
				normal.add(first);
				String square = temp.substring(temp.indexOf("[") + 1, temp.indexOf("]"));
				squares.add(square);
				temp = temp.substring(temp.indexOf("]") + 1);				
			} while(temp.contains("[") || temp.contains("]"));

			if(!temp.isBlank())
				normal.add(temp);

			boolean sqabba = false; 
			for (String s : squares) {
				if(checkAbba(s)) {
					sqabba = true;
					break;
				}
			}
			if(!sqabba) {
				for (String s : normal) {
					if(checkAbba(s)) {
						countabba++; 
						break;
					} 
				}
			}

			if(checkAba(normal, squares)) {
				countaba++;
			}
		}
		int[] count = new int[2];
		count[0] = countabba; 
		count[1] = countaba;
		return count; 
	}

	static boolean checkAbba(String in) {
		for(int i = 0; i < in.length() - 3; i++) {
			if(in.charAt(i) != in.charAt(i + 1)) {
				String rev = String.valueOf(in.charAt(i + 1)) + String.valueOf(in.charAt(i));
				if(rev.equals(in.substring(i + 2, i + 4))) {
					return true; 
				}
			}
		}
		return false;
	}

	static boolean checkAba(List<String> normal, List<String> square) {
		for(String in : normal) {
			for(int i = 0; i < in.length() - 2; i++) {
				if(in.charAt(i) != in.charAt(i + 1)) {
					if(in.charAt(i) == in.charAt(i + 2)) {
						String bab = String.valueOf(in.charAt(i + 1)) + String.valueOf(in.charAt(i) + String.valueOf(in.charAt(i + 1)));

						for(String sq : square) {
							for(int j = 0; j < sq.length() - 2; j++) {
								if(bab.equals(sq.substring(j, j + 3))) {
									return true; 
								}
							}					
						}
					}
				}
			}
		}
		return false;
	}
}
