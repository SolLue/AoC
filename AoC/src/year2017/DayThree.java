package year2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2017/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		int input = Integer.parseInt(br.readLine());
		br.close();


		// Needed: coords of input
		int layer = 0;	
		int layerMax = 0;
		int layerMin = 0;
		int iterate = 0; 
		boolean findLayer = true;
		do {
			layerMax = (int)Math.pow(2 * iterate + 1, 2);
			layerMin = (int)Math.pow(2 * iterate - 1, 2);
			if(layerMin <= input && layerMax >= input) {
				//System.out.println("hurray");
				findLayer = false;
				layer = iterate; 
			} else {
				//System.out.println(layerMin + " " + layerMax);
				iterate++; 
			}
		} while(findLayer);

		System.out.println(layer);
		int middle = (int)Math.ceil((8 * layer / 4) / 2); 
			
		
		
		System.out.println(middle);
		
		int xstart = 0;
		int ystart = 0; 
		int xgoal = 0; 
		int ygoal = 0; 
		int distance = Math.abs(xstart - xgoal) + Math.abs(ystart - ygoal);

		System.out.println("Day Three, Part One: " + distance);


		System.out.println("Day Three, Part Two: " + 0);

	}

}
