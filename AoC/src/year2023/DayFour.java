package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DayFour {

	static int sum = 0;

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day4.txt");
		Scanner scan = new Scanner(fr);
		Map<Integer, Integer> lookUp = new HashMap<Integer, Integer>();
		int amountOfCards = 0;
		
		List<Integer> currentCards = new ArrayList<Integer>();
		List<Integer> wonCards = new ArrayList<Integer>();
		
		while (scan.hasNextLine()) {
			String original = scan.nextLine();
			String temp = original;
			String id = temp.substring(temp.indexOf(" "), temp.indexOf(":"));

			temp = temp.substring(temp.indexOf(":") + 2);

			String[] t = temp.split("\\|");
			String[] winningS = t[0].split("\\s+");
			String[] actualnumbersS = t[1].split("\\s+");

			List<String> won = new ArrayList<String>();

			for (int i = 0; i < winningS.length; i++) {
				winningS[i] = winningS[i].trim();
				actualnumbersS[i] = actualnumbersS[i].trim();
			}

			for (int i = 0; i < winningS.length; i++) {
				for (int j = 0; j < actualnumbersS.length; j++) {
					if (!winningS[i].equals("")) {
						if (winningS[i].equals(actualnumbersS[j])) {
							won.add(actualnumbersS[j]);
						}
					}
				}
			}

			lookUp.put(Integer.parseInt(id.trim()), won.size());

			amountOfCards++;

			int s = 1;
			for (int i = 1; i < won.size(); i++) {
				s *= 2;
			}

			if (won.size() > 0) {
				sumUp(s);	
				currentCards.add(Integer.parseInt(id.trim()));
			}
		}
		
		
		boolean cardsleft = true;
		while(cardsleft) {
			for(int i = 0; i < currentCards.size(); i++) {	
				int amount = lookUp.get(currentCards.get(i));

				for(int j = 0; j < amount; j++) {				
					wonCards.add(currentCards.get(i) + j + 1);
					amountOfCards++;
				}
			}

			currentCards.clear();
			currentCards.addAll(wonCards);
			wonCards.clear();
			
			if(currentCards.size() == 0) {
				cardsleft = false;
			}
		}
		

		System.out.println("Day four, part one: " + sum);
		System.out.println("Day four, part two: " + amountOfCards);

		scan.close();
	}

	public static void sumUp(int power) {
		sum += power;
	}
}
