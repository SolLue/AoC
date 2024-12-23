package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import utility.Property;

public class DayTwentyThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day23.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String[]> input = new ArrayList<String[]>();
		String temp = br.readLine();
		while(temp != null) {
			String[] c = temp.split("-");
			input.add(c);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<String[]> input2 = new ArrayList<String[]>(input);
		List<Lan> lan = getTripples(input2);	
		List<Lan> lanT = new ArrayList<Lan>();
		for (Lan lan2 : lan) {
			if(lan2.a.startsWith("t") || lan2.b.startsWith("t") || lan2.c.startsWith("t"))
				lanT.add(lan2);
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyThree, Part One: " + lanT.size());
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		Map<String, Set<String>> network = new HashMap<String, Set<String>>();
		for (String[] s : input) {
			network.computeIfAbsent(s[0], i -> new HashSet<>()).add(s[1]);
			network.computeIfAbsent(s[1], i -> new HashSet<>()).add(s[0]);
		}
				
		Set<String> largest = findLanParties(network).stream().max(Comparator.comparingInt(Set::size))
				.orElseThrow();
		String blarg = largest.stream().sorted().collect(Collectors.joining(","));

		stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyThree, Part Two: " + blarg);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static List<Lan> getTripples(List<String[]> input) {
		List<Lan> lan = new ArrayList<Lan>();
		while (!input.isEmpty()) {
			String arr[] = input.remove(0);
			List<List<String[]>> tem = getAllWithConnections(arr[0], arr[1], input);
			List<String[]> conn1 = tem.get(0);
			List<String[]> conn2 = tem.get(1);
			for (String[] str1 : conn1) {
				for (String[] str2 : conn2) {
					Set<String> test = new HashSet<String>(); 
					test.add(arr[0]);
					test.add(arr[1]);
					test.add(str1[0]);
					test.add(str1[1]);
					test.add(str2[0]);
					test.add(str2[1]);
					if (test.size() == 3) {
						List<String> s = new ArrayList<String>(test);
						Lan lala = new Lan(s.get(0), s.get(1), s.get(2));
						lan.add(lala);
					}
				}
			}
		}
		return lan;
	}

	static Set<Set<String>> findLanParties(Map<String, Set<String>> in) {
		Set<Set<String>> groups = new HashSet<>();
		for (String node : in.keySet()) {
			Set<String> checked = new HashSet<>();
			Set<String> passed = new HashSet<>();
			Queue<String> queue = new ArrayDeque<String>();
			queue.add(node);
			while (!queue.isEmpty()) {
				String n = queue.poll();
				if (!checked.contains(n) && in.get(n).containsAll(passed)) {
					passed.add(n);
					checked.add(n);
					for (String next : in.get(n)) {
						if (!checked.contains(next)) {
							queue.add(next);
						}
					}
				}
			}
			groups.add(passed);
		}
		return groups;
	}

	static List<List<String[]>> getAllWithConnections(String a, String b, List<String[]> input) {
		List<List<String[]>> arrrr = new ArrayList<List<String[]>>();
		List<String[]> arr1 = new ArrayList<String[]>();
		List<String[]> arr2 = new ArrayList<String[]>();

		for (String[] strings : input) {
			if(strings[0].equals(a) || strings[1].equals(a)) {
				arr1.add(strings);
			}
			if (strings[1].equals(b) || strings[0].equals(b)) {
				arr2.add(strings);
			}
		}	
		arrrr.add(arr1);
		arrrr.add(arr2);
		return arrrr;
	}

	static class Lan {
		String a; 
		String b; 
		String c;
		Lan(String i, String j, String k) {
			this.a = i; 
			this.b = j; 
			this.c = k;
		}
		Lan(String i, String j) {
			this.a = i; 
			this.b = j; 
			this.c = "";
		}
	}
}
