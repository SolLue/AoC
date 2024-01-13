package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		List<int[]> input = new ArrayList<int[]>(); 
		String t = br.readLine();
		while(t != null) {
			t = t.replaceAll("\\s+", " ").trim();
			String[] op = t.split(" ");
			int[] tria = new int[3];
			tria[0] = Integer.parseInt(op[0]);
			tria[1] = Integer.parseInt(op[1]);
			tria[2] = Integer.parseInt(op[2]);
			input.add(tria);
			t = br.readLine();
		}
		br.close();

		int possible = findValidTriangles(input);

		System.out.println("Day Three, Part One: " + possible);	

		possible = findValidTrianglesVertical(input);				
		System.out.println("Day Three, Part Two: " + possible);
	}

	static int findValidTriangles(List<int[]> input) {
		int possible = 0; 
		for (int[] triangle : input) {
			if((triangle[0] + triangle[1] > triangle[2])
					&& (triangle[1] + triangle[2] > triangle[0])
					&& (triangle[2] + triangle[0] > triangle[1])) {
				possible++;
			}
		}
		return possible;
	}

	static int findValidTrianglesVertical(List<int[]> input) {
		int possible = 0; 
		for (int i = 0; i < input.size(); i += 3) {
			if((input.get(i)[0] + input.get(i + 1)[0] > input.get(i + 2)[0])
					&& (input.get(i + 2)[0] + input.get(i)[0] > input.get(i + 1)[0])
					&& (input.get(i + 1)[0] + input.get(i + 2)[0] > input.get(i)[0])) {
				possible++;
			}
			if((input.get(i)[1] + input.get(i + 1)[1] > input.get(i + 2)[1])
					&& (input.get(i + 2)[1] + input.get(i)[1] > input.get(i + 1)[1])
					&& (input.get(i + 1)[1] + input.get(i + 2)[1] > input.get(i)[1])) {
				possible++;
			}
			if((input.get(i)[2] + input.get(i + 1)[2] > input.get(i + 2)[2])
					&& (input.get(i + 2)[2] + input.get(i)[2] > input.get(i + 1)[2])
					&& (input.get(i + 1)[2] + input.get(i + 2)[2] > input.get(i)[2])) {
				possible++;
			}
		}
		return possible;
	}
}