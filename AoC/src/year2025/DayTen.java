package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import utility.Property;

public class DayTen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day10.txt");
		BufferedReader br = new BufferedReader(fr);

		List<Machine> machines = new ArrayList<Machine>();
		String temp = br.readLine();
		while(temp != null) {
			String arr[] = temp.split("\\s+");
			String lights = null;
			List<List<Integer>> butts = new ArrayList<List<Integer>>();
			List<Integer> jol = new ArrayList<Integer>(); 
			for (String s : arr) {
				List<Integer> buttons = new ArrayList<Integer>();
				if (s.contains("(")) {
					s = s.substring(1, s.length() - 1);
					String[] sigh = s.split(",");
					for (String si : sigh) {
						buttons.add(Integer.parseInt(si));						
					}
					butts.add(buttons);
				}  else if (s.contains("{")) {
					s = s.substring(1, s.length() - 1);
					String[] sigh = s.split(",");
					for (String si : sigh) {
						jol.add(Integer.parseInt(si));						
					}
				} else if (s.contains("["))
					lights = arr[0].substring(1, arr[0].length() - 1);
			}
			machines.add(new Machine(lights, butts, jol));
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
	
		int fewestButtons = 0; 

		for (Machine machine : machines) {
			String start = ".".repeat(machine.lightDiagram.length());
			
			int clicks = bfs(machine, start);
			fewestButtons += clicks; 		
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Ten, Part One: " + fewestButtons);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		fewestButtons = 0; 
		for (Machine machine : machines) {
			int clicks = DayTenPart2.linearShit(machine.buttonDiagram, machine.joltageRequirements);
			fewestButtons += clicks; 		
		}
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Ten, Part Two: " + fewestButtons);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	
	static int bfs(Machine machine, String start) {
	    Queue<Node> queue = new PriorityQueue<>();
	    Node startNode = new Node(start, 0);
	    queue.add(startNode);
	    Set<String> visited = new HashSet<>();
	    visited.add(startNode.value);
	    
	    Node currentNode;

	    while (!queue.isEmpty()) {
	        currentNode = queue.poll();

	        visited.add(currentNode.value);

	        if (currentNode.value.equals(machine.lightDiagram))
	        	return currentNode.cost;
	        
	        queue.addAll(getNeighbours(machine, currentNode, visited));
	    }
	    return -123123;
	}
	
	static List<Node> getNeighbours(Machine m, Node current, Set<String> visited) {
		List<List<Integer>> buttonDiagram = m.buttonDiagram;
		List<Node> possible = new ArrayList<Node>();
		
		int newCost = current.cost + 1;
		
		for (List<Integer> intList : buttonDiagram) {
			String te = current.value;
			StringBuilder sb = new StringBuilder(te);
			for (Integer integer : intList) {
				if (te.charAt(integer) == '#')
					sb.setCharAt(integer, '.');
				else if (te.charAt(integer) == '.')
					sb.setCharAt(integer, '#');					 
			}
			
			if (!visited.contains(sb.toString())) {
				Node newNode = new Node(sb.toString(), newCost);
				possible.add(newNode);
			}
		}
		return possible;
	}
	    
	static class Node implements Comparable<Node> {
		String value; 
		int cost; 
		
		Node(String s, int c) {
			this.value = s;
			this.cost = c;
		}
		
		@Override
		public String toString() {
			return this.value + " " + this.cost;
		}

		@Override
		public int compareTo(Node n) {
			return Integer.compare(this.cost, n.cost);
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof Node) {
				Node r = (Node) o;
				return this.value.equals(r.value);
			}
			return false;
		}

		@Override
		public int hashCode() {
			int hash = this.value.hashCode();
			return hash;
		}
	}
    	
	static class Machine {
		String lightDiagram; 
		List<List<Integer>> buttonDiagram; 
		List<Integer> joltageRequirements; 
		
		Machine(String s, List<List<Integer>> butt, List<Integer> jol) {
			this.lightDiagram = s; 
			this.buttonDiagram = butt; 
			this.joltageRequirements = jol;
		}
		
		@Override
		public String toString() {
			return lightDiagram + " " + buttonDiagram + " " + joltageRequirements;
		}
	}
}
