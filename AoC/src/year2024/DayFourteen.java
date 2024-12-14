package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utility.Property;

public class DayFourteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day14.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		List<Robot> robots = resetRobots(input);

		int wide = 101; 
		int tall = 103; 
		int seconds = 100; 
		int count = 0; 
		while (count != seconds) {
			for (Robot robot : robots) {
				robot.move(wide, tall);
			}
			count++;
		}

		int upleft = 0; 
		int upright = 0; 
		int downleft = 0;
		int downright = 0; 

		for (Robot robot : robots) {
			if(robot.pos.y < Math.floor(tall / 2) && robot.pos.x < Math.floor(wide / 2))
				upleft++; 
			if(robot.pos.y < Math.floor(tall / 2) && robot.pos.x > Math.floor(wide / 2))
				upright++; 
			if(robot.pos.y > Math.floor(tall / 2) && robot.pos.x < Math.floor(wide / 2))
				downleft++; 
			if(robot.pos.y > Math.floor(tall / 2) && robot.pos.x > Math.floor(wide / 2))
				downright++; 
		}

		long safetyfactor = upleft * upright * downleft * downright;

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Fourteen, Part One: " + safetyfactor);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		robots.clear();
		robots = resetRobots(input);

		char[][] grid = new char[tall][wide];
		for(int i = 0; i < tall; i++) {
			for(int j = 0; j < wide; j++) {
				grid[i][j] = '.';
			}	
		}

		count = 0; 
		boolean notFound = true;
		while (notFound) {
			for (Robot robot : robots) {
				robot.move(wide, tall);
			}
			count++; 
			notFound = checkTree(grid, robots, wide, tall);
		}

		for(int i = 0; i < tall; i++) {
			for(int j = 0; j < wide; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Fourteen, Part Two: " + count);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static boolean checkTree(char[][] grid, List<Robot> robots, int wide, int tall) {
		boolean notFound = true;
		for(int i = 0; i < tall; i++) {
			for(int j = 0; j < wide; j++) {
				grid[i][j] = '.';
			}	
		}
		for (Robot robot : robots) {
			grid[robot.pos.y][robot.pos.x] = '#';
		}

		for(int i = 2; i < tall - 2; i++) {
			for(int j = 2; j < wide - 2; j++) {
				if(grid[i][j] == '#' && grid[i][j + 1] == '#') {
					if(grid[i + 1][j] == '#' && grid[i + 1][j + 1] == '#') {
						if(grid[i + 2][j] == '#' && grid[i + 2][j + 1] == '#') {
							if(grid[i + 1][j + 2] == '#' && grid[i + 2][j + 2] == '#') 
								notFound = false; 	
						}
					}
				}
			}
		}
		return notFound;
	}

	static List<Robot> resetRobots(List<String> input) {
		List<Robot> robots = new ArrayList<Robot>();

		for (String temp : input) {
			String[] split = temp.split("\\s+");
			String[] pos = split[0].split(",");
			String[] velos = split[1].split(",");

			pos[0] = pos[0].replaceAll("p=", "");
			velos[0] = velos[0].replaceAll("v=", "");

			int x = Integer.parseInt(pos[0]);
			int y = Integer.parseInt(pos[1]);
			int vx = Integer.parseInt(velos[0]);
			int vy = Integer.parseInt(velos[1]);
			Robot rob = new Robot(x, y, vx, vy);
			robots.add(rob);
		}
		return robots; 
	}

	static class Robot {
		Position pos; 
		int velocityx;
		int velocityy; 

		Robot(int x, int y, int vx, int vy) {
			this.pos = new Position(x, y); 
			this.velocityx = vx; 
			this.velocityy = vy; 
		}
		void move(int wide, int tall) {
			int newX = 0; 
			int newY = 0; 
			if(this.velocityx > 0) {
				if (this.pos.x + this.velocityx < wide) {
					newX = this.pos.x + this.velocityx;
				} else {
					newX = ((this.pos.x + this.velocityx) - wide);
				}
			} else {
				if (this.pos.x + this.velocityx < 0) {
					newX = (wide + (this.pos.x + this.velocityx));
				} else {
					newX = this.pos.x + this.velocityx;
				}
			}

			if (this.velocityy > 0) {
				if (this.pos.y + this.velocityy < tall) {
					newY = this.pos.y + this.velocityy;
				} else { 
					newY = ((this.pos.y + this.velocityy) - tall);
				}
			} else {
				if (this.pos.y + this.velocityy < 0) {
					newY = (tall + (this.pos.y + this.velocityy));
				} else {
					newY = this.pos.y + this.velocityy;
				}
			}
			this.pos.x = newX; 
			this.pos.y = newY; 
		}
	}
	static class Position {
		int x;
		int y;

		Position(int i, int j) {
			this.x = i;
			this.y = j;
		}

		public String toString() {
			return "[" + this.x + " / " + this.y + "] ";
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			Position tmp = (Position) o;
			if (this.x == tmp.x && this.y == tmp.y)
				return true;
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}
}
