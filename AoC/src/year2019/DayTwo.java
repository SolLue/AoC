package year2019;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2019/input/day2.txt");
		BufferedReader br = new BufferedReader(fr);

		String temp = br.readLine();
		br.close();

		String[] t = temp.split(",");
		int[] opcodes = new int[t.length];
		for (int i = 0; i < t.length; i++) {
			opcodes[i] = Integer.parseInt(t[i]);
		}

		long startTime = System.currentTimeMillis(); 

		int[] copyCodes = getOpCodes(opcodes);
		runProgram(copyCodes, 12, 2);

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Two, Part One: " + copyCodes[0]);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		int verb = 0; 
		int noun = 0;
		boolean found = false;
		for(int n = 0; n < 99; n++) {
			for(int v = 0; v < 99; v++) {
				copyCodes = getOpCodes(opcodes);
				runProgram(copyCodes, n, v);
				if (copyCodes[0] == 19690720) {
					found = true; 
					noun = n; 
					verb = v; 
					break;
				}
			}
			if (found)
				break;
		}
		int result = 100 * noun + verb; 

		stopTime = System.currentTimeMillis();
		System.out.println("Day Two, Part Two: " + result);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static int[] getOpCodes(int[] opcodes) {
		int[] op = new int[opcodes.length];
		for (int i = 0; i < opcodes.length; i++) {
			op[i] = opcodes[i];
		}
		return op;
	}

	static void runProgram(int[] opcodes, int noun, int verb) {
		opcodes[1] = noun; 
		opcodes[2] = verb;
		int current = 0;
		int opcode = opcodes[current];
		while (opcode != 99) {
			opcode = opcodes[current];

			if (opcode == 1) {
				opcodes[opcodes[current + 3]] = opcodes[opcodes[current + 1]] + opcodes[opcodes[current + 2]];
			} else if (opcode == 2) {
				opcodes[opcodes[current + 3]] = opcodes[opcodes[current + 1]] * opcodes[opcodes[current + 2]];
			} 
			current += 4; 
		}
	}
}
