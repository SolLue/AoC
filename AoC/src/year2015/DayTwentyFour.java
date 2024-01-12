package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import utility.Property;

public class DayTwentyFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day24.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Package> packages = new ArrayList<>();
		List<Integer> input = new ArrayList<>();

		String t = br.readLine();

		while(t != null) {
			input.add(Integer.parseInt(t));
			Package pa = new Package(Integer.parseInt(t));
			packages.add(pa);
			t = br.readLine();
		}
		br.close();

		int max = input.stream().mapToInt(Integer::intValue).sum() / 3;

		List<List<Package>> sigh = new ArrayList<List<Package>>();
		for(int i = packages.size() - 1; i >= packages.size() - 6; i--) {
			sigh.addAll(getAllPaths(packages.get(i), max, packages, 6));
		}

		Set<List<Integer>> group1 = new HashSet<List<Integer>>();

		for (List<Package> list : sigh) {
			List<Integer> temp = new ArrayList<Integer>();
			for (Package b : list) {
				temp.add(b.weight);
			}
			Collections.sort(temp);
			group1.add(temp);
		}
		
		long quantum = Long.MAX_VALUE;
		for(List<Integer> list : group1) {
			long temp = 1; 
			for (int s : list) {
				temp *= s; 
			}
			if(temp < quantum) {
				quantum = temp;
			}
		}

		System.out.println("Day TwentyFour, Part One: " + quantum);

		max = input.stream().mapToInt(Integer::intValue).sum() / 4;
		sigh = new ArrayList<List<Package>>();
		for(int i = packages.size() - 1; i >= packages.size() - 6; i--) {
			sigh.addAll(getAllPaths(packages.get(i), max, packages, 4));
		}

		group1 = new HashSet<List<Integer>>();

		for (List<Package> list : sigh) {
			List<Integer> temp = new ArrayList<Integer>();
			for (Package b : list) {
				temp.add(b.weight);
			}
			Collections.sort(temp);
			group1.add(temp);
		}
		
		quantum = Long.MAX_VALUE;
		for(List<Integer> list : group1) {
			long temp = 1; 
			for (int s : list) {
				temp *= s; 
			}
			if(temp < quantum) {
				quantum = temp;
			}
		}

		System.out.println("Day TwentyFour, Part Two: " + quantum);
	}

	static List<List<Package>> getAllPaths(Package begin, int max, List<Package> input, int shortest) { 
		List<List<Package>> paths = new ArrayList<List<Package>>();
		recursiveGetPaths(begin, paths, new LinkedList<Package>(), max, input, shortest); 
		return paths;
	}

	static void recursiveGetPaths(Package current, List<List<Package>> paths, LinkedList<Package> path, int max,
			List<Package> input, int shortest) {
		path.add(current);

		if(path.size() <= shortest) {
			if(path.stream().map(e -> e.weight).reduce(0, Integer::sum) == max) {
				paths.add(new ArrayList<Package>(path));
				if(path.size() < shortest) 
					shortest = path.size();
				path.remove(current);
				return; 
			}

			for (int i = 0; i < input.size(); i++) {
				if (!path.contains(input.get(i)) && path.stream().map(e -> e.weight).reduce(0, Integer::sum) + 
						input.get(i).weight <= max) {
					recursiveGetPaths(input.get(i), paths, path, max, input, shortest);
				}
			}
		}
		path.remove(current);
	}
	
	record Package(int weight) {};
}

