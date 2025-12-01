package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayNineteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day19.txt");
		BufferedReader br = new BufferedReader(fr);

		int amountElves = Integer.parseInt(br.readLine());
		br.close();

		long startTime = System.currentTimeMillis(); 

		Elf current = null;
		for (int i = amountElves; i > 0; i--) {
			Elf e = new Elf(i, 1);
			e.next = current; 
			current = e; 
		}

		Elf start = null;
		while (current.next != null) {
			if (current.number == 1)
				start = current;
			current = current.next;
		}
		if (current.number == amountElves) 
			current.next = start; 

		current = start; 
		while (current.next != current) {
			if(current.next.amount > 0) {
				current.amount += current.next.amount;
				current.next.amount = 0; 
				current.next = current.next.next;
			} 
			current = current.next; 
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Eighteen, Part One: " + current.number);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		int currentElf = 1; 
		while (currentElf * 3 < amountElves) {
			currentElf *= 3; 
		}

		int winner = amountElves - currentElf; 
		if (amountElves > currentElf * 2) 
			winner += amountElves - currentElf * 2;

		stopTime = System.currentTimeMillis();
		System.out.println("Day Eighteen, Part Two: " + winner);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static class Elf {
		int amount; 
		int number;
		Elf next;

		Elf(int n, int a) {
			this.number = n;
			this.amount = a;
		}

		@Override
		public String toString() {
			return "Elf [gifts=" + amount + ", elf=" + number + "]";
		}
	}
}
