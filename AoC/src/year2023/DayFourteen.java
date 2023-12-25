package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DayFourteen {

	static int sum;
	static long sumL; 
	
	static Map<Integer, char[][]> board; 
		
	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day14.txt");
		Scanner scan = new Scanner(fr);
		List<String> input = new ArrayList<String>();

		while(scan.hasNextLine()) {
			input.add(scan.nextLine());
		}		
		scan.close();
	      long start1 = System.currentTimeMillis();
		char[][] in = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			in[i] = input.get(i).toCharArray();
		}

		tiltNorth(in);
		
		int count = 1; 
		for(int i = in.length - 1; i >= 0; i--) {
			for(int j = 0; j < in[i].length; j++) {
				if(in[i][j] == 'O') {
					sumUp(count);
				}	
			}
			count++; 
		}
	      long end1 = System.currentTimeMillis();      
		System.out.println("Day Fourteen, Part One: " + sum);
		System.out.println((end1-start1));
		board = new HashMap<Integer, char[][]>();
		
		// reset for part 2
		sumL = 0; 
	      long start2 = System.currentTimeMillis();

		in = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			in[i] = input.get(i).toCharArray();
		}

		Map<String, Long> map = new HashMap<String, Long>();

		for (long cycle = 0; cycle < 1000000000; cycle++) {
			tiltNorth(in);
			tiltWest(in);
			tiltSouth(in);
			tiltEast(in);
			
			String charToString = convert(in);
			if (map.containsKey(charToString)) {
				long mod = cycle - map.get(charToString);
				cycle += mod* ((1000000000 - cycle) / mod);
			}
			map.put(charToString, cycle);
		}
	      long end2 = System.currentTimeMillis();      

		getCount(in); 
		
		System.out.println("Day Fourteen, Part Two: " + sumL);	
		System.out.println((end2-start2));

	}

	
	static String convert(char[][] in) {
		StringBuffer sb = new StringBuffer();
		for (char[] c : in) {
			sb.append(new String(c));
		}
		return  sb.toString();
	}
	
	static char[][] tiltNorth(char[][] in) {
		for(int i = 0; i < in.length; i++) {
			for(int j = 0; j < in[i].length; j++) {
				if(in[i][j] == 'O') {
					int temp = i - 1;
					while(temp >= 0) {
						if(in[temp][j] == '.') {
							in[temp][j] = 'O';
							in[temp + 1][j] = '.'; 
						} else if(in[temp][j] == '#' || in[temp][j] == 'O')
							break; 
						temp--;
					}
				}

			}	
		}
		return in;
	}
	
	static char[][] copyArray(char[][] in) {
		char[][] copy = new char[in.length][];
		for(int i = in.length - 1; i >= 0; i--) {
			copy[i] = in[i];
		}
		return copy; 
	}
	
	static long getCount(char[][] in) {
		int count = 1; 
		for(int i = in.length - 1; i >= 0; i--) {
			for(int j = 0; j < in[i].length; j++) {
				if(in[i][j] == 'O') {
					sumUpL(count);
				}	
			}
			count++; 
		}
		return count; 
	}
	
	static char[][] tiltWest(char[][] in) {
		for(int i = 0; i < in.length; i++) {
			for(int j = 0; j < in[i].length; j++) {
				if(in[i][j] == 'O') {
					int temp = j - 1;
					while(temp >= 0) {
						if(in[i][temp] == '.') {
							in[i][temp] = 'O';
							in[i][temp + 1] = '.'; 
						} else if(in[i][temp] == '#' || in[i][temp] == 'O')
							break; 
						temp--;
					}
				}
			}	
		}
		return in;
	}

	static char[][] tiltSouth(char[][] in) {
		for(int i = in.length - 1; i >= 0; i--) {
			for(int j = 0; j < in[i].length; j++) {
				if(in[i][j] == 'O') {
					int temp = i + 1;
					while(temp < in.length) {
						if(in[temp][j] == '.') {
							in[temp][j] = 'O';
							in[temp - 1][j] = '.'; 
						} else if(in[temp][j] == '#' || in[temp][j] == 'O')
							break; 
						temp++;
					}
				}

			}	
		}
		return in;
	}
	static char[][] tiltEast(char[][] in) {
		for(int i = 0; i < in.length; i++) {
			for(int j = in[i].length - 1; j >= 0; j--) {
				if(in[i][j] == 'O') {
					int temp = j + 1;
					while(temp < in[i].length) {
						if(in[i][temp] == '.') {
							in[i][temp] = 'O';
							in[i][temp - 1] = '.'; 
						} else if(in[i][temp] == '#' || in[i][temp] == 'O')
							break; 
						temp++;
					}
				}

			}	
		}
		return in;
	}

	static void sumUp(int s) {
		sum += s;
	}
	static void sumUpL(int s) {
		sumL += s;
	}

}
