package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import utility.Property;

public class DayTwentyTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day22.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		Map<MonkeyBusiness, Integer> monkey = new HashMap<MonkeyBusiness, Integer>();

		long result = 0;
		for (String string : input) {
			long current = Integer.parseInt(string);
			LinkedList<Integer> changes = new LinkedList<Integer>();
			HashSet<MonkeyBusiness> seen = new HashSet<MonkeyBusiness>();
			int prevCost = (int) current % 10;
			for (int i = 0; i < 2000; i++) {
				long secret = current;
				current = secretNumber(secret);

				int cost = (int) current % 10; 
				int diff = prevCost - cost;
				changes.add(diff);

				if (changes.size() == 4) {
					MonkeyBusiness mb = new MonkeyBusiness(changes.get(0), changes.get(1),
							changes.get(2), changes.get(3));
					if(!seen.contains(mb)) {
						if(monkey.containsKey(mb)) {
							monkey.put(mb, monkey.get(mb) + cost);
						} else {	
							monkey.put(mb, cost);
						}
						seen.add(mb);
					}

					changes.removeFirst();
				}
				prevCost = cost;
			}
			result += current;	
		}

		long bananas = 0;


		for (MonkeyBusiness monkeyBusiness : monkey.keySet()) {
			if (monkey.get(monkeyBusiness) > bananas) {
				bananas = monkey.get(monkeyBusiness);
			}
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day 22, Part One: " + result);
		System.out.println("Day 22, Part Two: " + bananas);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static long secretNumber(long secret) {
		secret = secret ^ (secret * 64);
		secret = secret % 16777216;
		secret = secret ^ (secret / 32);
		secret = secret % 16777216;
		secret = secret ^ (secret * 2048);
		secret = secret % 16777216;

		return secret;
	}

	static class MonkeyBusiness {
		int p1; int p2; int p3; int p4;
		int cost;
		MonkeyBusiness (int p1, int p2, int p3, int p4) {
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
			this.p4 = p4;
			this.cost = 0; 
		}

		void setCost(int n) {
			System.out.println("BEFORE"+ cost);

			this.cost += n;
			System.out.println("COST AFTER "+ cost);
		}

		public String toString() {
			return this.p1 + " " + this.p2 + " " + this.p3 + " " + this.p4;

		}

		@Override
		public int hashCode() {
			return Objects.hash(p1, p2, p3, p4);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MonkeyBusiness other = (MonkeyBusiness) obj;
			return p1 == other.p1 && p2 == other.p2 && p3 == other.p3 && p4 == other.p4;
		}
	}
}
