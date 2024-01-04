package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utility.Property;

public class DaySeventeen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day17.txt");
		BufferedReader br = new BufferedReader(fr);

		String t = br.readLine();
		List<Integer> input = new ArrayList<Integer>();
		while (t != null) {
			input.add(Integer.parseInt(t));
			t = br.readLine();
		}
		br.close();

		int total = 150;
		List<Integer> perm = new ArrayList<Integer>();
		int differentamounts = combinations(total, 0, input, perm);

		System.out.println("Day Seventeen, Part One: " + differentamounts);
		
		int min = Collections.min(perm);
		long minL = perm.stream().filter(x -> x.equals(min)).count();
		System.out.println("Day Seventeen, Part Two: " + minL);
	}

	static int combinations(int li, int used, List<Integer> input, List<Integer> buildingUp) {
		if (li == 0) {
			buildingUp.add(used);
			return 1;
		} else if (li < 0 || input.isEmpty())
			return 0;
		else {
			List<Integer> temp = input.subList(1, input.size());
			return combinations(li - input.get(0), used + 1, temp, buildingUp) 
					+ combinations(li, used, temp, buildingUp);
		}
	}
}
