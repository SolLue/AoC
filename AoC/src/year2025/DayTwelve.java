package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayTwelve {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day12.txt");
		BufferedReader br = new BufferedReader(fr);

		List<char[][]> presents = new ArrayList<char[][]>();
		List<String> regions = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			if (temp.contains("x"))
				regions.add(temp);
			else if (temp.contains(":")) {
				char[][] pre = new char[3][];
				for (int i = 0; i < 3; i++) {
					temp = br.readLine();
					pre[i] = temp.toCharArray();
				}
				presents.add(pre);
			}
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis();

		int amountRegions = 0; 
		for (String s : regions) {
			String[] arr = s.split(":");
			int width = Integer.parseInt(arr[0].substring(0, arr[0].indexOf("x")));
			int height = Integer.parseInt(arr[0].substring(arr[0].indexOf("x") + 1));
			
			String te = arr[1].trim();
			String[] amountarr = te.split("\\s+");
			
			
			int total = 0; 
			for (int i = 0; i < amountarr.length; i++) {
				int amount = Integer.parseInt(amountarr[i]);
				char[][] current = presents.get(i);
				int space = 0; 
				space += getAreaFromPresent(current);
				total += space * amount; 			
			}
			
			if (total <= (width * height))
				amountRegions++;
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Twelve, Part One: " + amountRegions);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis();

		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Twelve, Part Two: " + "over way too early - understandable but still sad :c ");
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static int getAreaFromPresent(char[][] present) {
		int i = 0; 
		for (char[] cs : present) {
			for (char c : cs) {
				if (c == '#')
					i++; 
			}
		}
		return i;
	}
	
}
