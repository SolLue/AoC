package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayTwenty {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day20.txt");
		BufferedReader br = new BufferedReader(fr);

		Integer input = Integer.parseInt(br.readLine());
		br.close();

		int lowestHouse = busyElves(input);

		System.out.println("Day Twenty, Part One: " + lowestHouse);

		int lowest = busyPickyElves(input);
		System.out.println("Day Twenty, Part Two: " + lowest);

	}

	static int busyElves(Integer goal) {
		int elf = goal / 100;
		boolean housefound = false;

		while(!housefound) {
			int gifts = 0;
			int d = getdivis(elf).stream().mapToInt(Integer::intValue).sum();

			gifts += d * 10;

			if(gifts >= goal) {
				housefound = true;
			} else {
				elf++;
			}
		}
		return elf;
	}

	static List<Integer> getdivis(int num) {
		List<Integer> small = new ArrayList<Integer>();
		for(int i = 1; i <= (int) Math.sqrt(num) + 1; i++) {
			if(num % i == 0) {
				small.add(i);
			}
		}
		List<Integer> big = new ArrayList<Integer>();
		for(Integer i : small) {
			if(num != i * i) {
				if(!small.contains(num / i))
					big.add(num / i);
			}
		}
		small.addAll(big);

		return small;
	}

	static int busyPickyElves(Integer goal) {
		int elf = goal / 100;
		boolean housefound = false;
 
		while(!housefound) {
			int gifts = 0;
			List<Integer> div = getdivis(elf);

			for (Integer integer : div) {
				if(elf / integer <= 50)
					gifts += integer * 11;					
			}

			if(gifts >= goal) {
				housefound = true;
			} else {
				elf++;
			}
		}
		return elf;
	}
}
