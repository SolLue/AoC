package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayEight {

	static int steps = 0;

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day8.txt");
		Scanner scan = new Scanner(fr);

		List<String> data = new ArrayList<String>();
		List<String> left = new ArrayList<String>();
		List<String> right = new ArrayList<String>();

		String instructions = ""; 
		boolean first = true;

		while(scan.hasNextLine()) {
			String temp = scan.nextLine();
			if(first && !temp.equals("")) {
				instructions = temp;
				first = false; 
			} else if(!temp.equals("")) {
				data.add(temp.substring(0, temp.indexOf(" ")));
				left.add(temp.substring(temp.indexOf("(") + 1, temp.indexOf(","))); 
				right.add(temp.substring(temp.indexOf(",") + 2, temp.indexOf(")")));			
			}
		}
		scan.close();

		String current = "AAA"; 
		int currentIndex = 0;

		for(int i = 0; i < data.size(); i++) {
			if(current.equals(data.get(i))) {
				currentIndex = i; 
			}
		}

		boolean found = false; 
		while(!found) {
			for(char c : instructions.toCharArray()) {
				if(c == 'R') {
					current = right.get(currentIndex);
					for(int i = 0; i < data.size(); i++) {
						if(current.equals(data.get(i))) {
							currentIndex = i; 
						}
					}
				} else if(c == 'L') {
					current = left.get(currentIndex);
					for(int i = 0; i < data.size(); i++) {
						if(current.equals(data.get(i))) {
							currentIndex = i; 
						}
					}
				}
				steps++;
			}
			if(current.equals("ZZZ")) {
				found = true; 
			}
		}		

		System.out.println("Day eight, part one: " + steps);

		steps = 0;

		List<String> currentList = new ArrayList<String>(); 
		List<Integer> currentIndexList = new ArrayList<Integer>();

		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).endsWith("A")) {
				currentList.add(data.get(i));
				currentIndexList.add(i);
			}
		}

		List<Integer> stepstaken = new ArrayList<Integer>();

		for (int g = 0; g < currentList.size(); g++) {
			found = false;
			while(!found) {
				for(char c : instructions.toCharArray()) {
					if(c == 'R') {
						currentList.set(g, right.get(currentIndexList.get(g))); 

						for(int j = 0; j < data.size(); j++) {
							if(currentList.get(g).equals(data.get(j))) {
								currentIndexList.set(g, j);
							}
						}

					} else if(c == 'L') {
						currentList.set(g, left.get(currentIndexList.get(g))); 

						for(int j = 0; j < data.size(); j++) {
							if(currentList.get(g).equals(data.get(j))) {
								currentIndexList.set(g, j);
							}
						}
					}
					steps++;
				}
				if(currentList.get(g).endsWith("Z")) {
					found = true; 
				} 
			}
			stepstaken.add(steps);
			steps = 0; 
		}

		long[] inputforpart2 = new long[stepstaken.size()];

		for (int i = 0; i < stepstaken.size(); i++) {
			Long l = Long.valueOf(stepstaken.get(i));
			inputforpart2[i] = l;
		}

		long lcm = leastCommon(inputforpart2);

		System.out.println("Day eight, part two: " + lcm);		
	}

	static long leastCommon(long nr1, long nr2) {
		return nr1 * (nr2 / gcd(nr1, nr2));
	}

	static long leastCommon(long[] input) {
		long result = input[0];
		for(int i = 1; i < input.length; i++) result = leastCommon(result, input[i]);
		return result;
	}

	static long gcd(long[] input) {
		long result = input[0];
		for(int i = 1; i < input.length; i++)
			result = gcd(result, input[i]);
		return result;
	}

	static long gcd(long nr1, long nr2) {
		while (nr2 > 0) {
			long temp = nr2;
			nr2 = nr1 % nr2; 
			nr1 = temp;
		}
		return nr1;
	}

}
