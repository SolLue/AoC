package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utility.Property;

public class DayTwenty {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day20.txt");
		BufferedReader br = new BufferedReader(fr);

		List<IPrange> ranges = new ArrayList<IPrange>();
		String temp = br.readLine();
		while(temp != null) {
			String[] arr = temp.split("-");
			IPrange ip = new IPrange(Long.parseLong(arr[0]), Long.parseLong(arr[1]));
			ranges.add(ip);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		long absMin = 0L; 
		long absMax = 4294967295L;

		Collections.sort(ranges);

		long ip = 0;
		long tempLow = absMin; 
		boolean found = false;
		while (!found) {
			tempLow = ip;
			for (IPrange iprange : ranges) {
				if (ip >= iprange.min && ip <= iprange.max) {
					ip = iprange.max + 1;
				}
			}
			if (tempLow == ip) 
				found = true;
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Twenty, Part One: " + ip);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		long count = 0L;
		long previous = 0L;
		found = false;
		while (!found) {
			tempLow = ip;
			for (IPrange iprange : ranges) {
				if (ip >= iprange.min && ip <= iprange.max) {
					if (iprange.max > previous) {
						ip = iprange.max + 1;
						previous = iprange.max;
					}
				}
			}
			if (tempLow == ip) {
				count++; 
				ip++;
			}
			if (ip == absMax + 1)
				found = true; 
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Twenty, Part Two: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static class IPrange implements Comparable<IPrange> {
		long min; 
		long max; 
		IPrange(long n, long x) {
			this.min = n; 
			this.max = x; 
		}
		@Override
		public String toString() {
			return "IPrange [min=" + min + ", max=" + max + "]";
		}
		@Override
		public int compareTo(IPrange o) {
			int difference = (int) (this.max - o.max);
			return difference;
		}
	}
}
