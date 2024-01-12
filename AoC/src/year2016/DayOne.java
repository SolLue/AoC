package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Coordinate;
import utility.Direction;
import utility.Property;

public class DayOne {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day1.txt");
		BufferedReader br = new BufferedReader(fr);
		String t = br.readLine();
		br.close();

		String[] input = t.split(", ");
		int[] bunbun = bunnyInstruction(input);

		System.out.println("Day One, Part One: " + bunbun[0]);	
		System.out.println("Day One, Part Two: " + bunbun[1]);
	}

	static int[] bunnyInstruction(String[] input) {
		int[] res = new int[2];
		List<Coordinate> visited = new ArrayList<>();
		Coordinate start = new Coordinate(0, 0);
		Direction startD = Direction.N;
		visited.add(start);
		boolean secondFound = false; 
		Coordinate part2 = new Coordinate(0, 0);

		for (String string : input) {
			int steps = 0; 
			if(string.contains("R")) {
				steps = Integer.parseInt(string.substring(1));
				startD = startD.turnRight();
			}
			if(string.contains("L")) {
				steps = Integer.parseInt(string.substring(1));
				startD = startD.turnLeft();
			}

			if(startD.equals(Direction.E) || startD.equals(Direction.W)) {
				Coordinate c = new Coordinate(start.getX(), start.getY()); 
				start = start.moveX(startD, steps);

				if(!secondFound) {
					for(int i = 0; i < steps; i++) {
						if(startD.equals(Direction.E)) {
							c.setX(c.getX() + 1);
						} else if(startD.equals(Direction.W)) {
							c.setX(c.getX() - 1);
						}
						if(visited.contains(c)) {
							secondFound = true; 
							part2 = c; 	
							break;
						} else if(!visited.contains(c)) {
							visited.add(new Coordinate(c.getX(), c.getY()));
						} 
					}
				}
			}

			if(startD.equals(Direction.N) || startD.equals(Direction.S)) {
				Coordinate c = new Coordinate(start.getX(), start.getY());
				start = start.moveY(startD, steps);

				if(!secondFound) {
					for(int i = 0; i < steps; i++) {
						if(startD.equals(Direction.S)) {
							c.setY(c.getY() + 1);
						} else if(startD.equals(Direction.N)) {
							c.setY(c.getY() - 1);
						}
						if(visited.contains(c)) {
							secondFound = true; 
							part2 = c; 	
							break;
						} else if(!visited.contains(c)) {
							visited.add(new Coordinate(c.getX(), c.getY()));
						}
					}
				}
			}
		}
		
		res[0] = Math.abs(start.getX()) + Math.abs(start.getY()); 
		res[1] = Math.abs(part2.getX()) + Math.abs(part2.getY());
		return res;
	}
}
