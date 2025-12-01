package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class TwentyThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day23.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<String> inputpart1 = new ArrayList<String>(input);
		int valueA = runInstructions(inputpart1, 7);

		long stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyThree, Part One: " + valueA);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		// Changing input list to multiple instead of jumping back and adding up otherwise add "--" 
		// so the program jumps to the correct space (list length stays the same)
		List<String> inputpart2 = new ArrayList<String>();
		for (int i = 0; i < input.size(); i++) {
			if (i < 4)
				inputpart2.add(input.get(i));
			else if (i > 9)
				inputpart2.add(input.get(i));
			else if (i == 4) {
				inputpart2.add("mul a b d");
			} else if (i == 5) {
				inputpart2.add("cpy 0 d");
			} else {
				inputpart2.add("--");
			}
		}
		
		valueA = runInstructions(inputpart2, 12);

		stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyThree, Part Two: " + valueA);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
		
	static int runInstructions(List<String> input, int init) {
		int done = 0;
		Register[] arrReg = new Register[4];
		arrReg[0] = new Register('a', init);
		arrReg[1] = new Register('b');
		arrReg[2] = new Register('c');
		arrReg[3] = new Register('d');

		while(done <= input.size() - 1) {
			if(input.get(done).contains("cpy")) {
				String[] options = input.get(done).split(" ");	
				if(!isNumber(options[1]) && !isNumber(options[2])) {
					arrReg[getNumber(options[2].charAt(0))].setValue(arrReg[getNumber(options[1].charAt(0))].value);
				} else if(!isNumber(options[2]) && isNumber(options[1])) {
					arrReg[getNumber(options[2].charAt(0))].setValue(Integer.parseInt(options[1]));
				} 
				done++;
			} else if(input.get(done).contains("inc")) {
				String[] options = input.get(done).split(" ");
				arrReg[getNumber(options[1].charAt(0))].increase();
				done++;
			} else if(input.get(done).contains("dec")) {
				String[] options = input.get(done).split(" ");
				arrReg[getNumber(options[1].charAt(0))].decrease();
				done++;
			} else if(input.get(done).contains("jnz")) {
				String[] options = input.get(done).split(" ");
				int check = 0;
				if(Character.isAlphabetic(options[1].charAt(0))) {
					check = arrReg[getNumber(options[1].charAt(0))].value;
				} else if (isNumber(options[1])) {
					check = Integer.parseInt(options[1]);
				}
				if(check != 0) {
					int jump = 1; 
					if (!isNumber(options[2])) {
						jump = arrReg[getNumber(options[2].charAt(0))].value;
					} else if (isNumber(options[2])) {
						jump = Integer.parseInt(options[2]);
					}
					done = done + jump;
				} else {
					done++;
				}
			} else if(input.get(done).contains("tgl")) {
				String[] options = input.get(done).split(" ");
				int register = 0;
				if(!isNumber(options[1])) {
					register = arrReg[getNumber(options[1].charAt(0))].value;
				}
				if(done + register < input.size()) {
					String instruction = input.get(done + register);
					String changed = toggle(instruction, input);
					input.remove(done + register);
					input.add(done + register, changed);
				}
				done++;
			} else if(input.get(done).contains("mul")) {
				String[] options = input.get(done).split(" ");
				arrReg[getNumber(options[1].charAt(0))].setValue(
						arrReg[getNumber(options[2].charAt(0))].value * arrReg[getNumber(options[3].charAt(0))].value);
				done++;
			} else {
				done++;
			}
		}
		return arrReg[0].value;
	}

	static boolean isNumber(String test) {
		if (test.charAt(0) == '-')
			return true; 
		else if (Character.isDigit(test.charAt(0)))
			return true; 
		return false; 
	}
	
	static String toggle(String instruction, List<String> input) {
		StringBuilder sb = new StringBuilder();
		String[] options = instruction.split(" ");	
		
		if (options.length == 3) {
			if(instruction.contains("jnz"))
				sb.append("cpy " + options[1] + " " + options[2]);
			else
				sb.append("jnz " + options[1] + " " + options[2]);		
		} else {
			if (options[0].contains("inc"))
				sb.append("dec " + options[1]);		
			else 
				sb.append("inc " + options[1]);							
		}
		return sb.toString();
	}
	static int getNumber(char reg) {
		if(reg == 'a')
			return 0;
		if(reg == 'b')
			return 1;
		if(reg == 'c')
			return 2;
		if(reg == 'd')
			return 3;
		return -1;
	}

	static class Register {
		char regName; 
		int value; 

		Register(char r) {
			this.regName = r; 
			this.value = 0;
		}
		Register(char r, int v) {
			this.regName = r; 
			this.value = v; 
		}
		void decrease() {
			this.value--;
		}
		void increase() {
			this.value++;
		}
		void setValue(int v) {
			this.value = v; 
		}
		@Override
		public String toString() {
			return regName + " -> " + value;
		}
	}
}