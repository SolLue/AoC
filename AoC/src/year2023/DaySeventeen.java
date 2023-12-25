package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class DaySeventeen {

	static int row;
	static int col;
	static int[][] input; 
	
	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day17.txt");
		Scanner scan = new Scanner(fr);
		List<String> in = new ArrayList<String>();

		while(scan.hasNextLine()) {
			in.add(scan.nextLine());
		}	
		scan.close();

		input = new int[in.size()][];

		for(int i = 0; i < in.size(); i++) {
			input[i] = new int[in.get(i).length()];
			for(int j = 0; j < in.get(i).length(); j++) {
				input[i][j] = Integer.parseInt(in.get(i).charAt(j) + "");
			}
		}		

		Graph graph = new Graph();

		graph.djikstra(0 + " " + 0);

		row = input.length - 1;
		col = input[1].length - 1;
		int sum = graph.printPath(row + " " + col);



		System.out.println("Day Seventeen, Part One: " + sum);
		System.out.println("Day Seventeen, Part Two: " + 0);

	}
	static class Edge  {
		Vertex destination;
		int cost;
		char direction = ' ';
		Edge(Vertex d, int c, char di) {
			this.destination = d;
			this.cost = c;
			this.direction = di;
		}
	}

	static class Vertex {
		String c;
		int streak;
		int distance; 
		List<Edge> adj;
		Vertex prev; 
		int scratch; 

		Vertex(String c) {
			this.c = c; 
			this.adj = new LinkedList<Edge>(); 
			reset();
		}
		void reset() {
			distance = Graph.INFINITY;
			this.prev = null;
			this.scratch = 0; 
			this.streak = 0; 
		}


	}

	static class Graph {
		static final int INFINITY = Integer.MAX_VALUE;
		static Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

		void addEdge(String source, String destination, int cost, char dir) {
			Vertex v = getVertex(source);
			Vertex w = getVertex(destination);
			v.adj.add(new Edge(w, cost, dir));
		}

		Vertex getVertex(String c) {
			Vertex v = vertexMap.get(c);
			if(v == null) {
				v = new Vertex(c);
				vertexMap.put(c, v);
			}
			return v; 
		}
		void clear() {
			for(Vertex v : vertexMap.values()) {
				v.reset();
			}
		}
		void printPath(Vertex dest) {
			if(dest.prev != null) {
				printPath(dest.prev);
				System.out.print(" to ");
			}
			System.out.println(dest.c.toString() + " cost here " + dest.distance);
		}
		int printPath(String dest) {
			Vertex w = vertexMap.get(dest);
			int sum = 0; 
			if(w.distance == INFINITY)
				System.out.println(dest.toString() + " is unreachable");
			else {
				sum += w.distance;
				System.out.println("cost: " + w.distance);
				printPath(w);
				System.out.println();
			}
			System.out.println(dest.toString() + " done ; cost " + sum);
			return sum;
		}

		void djikstra(String s) {
			Queue<Path> pq = new PriorityQueue<Path>();
			Vertex start = vertexMap.get(s);	
			clear();			
			pq.add(new Path(start, 0));
			start.distance = 0; 
			int vertexsSeen = 0;

			int currentstreak = 0;
			char currentdir = 'r';

			while(!pq.isEmpty() && vertexsSeen < vertexMap.size()) {
				Path vrec = pq.remove();
				Vertex v = vrec.dest;
				
				if(v.scratch != 0)
					continue;
				v.scratch = 1;

				vertexsSeen++;

				for(Edge e : v.adj) {
					if(currentdir == e.direction && currentstreak < 2) {
						
						Vertex w = e.destination;
						int cvw = e.cost;
						if(w.distance > v.distance + cvw) {
							w.distance = v.distance + cvw;
							w.prev = v;
							pq.add(new Path(w, w.distance));
				
						} 
					}
				}
			}
		}



	}

	static class Path implements Comparable<Path> {
		Vertex dest;
		int cost;

		Path(Vertex d, int c) {
			this.dest = d;
			this.cost = c; 
		}
		@Override
		public int compareTo(Path o) {
			return this.cost < o.cost ? -1 : this.cost > o.cost ? 1 : 0;
		}

		public String toString() {
			return this.dest.c + " " + this.cost;
		}

	}


}