package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import utility.Property;

public class DaySeventeen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day17.txt");
		BufferedReader br = new BufferedReader(fr);

		long registerAOri = 0; 
		long registerBOri = 0; 
		long registerCOri = 0; 
		List<Long> program = new ArrayList<Long>();

		String temp = br.readLine();
		while(temp != null) {
			if(temp.contains("Register A:")) {
				String[] eh = temp.split("Register A: ");
				registerAOri = Long.parseLong(eh[1]);
			} else if(temp.contains("Register B:")) {
				String[] eh = temp.split("Register B: ");
				registerBOri = Integer.parseInt(eh[1]);
			} else if(temp.contains("Register C:")) {
				String[] eh = temp.split("Register C: ");
				registerCOri = Integer.parseInt(eh[1]);
			} else if(!temp.isEmpty()) {
				String[] p = temp.split("\\s+");
				String[] pnumbers = p[1].split(",");
				for (String string : pnumbers) {
					program.add(Long.parseLong(string));
				}
			}

			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		long registerA = registerAOri;
		long registerB = registerBOri;
		long registerC = registerCOri;

		List<Long> out = getOutput(registerA, registerB, registerC, program);
		String output = "";
		for (int i = 0; i < out.size(); i++) {
			output += out.get(i) + "";
			if (i < out.size() - 1)
				output += ",";	
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Seventeen, Part One: " + output);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		//	long registerAL = registerAOri;
		long registerBL = registerBOri;
		long registerCL = registerCOri;

		long newA = getTooManyNumbers(program, registerBL, registerCL);

		stopTime = System.currentTimeMillis();
		System.out.println("Day Seventeen, Part Two: " + newA);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static long getTooManyNumbers(List<Long> program, long regB, long regC) {
		SortedSet<Long> candidates = new TreeSet<>();
		candidates.add(0L);

		for (int i = 0; i < program.size(); i++) {
			long lastInstruction = program.get(program.size() - i - 1);
			SortedSet<Long> newCandidates = new TreeSet<>();
			for (Long old : candidates) {
				Long candidate = old << 3;
				for (int j = 0; j <= 8; j++) {
					List<Long> out = getOutput(candidate, regB, regC, program); 
				
					if (out.get(0) == lastInstruction) {
						newCandidates.add(candidate);
					}
					candidate++;
				}
			}
			candidates = newCandidates;
		}

		long test = 0L;
		for (Long long1 : candidates) {
			List<Long> out = getOutput(long1, regB, regC, program);

			boolean valid = true; 
			for (int i = 0; i < program.size(); i++) {
				if (out.get(i) != program.get(i)) {
					valid = false;
					break;
				}
			}
			if(valid) {
				test = long1; 
				break;
			}
		}
		return test;
	}

	static List<Long> getOutput(long registerA, long registerB, long registerC, List<Long> program) {
		List<Long> out = new ArrayList<Long>(); 

		for (int i = 0; i < program.size(); i += 2) {
			long num = program.get(i);
			long operand = program.get(i + 1);
			if (operand >= 0 && operand <= 3)
				operand = program.get(i + 1);
			else if (operand == 4)
				operand = registerA; 
			else if (operand == 5)
				operand = registerB; 
			else if (operand == 6)
				operand = registerC;

			if(num == 0) {
				registerA = (long) ((long) registerA / Math.pow(2, operand));
			} else if(num == 1) {
				registerB = registerB ^ program.get(i + 1);
			} else if(num == 2) {
				registerB = operand % 8; 
			} else if(num == 3) {
				if (registerA != 0) {
					i = (int) (program.get(i + 1) - 2);
				} 
			} else if(num == 4) {
				registerB = registerB ^ registerC;
			} else if(num == 5) {
				long output = operand % 8;
				out.add(output);
			} else if(num == 6) {
				registerB = (long) ((long) registerA / Math.pow(2, operand));
			} else if(num == 7) {
				registerC = (long) ((long) registerA / Math.pow(2, operand));
			}
		}
		return out;		
	}

}
