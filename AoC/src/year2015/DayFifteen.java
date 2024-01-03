package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayFifteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day15.txt");
		BufferedReader br = new BufferedReader(fr);

		String temp = br.readLine();
		List<String> input = new ArrayList<String>();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		Map<String, Ingredients> ing = new HashMap<>();
		for (String string : input) {
			String[] options = string.split(" ");
			String name = options[0].substring(0, options[0].length() - 1);
			Ingredients i = new Ingredients(Integer.parseInt(options[2].trim().substring(0, options[2].length() - 1)),
					Integer.parseInt(options[4].trim().substring(0, options[4].length() - 1)),
					Integer.parseInt(options[6].trim().substring(0, options[6].length() - 1)),
					Integer.parseInt(options[8].trim().substring(0, options[8].length() - 1)),
					Integer.parseInt(options[10].trim()));
			ing.put(name, i);
		}

		int maxScore = findBestCookie(ing);

		System.out.println("Day Fifteen, Part One: " + maxScore);

		maxScore = findBestCookieCalorie(ing);
		System.out.println("Day Fifteen, Part Two: " + maxScore);
	}

	static int findBestCookie(Map<String, Ingredients> ing) {
		int max = 0;
		
		for (int i = 0; i <= 100; i++) {
			for (int j = 0; j <= 100; j++) {
				for (int k = 0; k <= 100; k++) {
					for (int l = 0; l <= 100; l++) {
						if(i + j + k + l == 100) {
							int capacity = (ing.get("Sprinkles").capacity * i) + (ing.get("Butterscotch").capacity * j) + (ing.get("Chocolate").capacity * k) + (ing.get("Candy").capacity * l);
							int durability = (ing.get("Sprinkles").durability * i) + (ing.get("Butterscotch").durability* j) + (ing.get("Chocolate").durability * k) + (ing.get("Candy").durability * l);
							int flavour =  (ing.get("Sprinkles").flavour * i) + (ing.get("Butterscotch").flavour * j) + (ing.get("Chocolate").flavour * k) + (ing.get("Candy").flavour * l);
							int texture =  (ing.get("Sprinkles").texture * i) + (ing.get("Butterscotch").texture * j) + (ing.get("Chocolate").texture * k) + (ing.get("Candy").texture * l);
							int addTogether;
							if(capacity > 0 && durability > 0 && flavour > 0 && texture > 0) {
								addTogether = capacity * durability * flavour * texture;
							}
							else {
								addTogether = 0;
							}
							if(addTogether > max) { 
								max = addTogether;
							}
						}
						else if(i + j + k + l > 100) {
							break;
						}
					}
				}
			}
		}

		return max;
	}
	
	static int findBestCookieCalorie(Map<String, Ingredients> ing) {
		int max = 0;
		
		for (int i = 0; i <= 100; i++) {
			for (int j = 0; j <= 100; j++) {
				for (int k = 0; k <= 100; k++) {
					for (int l = 0; l <= 100; l++) {
						if(i + j + k + l == 100) {
							int capacity = (ing.get("Sprinkles").capacity * i) + (ing.get("Butterscotch").capacity * j) + (ing.get("Chocolate").capacity * k) + (ing.get("Candy").capacity * l);
							int durability = (ing.get("Sprinkles").durability * i) + (ing.get("Butterscotch").durability* j) + (ing.get("Chocolate").durability * k) + (ing.get("Candy").durability * l);
							int flavour =  (ing.get("Sprinkles").flavour * i) + (ing.get("Butterscotch").flavour * j) + (ing.get("Chocolate").flavour * k) + (ing.get("Candy").flavour * l);
							int texture =  (ing.get("Sprinkles").texture * i) + (ing.get("Butterscotch").texture * j) + (ing.get("Chocolate").texture * k) + (ing.get("Candy").texture * l);
							int calories =  (ing.get("Sprinkles").calories * i) + (ing.get("Butterscotch").calories * j) + (ing.get("Chocolate").calories * k) + (ing.get("Candy").calories * l);
							int addTogether;
							if(capacity > 0 && durability > 0 && flavour > 0 && texture > 0) {
								addTogether = capacity * durability * flavour * texture;
							}
							else {
								addTogether = 0;
							}
							if(addTogether > max && calories == 500) {
								max = addTogether;
							}
						}
						else if(i + j + k + l > 100) {
							break;
						}
					}
				}
			}
		}

		return max;
	}

	static class Ingredients {
		int capacity;
		int durability;
		int flavour;
		int texture;
		int calories;

		public Ingredients(int c, int d, int f, int t, int ca) {
			this.capacity = c;
			this.durability = d;
			this.flavour = f;
			this.texture = t;
			this.calories = ca;
		}

		@Override
		public String toString() {
			return this.capacity + " " + this.durability + " " + this.flavour + " " + this.texture + " "
					+ this.calories;
		}
	}
}
