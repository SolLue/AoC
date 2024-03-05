package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayTwelve {

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day12.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		int valueA = runInstructions(input, 0);
		System.out.println("Day Twelve, Part One: " + valueA);
	
		valueA = runInstructions(input, 1);
		System.out.println("Day Twelve, Part Two: " + valueA);

	}

	static int runInstructions(List<String> input, int init) {
		int done = 0;
		Register[] arrReg = new Register[4];
		arrReg[0] = new Register('a');
		arrReg[1] = new Register('b');
		arrReg[2] = new Register('c', init);
		arrReg[3] = new Register('d');
		
		while(done <= input.size() - 1) {
			if(input.get(done).contains("cpy")) {
				String[] options = input.get(done).split(" ");	
				if(Character.isAlphabetic(options[1].charAt(0)) && Character.isAlphabetic(options[2].charAt(0))) {
					arrReg[getNumber(options[2].charAt(0))].setValue(arrReg[getNumber(options[1].charAt(0))].value);
				} else if(Character.isAlphabetic(options[2].charAt(0)) && Character.isDigit(options[1].charAt(0))) {
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
				} else if(Character.isDigit(options[1].charAt(0))) {
					check = Integer.parseInt(options[1]);
				}
				if(check != 0) {
					done = done + Integer.parseInt(options[2]);
				} else {
					done++;
				}
			}
		}

		return arrReg[0].value;
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
	}
}
