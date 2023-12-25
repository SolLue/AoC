package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DayTwelve {

	static long sum = 0;
	static Map<String, Long> memo = new HashMap<>();

	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day12.txt");
		Scanner scan = new Scanner(fr);

		List<String> input = new ArrayList<String>(); 
		while(scan.hasNextLine()) {
			input.add(scan.nextLine());
		}
		scan.close();

		//part 1
		for (String s : input) {
			String[] in = s.split(" ");
			in[0] = in[0];
			in[1] = in[1];

			List<Integer> numList = new ArrayList<Integer>();

			String[] temp = in[1].split(",");
			for (String string : temp) {
				numList.add(Integer.parseInt(string));
			}

			//populate defect with group sizes
			int[] defect = new int[numList.size() * 2 + 1];
			for (int i = 1; i < defect.length - 1; i++) {
				if(i % 2 == 0) {
					defect[i] = 1;
				} else {
					defect[i] = numList.get(i / 2);
				}
			}

			int gsum = 0;
			for (int i : defect) {
				gsum += i; 
			}

			int spotswithQ = in[0].length() - gsum;

			sum += count(spotswithQ, 0, defect, in[0]);
			memo.clear();

		}
		System.out.println("Day Twelve, Part One: " + sum);
		sum = 0; 

		//part 2
		for (String s : input) {
			String[] in = s.split(" ");
			in[0] = (in[0] + "?").repeat(4) + in[0];
			in[1] = (in[1] + ",").repeat(5);

			List<Integer> numList = new ArrayList<Integer>();

			String[] temp = in[1].split(",");
			for (String string : temp) {
				numList.add(Integer.parseInt(string));
			}

			int[] defect = new int[numList.size() * 2 + 1];
			for (int i = 1; i < defect.length - 1; i++) {
				if(i % 2 == 0)
					defect[i] = 1;
				else
					defect[i] = numList.get(i / 2);
			}

			int gsum = 0;
			for (int i : defect) {
				gsum += i; 
			}

			int spotswithQ = in[0].length() - gsum;

			sum += count(spotswithQ, 0, defect, in[0]);
			memo.clear();
		}

		System.out.println("Day Twelve, Part Two: " + sum);
	}

	static long count(int spots, int id, int[] defect, String t) {
		if (spots == 0) {
			if(isValid(defect.length, defect, t))
				return 1; 
			else
				return 0; 
		}
		if (id > defect.length)
			return 0;

		long res = 0;
		for (int i = 0; i <= spots; i++) {
			defect[id] += i;
			if (isValid(id + 1, defect, t)) {
				String key = (id + 2) + " " + (spots - i);
				if (!memo.containsKey(key)) {
					memo.put(key, count(spots - i, id + 2, defect, t));
				}
				res += memo.get(key);
			}

			defect[id] -= i;
		}
		return res;
	}

	static boolean isValid(int gr, int[] groups, String t) {
		String result = "";
		for (int x = 0; x < gr; x++) {
			if(x % 2 == 0)
				result += ".".repeat(groups[x]);
			else  
				result += "#".repeat(groups[x]);
		}

		for (int x = 0; x < result.length(); x++) {
			if (t.charAt(x) != '?' && result.charAt(x) != t.charAt(x)) {
				return false;
			}
		}
		return true;
	}
}
