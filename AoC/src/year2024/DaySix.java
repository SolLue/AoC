package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import utility.Property;

public class DaySix {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day6.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		char[][] area = createArea(input);
		Map<Coordinates, Boolean> obstacles = createObstacles(area);
		Guard guard = placeGuard(area);
		Map<Coordinates, Boolean> exploding = new HashMap<Coordinates, Boolean>();

		while(guard.inRange(area)) {
			if(!obstacles.containsKey(guard.getNext())) {
				area[guard.coordinates.y][guard.coordinates.x] = 'X';	

				if (guard.direction == '^') {
					if (guard.coordinates.y - 1 >= 0)
						exploding.put(new Coordinates(guard.coordinates.x, guard.coordinates.y - 1), false);
				} else if (guard.direction == 'v') { 
					if (guard.coordinates.y + 1 < area.length)
						exploding.put(new Coordinates(guard.coordinates.x, guard.coordinates.y + 1), false);
				} else if (guard.direction == '>') {
					if (guard.coordinates.x + 1 < area[0].length)
						exploding.put(new Coordinates(guard.coordinates.x + 1, guard.coordinates.y), false);
				} else if (guard.direction == '<')
					if (guard.coordinates.x - 1 >= 0)
						exploding.put(new Coordinates(guard.coordinates.x - 1, guard.coordinates.y), false);

				guard.move();
			} else {
				guard.changeDirection();
			}
		}

		int distinctPositions = 0;
		for (char[] cs : area) {
			for(char c : cs) {
				if (c == 'X')
					distinctPositions++;
			}
		}		
		long stopTime = System.currentTimeMillis();

		System.out.println("Day Six, Part One: " + distinctPositions);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		List<Coordinates >possibleObstacles = new ArrayList<Coordinates>();
		area = createArea(input);
		Guard placeholder = placeGuard(area);

		if (exploding.containsKey(guard.coordinates)) {
			exploding.remove(guard.coordinates);
		}

		for (Coordinates cords : obstacles.keySet()) {
			if (exploding.containsKey(cords))
				exploding.remove(cords);
		}		

		for (Coordinates cords : exploding.keySet()) {
			Coordinates newObstacle = new Coordinates(cords.x, cords.y);
			obstacles.put(newObstacle, false);

			guard = new Guard('^', new Coordinates(placeholder.coordinates.x, placeholder.coordinates.y));

			boolean loop = testForLoop(area, guard, obstacles);
			if (!loop) {
				obstacles.remove(newObstacle);
			} else {
				possibleObstacles.add(newObstacle);
				obstacles.remove(newObstacle);
			}
		}
		stopTime = System.currentTimeMillis();
		System.out.println("Day Six, Part Two: " + possibleObstacles.size());
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static boolean testForLoop(char[][] area, Guard guard, Map<Coordinates, Boolean> obstacles) {
		Map<Coordinates, Integer> timesVisited = new HashMap<Coordinates, Integer>();
		boolean loop = false; 
		while(guard.inRange(area) && !loop) {
			if(!obstacles.containsKey(guard.getNext())) {
				Coordinates temp = new Coordinates(guard.coordinates.x, guard.coordinates.y);
				if (timesVisited.containsKey(temp)) {
					int times = timesVisited.get(temp);
					times += 1; 
					timesVisited.put(temp, times);
					if (times > 4) {
						loop = true; 
					}
				} else {
					timesVisited.put(temp , 1);							
				}
				guard.move();
			} else {
				guard.changeDirection();
			}
		}
		return loop; 
	}

	static char[][] createArea(List<String> input){
		char[][] area = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			char[] t = input.get(i).toCharArray();
			area[i] = t;
		}
		return area; 
	}

	static Map<Coordinates, Boolean> createObstacles(char[][] area) {
		Map<Coordinates, Boolean> obstacles = new HashMap<Coordinates, Boolean>();
		for (int i = 0; i < area.length; i++) {
			for (int j = 0; j < area[0].length; j++) {
				if (area[i][j] == '#') {
					Coordinates coord = new Coordinates(j, i);
					obstacles.put(coord, true);
				} 
			}
		}
		return obstacles; 
	}

	static Guard placeGuard(char[][] area) {
		Coordinates temp = null;
		for (int i = 0; i < area.length; i++) {
			for (int j = 0; j < area[0].length; j++) {
				if (area[i][j] == '^') 
					temp = new Coordinates(j, i);
			}
		}
		Guard guard = new Guard('^', new Coordinates(temp.x, temp.y));
		return guard;
	}

	static class Guard {
		char direction; 
		Coordinates coordinates; 

		Guard(Coordinates c) {
			this.coordinates = c;
			this.direction = '^';
		}
		Guard(char d, Coordinates c) {
			this.direction = d; 
			this.coordinates = c;
		}

		void changeDirection() {
			if (this.direction == '^') 
				this.direction = '>';
			else if (this.direction == '>')
				this.direction = 'v';
			else if (this.direction == 'v')
				this.direction = '<';
			else if (this.direction == '<')
				this.direction = '^';
		}
		void move() {
			if (this.direction == '^') 
				this.coordinates.moveY(-1);
			else if (this.direction == '>')
				this.coordinates.moveX(1);
			else if (this.direction == 'v')
				this.coordinates.moveY(1);
			else if (this.direction == '<')
				this.coordinates.moveX(-1);
		}

		Coordinates getNext() {
			if (this.direction == '^') 
				return new Coordinates(this.coordinates.x, this.coordinates.y - 1);
			else if (this.direction == '>')
				return new Coordinates(this.coordinates.x + 1, this.coordinates.y);
			else if (this.direction == 'v')
				return new Coordinates(this.coordinates.x, this.coordinates.y + 1);
			else if (this.direction == '<')
				return new Coordinates(this.coordinates.x - 1, this.coordinates.y);
			return null;
		}

		boolean inRange(char[][] area) {
			return this.coordinates.x >= 0 && this.coordinates.x < area[0].length 
					&& this.coordinates.y >= 0 && this.coordinates.y < area.length;
		}
	}

	static class Coordinates{
		int x; 
		int y; 
		Coordinates(int i, int j) {
			this.x = i; 
			this.y = j;
		}
		void moveX(int i) {
			this.x += i;
		}
		void moveY(int i) {
			this.y += i;
		}
		@Override
		public boolean equals(Object o) {
			if(this == o)
				return true;
			if(o == null)
				return false;
			if(!(o instanceof Coordinates))
				return false;
			Coordinates tmp = (Coordinates) o;

			return this.x == tmp.x && this.y == tmp.y;
		}
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
		@Override
		public String toString() {
			return "[" + this.x + "/" + this.y + "]";
		}
	}
}
