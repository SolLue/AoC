package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayFifteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day15.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Disk> disks = new ArrayList<Disk>();
		String temp = br.readLine();
		while(temp != null) {
			String[] arr = temp.split(" ");
			Disk d = new Disk(Integer.parseInt(arr[1].substring(1)), Integer.parseInt(arr[3]), 
					Integer.parseInt(arr[6].substring(5, arr[6].length() - 1)), 
					Integer.parseInt(arr[11].substring(0, arr[11].length() - 1)));
			disks.add(d);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		int buttonPressTime = 0; 
		boolean success = false; 
		while (!success) {
			boolean round = true;
			for (int i = 0; i < disks.size(); i++) {
				Disk disk = disks.get(i); 
				int position = (disk.currentPosition + (i + buttonPressTime + 1)) % disk.amount;
				if (position != 0) {
					round = false;
					break;
				}
			}
			if(round) {
				success = true;
				break;
			}
			buttonPressTime++;
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Fifteen, Part One: " + buttonPressTime);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		disks.add(new Disk(disks.size(), 11, 0, 0));
		buttonPressTime = 0; 
		success = false; 
		while (!success) {
			boolean round = true;
			for (int i = 0; i < disks.size(); i++) {
				Disk disk = disks.get(i); 
				int position = (disk.currentPosition + (i + buttonPressTime + 1)) % disk.amount;
				if (position != 0) {
					round = false;
					break;
				}
			}
			if(round) {
				success = true;
				break;
			}
			buttonPressTime++;
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Fifteen, Part Two: " + buttonPressTime);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static class Disk {
		int number; 
		int amount; 
		int currentTime; 
		int currentPosition;
		Disk(int n, int a, int c, int cp) {
			this.number = n; 
			this.amount = a; 
			this.currentTime = c; 
			this.currentPosition = cp; 
		}
		@Override
		public String toString() {
			return "Disk [number=" + number + ", amount=" + amount + ", currentTime=" + currentTime
					+ ", currentPosition=" + currentPosition + "]";
		}
	}
}
