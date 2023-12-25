package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayNine {

	static int sum; 
	static int sumPart2;
	
	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day9.txt");
		Scanner scan = new Scanner(fr);
		List<String> inputarr = new ArrayList<String>();

		while (scan.hasNextLine()) {
			String t = scan.nextLine();
			inputarr.add(t);
		}
		scan.close();
		
		for(int i = 0; i < inputarr.size(); i++) {
			List<List<Integer>> lists = new ArrayList<List<Integer>>();
			List<Integer> temp = new ArrayList<Integer>();

			String[] split = inputarr.get(i).split("\\s+");
			for (String string : split) {
				temp.add(Integer.parseInt(string));
			}
			lists.add(temp);

			List<Integer> line = differences(temp);
			lists.add(line);
			boolean done = true; 
			do {
				line = differences(line);
				lists.add(line);

				for(int lineint : line) {
					if (lineint == 0) {
						done = true; 
					} else {
						done = false; 
					}
					if(!done) {
						break;
					}
				}
			} while(!done);

			for(int j = lists.size() - 1; j >= 1; j--) {
				int[] result = backwards(lists.get(j), lists.get(j - 1));
				List<Integer> t = new ArrayList<Integer>();
				t.add(result[1]);
				t.addAll(lists.get(j - 1));
				t.add(result[0]);
				lists.set(j - 1, t);
			}

	//		String t = lists.get(0).get(0) + " ";
	//		t += inputarr.get(i);
	//		t += " " + lists.get(0).get(lists.get(0).size() - 1);
	//		inputarr.set(i, t);
			sumUp(lists.get(0).get(lists.get(0).size() - 1));
			sumUpHistory(lists.get(0).get(0));
		}

		System.out.println("Day Nine, Part One: " + sum);
		System.out.println("Day Nine, Part Two: " + sumPart2);

	}

	static List<Integer> differences(List<Integer> list) {
		List<Integer> line = new ArrayList<Integer>();
		for (int j = 0; j < list.size() - 1; j++) {
			int nr2 = list.get(j); 
			int nr1 = list.get(j + 1);
			line.add(nr1 - nr2);
		}
		return line;
	}

	static int[] backwards(List<Integer> below, List<Integer> above) {
		int[] result = new int[2];
		result[0] = below.get(below.size() - 1) + above.get(above.size() - 1);
		result[1] = above.get(0) - below.get(0);
		
		return result;
	}
	
	static void sumUp(int s) {
		sum += s;
	}
	static void sumUpHistory(int s) {
		sumPart2 += s;
	}

}
