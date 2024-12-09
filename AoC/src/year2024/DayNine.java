package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import utility.Property;

public class DayNine {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day9.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		long startTime = System.currentTimeMillis(); 
		List<Block> fullDisk = new ArrayList<Block>(); 
		List<Block> actualDisk = new ArrayList<Block>(); 

		int idCounter = 0; 
		for (int i = 0; i < input.length(); i++) {
			if (i % 2 == 0) {
				int repeat = Integer.parseInt(input.charAt(i) + "");
				for (int j = 0; j < repeat; j++) {
					actualDisk.add(new Block(idCounter, 'f', repeat));
					fullDisk.add(new Block(idCounter, 'f', repeat));
				}
				idCounter++;
			} else {
				int repeat = Integer.parseInt(input.charAt(i) + "");
				for (int j = 0; j < repeat; j++) {
					actualDisk.add(new Block(-1, '.', repeat));
				}
			}
		}

		List<Block> output =  new ArrayList<Block>(); 
		int counter = fullDisk.size() - 1;
		for (int i = 0; i < fullDisk.size(); i++) {
			if (counter >= 0) {
				if (actualDisk.get(i).isFree()) {
					output.add(fullDisk.get(counter));
					counter--;
				} else {
					output.add(actualDisk.get(i));
				}
			}
		}

		long checksum = 0; 
		for (int i = 0; i < output.size(); i++) {
			int id = output.get(i).id;
			checksum = checksum + (id * i);
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Nine, Part One: " + checksum);
		System.out.println("Time in ms " + (stopTime - startTime));
		startTime = System.currentTimeMillis(); 

		List<Block> parts = fullDisk.stream()
				.distinct()
				.collect(Collectors.toList());

		output = new ArrayList<Block>();		

		for (Block block : actualDisk) {
			output.add(block);
		}

		int idMoved = Integer.MAX_VALUE;
		int backCounter = parts.size() - 1; 
		while(idMoved > 0) {
			int id = parts.get(backCounter).id;
			if (idMoved > id) {
				idMoved = id;
			}
			Block move = parts.get(backCounter);

			for (int i = 0; i < output.size(); i++) {
				if (output.get(i).isFree()) {
					int size = output.get(i).size;
					if(size >= move.size) {
						for (int j = 0; j < move.size; j++) {
							output.set(i, move);
							int k = 1;
							while (i + k < output.size() && output.get(i + k).isFree()) {
								output.get(i + k).decreaseSize();
								k++;
							}
							i++;
						}
						break;
					}
				}
			}

			int count = 0;
			for (int i = 0; i < output.size(); i++) {
				if (output.get(i).id == idMoved) 
					count++;
			}
			int sigh = output.size() - 1;
			while (count > move.size) {
				if (output.get(sigh).id == idMoved) {
					output.set(sigh, new Block(-1, '.', 1));
					count--;
				}
				sigh--;
			}
			parts.remove(backCounter);
			backCounter--;
		}

		checksum = 0; 
		for (int i = 0; i < output.size(); i++) {
			int id = output.get(i).id;
			if(id != -1)
				checksum = checksum + (id * i);
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Nine, Part Two: " + checksum);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static class Block {
		int id; 
		int size;
		char c;

		Block(int i, char c, int s) {
			this.id = i; 
			this.c = c;
			this.size = s;
		}
		void decreaseSize() {
			this.size--;
		}
		boolean isFree() {
			return this.c == '.';
		}
		public String toString() {
			if (this.id < 0)
				return this.c + " ";
			else
				return this.id + " ";
		}
		@Override
		public boolean equals(Object o) {
			if(this == o)
				return true;
			if(o == null)
				return false;
			if(!(o instanceof Block))
				return false;
			Block tmp = (Block) o;

			return this.id == tmp.id && this.size == tmp.size;
		}
		@Override
		public int hashCode() {
			return Objects.hash(this.id, this.size);
		}
	}
}