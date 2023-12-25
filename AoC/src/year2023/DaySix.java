package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DaySix {

	static int product;

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day6.txt");
		Scanner scan = new Scanner(fr);

		List<String> time = new ArrayList<String>();
		List<String> distance = new ArrayList<String>();

		while (scan.hasNextLine()) {
			String temp = scan.nextLine();
			if (temp.startsWith("Time:")) {
				temp = temp.substring(temp.indexOf(":") + 1).trim();
				String[] arr = temp.split("\\s+");
				for (String string : arr) {
					string = string.trim();
					time.add(string);
				}
			}
			if (temp.startsWith("Distance:")) {
				temp = temp.substring(temp.indexOf(":") + 1).trim();
				String[] arr = temp.split("\\s+");
				for (String string : arr) {
					string = string.trim();
					distance.add(string);
				}
			}
		}
		scan.close();

		product = 1;

		int success = 0;
		// for each race
		for (int i = 0; i < time.size(); i++) {
			int pressButton = 0;
			int distanceTravelled = 0;
			// possible variations: 1 to (totalTime - 1)

			int totalTime = Integer.parseInt(time.get(i));
			for (int j = 1; j < totalTime; j++) {
				pressButton = j;
				distanceTravelled = pressButton * (totalTime - pressButton);

				if (distanceTravelled > Integer.parseInt(distance.get(i))) {
					success++;
				}
			}
			if (success != 0)
				multiplyUp(success);
			success = 0;

		}
		System.out.println("Day Six, Part 1: " + product);


		long successl = (long) 0;
		String totalTimeTogether = "";
		String totalDistanceTogether = "";

		for (int i = 0; i < time.size(); i++) {
			totalTimeTogether += time.get(i);
			totalDistanceTogether += distance.get(i);
		}

		long totalTime = Long.parseLong(totalTimeTogether);
		long totalDistance = Long.parseLong(totalDistanceTogether);
				
		for (long i = 0; i < totalTime; i++) {
			long pressButton = i + 1;
			long distanceTravelled = 0;

			distanceTravelled = pressButton * (totalTime - pressButton);

			if (distanceTravelled > totalDistance) {
				successl++;
			}
		}

		System.out.println("Day Six, Part 2: " + successl);

	}

	public static void multiplyUp(int power) {
		product *= power;
	}

}
