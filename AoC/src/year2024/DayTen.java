package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import utility.Property;

public class DayTen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day10.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis();

		int[][] grid = new int[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			int[] te = new int[input.get(i).length()];
			for (int j = 0; j < input.get(i).length(); j++) {
				te[j] = Integer.parseInt(input.get(i).charAt(j) + "");
			}
			grid[i] = te;

		}

		Graph gridgraph = establishGraph(grid);
		int rating = 0; 
		int score = 0; 
		List<Map<Coordinate, Integer>> trails = new ArrayList<Map<Coordinate, Integer>>(); 
		for (int i = 0; i < grid.length; i++) { 
			for (int j = 0; j < grid[i].length; j++) { 
				// get trailhead 
				if (grid[i][j] == 0) { 
					Coordinate current = gridgraph.getCoordinate(i, j, grid[i][j]);
					List<Map<Coordinate, Integer>> tem = getAllPaths(gridgraph, current);
					
					rating += tem.size();
					
					Set<Coordinate> end = new HashSet<Coordinate>();
					for (Map<Coordinate, Integer> map : tem) {
						for (Coordinate coordinate : map.keySet()) {
							if (coordinate.height == 9)
								end.add(coordinate);
						}						
					}
					score += end.size();		
				}
			}
		}
		
		long stopTime = System.currentTimeMillis();

		System.out.println("Day Ten, Part One: " + score);
		System.out.println("Time in ms " + (stopTime - startTime));

		System.out.println("Day Ten, Part Two: " + rating);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	static List<Map<Coordinate, Integer>> getAllPaths(Graph graph, Coordinate source) {
		List<Map<Coordinate, Integer>> paths = new ArrayList<Map<Coordinate, Integer>>();
		recursiveGetPaths(graph, source, paths, new LinkedHashMap<Coordinate, Integer>()); 
		return paths;
	}

	static void recursiveGetPaths(Graph graph, Coordinate current, List<Map<Coordinate, Integer>> paths,
			LinkedHashMap<Coordinate, Integer> path) {
		path.put(current, current.height);

		if (current.height == 9) {
			paths.add(new HashMap<Coordinate, Integer>(path));
			path.remove(current);
			return;
		}

		Map<Coordinate, Integer> edges = graph.getEdges(current);

		for (Coordinate e : edges.keySet()) {
			if (!path.containsKey(e)) {
				recursiveGetPaths(graph, e, paths, path);
			}
		}
		path.remove(current);
	}

	static Graph establishGraph(int[][] grid) {
		Graph gridgraph = new Graph();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Coordinate current = new Coordinate(i, j, grid[i][j]);
				gridgraph.addCoordinate(current);
				// left up
				if (j == 0 && i == 0) {
					if (grid[i][j + 1] == current.height + 1) {
						Coordinate right = new Coordinate(i, j + 1, grid[i][j + 1]);
						gridgraph.addCoordinate(right);
						gridgraph.addEdge(current, right, grid[i][j + 1]);
					}
					if (grid[i + 1][j] == current.height + 1) {
						Coordinate down = new Coordinate(i + 1, j, grid[i + 1][j]);
						gridgraph.addCoordinate(down);
						gridgraph.addEdge(current, down, grid[i + 1][j]);
					}
				} // down right
				else if (j == grid[i].length - 1 && i == grid.length - 1) {
					if (grid[i][j - 1] == current.height + 1) {
						Coordinate left = new Coordinate(i, j - 1, grid[i][j - 1]);
						gridgraph.addCoordinate(left);
						gridgraph.addEdge(current, left, grid[i][j - 1]);
					}
					if (grid[i - 1][j] == current.height + 1) {
						Coordinate up = new Coordinate(i - 1, j, grid[i - 1][j]);
						gridgraph.addCoordinate(up);
						gridgraph.addEdge(current, up, grid[i - 1][j]);
					}
				}
				// left down
				else if (j == 0 && i == grid.length - 1) {
					if (grid[i][j + 1] == current.height + 1) {
						Coordinate right = new Coordinate(i, j + 1, grid[i][j + 1]);
						gridgraph.addCoordinate(right);
						gridgraph.addEdge(current, right, grid[i][j + 1]);
					}
					if (grid[i - 1][j] == current.height + 1) {
						Coordinate up = new Coordinate(i - 1, j, grid[i - 1][j]);
						gridgraph.addCoordinate(up);
						gridgraph.addEdge(current, up, grid[i - 1][j]);
					}
				}
				// up right
				else if (j == grid[i].length - 1 && i == 0) {
					if (grid[i][j - 1] == current.height + 1) {
						Coordinate left = new Coordinate(i, j - 1, grid[i][j - 1]);
						gridgraph.addCoordinate(left);
						gridgraph.addEdge(current, left, grid[i][j - 1]);
					}
					if (grid[i + 1][j] == current.height + 1) {
						Coordinate down = new Coordinate(i + 1, j, grid[i + 1][j]);
						gridgraph.addCoordinate(down);
						gridgraph.addEdge(current, down, grid[i + 1][j]);
					}
				}
				// right
				else if (j == grid[i].length - 1 && i > 0 && i < grid.length - 1) {
					if (grid[i][j - 1] == current.height + 1) {
						Coordinate left = new Coordinate(i, j - 1, grid[i][j - 1]);
						gridgraph.addCoordinate(left);
						gridgraph.addEdge(current, left, grid[i][j - 1]);
					}
					if (grid[i + 1][j] == current.height + 1) {
						Coordinate down = new Coordinate(i + 1, j, grid[i + 1][j]);
						gridgraph.addCoordinate(down);
						gridgraph.addEdge(current, down, grid[i + 1][j]);
					}
					if (grid[i - 1][j] == current.height + 1) {
						Coordinate up = new Coordinate(i - 1, j, grid[i - 1][j]);
						gridgraph.addCoordinate(up);
						gridgraph.addEdge(current, up, grid[i - 1][j]);
					}
				}
				// left
				else if (j == 0 && i > 0 && i > 0 && i < grid.length - 1) {
					if (grid[i][j + 1] == current.height + 1) {
						Coordinate right = new Coordinate(i, j + 1, grid[i][j + 1]);
						gridgraph.addCoordinate(right);
						gridgraph.addEdge(current, right, grid[i][j + 1]);
					}
					if (grid[i + 1][j] == current.height + 1) {
						Coordinate down = new Coordinate(i + 1, j, grid[i + 1][j]);
						gridgraph.addCoordinate(down);
						gridgraph.addEdge(current, down, grid[i + 1][j]);
					}
					if (grid[i - 1][j] == current.height + 1) {
						Coordinate up = new Coordinate(i - 1, j, grid[i - 1][j]);
						gridgraph.addCoordinate(up);
						gridgraph.addEdge(current, up, grid[i - 1][j]);
					}
				}
				// up
				else if (i == 0 && j > 0 && j < grid[i].length - 1) {
					if (grid[i + 1][j] == current.height + 1) {
						Coordinate down = new Coordinate(i + 1, j, grid[i + 1][j]);
						gridgraph.addCoordinate(down);
						gridgraph.addEdge(current, down, grid[i + 1][j]);
					}
					if (grid[i][j + 1] == current.height + 1) {
						Coordinate right = new Coordinate(i, j + 1, grid[i][j + 1]);
						gridgraph.addCoordinate(right);
						gridgraph.addEdge(current, right, grid[i][j + 1]);
					}
					if (grid[i][j - 1] == current.height + 1) {
						Coordinate left = new Coordinate(i, j - 1, grid[i][j - 1]);
						gridgraph.addCoordinate(left);
						gridgraph.addEdge(current, left, grid[i][j - 1]);
					}
				}
				// down
				else if (i == grid.length - 1 && j > 0 && j < grid[i].length) {
					if (grid[i - 1][j] == current.height + 1) {
						Coordinate up = new Coordinate(i - 1, j, grid[i - 1][j]);
						gridgraph.addCoordinate(up);
						gridgraph.addEdge(current, up, grid[i - 1][j]);
					}
					if (grid[i][j + 1] == current.height + 1) {
						Coordinate right = new Coordinate(i, j + 1, grid[i][j + 1]);
						gridgraph.addCoordinate(right);
						gridgraph.addEdge(current, right, grid[i][j + 1]);
					}
					if (grid[i][j - 1] == current.height + 1) {
						Coordinate left = new Coordinate(i, j - 1, grid[i][j - 1]);
						gridgraph.addCoordinate(left);
						gridgraph.addEdge(current, left, grid[i][j - 1]);
					}
				} else {
					if (grid[i - 1][j] == current.height + 1) {
						Coordinate up = new Coordinate(i - 1, j, grid[i - 1][j]);
						gridgraph.addCoordinate(up);
						gridgraph.addEdge(current, up, grid[i - 1][j]);
					}
					if (grid[i + 1][j] == current.height + 1) {
						Coordinate down = new Coordinate(i + 1, j, grid[i + 1][j]);
						gridgraph.addCoordinate(down);
						gridgraph.addEdge(current, down, grid[i + 1][j]);
					}
					if (grid[i][j - 1] == current.height + 1) {
						Coordinate left = new Coordinate(i, j - 1, grid[i][j - 1]);
						gridgraph.addCoordinate(left);
						gridgraph.addEdge(current, left, grid[i][j - 1]);
					}
					if (grid[i][j + 1] == current.height + 1) {
						Coordinate right = new Coordinate(i, j + 1, grid[i][j + 1]);
						gridgraph.addCoordinate(right);
						gridgraph.addEdge(current, right, grid[i][j + 1]);
					}
				}
			}
		}
		return gridgraph;
	}

	static class Coordinate implements Comparable<Coordinate> {
		int x;
		int y;
		int height;

		Coordinate(int i, int j, int h) {
			this.x = i;
			this.y = j;
			this.height = h;
		}

		public String toString() {
			return "[" + this.x + " / " + this.y + "] " + this.height;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			Coordinate tmp = (Coordinate) o;
			if (this.x == tmp.x && this.y == tmp.y && this.height == tmp.height)
				return true;
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public int compareTo(Coordinate o) {
			return Integer.compare(this.height, o.height);
		}
	}

	static class Graph {
		private Map<Coordinate, Map<Coordinate, Integer>> edges;

		Graph() {
			this.edges = new HashMap<Coordinate, Map<Coordinate, Integer>>();
		}

		Map<Coordinate, Integer> getEdges(Coordinate current) {
			if (this.edges.containsKey(current))
				return this.edges.get(current);
			return null;
		}

		Map<Coordinate, Map<Coordinate, Integer>> getEdges() {
			return this.edges;
		}

		Coordinate getCoordinate(int x, int y, int h) {
			Coordinate n = new Coordinate(x, y, h);
			if (edges.containsKey(n))
				return n;
			return null;
		}

		Coordinate getCoordinate(Coordinate n) {
			if (this.edges.containsKey(n))
				return n;
			return null;
		}

		boolean addEdge(Coordinate current, Coordinate edge, int weight) {
			if (this.edges.containsKey(current) && edges.containsKey(edge)) {
				Map<Coordinate, Integer> temp = this.edges.get(current);
				temp.put(edge, weight);
				this.edges.put(current, temp);
				return true;
			}
			return false;
		}

		boolean addCoordinate(Coordinate t) {
			if (!edges.containsKey(t)) {
				this.edges.put(t, new HashMap<>());
				return true;
			}
			return false;
		}
	}
}