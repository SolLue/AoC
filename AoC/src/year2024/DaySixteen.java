package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import utility.Coordinate;
import utility.Property;


public class DaySixteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day16.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 


		char[][] grid = new char[input.size()][];
		for(int i = 0; i < input.size(); i++) {
			grid[i] = input.get(i).toCharArray();
		}

	//	Graph graph = establishGraph(grid);

		Node start = null;
		Node end = null;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'E') {
			//		end = graph.getNode(i, j);
				}
				if (grid[i][j] == 'S') {
			//		start = graph.getNode(i, j);					
				}
			}
		}

	//	Map<Node, Integer> dij = djikstra(graph, start, end);
	//	int cost = dij.get(end);
		long stopTime = System.currentTimeMillis();

	//	System.out.println("Day Sixteen, Part One: " + cost);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 


		
		
		
		
		
	//	System.out.println(dij);
		
		
		//	graph = establishGraph(grid);
		//	List<Map<Node, Integer>> paths = getAllPathsDestination(graph, start, end, cost);

		/*	System.out.println(paths.size());
		for (Map<Node, Integer> map : paths) {
			int sigh = 0;
			System.out.println(map);
			for (Node node : map.keySet()) {
				sigh += map.get(node);
			}
		System.out.println(sigh);
		}
		 */



		stopTime = System.currentTimeMillis();
		System.out.println("Day Sixteen, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static boolean inRange(char[][] grid, int i, int j) {
		return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length;
	}

	/*static Graph establishGraph(char[][] grid) {
		Graph gridgraph = new Graph();
		int[] idir = new int[] {0, -1, 0, 1 };
		int[] jdir = new int[] {-1, 0, 1, 0 };
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] != '#') {
					Node current = new Node(i, j);
					gridgraph.addNode(current);

					for (int x = 0; x < idir.length; x++) {
						if (inRange(grid, i + idir[x], j + jdir[x])) {
							if (grid[i + idir[x]][j + jdir[x]] != '#') {
								Node coord = new Node(i + idir[x], j + jdir[x]);
								gridgraph.addNode(coord);

								if(current.x == coord.x && current.y == coord.y - 1) {
									coord.direction = 'E';
								}else if(current.x == coord.x && current.y == coord.y + 1) {
									coord.direction = 'W';
								} else if(current.x == coord.x - 1 && current.y == coord.y) {
									coord.direction = 'S';
								} else if(current.x == coord.x + 1 && current.y == coord.y) {
									coord.direction = 'N';
								}
								gridgraph.addEdge(current, coord, 1);
							}
						}
					}
				}
			}
		}
		return gridgraph;
	}*/


	static Map<Node, Integer> djikstra(Graph g, Node start, Node destination) {
		Queue<Node> queue = new PriorityQueue<Node>();
		queue.add(start);


		Map<Node, Integer> costSoFar = new HashMap<Node, Integer>();

		costSoFar.put(start, 0);

	//	while (!queue.isEmpty()) {
	//		Node current = queue.poll();
	//		Map<Node, Integer> neighbour = g.getEdges(current);

		/*	for(Node next : neighbour.keySet()) {

				int nextCost = 1; 
				if (current.direction == 'E') {
					if (next.direction == 'N') {
						nextCost = 1001;
					} else if (next.direction == 'S') {
						nextCost = 1001;
					}
				} else if (current.direction == 'N') {
					if (next.direction == 'E') {
						nextCost = 1001;
					} else if (next.direction == 'W') {
						nextCost = 1001;
					} 
				} else if (current.direction == 'W') {
					if (next.direction == 'N') {
						nextCost = 1001;
					} else if (next.direction == 'S') {
						nextCost = 1001;
					}
				} else if (current.direction == 'S') {
					if (next.direction == 'E') {
						nextCost = 1001;
					} else if (next.direction == 'W') {
						nextCost = 1001;
					}
				}

				int newcost = costSoFar.get(current) + nextCost;
				if(!costSoFar.containsKey(next)) {
					costSoFar.put(next, newcost);
					queue.add(next);
				}

			}
		}
*/
		return costSoFar;
	}


	/*static Map<Node, Integer> djikstra(Graph g, Node start, Node destination) {
		Queue<Node> queue = new PriorityQueue<Node>();
		queue.add(start);

		List<Node> visited = new ArrayList<Node>(); 
		visited.add(start);
		Map<Node, Integer> costSoFar = new HashMap<Node, Integer>();
		costSoFar.put(start, 0);

		while(!queue.isEmpty()) {
			Node current = queue.poll();

			if(start.equals(destination))
				return costSoFar; 

			for(Node next : g.getEdges(current).keySet()) {

				if(current.x == next.x && current.y == next.y - 1) {
					next.direction = 'E';
				}else if(current.x == next.x && current.y == next.y + 1) {
					next.direction = 'W';
				} else if(current.x == next.x - 1 && current.y == next.y) {
					next.direction = 'S';
				} else if(current.x == next.x + 1 && current.y == next.y) {
					next.direction = 'N';
				}

				int nextCost = 1; 
				if (current.direction == 'E') {
					if (next.direction == 'N') {
						nextCost = 1001;
					} else if (next.direction == 'S') {
						nextCost = 1001;
					}
				} else if (current.direction == 'N') {
					if (next.direction == 'E') {
						nextCost = 1001;
					} else if (next.direction == 'W') {
						nextCost = 1001;
					} 
				} else if (current.direction == 'W') {
					if (next.direction == 'N') {
						nextCost = 1001;
					} else if (next.direction == 'S') {
						nextCost = 1001;
					}
				} else if (current.direction == 'S') {
					if (next.direction == 'E') {
						nextCost = 1001;
					} else if (next.direction == 'W') {
						nextCost = 1001;
					}
				}
				int newcost = costSoFar.get(current) + nextCost;
				if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
					costSoFar.put(next, newcost);
					queue.add(next);
					visited.add(next);
				}
			}
		}
		return costSoFar;	
	}*/

	
	static class Node implements Comparable<Node> {
		int x;
		int y;
		char direction;
		//		int cost; 

		/*	Node(int i, int j, int c) {
			this.x = i;
			this.y = j;
			this.cost = c; 
		}
		 */
		Node(int i, int j) {
			this.x = i;
			this.y = j;
			//		this.cost = 1; 
			this.direction = 'E';
		}

		//	void setCost(int c) {
		//		this.cost = c; 
		//	}

		//	public int getCost() {
		//		return this.cost; 
		//	}

		public String toString() {
			return this.x + "/" + this.y ;//+ " " + this.cost + " facing " + this.direction;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(1, 1);
		}

		@Override
		public int hashCode() {
			return Integer.valueOf(this.x).hashCode() + Integer.valueOf(this.y).hashCode();
		}

		@Override
		public boolean equals(Object o) {
			Node c = (Node) o;
			if(this.x == c.x && this.y == c.y) {
				return true; 
			}
			return false;
		}
	}

	/*static class Graph {
		private Map<Node, Map<Node, Integer>> edges;

		Graph() {
			this.edges = new HashMap<Node, Map<Node, Integer>>();
		}

		Map<Node, Integer> getEdges(Node current) {
			if (this.edges.containsKey(current))
				return this.edges.get(current);
			return null;
		}

		Map<Node, Map<Node, Integer>> getEdges() {
			return this.edges;
		}

		Node getNode(int x, int y) {
			Node n = new Node(x, y);
			if (edges.containsKey(n))
				return n;
			return null;
		}

		Node getNode(Node n) {
			if (this.edges.containsKey(n))
				return n;
			return null;
		}

		boolean addEdge(Node current, Node edge, int weight) {
			if (this.edges.containsKey(current) && edges.containsKey(edge)) {
				Map<Node, Integer> temp = this.edges.get(current);
				temp.put(edge, weight);
				this.edges.put(current, temp);
				return true;
			}
			return false;
		}

		boolean addNode(Node t) {
			if (!edges.containsKey(t)) {
				this.edges.put(t, new HashMap<>());
				return true;
			}
			return false;
		}
	}
	*/
	static class Graph {
		int[][] grid; 
		List<Node> obstacle; 
			
		Graph(int h, int w, List<Node> o) {
			this.grid = new int [h][];
			for(int i = 0; i < h; i++) {
				this.grid[i] = new int[w];
			}
			this.obstacle = o;
		}
		
		Graph(int h, int w) {
			this.grid = new int [h][];
			for(int i = 0; i < h; i++) {
				this.grid[i] = new int[w];
			}
			this.obstacle = new ArrayList<Node>();
		}
		List<Node> getObstacles() {
			return this.obstacle;
		}
		boolean inBounds(int x, int y) {
			return (0 <= x && x < this.grid[0].length) && (0 <= y && y < this.grid.length);
		}
		boolean inBounds(Node c) {
			return (c.x >= 0 && c.x < this.grid[0].length) 
					&& (c.y >= 0 && c.y < this.grid.length);
		}
		boolean passable(int x, int y) {
			Node c = new Node(x, y);
			return obstacle.contains(c);
		}
		boolean passable(Node c) {
			return !obstacle.contains(c);
		}
		
		// change the order of coordinates added here for different movement -> ugly paths 
		List<Node> getNeighbours(Node c) {
			List<Node> temp = new ArrayList<Node>(); 
			temp.add(new Node(c.x, c.y - 1)); //N
			temp.add(new Node(c.x + 1, c.y)); //E
			temp.add(new Node(c.x - 1, c.y)); //W
			temp.add(new Node(c.x, c.y + 1)); //S
			
			temp = temp.stream().filter(e -> inBounds(e)).collect(Collectors.toList());
			temp = temp.stream().filter(e -> passable(e)).collect(Collectors.toList());

			return temp;
		}
	}
}
