package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import utility.Property;


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
		List<Floor> floors = new ArrayList<Floor>(); 

		for(int i = 0; i < floorAmount; i++) {
			if(!input.get(i).contains("nothing")) {
				String temp = input.get(i).substring(input.get(i).indexOf("contains") + 11, input.get(i).length() - 1);
				temp = temp.replaceAll("\\b and a \\b", ",");
				String[] list = temp.split(",");

				Floor f = new Floor(i + 1);
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
			}

		}

		for (Floor floor : floors) {
			System.out.println(floor.toString());
		}



		System.out.println("Day Eleven, Part One: " + 0);

		System.out.println("Day Eleven, Part Two: " + 0);

	}

	static boolean isValid(List<Floor> floor, int current, int wantedFloor) {
		// valid if microchip is paired to generator + no other microchip
		return false;
	}
	
	static record Component(String element) {
		public String toString() {
			return element;
		}
	}
	static class Floor{
		int number;
		List<Component> contents;

		Floor(int n) {
			this.number = n;
			this.contents = new ArrayList<Component>();
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
	}	
}
