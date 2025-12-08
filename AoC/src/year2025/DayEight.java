package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utility.Property;

public class DayEight {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day8.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis();

		int id = 0;
		List<Coords> junctionboxes = new ArrayList<Coords>();
		for (String s : input) {
			String[] arr = s.split(",");
			Coords c = new Coords(id, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
			junctionboxes.add(c);
			id++;
		}

		List<Neighbour> neighbours = new ArrayList<>();
		for (int i = 0; i < junctionboxes.size(); i++) {
			for (int j = i + 1; j < junctionboxes.size(); j++) {
				long euc = euclideanDistance(junctionboxes.get(i), junctionboxes.get(j));
				neighbours.add(new Neighbour(i, j, euc));
			}
		}
		Collections.sort(neighbours);

		List<Circuit> circuit = new ArrayList<Circuit>();
		for (Coords j : junctionboxes) {
			Circuit c = new Circuit();
			c.add(j);
			circuit.add(c);
		}

		for (int i = 0; i < 1000; i++) {
			int id1 = neighbours.get(i).box1;
			int id2 = neighbours.get(i).box2;
			Circuit toDelete = null;
			Circuit c1 = null;
			Circuit c2 = null;
			for (Circuit circ : circuit) {
				if (circ.exists(junctionboxes.get(id1)))
					c1 = circ;
				if (circ.exists(junctionboxes.get(id2)))
					c2 = circ;
			}
			if (c2 != null) {
				for (Coords c : c2.boxes) {
					c1.add(c);
				}
				if (!c1.boxes.equals(c2.boxes))
					toDelete = c2;
			}
			circuit.remove(toDelete);
		}

		Collections.sort(circuit);
		Collections.reverse(circuit);

		long result = circuit.get(0).boxes.size();

		for (int i = 1; i < 3; i++) {
			result *= circuit.get(i).boxes.size();
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Eight, Part One: " + result);
		System.out.println("Time in ms " + (stopTime - startTime));

		
		startTime = System.currentTimeMillis();

		circuit = new ArrayList<Circuit>();
		for (Coords j : junctionboxes) {
			Circuit c = new Circuit();
			c.add(j);
			circuit.add(c);
		}

		int idend1 = 0; 
		int idend2 = 0; 
		for (int i = 0; i < neighbours.size(); i++) {
			int id1 = neighbours.get(i).box1;
			int id2 = neighbours.get(i).box2;
			Circuit toDelete = null;
			Circuit c1 = null;
			Circuit c2 = null;
			for (Circuit circ : circuit) {
				if (circ.exists(junctionboxes.get(id1)))
					c1 = circ;
				if (circ.exists(junctionboxes.get(id2)))
					c2 = circ;
			}
			if (c2 != null) {
				for (Coords c : c2.boxes) {
					c1.add(c);
				}
				if (!c1.boxes.equals(c2.boxes))
					toDelete = c2;
			}
			circuit.remove(toDelete);

			if(circuit.size() == 1) {
				idend1 = id1; 
				idend2 = id2;
				break;
			}
		}
		
		long resultPart2 = (long)junctionboxes.get(idend1).x * (long)junctionboxes.get(idend2).x;
				
		stopTime = System.currentTimeMillis();
		System.out.println("Day Eight, Part Two: " + resultPart2);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static long euclideanDistance(Coords coords, Coords coords2) {
		return (long) Math.sqrt(Math.pow((coords.x - coords2.x), 2) + Math.pow((coords.y - coords2.y), 2)
				+ Math.pow((coords.z - coords2.z), 2));
	}

	static record Coords(int id, int x, int y, int z) {}

	static class Circuit implements Comparable<Circuit> {
		List<Coords> boxes;

		Circuit() {
			this.boxes = new ArrayList<Coords>();
		}

		boolean exists(Coords c) {
			for (Coords coords : boxes) {
				if (coords.id == c.id)
					return true;
			}
			return false;
		}

		void add(Coords coord) {
			if (!exists(coord))
				this.boxes.add(coord);
		}

		@Override
		public String toString() {
			return this.boxes.toString();
		}

		@Override
		public int compareTo(Circuit o) {
			return Integer.compare(this.boxes.size(), o.boxes.size());
		}
	}

	static class Neighbour implements Comparable<Neighbour> {
		int box1;
		int box2;
		long distance;

		Neighbour(int a, int b, long dist) {
			this.box1 = a;
			this.box2 = b;
			this.distance = dist;
		}

		@Override
		public int compareTo(Neighbour o) {
			return Long.compare(this.distance, o.distance);
		}

		@Override
		public String toString() {
			return "[" + this.box1 + " " + this.box2 + " " + this.distance + "]";
		}
	}
}
