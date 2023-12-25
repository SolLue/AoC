package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class DayTwo {

	static int sum = 0;
	static int sum1 = 0; 
	
	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day2.txt");
		Scanner scan = new Scanner(fr);
		
	
		
		while(scan.hasNextLine()) {
			String temp = scan.nextLine();
			String id = temp.substring(temp.indexOf(" "), temp.indexOf(":"));
			id = id.trim();
			temp = temp.substring(temp.indexOf(":") + 2);
			
			String[] sets = temp.split(";");
			
			int blue = 0; 
			int red = 0; 
			int green = 0; 

			int maxblue = 0; 
			int maxred = 0; 
			int maxgreen = 0;			
			
			boolean flag = true; 
			
			for (String string : sets) {
				string = string.trim();

				String[] colours = string.split(",");
				for (String c : colours) {
					c = c.trim(); 		
					if(c.contains("blue")) {
						blue += Integer.parseInt(c.substring(0, c.indexOf(" ")));
						if(Integer.parseInt(c.substring(0, c.indexOf(" "))) > maxblue) {
							maxblue = Integer.parseInt(c.substring(0, c.indexOf(" "))); 
						}
					} else if(c.contains("red")) {
						red += Integer.parseInt(c.substring(0, c.indexOf(" ")));
						if(Integer.parseInt(c.substring(0, c.indexOf(" "))) > maxred) {
							maxred = Integer.parseInt(c.substring(0, c.indexOf(" "))); 
						}
					} else if(c.contains("green")) {
						green += Integer.parseInt(c.substring(0, c.indexOf(" ")));
						if(Integer.parseInt(c.substring(0, c.indexOf(" "))) > maxgreen) {
							maxgreen = Integer.parseInt(c.substring(0, c.indexOf(" "))); 
						}
					}
				}
				
				if(red > 12 || green > 13 || blue > 14) {
					flag = false; 
				}	
				blue = 0; red = 0; green = 0; 
				
			}
			
			//12 red cubes, 13 green cubes and 14 blue cubes
			if(flag) {
				sumUp(id);
			}
			
			multiplyUp(maxred, maxblue, maxgreen);
			
			
			
			
		}
		
		System.out.println("Day two, part one: " + sum);
		System.out.println("Day two, part two: " + sum1);

		scan.close();
	}
	
	public static void sumUp(String text) {
		int parsing = Integer.parseInt(text);
		sum += parsing;
	}
	
	public static void multiplyUp(int r, int b, int g) {
		sumUp(r * b * g);
	}
	
	public static void sumUp(int power) {
		sum1 += power;
	}
	
}
