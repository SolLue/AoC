package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day2.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		int twoLetters = 0;
		int threeLetters = 0;
		for (int i = 0; i < input.size(); i++) {
			char[] c = input.get(i).toCharArray();
			Map<Character, Integer> letters = new HashMap<Character, Integer>();
			boolean twoletterflag = false;
			boolean threeletterflag = false;
			for (char chara : c) {
				if (letters.containsKey(chara)) {
					int t = letters.get(chara);
					t += 1;
					letters.put(chara, t);
				} else
					letters.put(chara, 1);
			}
			for (Character key : letters.keySet()) {
				if (!twoletterflag) {
					if (letters.get(key) == 2) {
						twoLetters += 1;
						twoletterflag = true;
					}
				}
				if (!threeletterflag) {

					if (letters.get(key) == 3) {
						threeLetters += 1;
						threeletterflag = true;
					}
				}
			}
		}
		System.out.println("Day Two, Part One: " + (twoLetters * threeLetters));

		String twocharas = "";
		boolean found = false;
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < input.size(); j++) {
				if (!found) {
					if (i != j) {
						int count = 0;
						for (int k = 0; k < input.get(i).length(); k++) {
							if (input.get(i).charAt(k) != input.get(j).charAt(k))
								count++;
							if (count > 1)
								break;
							else if (count == 1 && k == input.get(i).length() - 1) {
								char[] one = input.get(i).toCharArray();
								char[] two = input.get(j).toCharArray();
								for (int o = 0; o < one.length; o++) {
									if (one[o] == two[o]) {
										twocharas += one[o] + "";
										found = true;
									}
								}

							}
						}
					}
				}
			}
		}
		System.out.println("Day Two, Part Two: " + twocharas);
	}
}