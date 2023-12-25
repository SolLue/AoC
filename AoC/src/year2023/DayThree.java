package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayThree {

	static int sum = 0;
	static int gear = 0;

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day3.txt");
		Scanner scan = new Scanner(fr);

		List<String> input = new ArrayList<String>();

		while (scan.hasNextLine()) {
			input.add(scan.nextLine());
		}

		boolean adjacent = false;

		for (int i = 0; i < input.size(); i++) {
			String nr = "";

			for (int j = 0; j < input.get(i).length(); j++) {

				if (Character.isDigit(input.get(i).charAt(j))) {
					nr += input.get(i).charAt(j);

					if (j != 0 && j != input.get(i).length() - 1 && !adjacent) {
						if ((input.get(i).charAt(j - 1) != '.' && !Character.isDigit(input.get(i).charAt(j - 1)))
								|| (input.get(i).charAt(j + 1) != '.'
										&& !Character.isDigit(input.get(i).charAt(j + 1)))) {
							adjacent = true;
						}
					}
					if (j == 0 && !adjacent) {
						if (input.get(i).charAt(j + 1) != '.' && !Character.isDigit(input.get(i).charAt(j + 1))) {
							adjacent = true;
						}
					}
					if (j == input.get(i).length() - 1 && !adjacent) {
						if (input.get(i).charAt(j - 1) != '.' && !Character.isDigit(input.get(i).charAt(j - 1))) {
							adjacent = true;
						}
					}

					if (!adjacent) {
						if (i != 0 && i != input.size() - 1) {
							if ((input.get(i - 1).charAt(j) != '.' && !Character.isDigit(input.get(i - 1).charAt(j)))
									|| (input.get(i + 1).charAt(j) != '.'
											&& !Character.isDigit(input.get(i + 1).charAt(j)))) {
								adjacent = true;
							}
						}
						if ((i != 0 && i != input.size() - 1) && (j != 0 && j != input.get(i).length() - 1)) {
							if ((input.get(i - 1).charAt(j - 1) != '.'
									&& !Character.isDigit(input.get(i - 1).charAt(j - 1)))
									|| (input.get(i - 1).charAt(j + 1) != '.'
											&& !Character.isDigit(input.get(i - 1).charAt(j)))
									|| (input.get(i + 1).charAt(j + 1) != '.'
											&& !Character.isDigit(input.get(i + 1).charAt(j + 1)))
									|| (input.get(i + 1).charAt(j - 1) != '.'
											&& !Character.isDigit(input.get(i + 1).charAt(j - 1))) && !adjacent) {
								adjacent = true;
							}
						}
						if (i == 0 && !adjacent && j != 0) {
							if ((input.get(i + 1).charAt(j + 1) != '.'
									&& !Character.isDigit(input.get(i + 1).charAt(j + 1)))
									|| (input.get(i + 1).charAt(j - 1) != '.'
											&& !Character.isDigit(input.get(i + 1).charAt(j - 1))) && !adjacent) {
								adjacent = true;
							}
						}
						if (i == input.size() - 1 && !adjacent && j != input.get(i).length() - 1) {
							if ((input.get(i - 1).charAt(j - 1) != '.'
									&& !Character.isDigit(input.get(i - 1).charAt(j - 1)))
									|| (input.get(i - 1).charAt(j + 1) != '.'
											&& !Character.isDigit(input.get(i - 1).charAt(j))) && !adjacent) {
								adjacent = true;
							}
						}

					}

				} else {
					if (!adjacent) {
						String t = input.get(i);
						t = t.replace(nr, ".".repeat(nr.length()));
						input.set(i, t);
					}
					if (adjacent) {
						sumUp(nr);
						adjacent = false;
					}
					nr = "";
				}
			}
			if (adjacent && !nr.equals("")) {
				sumUp(nr);
				adjacent = false;
				nr = "";
			}
		}

		for (int i = 0; i < input.size(); i++) {
			List<Integer> numbers = new ArrayList<Integer>();
			for (int j = 0; j < input.get(i).length(); j++) {
				if (input.get(i).charAt(j) == '*') {

					// left
					if (j != 0) {
						if (Character.isDigit(input.get(i).charAt(j - 1))) {
							int o = j - 1;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i).charAt(o - 1))) {
								o--;
							}
							while (o < input.get(i).length() && Character.isDigit(input.get(i).charAt(o))) {
								n += input.get(i).charAt(o);
								o++;
							}
							if (!n.equals("")) {
								numbers.add(Integer.parseInt(n));
							}
						}
					}
					// right
					if (j + 1 < input.get(i).length()) {
						if (Character.isDigit(input.get(i).charAt(j + 1))) {
							int o = j + 1;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i).charAt(o - 1))) {
								o--;
							}
							while (o < input.get(i).length() && Character.isDigit(input.get(i).charAt(o))) {
								n += input.get(i).charAt(o);
								o++;
							}
							if (!n.equals("")) {
								numbers.add(Integer.parseInt(n));
							}
						}
					} else {
						if (Character.isDigit(input.get(i).charAt(j))) {
							int o = j;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i).charAt(o - 1))) {
								o--;
							}
							while (o < input.get(i).length() && Character.isDigit(input.get(i).charAt(o))) {
								n += input.get(i).charAt(o);
								o++;
							}
							if (!n.equals("")) {
								numbers.add(Integer.parseInt(n));
							}
						}
					}

					boolean up = false;
					boolean down = false;

					if (i != 0) {
						// up for in between rows
						if (Character.isDigit(input.get(i - 1).charAt(j))) {
							int o = j;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i - 1).charAt(o - 1))) {
								o--;
							}

							while (o < input.get(i).length() && Character.isDigit(input.get(i - 1).charAt(o))) {
								n += input.get(i - 1).charAt(o);
								o++;
							}
							if (!n.equals("")) {
								up = true;
								numbers.add(Integer.parseInt(n));
							}
						}
						// left and up
						if (Character.isDigit(input.get(i - 1).charAt(j - 1))) {
							int o = j - 1;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i - 1).charAt(o - 1))) {
								o--;
							}

							while (o < input.get(i).length() && Character.isDigit(input.get(i - 1).charAt(o))) {
								n += input.get(i - 1).charAt(o);
								o++;
							}
							if (!n.equals("") && !up) {
								numbers.add(Integer.parseInt(n));
							}
						}
						// up and right
						if (Character.isDigit(input.get(i - 1).charAt(j + 1))) {
							int o = j + 1;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i - 1).charAt(o - 1))) {
								o--;
							}

							while (o < input.get(i - 1).length() && Character.isDigit(input.get(i - 1).charAt(o))) {
								n += input.get(i - 1).charAt(o);
								o++;

							}
							if (!n.equals("") && !up) {
								numbers.add(Integer.parseInt(n));
							}
						}
					}

					if (i != input.size() - 1) {
						// down for in between rows
						if (Character.isDigit(input.get(i + 1).charAt(j))) {
							int o = j;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i + 1).charAt(o - 1))) {
								o--;
							}

							while (o < input.get(i).length() && Character.isDigit(input.get(i + 1).charAt(o))) {
								n += input.get(i + 1).charAt(o);
								o++;
							}
							if (!n.equals("")) {
								down = true;
								numbers.add(Integer.parseInt(n));
							}
						}

						// down right
						if (Character.isDigit(input.get(i + 1).charAt(j + 1))) {
							int o = j + 1;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i + 1).charAt(o - 1))) {
								o--;
							}

							while (o < input.get(i).length() && Character.isDigit(input.get(i + 1).charAt(o))) {
								n += input.get(i + 1).charAt(o);
								o++;
							}
							if (!n.equals("") && !down) {
								numbers.add(Integer.parseInt(n));
							}

						}
						// down left
						if (Character.isDigit(input.get(i + 1).charAt(j - 1))) {
							int o = j - 1;
							String n = "";
							while (o >= 1 && Character.isDigit(input.get(i + 1).charAt(o - 1))) {
								o--;
							}

							while (o < input.get(i).length() && Character.isDigit(input.get(i + 1).charAt(o))) {
								n += input.get(i + 1).charAt(o);
								o++;
							}
							if (!n.equals("") && !down) {
								numbers.add(Integer.parseInt(n));
							}

						}
					}
					down = false;
					up = false;
				}

				if (numbers.size() == 2) {

					sumUp(numbers.get(0) * numbers.get(1));
					numbers.clear();

				} else {
					numbers.clear();
				}
			}

			if (numbers.size() == 2) {

				sumUp(numbers.get(0) * numbers.get(1));
				numbers.clear();

			} else {
				numbers.clear();
			}

		}

		System.out.println("Day three, part one: " + sum);
		System.out.println("Day three, part two: " + gear);

		scan.close();
	}

	public static void sumUp(String text) {
		int parsing = Integer.parseInt(text);
		sum += parsing;
	}

	public static void sumUp(int nr) {
		gear += nr;
	}
}
