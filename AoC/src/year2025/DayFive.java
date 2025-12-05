package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		List<String> avail_input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			if (temp.contains("-"))
				input.add(temp);			
			else if (!temp.isBlank())
				avail_input.add(temp);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		List<Range> ranges = new ArrayList<Range>();
		for (String string : input) {
			String[] t = string.split("-");
			Range r = new Range(Long.parseLong(t[0]), Long.parseLong(t[1]));
			ranges.add(r);
		}
		List<Long> available = new ArrayList<Long>(); 
		for (String s : avail_input) {
			available.add(Long.parseLong(s));
		}

		int amount_fresh = 0;
		
		for (Long avail : available) {
			for (Range range : ranges) {
				if (avail >= range.start && avail <= range.end) {
					amount_fresh++;
					break;
				}
			}		
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Five, Part One: " + amount_fresh);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		long[][] arrays_sort = new long[ranges.size()][];
		
		for (int i = 0; i < ranges.size(); i++) {
			arrays_sort[i] = new long[2]; 
			arrays_sort[i][0] = ranges.get(i).start;
			arrays_sort[i][1] = ranges.get(i).end;
		}
		Arrays.sort(arrays_sort, (a, b) -> Long.compare(a[0], b[0]));

        List<Range> merged = new ArrayList<>();
        long[] currentInterval = arrays_sort[0];

        for (int i = 0; i < arrays_sort.length; i++) {
        	long[] interval = arrays_sort[i];
            long currentEnd = currentInterval[1];
            long nextStart = interval[0];
            long nextEnd = interval[1];

            if (currentEnd >= nextStart) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
            	merged.add(new Range(currentInterval[0], currentInterval[1]));
                currentInterval = interval;               
            }             	
        }
        merged.add(new Range(currentInterval[0], currentInterval[1]));

		long fresh = 0; 
		for (Range range : merged) {
			fresh += (range.end - range.start) + 1;
		}
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Five, Part Two: " + fresh);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static class Range{
		long start; 
		long end;
		Range(long start, long end) {
			this.start = start;
			this.end = end; 
		}
		public String toString() {
			return "[" + this.start + ", " + this.end + "]";
		}
	}
}
