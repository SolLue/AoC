package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.Property;

public class DayTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day2.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		long startTime = System.currentTimeMillis(); 
		
		String[] string_ranges = input.split(","); 
		List<Range> ranges = new ArrayList<Range>(); 
		
		for (String is : string_ranges) {
			String[] temp = is.split("-");
			Range r = new Range(Long.parseLong(temp[0]), Long.parseLong(temp[1]));
			ranges.add(r);
		}
		
		long invalid = 0; 
		for (Range range : ranges) {
			long start = range.firstID; 
			long stop = range.lastID; 
			for (long i = start; i <= stop; i ++) {
				String istring = i + ""; 
				
				for (int k = 1; k < istring.length(); k++) {
					Pattern pattern = Pattern.compile("^(.{" + k + "})\\1$");
					if(checkForDuplicate(istring, pattern)) {						
						invalid += i; 
					}
				} 
			}
		}		

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Two, Part One: " + invalid);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		invalid = 0; 
		
		for (Range range : ranges) {
			long start = range.firstID; 
			long stop = range.lastID; 
			for (long i = start; i <= stop; i ++) {
				String istring = i + ""; 
				Pattern pattern = Pattern.compile("^(.+)\\1+$");
				Matcher matcher = pattern.matcher(istring);
				boolean found = matcher.find();
				if(found) 
					invalid += i; 
			}
		}	
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Two, Part Two: " + invalid);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
		
	static boolean checkForDuplicate(String istring, Pattern pattern) {
		Matcher matcher = pattern.matcher(istring);
		return matcher.find();
	}

	static class Range {
		long firstID = 0; 
		long lastID = 0; 
		
		Range(long st, long sto) {
			this.firstID = st; 
			this.lastID = sto; 
		}
		
		public String toString() {
			return this.firstID + " - " + this.lastID;
		}
	}
}
