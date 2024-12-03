package year2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.Property;

public class DayFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2017/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> passphrase = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			passphrase.add(temp);			
			temp = br.readLine();
		}
		br.close();

		int valid = getValid(passphrase); 

		System.out.println("Day Four, Part One: " + valid);

		List<String> newPassphrase = new ArrayList<String>();
		for (String string : passphrase) {
			String tempString = ""; 
			String[] arr = string.split("\\s+");
			List<char []> charas = new ArrayList<char []>();
			for (String string2 : arr) {
				char[] t = string2.toCharArray();
				Arrays.sort(t);
				charas.add(t);
			}
			for (char[] t : charas) {
				tempString += " " + new String(t);
			}
			newPassphrase.add(tempString.trim());
		}
		
		int validTwo = getValid(newPassphrase);
		System.out.println("Day Four, Part Two: " + validTwo);
	}

	static int getValid(List<String> passphrase) {
		int valid = 0;

		for (String string : passphrase) {
			String[] arr = string.split("\\s+");
			int count = 0; 
			for (String string2 : arr) {
				Pattern pattern = Pattern.compile("\\b" + string2 + "\\b");
				String temp = string.replaceFirst("\\b" + string2 + "\\b", "");
				Matcher matcher = pattern.matcher(temp);
				while(matcher.find()) {
					count++;
				}
			}
			if (count == 0) {
				valid++;
			}
		}
		return valid;
	}
}
