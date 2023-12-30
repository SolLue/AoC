package utility.PathFinding;

import java.util.HashMap;
import java.util.Map;

public class GraphWeighted {
	private Map<Node, Map<Node, Integer>> edges;

	public GraphWeighted() {
		this.edges = new HashMap<Node, Map<Node, Integer>>();
	}

	public Map<Node, Integer> getEdgesOfNode(Node current) {
		if(edges.containsKey(current))
			return edges.get(current);
		return null; 
	}

	public Map<Node, Map<Node, Integer>> getEdges() {
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
	
	public boolean addEdge(Node current, Node edge, int weight) {
		if(edges.containsKey(current) && edges.containsKey(edge)) {
			Map<Node, Integer> temp = this.edges.get(current);
			temp.put(edge, weight);
			this.edges.put(current, temp);
			return true; 
		} 
		return false;
	}

	public boolean addNode(Node t) {
		if(!edges.containsKey(t)) {
			this.edges.put(t, new HashMap<>());
			return true; 
		} 
		return false;
	}



}
