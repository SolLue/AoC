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
import java.util.TreeSet;

import utility.Property;

public class DaySeven {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day7.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Node> steps = new ArrayList<Node>();
		String temp = br.readLine();
		boolean first = true; 
		Node root = null;
		while(temp != null) {
			String[] t = temp.split("must be finished before step ");
			char c1 = t[0].charAt(5);
			char c2 = t[1].charAt(0);
			if (first) {
				root = new Node(c1);
				steps.add(root);
				first = false;
			}
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

			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		String output = root.chara + "";
/*
 * I HATE THIS
		Set<Character> availableSet = new TreeSet<Character>();
		availableSet.add(root.chara);

		int count = 0; 

		int length = 6;
		String output = root.chara + "";
		while(length != output.length()) {
			System.out.println(availableSet);
			for (Node node : steps) {
				Set<Character> req = node.requirement;
				boolean available = true; 
				for (Character chara : req) {
					if (!output.contains(chara + "")) {
						available = false; 
					}
					if (available) {
						availableSet.add(node.chara);
					}
				}
				for(Character c : availableSet) {
					if(!output.contains(c + "")) {
						output += c + "";
						break;
					}
				}
				count++;

				if (count == 6)
					break;



			}
			System.out.println(availableSet);
			System.out.println(output);
			break;
*/

			/*	for (Node node : steps) {
				Set<Character> req = node.requirement;
				for (Character chara : req) {
					if (output.contains(chara + "")) {
						availableSet.add(chara);
						System.out.println(availableSet);
					}
				}				
				if(availableSet.contains(node.chara)) {
					if (!output.contains(node.chara + "")) {
						output += node.chara;
					}
				}

			}*/
	//	}



		System.out.println();
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part One: " + output);
		System.out.println("Time in ms " + (stopTime - startTime));
		startTime = System.currentTimeMillis(); 







		stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static class Node implements Comparable<Character> {
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
		@Override
		public int compareTo(Character o) {
			return Character.valueOf(this.chara).compareTo(Character.valueOf(o));

		}
	}
}