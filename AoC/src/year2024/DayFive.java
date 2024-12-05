package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		Map<Integer, List<Integer>> rules = new HashMap<Integer, List<Integer>>();
		List<List<Integer>> updates = new ArrayList<List<Integer>>();
		for (String string : input) {
			if(string.contains("|")) {
				String[] arr = string.split("\\|");
				if(rules.containsKey(Integer.parseInt(arr[0]))) {
					List<Integer> tempo = rules.get(Integer.parseInt(arr[0]));
					tempo.add(Integer.parseInt(arr[1]));
					rules.put(Integer.parseInt(arr[0]), rules.get(Integer.parseInt(arr[0])));
				} else {
					List<Integer> tempo = new ArrayList<Integer>();
					tempo.add(Integer.parseInt(arr[1]));
					rules.put(Integer.parseInt(arr[0]), tempo);
				}
			} else if (!string.isBlank()) {
				String[] arr = string.split(",");
				List<Integer> t = new ArrayList<Integer>();
				for (String s : arr) {
					t.add(Integer.parseInt(s));
				}
				updates.add(t);
			}
		}

		List<List<Integer>> correctOrder = new ArrayList<List<Integer>>();
		List<List<Integer>> inCorrectUpdates = new ArrayList<List<Integer>>();
		for (List<Integer> list : updates) {
			boolean order = checkIfOrdered(list, rules);	
			if(order) 
				correctOrder.add(list);
			else if(!order)
				inCorrectUpdates.add(list);	
		}

		int middle = addMiddle(correctOrder);

		System.out.println("Day Five, Part One: " + middle);

		List<List<Integer>> inCorrectUpdatesOrdered = new ArrayList<List<Integer>>();

		for (List<Integer> list : inCorrectUpdates) {
			List<Integer> ordered = orderList(list, rules);
			boolean order = checkIfOrdered(ordered, rules); 
			if(order) 
				inCorrectUpdatesOrdered.add(ordered);
			else 
				System.out.println("something went wrong " + list);
		}

		middle = addMiddle(inCorrectUpdatesOrdered);
		System.out.println("Day Five, Part Two: " + middle);
	}

	static boolean checkIfOrdered(List<Integer> list, Map<Integer, List<Integer>> rules) {
		boolean order = true; 
		for (int i = list.size() - 1; i >= 0; i--) {
			int current = list.get(i);
			List<Integer> currentRule = rules.get(current);
			for (int j = 0; j <= i; j++) {
				if(j != i) {
					if(currentRule != null) {
						if(currentRule.contains(list.get(j))) {
							order = false; 
							break;
						}
					}
				}
			}
		}
		return order; 
	}

	static List<Integer> orderList(List<Integer> list, Map<Integer, List<Integer>> rules) {
		boolean order = false;
		while (!order) {
			for (int i = list.size() - 1; i >= 0; i--) {
				int current = list.get(i);
				List<Integer> currentRule = rules.get(current);
				for (int j = 0; j <= i; j++) {
					if(j != i) {
						if(currentRule != null) {
							if(currentRule.contains(list.get(j))) {
								int temp = list.get(j);
								list.set(i, temp);
								list.set(j, current);
								break;
							}
						}
					}
				}
			}
			order = checkIfOrdered(list, rules);
		}
		return list; 
	}

	static int addMiddle(List<List<Integer>> list) {
		int middle = 0; 
		for (List<Integer> innerlist : list) {
			middle += innerlist.get((int)Math.floor(innerlist.size() / 2));
		}
		return middle;
	}
}
