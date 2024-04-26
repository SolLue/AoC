package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayThirteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day13.txt");
		BufferedReader br = new BufferedReader(fr);

		int input = Integer.parseInt(br.readLine().trim());
		br.close();

		System.out.println("Day Thirteen, Part One: " + 0);

		System.out.println("Day Thirteen, Part Two: " + 0);

	}


	static boolean isOpenSpace(int x, int y, int input) {
		int nr = x * x + 3 * x + 2 * x * y + y + y * y;
		nr =+ input;
		String binary = Integer.toString(nr, 2);
		long countOnes = binary.chars().filter(ch -> ch == '1').count();
		
		if(countOnes % 2 == 0)
			return true; 
		else 
			return false; 
	}

}
