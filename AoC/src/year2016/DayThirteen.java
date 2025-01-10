package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import utility.Property;

public class DayThirteen {
	static int input; 
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day13.txt");
		BufferedReader br = new BufferedReader(fr);

		input = Integer.parseInt(br.readLine().trim());
		br.close();

		Node start = new Node(1, 1);
		Node destination = new Node(31, 39);
			
		Map<Node, Integer> path = djikstra(start, destination);
		List<Node> ends = new ArrayList<Node>();
		
		System.out.println("Day Thirteen, Part One: " + path.get(destination));

		
		for (int i = 0; i < 51; i++) {
			for (int j = 0; j < 51; j++) {
				if(passable(new Node(i, j)) && negativeCheck(new Node(j, j)))
					ends.add(new Node(i, j));		
			}	
		}
		Set<Node> unique = new HashSet<Node>();

		for (Node node : ends) {
			Map<Node, Integer> temp = djikstra(start, node);
			for (Node n : temp.keySet()) {
				if (temp.get(n) <= 50)
					unique.add(n);
			}			
		}
		System.out.println("Day Thirteen, Part Two: " + unique.size());

	}

	static Map<Node, Integer> djikstra(Node start, Node end) {
		Queue<Node> queue = new PriorityQueue<Node>();
		queue.add(start);
		start.cost = 0; 
		List<Node> cameFrom = new ArrayList<Node>(); 
		cameFrom.add(start);
		Map<Node, Integer> costSoFar = new HashMap<Node, Integer>();
		costSoFar.put(start, 0);

		while(!queue.isEmpty()) {
			Node current = queue.poll();
			if(current.equals(end)) {
				return costSoFar; 
			}

			for(Node next : getNeighbours(current)) {
				int newcost = costSoFar.get(current) + 1;
				if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
					costSoFar.put(next, newcost);
					queue.add(next);
					cameFrom.add(next);
					next.cost = newcost;
				}
			}
		}
		return costSoFar;	
	}

	static List<Node> getNeighbours(Node current) {
		List<Node> temp = new ArrayList<Node>();
		temp.add(new Node(current.x - 1, current.y));
		temp.add(new Node(current.x + 1, current.y));
		temp.add(new Node(current.x, current.y + 1));
		temp.add(new Node(current.x, current.y - 1));
		temp = temp.stream().filter(e -> passable(e)).collect(Collectors.toList());
		temp = temp.stream().filter(e -> negativeCheck(e)).collect(Collectors.toList());
		return temp;
	}
	
	static boolean passable(Node c) {
		int nr = c.x * c.x + 3 * c.x + 2 * c.x * c.y + c.y + c.y * c.y;
		nr += input;
		String binary = Integer.toString(nr, 2);
		long countOnes = binary.chars().filter(ch -> ch == '1').count();

		if(countOnes % 2 == 0)
			return true; 
		return false; 
	}

	static boolean negativeCheck(Node c) {
		if(c.x < 0 || c.y < 0)
			return false; 
		return true; 
	}

	static class Node implements Comparable<Node> {
		int cost; 
		int x; 
		int y; 
		Node prev = null; 

		public Node(int x, int y) {
			this.x = x; 
			this.y = y; 
			this.cost = 0; 
		}

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + "]";
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			return x == other.x && y == other.y;
		}
	}
}
