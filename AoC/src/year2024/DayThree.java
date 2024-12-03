package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		String instructions = "";
		String temp = br.readLine();
		while(temp != null) {
			instructions += temp;
			temp = br.readLine();
		}
		br.close();

		int multi = extractAndMultiply(instructions);

		System.out.println("Day Three, Part One: " + multi);

		instructions = instructions.replaceAll("don't\\(\\).*?do\\(\\)|don't\\(\\).*$", "	");
		multi = extractAndMultiply(instructions);
		
		System.out.println("Day Three, Part Two: " + multi);
	}
	
	
	static int mul(int first, int second) {
		return first * second;
	}
	
	static int extractAndMultiply(String string) {
		Pattern pattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)");
		int multi = 0;
		
		Matcher matcher = pattern.matcher(string);
		List<String> matches = new ArrayList<String>();
	    while(matcher.find()) {
	    	matches.add(matcher.group(0));
	    }
	    for (String string2 : matches) {
			String t = string2;
			int first = Integer.parseInt(t.substring(t.indexOf("(") + 1, t.indexOf(",")));
			int second = Integer.parseInt(t.substring(t.indexOf(",") + 1, t.indexOf(")")));
			
			multi += mul(first, second);
	    }
			
	    return multi;
	}	
}

