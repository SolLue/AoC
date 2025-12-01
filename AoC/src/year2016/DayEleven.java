package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import utility.Property;
import year2016.DayThirteen.Node;


public class DayEleven {

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day11.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		int floorAmount = 4; 
		Map<Integer, List<Component>> floors = new HashMap<Integer, List<Component>>();
		
		for(int i = 0; i < floorAmount; i++) {
			List<Component> floor = new ArrayList<Component>();
			if(!input.get(i).contains("nothing")) {
				String temp = input.get(i).substring(input.get(i).indexOf("contains") + 11, input.get(i).length() - 1);
				temp = temp.replaceAll("\\b and a \\b", ",");
				String[] list = temp.split(",");

				for (String string : list) {
					Component f = null;
					if (string.contains("microchip")) {
						f = new Component(string.substring(0, string.indexOf("-")) + " microchip", i + 1);
					} else if (string.contains("generator")) {
						f = new Component(string.substring(0, string.indexOf(" ")) + " generator", i + 1);		
					}
					floor.add(f);
				}
			}
			floors.put(i + 1, floor);
		}


			/*	Floor f = new Floor(i + 1);
				for (String string : list) {
					if (string.contains("microchip")) {
						f.addContent(new Component(string.substring(0, string.indexOf("-")) + " microchip"));
					} else if (string.contains("generator")) {
						f.addContent(new Component(string.substring(0, string.indexOf(" ")) + " generator"));		
					}
				}
				floors.add(f);
			} else {
				floors.add(new Floor(i + 1));
			}*/
		//}


		
		
		for (Integer floor : floors.keySet()) {
			System.out.println(floor + " " + floors.get(floor));
		}
	
		List<Component> start = floors.get(0);
		List<Component> end = floors.get(floors.size() - 1);

		System.out.println(".............");
		int cost = getPath(floors, start, end);

		

		System.out.println("Day Eleven, Part One: " + cost);

		System.out.println("Day Eleven, Part Two: " + 0);

	}

	static int getPath(Map<Integer, List<Component>> floors, List<Component> start, List<Component> end) {
		Queue<List<Component>> queue = new PriorityQueue<List<Component>>();
		queue.add(start);
		int currentcost = 0; 
		List<List<Component>> cameFrom = new ArrayList<List<Component>>(); 
		cameFrom.add(start);

		while(!queue.isEmpty()) {
			List<Component> current = queue.poll();
			if(current.equals(end)) {
				return currentcost; 
			}

			for(List<Component> next : getNeighbours(current, floors)) {
				System.out.println(current + " " +getNeighbours(current, floors));
				//int newcost = costSoFar.get(current) + 1;
				//if(!costSoFar.containsKey(next) || newcost < costSoFar.get(next)) {
				//	costSoFar.put(next, newcost);
				//	queue.add(next);
				//	cameFrom.add(next);
				//	next.cost = newcost;
				//}
			}
		}
		return currentcost;	
	}

	static List<List<Component>> getNeighbours(List<Component> current, Map<Integer, List<Component>> floors) {
		List<List<Component>> temp = new ArrayList<List<Component>>();
		if(current.get(0).floor > 1) {
			temp.add(floors.get(current.get(0).floor - 1));
		} 
		if(current.get(0).floor < 4) {
			temp.add(floors.get(current.get(0).floor + 1));
		} 
		
	//	temp = temp.stream().filter(e -> valid(e)).collect(Collectors.toList());
		return temp;
	}

	static boolean valid(Component f) {


		return true; 
	}

	static class Component implements Comparable<Component> {
		String data; 
		int floor;
		int cost; 

		public Component(String d, int f) {
			this.data = d; 
			this.floor = f; 
		}

		@Override
		public String toString() {
			return "Component: " + this.data + " floor " + this.floor;
		}

		@Override
		public int hashCode() {
			return Objects.hash(data, floor);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Component other = (Component) obj;
			return Objects.equals(data, other.data) && floor == other.floor;
		}

		@Override
		public int compareTo(Component o) {
			return this.cost - o.cost; 
		}
	}

	/*static class Floor implements Comparable<Floor> {
		int number;
		List<Component> contents;
		int cost = 0; 

		Floor(int n) {
			this.number = n;
			this.contents = new ArrayList<Component>();
		}

		@Override
		public int hashCode() {
			return Objects.hash(contents, number);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Floor other = (Floor) obj;
			return Objects.equals(contents, other.contents) && number == other.number;
		}

		boolean addContent(Component o) {
			return this.contents.add(o);
		}
		boolean removeContent(Component o) {
			return contents.remove(o);
		}
		public String toString() {
			return "Floor " + this.number + ": " + this.contents.toString();
		}

		@Override
		public int compareTo(Floor o) {
			return this.cost - o.cost;
		}
	}	*/


}
