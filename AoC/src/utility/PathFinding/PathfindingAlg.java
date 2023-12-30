package utility.PathFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import utility.Coordinate;

public class PathfindingAlg<T> {

	public List<T> bfs(Graph<T> graph, T start) {
		Queue<T> path = new LinkedList<T>();
		path.add(start);
		Set<T> visited = new HashSet<T>();
		visited.add(start);
		List<T> p = new ArrayList<>(); 

		while(!path.isEmpty()) {
			T current = path.poll();
			System.out.println("currently at " + current.toString());
			p.add(current);

			for (T adj : graph.getAdjacent(current)) {
				if(!visited.contains(adj)) {
					path.add(adj);
					visited.add(adj);
				}
			}
		}
		return p; 
	}

	public List<Map<Node, Integer>> getAllPaths(GraphWeighted graph, Node source, int steps) { //Node destination) {
		List<Map<Node, Integer>> paths = new ArrayList<Map<Node, Integer>>();
		recursiveGetPaths(graph, source, paths, new LinkedHashMap<Node, Integer>(), steps, 0, 0); //destination, paths, new LinkedHashSet<Node>());
		return paths;
	}

	private void recursiveGetPaths(GraphWeighted graph, Node current, List<Map<Node, Integer>> paths, 
			LinkedHashMap<Node, Integer> path, int steps, int amount, int currentcost) {
		path.put(current, currentcost);

		amount++;
		if(amount == steps) {
			paths.add(new HashMap<Node, Integer>(path));
			path.remove(current);
			return; 
		}

		Map<Node, Integer> edges = graph.getEdgesOfNode(current);

		for (Node e : edges.keySet()) {
			if (!path.containsKey(e)) {
				currentcost = edges.get(e);
				recursiveGetPaths(graph, e, paths, path, steps, amount, currentcost);//destination, paths, path);
			}
		}
		path.remove(current);
	}

	public List<Coordinate> bfs_grid_ObstacleList(GraphSquare graph, Coordinate start) {
		Queue<Coordinate> path = new LinkedList<Coordinate>(); 
		path.add(start);
		//Map<Coordinate, Coordinate> cameFrom = new HashMap<Coordinate, Coordinate>();
		List<Coordinate> cameFrom = new ArrayList<Coordinate>(); 
		//cameFrom.put(start, null);
		cameFrom.add(start);

		while(!path.isEmpty()) {
			Coordinate current = path.poll();

			for(Coordinate next : graph.getNeighbours(current)) {
				if(!cameFrom.contains(next)) {
					path.add(next);
					cameFrom.add(next);
				}
			}
		}
		return cameFrom;
	}

	public List<Coordinate> bfs_grid_Goal(GraphSquare graph, Coordinate start, Coordinate destination) {
		Queue<Coordinate> path = new LinkedList<Coordinate>(); 
		path.add(start);

		List<Coordinate> cameFrom = new ArrayList<Coordinate>(); 
		cameFrom.add(start);

		while(!path.isEmpty()) {
			Coordinate current = path.poll();

			if(current.equals(destination))
				return cameFrom;

			for(Coordinate next : graph.getNeighbours(current)) {
				if(!cameFrom.contains(next)) {
					path.add(next);
					cameFrom.add(next);
				}
			}
		}
		return cameFrom;
	}

	/*	public Map<Node, Integer> djikstraNode(GraphWeighted g, Node start, Node destination) {
		List<Node> queue = new LinkedList<Node>();
		queue.add(start);

		List<Node> cameFrom = new ArrayList<Node>(); 
		cameFrom.add(start);
		Map<Node, Integer> costSoFar = new HashMap<Node, Integer>();
		costSoFar.put(start, 0);

		while(!queue.isEmpty()) {
			Node current = queue.remove(0);

			if(start.equals(destination))
				return costSoFar; 

			for(Node next : g.getEdges().keySet() {
				int newcost = costSoFar.get(current) + g.getNeighbours(current).get(next);
				if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
					costSoFar.put(next, newcost);
					queue.add(next);
					cameFrom.add(next);
				}
			}
		}
		return costSoFar;	
	}*/

	public Map<Coordinate, Integer> djikstra(GraphSquareWeighted g, Coordinate start, Coordinate destination) {
		Queue<Coordinate> queue = new PriorityQueue<Coordinate>();
		queue.add(start);

		List<Coordinate> cameFrom = new ArrayList<Coordinate>(); 
		cameFrom.add(start);
		Map<Coordinate, Integer> costSoFar = new HashMap<Coordinate, Integer>();
		costSoFar.put(start, 0);

		while(!queue.isEmpty()) {
			Coordinate current = queue.poll();

			if(start.equals(destination))
				return costSoFar; 

			for(Coordinate next : g.getNeighbours(current).keySet()) {
				int newcost = costSoFar.get(current) + g.getNeighbours(current).get(next);
				if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
					costSoFar.put(next, newcost);
					queue.add(next);
					cameFrom.add(next);
				}
			}
		}
		return costSoFar;	
	}

	/*
	 * the djikstra code above should also return the camefrom list
	 * 
	 * def reconstruct_path(came_from: dict[Location, Location],
                     start: Location, goal: Location) -> list[Location]:

    current: Location = goal
    path: list[Location] = []
    if goal not in came_from: # no path was found
        return []
    while current != start:
        path.append(current)
        current = came_from[current]
    path.append(start) # optional
    path.reverse() # optional
    return path
	 * 
	 */

	/*	public Map<Coordinate, Integer> astar(GraphSquareWeighted g, Coordinate start, Coordinate destination) {
		Queue<Coordinate> queue = new PriorityQueue<Coordinate>();
		queue.add(start);

		List<Coordinate> cameFrom = new ArrayList<Coordinate>(); 
		cameFrom.add(start);
		Map<Coordinate, Integer> costSoFar = new HashMap<Coordinate, Integer>();
		costSoFar.put(start, 0);

		while(!queue.isEmpty()) {
			Coordinate current = queue.poll();

			if(start.equals(destination))
				return costSoFar; 

			for(Coordinate next : g.getNeighbours(current).keySet()) {
				int newcost = costSoFar.get(current) + g.getNeighbours(current).get(next);
				if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
					costSoFar.put(next, newcost);
					int prio = newcost + g.heuristic(next, destination);
					//cant add the priority right now
					queue..add(next, prio);
					cameFrom.add(next);
				}
			}
		}
		return costSoFar;	
	}*/ 


}
