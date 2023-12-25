package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import utility.Coordinate;

public class DayTwentyOne {

	static char[][] input;
	static int sum = 0; 

	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day21.txt");
		Scanner scan = new Scanner(fr);

		List<String> in = new ArrayList<String>(); 
		while(scan.hasNextLine()) {
			in.add(scan.nextLine());
		}
		scan.close();

		input = new char[in.size()][];

		int x = 0; int y = 0; 
		for(int i = 0; i < in.size(); i++) {
			input[i] = in.get(i).toCharArray();
			if(in.get(i).contains("S")) {
				y = i; 
				int counter = 0; 
				for (char c : input[i]) {
					if(c == 'S')
						x = counter; 
					counter++;
				}
			}
		}
		
		
		//starting point S
		//garden plots . 
		//rocks #
		
		for(int i = 0; i < input.length; i++) {
			for(int j = 0; j < input[i].length; j++) {
				
				
			}
		}
		
		System.out.println(x + " " + y);

//		int amount = 6; 
	//	traversePath(, x, y);



		System.out.println("Day TwentyOne, Part Two: " + 0);

		System.out.println("Day TwentyOne, Part Two: " + 0);

	}

    static boolean bfs(Point start, int steps) {
        Set<Point> visited = new HashSet<>();
        Queue<Point> adjacent = new LinkedList<>();
        adjacent.add(start);
        int stepstaken = 0; 
        
        while (!adjacent.isEmpty()) {
            Point current = adjacent.remove();
            if (stepstaken == steps) {
                return true;
            }
            for (int i = 0; i < current.children.size(); i++) {
                Point adjacentPoint = current.children.get(i);
                if (!visited.contains(adjacentPoint)) {
                    adjacent.add(current.children.get(i));
                }
                stepstaken++;
            }
            visited.add(current);
        }

        return false;
    }

	static void sumUp(int s) {
		sum += s;
	}

	static class Point extends Coordinate {
		char type; 
		List<Point> children;

		Point(int x, int y, char t) {
			super(x, y);
			this.type = t; 
			this.children = new ArrayList<Point>();
		}
		
		boolean isObstacle() {
			return (this.type == '#');
		}
	}
}
