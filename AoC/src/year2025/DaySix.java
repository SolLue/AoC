package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DaySix {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day6.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		List<char []> charin = new ArrayList<char[]>();
		String operator = "";
		String temp = br.readLine();
		while (temp != null) {
			if (temp.contains("+"))
				operator = temp.trim();
			else {
				input.add(temp.trim());
				charin.add(temp.toCharArray());
			}
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis();

		String[] operatorarr = operator.split("\\s+");

		List<int[]> problem = new ArrayList<int[]>();
		for (String string : input) {
			String[] arr = string.split("\\s+");

			int[] toint = new int[arr.length];
			for (int i = 0; i < arr.length; i++) {
				if (!arr[i].isEmpty())
					toint[i] = Integer.parseInt(arr[i].trim());
			}
			problem.add(toint);
		}

		long total = 0;
		for (int i = 0; i < operatorarr.length; i++) {
			long solution = 0;
			for (int[] is : problem) {
				if (solution == 0)
					solution = is[i];
				else {
					if (operatorarr[i].equals("+")) 
						solution += is[i];
					 else if (operatorarr[i].equals("*")) 
						solution *= is[i];
				}
			}
			total += solution;
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Six, Part One: " + total);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis();
		total = 0; 
		int empty = 0; 
		int operatorIndex = operatorarr.length - 1;
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = charin.get(0).length - 1; i >= 0; i--) {
			String t = "";
			for(int j = 0; j < charin.size(); j++) {
				 t += charin.get(j)[i];
				 if (charin.get(j)[i]  == ' ')
					 empty++;
			}

			if (empty < input.size()) {
				numbers.add(Integer.parseInt(t.trim()));
				t = ""; 
				empty = 0; 
			} 
			if ((empty == input.size() && numbers.size() > 0) || i == 0) {
				empty = 0; 
				long solution = numbers.get(0); 
				for (int k = 1; k < numbers.size(); k++) {
					if (operatorarr[operatorIndex].equals("+"))
						solution += numbers.get(k);
					else
						solution *= numbers.get(k);
						
				}
				total += solution; 
				numbers.clear();
				operatorIndex--;
			}
		}		
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Six, Part Two: " + total);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
}
