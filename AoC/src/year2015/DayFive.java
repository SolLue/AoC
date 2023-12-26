package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String tString = br.readLine();
		while(tString != null) {
			input.add(tString);
			tString = br.readLine();
		}
		br.close();

		int counterNice = getPart1(input);
		System.out.println("Day Five, Part One: " + counterNice);

		counterNice = getPart2(input);
		System.out.println("Day Five, Part Two: " + counterNice);
	}

	static int getPart1(List<String> input) {
		int counterNice = 0; 

		String vowels = "aeiou";
		int counterVowels = 0; 
		String[] forbidden = {"ab", "cd", "pq", "xy"};
		boolean notForbiddenB = true; 
		boolean vowelsB = true; 
		boolean doubleB = false; 

		for(int i = 0; i < input.size(); i++) {
			for (String string : forbidden) {
				if(input.get(i).contains(string))
					notForbiddenB = false; 
			}
			for(int j = 0; j < input.get(i).length(); j++) {
				if(vowels.contains(String.valueOf(input.get(i).charAt(j)))) {
					counterVowels++;
				}
				if(!doubleB) {
					if(input.get(i).contains(String.valueOf(input.get(i).charAt(j)).repeat(2)))
						doubleB = true; 
				}
			}			
			if (counterVowels < 3)
				vowelsB = false; 
			if(notForbiddenB && vowelsB && doubleB) {
				notForbiddenB = true; 
				vowelsB = true; 
				doubleB = false; 
				counterNice++;
				counterVowels = 0; 
			} else {
				notForbiddenB = true; 
				vowelsB = true; 
				doubleB = false; 
				counterVowels = 0; 
			}
		}
		return counterNice;
	}

	static int getPart2(List<String> input) {
		int counterNice = 0; 

		boolean pair = false; 
		boolean repeat = false; 

		for(int i = 0; i < input.size(); i++) {
			for(int j = 0; j < input.get(i).length() - 2; j++) {
				if(!repeat) {
					if(input.get(i).charAt(j) == input.get(i).charAt(j + 2)) 
						repeat = true; 
				}
				if(!pair) {
					String p = input.get(i).substring(j, j + 2);
					String temp = input.get(i).substring(j + 2);
					if(temp.contains(p))
						pair = true; 
				}
			}			
			if(pair && repeat) {
				pair = false; 
				repeat = false; 
				counterNice++;
			} else {
				pair = false; 
				repeat = false; 
			}
		}
		return counterNice;
	}
}
