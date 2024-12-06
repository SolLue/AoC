package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import utility.Property;

public class DayFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		List<Record> records = new ArrayList<Record>();
		for (int i = 0; i < input.size(); i++) {
			String[] arr = input.get(i).split("\\s+");
			temp = arr[0].replaceAll("-", " ");
			temp = temp.substring(6);
			int month = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
			int day = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
			int hours = Integer.parseInt(arr[1].substring(0, arr[1].indexOf(":")));
			int minute = Integer.parseInt(arr[1].substring(3, arr[1].length() - 1));
			String rest = "";
			int id = -1;
			if (arr.length == 6) {
				id = Integer.parseInt(arr[3].substring(1));
				rest = "begins shift";
			} else {
				rest = arr[2] + " " + arr[3];
			}
			Record r = new Record(month, day, hours, minute, id, rest);
			records.add(r);
		}

		records = records.stream()
				.sorted((o1, o2) -> o1.compareTo(o2))
				.toList();

		int currentId = 0;
		List<int[]> tempo = new ArrayList<int[]>();
		int[] sleeping = new int[2];
		Map<Integer, List<int[]>> guardsAsleep = new HashMap<Integer, List<int[]>>();
		for (Record record : records) {
			if (record.id != -1)
				currentId = record.id;
			if (record.message.contains("asleep")) 
				sleeping[0] = record.minute;
			if (record.message.contains("wakes")) {
				sleeping[1] = record.minute;
				tempo.add(sleeping);
			}
			if (!tempo.isEmpty() && record.id == -1) {
				if (!guardsAsleep.containsKey(currentId)) {
					guardsAsleep.put(currentId, tempo);
					tempo = new ArrayList<int[]>();
					sleeping = new int[2];
				} else {
					List<int[]> t = guardsAsleep.get(currentId);
					t.add(sleeping);
					tempo = new ArrayList<int[]>();
					sleeping = new int[2];
				}
			}
		}

		List<Guard> guards = new ArrayList<Guard>();
		for (Integer key : guardsAsleep.keySet()) {
			List<int[]> sleep = guardsAsleep.get(key);
			Guard g = new Guard(key);
			for (int[] i : sleep) {
				g.addAmount(i[1] - i[0]);
				g.addTimes(i[0], i[1]);
			}
			guards.add(g);
		}

		Guard highestSleeper = guards.stream()
				.max(Comparator.comparing(Guard::getAmount))
				.orElseThrow(NoSuchElementException::new);

		Map<Integer, Integer> amountMinutes = calculateAmountMinuted(highestSleeper);

		int[] commonMinute = findMostCommonMinute(amountMinutes); 

		System.out.println("Day Four, Part One: " + (commonMinute[0] * highestSleeper.id));



		Map<Integer, int[]> compileGuard = new HashMap<Integer, int[]>();
		for (Guard guard : guards) {
			amountMinutes = calculateAmountMinuted(guard);

			int[] min = findMostCommonMinute(amountMinutes);
			compileGuard.put(guard.id, min);
		}

		int max = 0;
		int guard = 0;
		int commonM = 0; 
		for (Integer guardId : compileGuard.keySet()) {
			if(compileGuard.get(guardId)[1] > max) {
				max = compileGuard.get(guardId)[1];
				guard = guardId;
				commonM = compileGuard.get(guardId)[0];
			}
		}
		
		System.out.println("Day Four, Part Two: " + (guard * commonM));
	}

	private static Map<Integer, Integer> calculateAmountMinuted(Guard guard) {
		Map<Integer, Integer> amountMinutes = new HashMap<Integer, Integer>();
		for (int i : guard.sleepytimes) {
			if(!amountMinutes.containsKey(i)) {
				amountMinutes.put(i, 1);
			} else {
				int m = amountMinutes.get(i); 
				m += 1;
				amountMinutes.put(i, m);	
			}
		}
		return amountMinutes;
	}

	static int[] findMostCommonMinute(Map<Integer, Integer> amountMinutes) {
		int max = 0;
		int commonMinute = 0;
		for (Integer minute : amountMinutes.keySet()) {
			if(amountMinutes.get(minute) > max) {
				max = amountMinutes.get(minute);
				commonMinute = minute;
			}
		}
		return new int[] {commonMinute, max}; 
	}

	static class Guard {
		int id; 
		List<Integer> sleepytimes; 
		int amount; 

		Guard(int i) {
			this.id = i; 
			this.sleepytimes = new ArrayList<Integer>();
		}
		Guard(int i, List<Integer> s) {
			this.id = i; 
			this.sleepytimes = s;
		}
		void addTimes(int start, int stop) {
			for (int i = start; i < stop; i++) {
				this.sleepytimes.add(i);
			}
		}
		void addAmount(int i) {
			this.amount += i;
		}
		int getAmount() {
			return this.amount;
		}
		public String toString() {
			return id + " " + amount + " " + sleepytimes;
		}
	}

	static class Record {
		int month;
		int day;
		int hour;
		int minute;
		int id;
		String message;

		Record(int mo, int d, int h, int m, int i, String mes) {
			this.month = mo;
			this.day = d;
			this.hour = h;
			this.minute = m;
			this.id = i;
			this.message = mes;
		}

		int compareTo(Record t) {
			int compare = Integer.compare(this.month, t.month);
			if (compare == 0) {
				compare = Integer.compare(this.day, t.day);
				if (compare == 0) {
					compare = Integer.compare(this.hour, t.hour);
					if (compare == 0)
						compare = Integer.compare(this.minute, t.minute);
				}
			}
			return compare;
		}

		public String toString() {
			return this.month + "/" + this.day + " " + this.hour + ":" + this.minute + "	 " + this.id + " "
					+ this.message;
		}
	}
}
