package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayEleven {

	static String alphabet = "abcdefghijklmnopqrstuvwxyz";

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day11.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		boolean nextPassword = false; 
		while(!nextPassword) {
			input = iteratePassword(input);
			nextPassword = verify(input);
		}

		System.out.println("Day Eleven, Part One: " + input);

		nextPassword = false; 
		while(!nextPassword) {
			input = iteratePassword(input);
			nextPassword = verify(input);
		}
		System.out.println("Day Eleven, Part Two: " + input);
	}

	static String iteratePassword(String in) {
		char[] c = in.toCharArray();
		for (int i = c.length - 1; i >= 0; i--) {
			if(c[i] == alphabet.charAt(alphabet.length() - 1)) {
				c[i] = alphabet.charAt(0);
			} else {
				c[i] = grabNextCharacter(c[i]); 
				break;
			}
		}
		return new String(c);
	}

	static char grabNextCharacter(char c) {
		int index = alphabet.indexOf(c);
		return alphabet.charAt(index + 1); 
	}

	static boolean verify(String p) {
		if(p.contains("i") || p.contains("o") || p.contains("l")) {
			return false; 
		}
		int countdoubles = 0; 
		for (int i = 0; i < p.length() - 1; i++) {
			if(p.charAt(i) == p.charAt(i + 1)) {
				countdoubles++; 
				i++; 
			}
		}
		int countThreeLetters = 0; 
		for (int i = 0; i < p.length() - 3; i++) {
			String sub = returnNextThreeLetters(p.charAt(i));
			if(p.substring(i, i + 3).equals(sub)) {
				countThreeLetters++; 
				i += 3; 
			}
		}

		if(countThreeLetters != 1)
			return false; 
		if (countdoubles < 2)
			return false; 
		return true; 
	}

	static String returnNextThreeLetters(char c) {
		int index = alphabet.indexOf(c);
		if(index < alphabet.length() - 2)
			return alphabet.substring(index, index + 3);
		else 
			return "";
	}
}