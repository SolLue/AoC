package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import utility.Property;

public class DayTwentyFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day25.txt");
		BufferedReader br = new BufferedReader(fr);

		List<List<String>> in = new ArrayList<List<String>>();
		String temp = br.readLine();
		while(temp != null) {
			List<String> i = new ArrayList<String>();
			while(temp != null && !temp.isBlank()) {
				i.add(temp);			
				temp = br.readLine();
			}
			in.add(i);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<Key> keys = new ArrayList<Key>();
		List<Lock> locks = new ArrayList<Lock>();

		for (List<String> list : in) {
			if(list.get(0).contains("#")) {
				locks.add(extractLock(list));
			} else {
				keys.add(extractKey(list));
			}
		}
		
		Map<Lock, List<Key>> fit = new HashMap<Lock, List<Key>>();
		for (Lock lock : locks) {
			List<Key> possible = new ArrayList<Key>();
			for (Key key : keys) {
				boolean fits = true; 
				for (int i = 0; i < lock.heightsLock.length; i++) {
					if(lock.fittingKey[i] < key.heightsKey[i]) {
						fits = false;
					}
				}
				if (fits)
					possible.add(key);
			}
			if(!possible.isEmpty())
				fit.put(lock, possible);
		}
		
		int count = 0; 
		for (Lock lock : fit.keySet()) {
			count += fit.get(lock).size();		
		}


		long stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyFive, Part One: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 



		stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyFive, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static Lock extractLock(List<String> list) {
		int[] heights = new int[list.get(0).length()];
		for (int i = 0; i < list.get(0).length(); i++) {
			heights[i] = -1;
		}

		for(int i = 0; i < list.size(); i++) {
			char[] lock = list.get(i).toCharArray();
			for(int j = 0; j < lock.length; j++) {
				if (lock[j] == '.' && heights[j] == -1) {
					heights[j] = i - 1;
				}
			}	
		}
		return new Lock(heights, list.size());
	}

	static Key extractKey(List<String> list) {
		int[] heights = new int[list.get(0).length()];
		for (int i = 0; i < list.get(0).length(); i++) {
			heights[i] = -1;
		}

		for(int i = list.size() - 1; i >= 0; i--) {
			char[] lock = list.get(i).toCharArray();
			for(int j = 0; j < lock.length; j++) {
				if (lock[j] == '.' && heights[j] == -1) {
					heights[j] = list.size() - i - 2;
				}
			}	
		}
		return new Key(heights, list.size());
	}

	static class Lock {
		int[] heightsLock;
		int[] fittingKey;
		int maxheight;
		Lock(int[] heights, int m) {
			this.heightsLock = heights;
			this.maxheight = m;
			this.fittingKey = makeKey();
		}
		int[] makeKey() {
			int[] key = new int[this.heightsLock.length];
			for (int i = 0; i < this.heightsLock.length; i++) {
				key[i] = this.maxheight - this.heightsLock[i] - 2;
			}
			return key;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(fittingKey);
			result = prime * result + Arrays.hashCode(heightsLock);
			result = prime * result + Objects.hash(maxheight);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Lock other = (Lock) obj;
			return Arrays.equals(fittingKey, other.fittingKey) && Arrays.equals(heightsLock, other.heightsLock)
					&& maxheight == other.maxheight;
		}
	}
	static class Key {
		int[] heightsKey;
		int[] fittingLock;
		int maxheight; 
		Key(int[] heights, int m) {
			this.heightsKey = heights;
			this.maxheight = m;
			this.fittingLock = makeLock();
		}
		int[] makeLock() {
			int[] lock = new int[this.heightsKey.length];
			for (int i = 0; i < this.heightsKey.length; i++) {
				lock[i] = this.maxheight - this.heightsKey[i] - 2;
			}
			return lock;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(fittingLock);
			result = prime * result + Arrays.hashCode(heightsKey);
			result = prime * result + Objects.hash(maxheight);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			return Arrays.equals(fittingLock, other.fittingLock) && Arrays.equals(heightsKey, other.heightsKey)
					&& maxheight == other.maxheight;
		}
	}
}
