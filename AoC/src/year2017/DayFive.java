package year2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2017/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Integer> instructions = new ArrayList<Integer>();
		String temp = br.readLine();
		while(temp != null) {
			instructions.add(Integer.parseInt(temp));			
			temp = br.readLine();
		}
		br.close();

		List<Integer> instructionsPart2 = instructions.stream().collect(Collectors.toList());
		
		int index = 0; 
		int steps = 0;
		boolean outside = false; 
		while (!outside) {
			int currentInstruction = instructions.get(index);
			int jump = currentInstruction + 1;
			instructions.set(index, jump);
			index += currentInstruction;
			steps++;
			if (index < 0 || index >= instructions.size())
				outside = true;

		}
		System.out.println("Day Five, Part One: " + steps);

		index = 0; 
		steps = 0;
		outside = false; 
		while (!outside) {
			int currentInstruction = instructionsPart2.get(index);
			
			int jump = 0; 
			if(currentInstruction >= 3) {
				jump = currentInstruction - 1;
			} else {
				jump = currentInstruction + 1;
			}
			instructionsPart2.set(index, jump);	
			
			index += currentInstruction;
			steps++;
			if (index < 0 || index >= instructionsPart2.size())
				outside = true;

		}
				
		System.out.println("Day Five, Part One: " + steps);

	}
}
