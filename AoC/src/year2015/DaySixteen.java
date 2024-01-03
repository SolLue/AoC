package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DaySixteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day16.txt");
		BufferedReader br = new BufferedReader(fr);

		String t = br.readLine();
		List<String> input = new ArrayList<String>();
		while (t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		Sue sue = new Sue();
		sue.nr = 0;
		sue.children = 3;
		sue.cats = 7;
		sue.samoyeds = 2;
		sue.pomeranians = 3;
		sue.akitas = 0;
		sue.vizslas = 0;
		sue.goldfish = 5;
		sue.trees = 3;
		sue.cars = 2;
		sue.perfumes = 1;

		List<Sue> allSues = createSues(input);
		int number = getCorrectSue(sue, allSues);

		System.out.println("Day Sixteen, Part One: " + number);
		
		number = getCorrectSueRange(sue, allSues);
		System.out.println("Day Sixteen, Part Two: " + number);
	}

	static int getCorrectSue(Sue searchedFor, List<Sue> allSues) {
		boolean child = false; 	boolean car = false; 
		boolean cat = false; boolean sam = false; 
		boolean pom = false; boolean aki = false; 
		boolean viz = false; boolean gold = false; 
		boolean trees = false; boolean per = false; 
		for (Sue s : allSues) {
			if (searchedFor.children == s.children || s.children == -1) 
				child = true; 
			if (searchedFor.cats == s.cats || s.cats == -1) 
				cat = true;
			if (searchedFor.samoyeds == s.samoyeds || s.samoyeds == -1) 
				sam = true;
			if (searchedFor.pomeranians == s.pomeranians || s.pomeranians == -1) 
				pom = true;
			if (searchedFor.akitas == s.akitas || s.akitas == -1) 
				aki = true;
			if (searchedFor.vizslas == s.vizslas || s.vizslas == -1) 
				viz = true;
			if (searchedFor.goldfish == s.goldfish || s.goldfish == -1) 
				gold = true;
			if (searchedFor.trees == s.trees || s.trees == -1) 
				trees = true;
			if (searchedFor.cars == s.cars || s.cars == -1) 
				car = true;
			if (searchedFor.perfumes == s.perfumes || s.perfumes == -1)
				per = true;

			if(child && cat && car && sam && pom && aki && viz && gold && trees && per)
				return s.nr;
			else {
				child = false; car = false; 
				cat = false; sam = false; 
				pom = false; aki = false; 
				viz = false; gold = false; 
				trees = false; per = false; 
			}
		}
		return -1;
	}

	static int getCorrectSueRange(Sue searchedFor, List<Sue> allSues) {
		boolean child = false; 	boolean car = false; 
		boolean cat = false; boolean sam = false; 
		boolean pom = false; boolean aki = false; 
		boolean viz = false; boolean gold = false; 
		boolean trees = false; boolean per = false; 
		for (Sue s : allSues) {
			if (searchedFor.children == s.children || s.children == -1) 
				child = true; 
			if (searchedFor.cats < s.cats || s.cats == -1) 
				cat = true;
			if (searchedFor.samoyeds == s.samoyeds || s.samoyeds == -1) 
				sam = true;
			if (searchedFor.pomeranians > s.pomeranians || s.pomeranians == -1) 
				pom = true;
			if (searchedFor.akitas == s.akitas || s.akitas == -1) 
				aki = true;
			if (searchedFor.vizslas == s.vizslas || s.vizslas == -1) 
				viz = true;
			if (searchedFor.goldfish > s.goldfish || s.goldfish == -1) 
				gold = true;
			if (searchedFor.trees < s.trees || s.trees == -1) 
				trees = true;
			if (searchedFor.cars == s.cars || s.cars == -1) 
				car = true;
			if (searchedFor.perfumes == s.perfumes || s.perfumes == -1)
				per = true;

			if(child && cat && car && sam && pom && aki && viz && gold && trees && per)
				return s.nr;
			else {
				child = false; car = false; 
				cat = false; sam = false; 
				pom = false; aki = false; 
				viz = false; gold = false; 
				trees = false; per = false; 
			}
		}
		return -1;
	}
	
	static List<Sue> createSues(List<String> input) {
		List<Sue> allSues = new ArrayList<>();
		for (String string : input) {
			String[] options = string.split(" ");
			Sue s = new Sue();
			s.nr = Integer.parseInt(options[1].substring(0, options[1].indexOf(":")));

			for (int i = 2; i < options.length - 1; i++) {
				if (options[i].equals("children:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.children = Integer.parseInt(temp);

				} else if (options[i].equals("cats:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.cats = Integer.parseInt(temp);
				} else if (options[i].equals("samoyeds:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.samoyeds = Integer.parseInt(temp);
				} else if (options[i].equals("pomeranians:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.pomeranians = Integer.parseInt(temp);
				} else if (options[i].equals("akitas:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.akitas = Integer.parseInt(temp);
				} else if (options[i].equals("vizslas:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.vizslas = Integer.parseInt(temp);
				} else if (options[i].equals("goldfish:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.goldfish = Integer.parseInt(temp);
				} else if (options[i].equals("trees:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.trees = Integer.parseInt(temp);
				} else if (options[i].equals("cars:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.cars = Integer.parseInt(temp);
				} else if (options[i].equals("perfumes:")) {
					String temp = options[i + 1];
					temp = temp.replace(",", "");
					s.perfumes = Integer.parseInt(temp);
				}
			}
			allSues.add(s);
		}
		return allSues;
	}

	static class Sue {
		int nr;
		int children = -1;
		int cats = -1;
		int samoyeds = -1;
		int pomeranians = -1;
		int akitas = -1;
		int vizslas = -1;
		int goldfish = -1;
		int trees = -1;
		int cars = -1;
		int perfumes = -1;

		Sue() {
		}

		public String toString() {
			return "nr " + nr + " children " + children + " cats " + cats + " samoyeds " + samoyeds + " pomeranians "
					+ pomeranians + " akitas " + akitas + " vizslas " + vizslas + " goldfish " + goldfish + " trees "
					+ trees + " cars " + cars + " perfumes " + perfumes;
		}
	}
}
