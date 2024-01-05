package utility.PathFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphUnWeighted<T> {
	//weighted graph t- ex Node, int - Weight
	private Map<T, List<T>> edges;

	public GraphUnWeighted() {
		this.edges = new HashMap<T, List<T>>();
	}

	public List<T> getAdjacent(T current) {
		if(edges.containsKey(current))
			return edges.get(current);
		return null; 
	}

	public Map<T, List<T>> getGraph() {
		return this.edges;
	}
	
	public Node getNode(String s) {
		Node n = new Node(s);
		if(edges.containsKey(n))
			return n;
		return null; 
	}
	
	public Node getNode(Node n) {
		if(edges.containsKey(n))
			return n;
		return null; 
	}
	
	public boolean addEdge(T current, T edge) {
		if(edges.containsKey(current)) {
			List<T> temp = this.edges.get(current);
			temp.add(edge);
			this.edges.put(current, temp);
			return true; 
		} 
		return false;
	}

	public boolean addNode(T t) {
		if(!edges.containsKey(t)) {
			this.edges.put(t, new ArrayList<>());
			return true; 
		} 
		return false;
	}

	public List<T> getEdgesOfNode(T current) {
		if(edges.containsKey(current))
			return edges.get(current);
		return null; 
	}









	/*	private Map<Node, Map<Node, Integer>> graph = new HashMap<Node, Map<Node, Integer>>();

	public boolean addNode(Node node) {
		if (graph.containsKey(node)) 
			return false;
		graph.put(node, new HashMap<Node, Integer>());
		return true;
	}

	public Node getNode(String data) {
		for (Node n: graph.keySet()) {
			if (n.getData().equals(data))
				return n;
		}
		return null;
	}

	public void addEdge(Node source, Node destination, int length) {
		destination.setCost(length);
		graph.get(source).put(destination, length);
	}

	public Map<Node, Integer> getEdges(Node node) {
		if (node == null) {
            throw new NullPointerException("The node should not be null.");
        }
		System.out.println(node.getCost() + " " + node.getData());
        Map<Node, Integer> edges = graph.get(node);
        if (edges == null) {
            throw new NoSuchElementException("Source node does not exist.");
        }		return Collections.unmodifiableMap(edges);
	}

	@Override public Iterator<Node> iterator() {
		return graph.keySet().iterator();
	}*/
}