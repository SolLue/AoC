package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayTwentyFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day25.txt");
		BufferedReader br = new BufferedReader(fr);

		int row = 0; 
		int col = 0; 
		String t = br.readLine();
		br.close();
		String options[] = t.split(" ");
		for (int i = 0; i < options.length; i++) {
			if(options[i].contains("row"))
				row = Integer.parseInt(options[i + 1].substring(0, options[i + 1].length() - 1));
			if(options[i].contains("column"))
				col = Integer.parseInt(options[i + 1].substring(0, options[i + 1].length() - 1));
		}
		
		long first = 20151125;
		long multiplywith = 252533;
		long moduluswith = 33554393;

		long code = findCodeAtSpot(row, col, first, multiplywith, moduluswith);

		System.out.println("Day TwentyFive, Part One: " + code);
	}
	
	static long findCodeAtSpot(int row, int col, long first, long multiplywith, long moduluswith) {
		int n = row + col - 2; 
		int total = (n * (n + 1)) / 2 + col - 1; 
		
		for(int i = 0; i < total; i++) {
			first = (first * multiplywith) % moduluswith; 
		}
		
		return first; 
	}
}
