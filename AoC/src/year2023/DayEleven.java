package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 	julbord yesterday did not help.
 */

public class DayEleven {

	static int sum = 0;
	static long sum1 = 0;

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day11.txt");
		Scanner scan = new Scanner(fr);

		List<String> in = new ArrayList<String>();
		while(scan.hasNextLine()) {
			in.add(scan.nextLine());			
		}
		scan.close();

		List<Integer> rows = rowExpand(in);
		List<Integer> columns = colExpand(in);


		Map<Integer, String> universe = new HashMap<Integer, String>();
		Map<Integer, String> universeCopy = new HashMap<Integer, String>();
		int count = 1; 
		for(int i = 0; i < in.size(); i++) {
			for(int j = 0; j < in.get(i).length(); j++) {
				if(in.get(i).charAt(j) == '#') {
					universe.put(count, i + " " + j);
					count++;
				}
			}
		}
		for (Integer key : universe.keySet()) {
			universeCopy.put(key, universe.get(key));
		}

		for(int i = 1; i < universe.keySet().size() + 1; i++) {
			String[] s1 = universe.get(i).split("\\s+");
			int y1 = Integer.parseInt(s1[0].trim());
			int x1 = Integer.parseInt(s1[1].trim());

			String newCoords = ""; 

			int checkx = x1; 
			for(int x = 0; x < columns.size(); x++) {
				if(checkx > columns.get(x)) {
					x1 = x1 + 1; 
				}
			}			
			int checky = y1; 
			for(int y = 0; y < rows.size(); y++) {
				if(checky > rows.get(y)) {
					y1 = y1 + 1; 
				}
			}
			newCoords = newCoords + y1; 
			newCoords = newCoords + " " + x1; 
			
			if(!newCoords.equals("")) {
				universeCopy.put(i, newCoords);
			}
		}


		//part 1
		int j = 1; 
		for(int i = 1; i < universeCopy.keySet().size(); i++) {
			for(; j < universeCopy.keySet().size() + 1; j++) {
				if(i != j) {
					String[] s1 = universeCopy.get(i).split("\\s+");
					String[] s2 = universeCopy.get(j).split("\\s+");
					int y1 = Integer.parseInt(s1[0].trim());
					int y2 = Integer.parseInt(s2[0].trim());
					int x1 = Integer.parseInt(s1[1].trim());
					int x2 = Integer.parseInt(s2[1].trim());


					int res = Math.abs(x1 - x2) + Math.abs(y1 - y2); 
					sumUp(res);
				}
			}
			j = i + 1;
		}

		for (Integer key : universe.keySet()) {
			universeCopy.put(key, universe.get(key));
		}

		//part 2
		for(int i = 1; i < universeCopy.keySet().size() + 1; i++) {
			String[] s1 = universeCopy.get(i).split("\\s+");
			int y1 = Integer.parseInt(s1[0].trim());
			int x1 = Integer.parseInt(s1[1].trim());

			String newCoords = ""; 

			int checkx = x1; 
			for(int x = 0; x < columns.size(); x++) {
				if(checkx > columns.get(x)) {
					x1 = x1 + 999999; 
				}
			}			
			int checky = y1; 
			for(int y = 0; y < rows.size(); y++) {
				if(checky > rows.get(y)) {
					y1 = y1 + 999999; 
				}
			}
			newCoords = newCoords + y1; 
			newCoords = newCoords + " " + x1; 
			
			if(!newCoords.equals("")) {
				universeCopy.put(i, newCoords);
			}
		}
		
		sum1 = 0; 
		j = 1; 
		for(int i = 1; i < universeCopy.keySet().size(); i++) {
			for(; j < universeCopy.keySet().size() + 1; j++) {
				if(i != j) {
					String[] s1 = universeCopy.get(i).split("\\s+");
					String[] s2 = universeCopy.get(j).split("\\s+");
					int y1 = Integer.parseInt(s1[0].trim());
					int y2 = Integer.parseInt(s2[0].trim());
					int x1 = Integer.parseInt(s1[1].trim());
					int x2 = Integer.parseInt(s2[1].trim());


					long res = Math.abs(x1 - x2) + Math.abs(y1 - y2); 
					sumUpL(res);
				}
			}
			j = i + 1;
		}

		System.out.println("Day Eleven, Part One: " + sum);
		System.out.println("Day Eleven, Part Two: " + sum1);

	}

	static List<Integer> colExpand(List<String> input) {
		List<Integer> columns = new ArrayList<>();
		for (int i = 0; i < input.get(0).length(); i++) {
			boolean found = false;
			for (int j = 0; j < input.size(); j++) {
				String line = input.get(j);
				if (line.charAt(i) == '#') {
					found = true;
					break;
				}
			}
			if (!found) {
				columns.add(i);
			}
		}
		return columns;
	}

	static List<Integer> rowExpand(List<String> input) {
		List<Integer> rows = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			if (!line.contains("#")) {
				rows.add(i);
			}
		}
		return rows;
	}

	public static void sumUp(int s) {
		sum += s;
	}
	public static void sumUpL(long s) {
		sum1 += s;
	}
}
