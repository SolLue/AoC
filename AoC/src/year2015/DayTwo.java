package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.Property;

public class DayTwo {

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day2.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		long wrappingPaper = 0;
		long ribbonlength = 0; 

		for (String string : input) {
			int length = Integer.parseInt(string.substring(0, string.indexOf("x")));
			string = string.substring(string.indexOf("x") + 1);
			int width = Integer.parseInt(string.substring(0, string.indexOf("x")));
			string = string.substring(string.indexOf("x") + 1);
			int height = Integer.parseInt(string);
			int surface = surfaceCalc(length, width, height);
			int min = findMin(length, width, height);
			wrappingPaper += (long) surface + min; 

			ribbonlength += (long) ribbon(length, width, height);		
		}

		System.out.println("Day Two, Part One: " + wrappingPaper);
		System.out.println("Day Two, Part Two: " + ribbonlength);
	}

	static int surfaceCalc(int l, int w, int h)  {
		return (2 * l * w) + (2 * w * h) + (2 * h * l);
	}
	static int findMin(int l, int w, int h)  {
		int min = Math.min(l * w, w * h);
		min = Math.min(min, h * l);
		return min;
	}

	static int ribbon(int l, int w, int h) {
		int[] arr = {l, w, h};
		Arrays.sort(arr);
		return (arr[0] + arr[0] + arr[1] + arr[1]) + (l * w * h);			

	}

}
