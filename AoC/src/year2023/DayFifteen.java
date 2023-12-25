package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DayFifteen {

	static int sum;
	static int sum1; 

	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day15.txt");
		Scanner scan = new Scanner(fr);
		String in = scan.nextLine();		
		scan.close();

		String[] input = in.split(",");

		//part 1
		for (String string : input) {
			int hash = getHash(string);
			sumUp(hash);
		}

		//part 2
		Map<Integer, List<String>> boxes = new HashMap<Integer, List<String>>();

		for (String s : input) {
			String string = "";
			if(s.contains("="))
				string = s.substring(0, s.indexOf("="));
			else if(s.contains("-"))
				string = s.substring(0, s.indexOf("-"));
			
			int hash = getHash(string);
			
			List<String> le = new ArrayList<String>();
			boxes.put(hash, le);
		}

		for(int i = 0; i < input.length; i++) {
			String string = ""; 
			if(input[i].contains("="))
				string = input[i].substring(0, input[i].indexOf("="));
			else if(input[i].contains("-"))
				string = input[i].substring(0, input[i].indexOf("-"));
			
			int key = getHash(string);
			
			if(input[i].contains("=")) {
				String t = input[i].replace("=", " ");
			
				List<String> temp = boxes.get(key);
				if(temp.isEmpty()) {
					temp.add(t);
				} else {
					boolean found = false; 
					String supertemp = t.substring(0, t.indexOf(" "));
					for (int j = 0; j < temp.size(); j++) {
						if(temp.get(j).contains(supertemp)) {
							temp.set(j, t);
							found = true; 
						}
					}
					if(!found)
						temp.add(t);
				}
			}
			if(input[i].contains("-")) {
				List<String> temp = boxes.get(key);
				String t = input[i].replace("-", "");
				for (int j = 0; j < temp.size(); j++) {
					if(temp.get(j).contains(t))
						temp.remove(j);
				}
			}
		}

		for (int key : boxes.keySet()) {
			List<String> temp = boxes.get(key);
			int focal = 0; 
			for (int i = 0; i < temp.size(); i++) {
				focal = focal + ((key + 1) * (i + 1) *  
						Integer.parseInt(temp.get(i).substring(temp.get(i).indexOf(" ") + 1)));
			}
			sumUp1(focal);
		}

		System.out.println("Day Fifteen, Part One: " + sum);
		System.out.println("Day Fifteen, Part Two: " + sum1);

	}
	static void sumUp(int s) {
		sum += s;
	}
	static void sumUp1(int s) {
		sum1 += s;
	}

	static int getHash(String s) {
		int current = 0; 
		for(int i = 0; i < s.length(); i++ ) {
			int ascii = s.charAt(i);
			current = current + ascii; 
			current = current * 17; 
			current = current % 256; 
		}
		return current; 
	}

}
