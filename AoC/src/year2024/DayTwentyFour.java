package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utility.Property;

public class DayTwentyFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day24.txt");
		BufferedReader br = new BufferedReader(fr);

		Map<String, Integer> input = new HashMap<String, Integer>();
		List<Command> operations = new ArrayList<Command>();
		String temp = br.readLine();
		while(temp != null) {
			if (temp.contains(":")) {
				String[] s = temp.split(": ");
				input.put(s[0], Integer.parseInt(s[1]));
			} else if (temp.contains("->")) {
				String[] s = temp.split("\\s+");
				Command c = new Command(s[0], s[1], s[2], s[4]);
				operations.add(c);
			}
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<Command> operationsCopy = new ArrayList<Command>(operations);

		while (!operationsCopy.isEmpty()) {
			Command current = null;
			for (int i = 0; i < operationsCopy.size(); i++) {
				current = operationsCopy.get(i);
				if (input.get(current.first) != null && input.get(current.second) != null) {
					current.command(input);
					break;
				}
			}
			if (current != null) 
				operationsCopy.remove(current);
		}


		List<String> sorted = new ArrayList<String>();
		sorted.addAll(input.keySet().stream().filter(key -> key.startsWith("z"))
				.collect(Collectors.toList()));
		Collections.sort(sorted, Collections.reverseOrder());

		String out = ""; 
		for (String string : sorted) {
			out += input.get(string) + ""; 
		}

		long output = Long.parseLong(out, 2);



		long stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyFour, Part One: " + output);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 



		stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyFour, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static class Command {
		String first; 
		String second; 
		String command;
		String saveto; 

		Command(String f, String c, String s, String save) {
			this.first = f; 
			this.second = s; 
			this.command = c;
			this.saveto = save; 	
		}

		void command(Map<String, Integer> input) {
			int firstint = input.get(this.first);
			int secondint = input.get(this.second);

			if (this.command.equals("AND")) {
				int result = firstint & secondint;
				input.put(this.saveto, result);
			} else if (this.command.equals("OR")) {
				int result = firstint | secondint;
				input.put(this.saveto, result);
			} else if (this.command.equals("XOR")) {
				int result = firstint ^ secondint;
				input.put(this.saveto, result);
			}
		}
	}
}
