package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class TwentyTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day22.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 


		
		
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyTwo, Part One: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 
		
		
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyTwo, Part Two: " + 0);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	static class Node {
		int x; 
		int y; 
		Node right; 
		Node left; 
		int use; 
		
		Node(int xx, int yy, int u) {
			this.x = xx; 
			this.y = yy; 
			this.use = u; 
		}
	}
}
