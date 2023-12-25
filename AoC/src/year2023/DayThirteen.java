package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DayThirteen {

	static int sum;
	static long sumL;

	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day13.txt");
		Scanner scan = new Scanner(fr);

		List<List<String>> input = new ArrayList<List<String>>(); 

		List<String> t = new ArrayList<String>(); 
		while(scan.hasNextLine()) {
			String temp = scan.nextLine();
			if(temp.equals("")) {
				input.add(new ArrayList<String>(t));
				t.clear();
			} else {
				t.add(temp);
			}
		}
		input.add(new ArrayList<String>(t));
		scan.close();

		sum = 0; 

		//part 1
		for(int i = 0; i < input.size(); i++) { 
			char[][] vertical = new char[input.get(i).size()][]; 

			for(int j = 0; j < input.get(i).size(); j++) {				
				vertical[j] = input.get(i).get(j).toCharArray();
			}

			findMirror(vertical);
		}

		for(int i = 0; i < input.size(); i++) { 
			char[][] vertical = new char[input.get(i).size()][]; 

			for(int j = 0; j < input.get(i).size(); j++) {				
				vertical[j] = input.get(i).get(j).toCharArray();
			}

			findAlmostMirror(vertical);
		}
		System.out.println("Day Thirteen, Part One: " + sum);
		System.out.println("Day Thirteen, Part Two: " + sumL);

	}
	static void sumUp(int s) {
		sum += s;
	}
	static void sumUpL(int s) {
		sumL += s;
	}
	static void findMirror(char[][] in) {
		int v = -1; 
		int h = -1; 

		v = splitArraysVertically(in); 
		h = splitArraysHorizontally(in);

		if(v != -1) {
			sumUp(v);
		} if(h != -1) {
			sumUp(h * 100);
		}	
	}

	static int splitArraysHorizontally(char[][] in) {		
		int c = 0; 
		int match = -1; 
		boolean foundMatch = false; 
		int temp = -1;

		while(c < in.length - 1 && !foundMatch) {
			foundMatch = Arrays.equals(in[c], in[c + 1]);

			if(foundMatch) {
				temp = c;   
				int a = temp + 1; 
				while(temp >= 0 && a < in.length && foundMatch) {
					foundMatch = foundMatch && Arrays.equals(in[temp], in[a]);
					temp--;
					a++;
				}
			}
			if(foundMatch)
				match = c; 
			c++;
		}
		
		if(match != -1)
			match = match + 1; 
		return match;
	}

	static char[] getColumnArray(char[][] in, int index) {
		char[] column = new char[in.length]; 

		for(int i = 0; i < column.length; i++) {
			column[i] = in[i][index];
		}
		return column;
	}

	static int splitArraysVertically(char[][] in) {
		int c = 0; 
		char[] first; 
		char[] second; 
		int match = -1; 

		boolean foundMatch = false; 

		while(c < in[0].length - 1 && !foundMatch) {
			first = getColumnArray(in, c);
			second = getColumnArray(in, c + 1);
			foundMatch = Arrays.equals(first, second);

			if(foundMatch) {
				int temp = c; 
				int a = temp + 1; 
				while(temp >= 0 && a < in[0].length && foundMatch) {
					first = getColumnArray(in, temp);
					second = getColumnArray(in, a);
					foundMatch = foundMatch && Arrays.equals(first, second);
					temp--;
					a++;
				}
			}
			if(foundMatch)
				match = c; 
			c++;
		}

		if(match != -1)
			match = match + 1; 
		return match;
	}

	//part 2 lol

	static void findAlmostMirror(char[][] in) {
		int v = -1; 
		int h = -1; 

		v = splitArraysAlmostVertically(in); 
		h = splitArraysAlmostHorizontally(in);

		if(v != -1) {
			sumUpL(v);
		} 
		if(h != -1) {
			sumUpL(h * 100);
		}	
	}

	static int almostequals(char[] one, char[] two) {
		int almost = 0;
		for (int i = 0; i < one.length; i++) {
			if(one[i] != two[i]) 
				almost++;
		}
		return almost; 		
	}

	static int splitArraysAlmostHorizontally(char[][] in) {		
		int c = 0; 
		int match = -1; 
		int foundMatch = -1; 
		int temp = -1;
		
		while(c < in.length - 1 && foundMatch != 0) {
			foundMatch = foundMatch + almostequals(in[c], in[c + 1]);
			if(foundMatch == 0 || foundMatch == -1) {
				foundMatch = -1; 
				temp = c;   
				int a = temp + 1; 

				while(temp >= 0 && a < in.length) {					
					foundMatch = foundMatch + almostequals(in[temp], in[a]);
					temp--;
					a++;
				}
				if(foundMatch == 0) {
					match = c;
				}
			}c++;
			foundMatch = -1;
		}
		
		if(match > -1)
			match = match + 1; 

		return match;
	}

	static int splitArraysAlmostVertically(char[][] in) {
		int c = 0; 
		char[] first; 
		char[] second; 
		int match = -1; 
		int foundMatch = -1; 
		
		while(c < in[0].length - 1 && foundMatch != 0) {
			first = getColumnArray(in, c);
			second = getColumnArray(in, c + 1);
			foundMatch = foundMatch + almostequals(first, second);

			if(foundMatch == 0 || foundMatch == -1) {
				foundMatch = -1; 
				int temp = c; 
				int a = temp + 1; 
				while(temp >= 0 && a < in[0].length) {				
					first = getColumnArray(in, temp);
					second = getColumnArray(in, a);
					foundMatch = foundMatch + almostequals(first, second);
					temp--;
					a++;
				}
				if(foundMatch == 0) { 
					match = c; 
				}
			}
			c++;
			foundMatch = -1;
		}

		if(match > -1)
			match = match + 1;
		
		return match;

	}
}
