package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;
import utility.PathFinding.PathfindingAlg;
import utility.PathFinding.GraphWeighted;
import utility.PathFinding.Node;

public class DayNine {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day9.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String t = br.readLine();
		while (t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		Map<String, List<String>> sources = new HashMap<>();

		List<String> temp = new ArrayList<String>();
		for (String string : input) {
			temp.add(string);
			String[] options = string.split(" ");
			temp.add(options[2] + " to " + options[0] + " = " + options[4]);
		}
		Collections.sort(temp);

		List<String> s = new ArrayList<String>();
		for (String string : temp) {
			String[] options = string.split(" ");
			if (!sources.containsKey(options[0])) {
				s = new ArrayList<String>();
				sources.put(options[0].trim(), s);
				s.add(options[2].trim() + " " + options[4].trim());
			} else {
				s.add(options[2].trim() + " " + options[4].trim());
				sources.put(options[0].trim(), s);
			}
		}

		PathfindingAlg<Node> p = new PathfindingAlg<Node>();
		GraphWeighted graph = new GraphWeighted();

		for (String key : sources.keySet()) {
			graph.addNode(new Node(key));
		}
		for (String key : sources.keySet()) {
			for(String string : sources.get(key)) {
				String city = string.substring(0, string.indexOf(" "));
				graph.addEdge(graph.getNode(key), graph.getNode(city), 
						Integer.parseInt(string.substring(string.indexOf(" ") + 1)));
			}
		}
		
		List<Map<Node, Integer>> allPaths = new ArrayList<Map<Node,Integer>>();
		
		for (String key : sources.keySet()) {
			allPaths.addAll(p.getAllPaths(graph, graph.getNode(key), sources.keySet().size()));
		}
		
		List<Integer> costs = new ArrayList<Integer>();
		
		for (Map<Node, Integer> list : allPaths) {
			int cost = 0; 
			for (Node n : list.keySet()) {
				cost += list.get(n);
			}
			costs.add(cost);
		}


		System.out.println("Day Nine, Part One: " + Collections.min(costs));
		System.out.println("Day Nine, Part Two: " + Collections.max(costs));
	}



}
