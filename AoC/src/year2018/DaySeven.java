package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import utility.Property;

public class DaySeven {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day7.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Node> steps = new ArrayList<Node>();
		String temp = br.readLine();

		while(temp != null) {
			String[] t = temp.split("must be finished before step ");
			char c1 = t[0].charAt(5);
			char c2 = t[1].charAt(0);
			boolean found = false;
			for (int i = 0; i < steps.size(); i++) {
				if(steps.get(i).chara == c2) {
					steps.get(i).addRequirement(c1);
					found = true; 
				}
			}
			if(!found) {
				Node n = new Node(c2);
				n.addRequirement(c1);
				steps.add(n);				
			}
			found = false; 
			for (int i = 0; i < steps.size(); i++) {
				if(steps.get(i).chara == c1) 
					found = true; 
			}
			if(!found)
				steps.add(new Node(c1));
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		List<Node> available = new ArrayList<Node>();
		for (Node n : steps) {
			if (n.requirement.isEmpty()) {
				available.add(n);
			}
		}
		Collections.sort(available, new NodeComparator());

		int size = steps.size();	
		String output = ""; 
		while(output.length() != size) {
			Node current = null;
			if(!available.isEmpty()) {
				current = available.remove(0);
				output += current.chara;
			} 
			for (Node n : steps) {
				boolean avail = true; 
				for (char c : n.requirement) {
					if(!output.contains(c + "")) {
						avail = false; 
					}
				}
				if (avail && !available.contains(n) && !output.contains(n.chara + "")) {
					available.add(n);
				}
			}	
			Collections.sort(available, new NodeComparator());
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part One: " + output); 
		System.out.println("Time in ms " + (stopTime - startTime));
		startTime = System.currentTimeMillis(); 







		stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node a, Node b) {
			return Character.valueOf(a.chara).compareTo(Character.valueOf(b.chara));
		}
	}

	static class Node {
		char chara;
		Set<Character> requirement; 

		Node(char c) {
			this.chara = c;
			this.requirement = new HashSet<Character>();
		}

		void addRequirement(Character n) {
			this.requirement.add(n);
		}
		public String toString() {
			return this.chara + " : " + this.requirement;
		}
		@Override
		public boolean equals(Object o) {
			if(this == o)
				return true;
			if(o == null)
				return false;
			if(!(o instanceof Node))
				return false;
			Node tmp = (Node) o;

			return this.chara == tmp.chara && this.requirement == tmp.requirement;
		}
		@Override
		public int hashCode() {
			return Objects.hash(this.chara, this.requirement);
		}
	}
}