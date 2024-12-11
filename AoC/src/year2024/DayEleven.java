package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayEleven {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day11.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Integer> input = new ArrayList<Integer>();
		String t = br.readLine();
		String[] split = t.split("\\s+");
		for (String string : split) {
			input.add(Integer.parseInt(string));
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		Map<Long, Long> stone = resetStone(input);
	
		int blink = 25;
		stone = stoneRules(stone, blink);

		
		long stoneCount = countAllStones(stone);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Eleven, Part One: " + stoneCount);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		blink = 75;

		stone = resetStone(input);
		stone = stoneRules(stone, blink);
		stoneCount = countAllStones(stone);
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Eleven, Part Two: " + stoneCount);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static Map<Long, Long> resetStone(List<Integer> input) {
		Map<Long, Long> stone = new HashMap<Long, Long>();
		for (int i : input) {
			stone.put((long) i , stone.getOrDefault(stone, (long) 0) + 1);
		}
		return stone;
	}
		
	static Map<Long, Long> stoneRules(Map<Long, Long> stone, int blink) {
		int count = 0; 
		Map<Long, Long[]> memory = new HashMap<Long, Long[]>();
		Map<Long, Long> copy = new HashMap<Long, Long>(); 
		while (count != blink) {
			copy.clear();
			for (Long i : stone.keySet()) {
				long amount = stone.get(i);
				if (i == 0) {
					copy.put((long) 1, copy.getOrDefault((long) 1, (long) 0) + amount); 
				} else if(memory.containsKey(i)) {
					for (Long schlong : memory.get(i)) {
						copy.put(schlong, copy.getOrDefault(schlong, (long) 0) + amount); 
					}
				} else if (((i) + "").length() % 2 == 0) {
					int length = (i + "").length() / 2;
					long value1 = Long.parseLong((i + "").substring(0, length));
					long value2 = Long.parseLong((i + "").substring(length));
					copy.put(value1, copy.getOrDefault(value1, (long) 0) + amount);
					copy.put(value2, copy.getOrDefault(value2, (long) 0) + amount);					
					Long[] arr = new Long[] {value1, value2};
					memory.put(i, arr);	
				} else {
					long multiplyRule = i * 2024;
					copy.put(multiplyRule, copy.getOrDefault(multiplyRule, (long) 0) + amount);
					memory.put(i, new Long[] {multiplyRule});	
				}
			}
			stone = Map.copyOf(copy);
			count++;
		}
		return stone;
	}

	static long countAllStones(Map<Long, Long> stones) {
		long stone = 0; 
		for (Long i : stones.keySet()) {
			stone += stones.get(i);
		}
		return stone; 
	}

/*
 * Part 1 before part 2 made it too expensive - should have seen that coming. 
 *  	static List<Long> stoneRules(List<Long> stone) {
		int i = 0;
		int currentLength = stone.size();
		while (i < currentLength) {
			currentLength = stone.size();
			if (stone.get(i) == 0) {
				stone.set(i, (long) 1); 
			} else if((stone.get(i) + "").length() % 2 == 0) {
				int length = (stone.get(i) + "").length() / 2;
				int value1 = Integer.parseInt((stone.get(i) + "").substring(0, length));
				int value2 = Integer.parseInt((stone.get(i) + "").substring(length));
				stone.set(i, (long) value1);
				stone.add(i + 1, (long) value2);
				i++;
				currentLength++;
			} else {
				stone.set(i, stone.get(i) * 2024);
			}
			i++;
		}
		return stone;
	}*/
}
