package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import utility.Property;
import utility.PathFinding.GraphWeighted;
import utility.PathFinding.Node;

public class DayThirteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day13.txt");
		BufferedReader br = new BufferedReader(fr);

		String temp = br.readLine();
		List<String> input = new ArrayList<String>();
		while(temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		List<String> cleansed = new ArrayList<String>();
		for (String string : input) {
			boolean neg = false; 
			String[] options = string.split(" ");
			if(options[2].equals("lose")) 
				neg = true; 
			int amount = 0; 
			if(neg) {
				amount = Integer.parseInt("-" + options[3]);
			} else
				amount = Integer.parseInt(options[3]);
			cleansed.add(options[0].trim() + " " + amount + " " + options[10].substring(0, options[10].length() - 1).trim());
		}
		
		Map<String, List<String>> sources = createSourceMap(cleansed);
		GraphWeighted graph = createGraph(sources);
		List<Map<Node, Integer>> allPaths = new ArrayList<Map<Node,Integer>>();
		
		for (String key : sources.keySet()) {
			allPaths.addAll(getAllPaths(graph, graph.getNode(key), sources.keySet().size(), graph.getNode(key)));
		}

		List<Integer> costs = new ArrayList<Integer>();
		for (Map<Node, Integer> list : allPaths) {
			int cost = 0; 
			for (Node n : list.keySet()) {
				cost += list.get(n);
			}
			costs.add(cost);
		}
		
		System.out.println("Day Thirteen, Part One: " + Collections.max(costs));

		int amountpeople = sources.keySet().size();
		for(int i = 0; i < amountpeople; i++) {
			String key = cleansed.get(i * amountpeople).substring(0, cleansed.get(i * amountpeople).indexOf(" "));
			cleansed.add(i * amountpeople, key + " 0 Me");
		}
		
		for(String key : sources.keySet()) {
			cleansed.add("Me 0 " + key);
		}
		
		sources = createSourceMap(cleansed);
		
		graph = createGraph(sources);
		allPaths = new ArrayList<Map<Node,Integer>>();
		for (String key : sources.keySet()) {
			allPaths.addAll(getAllPaths(graph, graph.getNode(key), sources.keySet().size(), graph.getNode(key)));
		}

		costs = new ArrayList<Integer>();
		for (Map<Node, Integer> list : allPaths) {
			int cost = 0; 
			for (Node n : list.keySet()) {
				cost += list.get(n);
			}
			costs.add(cost);
		}
		
		System.out.println("Day Thirteen, Part Two: " + Collections.max(costs));
	}

	static List<Map<Node, Integer>> getAllPaths(GraphWeighted graph, Node source, int steps, Node parent) { 
		List<Map<Node, Integer>> paths = new ArrayList<Map<Node, Integer>>();
		recursiveGetPaths(graph, source, paths, new LinkedHashMap<Node, Integer>(), steps, 0, 0, parent); 
		return paths;
	}

	static void recursiveGetPaths(GraphWeighted graph, Node current, List<Map<Node, Integer>> paths, 
			LinkedHashMap<Node, Integer> path, int steps, int amount, int currentcost, Node parent) {
		path.put(current, currentcost);

		amount++;
		if(amount == steps) {
			Map<Node, Integer> edges = graph.getEdgesOfNode(current);
			for (Node e : edges.keySet()) {
				if (e.equals(parent)) {
					currentcost = edges.get(e);
					path.put(e, currentcost);
				}
			}
			paths.add(new HashMap<Node, Integer>(path));
			path.remove(current);
			return; 
		}

		Map<Node, Integer> edges = graph.getEdgesOfNode(current);

		for (Node e : edges.keySet()) {
			if (!path.containsKey(e)) {
				currentcost = edges.get(e);
				recursiveGetPaths(graph, e, paths, path, steps, amount, currentcost, parent);
			}
		}
		path.remove(current);
	}
	
	static GraphWeighted createGraph(Map<String, List<String>> sources) {
		GraphWeighted graph = new GraphWeighted();
		for (String key : sources.keySet()) {
			graph.addNode(new Node(key.trim()));
		}

		for (String key : sources.keySet()) {
			for (String string : sources.get(key)) {
				String[] options = string.split(" ");
				int amount = 0; 

				String subperson = options[1].trim();
				List<String> t = sources.get(subperson);
				for (String string2 : t) {
					if(string2.contains(key)) {
						amount = Integer.parseInt(string2.substring(0, string2.indexOf(" ")));
						break; 
					}
				}
				amount += Integer.parseInt(options[0]);
				graph.addEdge(graph.getNode(key), graph.getNode(subperson), amount);
			}
		}
		return graph; 
	}
	
	static Map<String, List<String>> createSourceMap(List<String> cleansed) {
		Map<String, List<String>> sources = new HashMap<String, List<String>>();

		for (String string : cleansed) {
			String[] options = string.split(" ");
			if(!sources.containsKey(options[0])) {
				sources.put(options[0].trim(), new ArrayList<String>());
				List<String> t = sources.get(options[0].trim());
				t.add(options[1].trim() + " " + options[2].trim());
				sources.put(options[0].trim(), t); 
			} else {
				List<String> t = sources.get(options[0].trim());
				t.add(options[1].trim() + " " + options[2].trim());
				sources.put(options[0].trim(), t);
			}
		}
		return sources; 
	}
	
}
