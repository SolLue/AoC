package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayTwentyThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day23.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> instructions = new ArrayList<String>();
		String t = br.readLine();
		while(t != null) {
			t = t.replace(",", "");
			instructions.add(t);
			t = br.readLine();
		}
		br.close();

		long b = followInstructions(instructions, 0, 0);
		System.out.println("Day TwentyThree, Part One: " + b);

		b = followInstructions(instructions, 1, 0);
		System.out.println("Day TwentyThree, Part Two: " + b);
	}

	static long followInstructions(List<String> input, long a, long b) {
		for (int i = 0; i < input.size(); i++) {			
			String[] options = input.get(i).split(" ");
			if(options[0].equals("hlf")) {
				if(options[1].equals("a"))
					a = a / 2;
				if(options[1].equals("b"))
					b = b / 2;
			} else if(options[0].equals("tpl")) {
				if(options[1].equals("a"))
					a = a * 3;
				if(options[1].equals("b"))
					b = a * 3;
			} else if(options[0].equals("inc")) {
				if(options[1].equals("a"))
					a++;
				if(options[1].equals("b"))
					b++;
			} else if(options[0].equals("jmp")) {
				int jump = Integer.parseInt(options[1]);
				i += jump - 1;
			} else if(options[0].equals("jie")) {
				int jump = Integer.parseInt(options[2]);
				if(options[1].equals("a")) {
					if(a % 2 == 0) 
						i += jump - 1;
				}
				if(options[1].equals("b")) {
					if(b % 2 == 0) 
						i += jump - 1;
				}
			} else if(options[0].equals("jio")) {
				int jump = Integer.parseInt(options[2]);
				if(options[1].equals("a")) {
					if(a == 1) 
						i += jump - 1;
				}
				if(options[1].equals("b")) {
					if(b == 1) 
						i += jump - 1;	
				}
			} else
				break;
		}
		return b;
	}
}
