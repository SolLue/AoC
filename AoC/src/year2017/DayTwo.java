package year2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utility.Property;

public class DayTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2017/input/day2.txt");
		BufferedReader br = new BufferedReader(fr);

		List<List<Integer>> spreadsheet = new ArrayList<List<Integer>>();
		String temp = br.readLine();
		while(temp != null) {
			List<Integer> temparray = new ArrayList<Integer>();
			String[] split = temp.split("\\s+");
			for (String string : split) {
				temparray.add(Integer.parseInt(string));
			}
			spreadsheet.add(temparray);
			temp = br.readLine();
		}
		br.close();

		int checksum = 0; 
		for (List<Integer> list : spreadsheet) {
			Collections.sort(list);
			checksum += list.get(list.size() - 1) - list.get(0);
		}

		System.out.println(spreadsheet);
		System.out.println("Day Two, Part One: " + checksum);

		int evenlyDivided = 0; 
		for (List<Integer> list : spreadsheet) {
			Collections.sort(list);
			for(int i = 0; i < list.size(); i++) {
				for(int j = 0; j < list.size(); j++) {
					if(j != i) {
						if (list.get(i) % list.get(j) == 0) {
							evenlyDivided += (list.get(i) / list.get(j));
							// Trying to abort "early"
							break;
						}
					}
				}
			}	
		}
		System.out.println("Day Two, Part Two: " + evenlyDivided);
	}	
}
