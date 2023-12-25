package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();
		
		
		
		
		System.out.println("Day Three, Part One: " + 0);
		System.out.println("Day Three, Part Two: " + 0);
	
	}
}
