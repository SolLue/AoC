package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayEight {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day8.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		Pixel[][] screen = new Pixel[6][];
		for (int i = 0; i < screen.length; i++) {
			screen[i] = new Pixel[50];
			for (int j = 0; j < screen[i].length; j++) {
				screen[i][j] = new Pixel();
			}
		}
		screen = followEncoding(input, screen);
		int countOn = countOn(screen);

		System.out.println("Day Eight, Part One: " + countOn);
		
		System.out.println("Day Eight, Part Two: " );
		for (Pixel[] pixels : screen) {
			for (Pixel p : pixels) {
				if(p.on)
					System.out.print("#");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
		
	}

	static int countOn(Pixel[][] screen) {
		int count = 0; 
		for(int i = 0; i < screen.length; i++) {
			for(int j = 0; j < screen[i].length; j++) {
				if(screen[i][j].on)
					count++;
			}	
		}
		return count; 
	}

	static Pixel[][] followEncoding(List<String> input, Pixel[][] screen) {
		for (String in : input) {
			if(in.startsWith("rect")) {
				String temp = in.substring(in.indexOf(" "));
				int wide = Integer.parseInt(temp.substring(0, temp.indexOf("x")).trim());
				int tall = Integer.parseInt(temp.substring(temp.indexOf("x") + 1));
				for(int i = 0; i < tall; i++) {
					for(int j = 0; j < wide; j++) {
						screen[i][j].on = true;
					}	
				}
			} 
			if(in.contains("rotate row")) {
				String temp = in.substring(in.indexOf("w") + 1).trim();
				int y = Integer.parseInt(temp.substring(temp.indexOf("=") + 1, temp.indexOf(" ")).trim());
				temp = temp.substring(temp.indexOf("by ") + 2);
				int rotate = Integer.parseInt(temp.trim());
				Pixel[] t = new Pixel[screen[y].length];
				for (int i = 0; i < screen[y].length; i++) {
					t[i] = new Pixel(screen[y][i].on);
				}
				for(int i = 0; i < screen[y].length; i++) {
					if(i + rotate < screen[y].length) {
						screen[y][i + rotate] = t[i];
					} else {
						int tempo = (i + rotate) % screen[y].length;
						screen[y][tempo] = t[i];
					}
				}
			}
			if(in.contains("rotate column")) {
				String temp = in.substring(in.indexOf("n ") + 1).trim();
				int x = Integer.parseInt(temp.substring(temp.indexOf("=") + 1, temp.indexOf(" ")).trim());
				temp = temp.substring(temp.indexOf("by ") + 2).trim();
				int rotate = Integer.parseInt(temp.trim());
				Pixel[] t = new Pixel[screen.length];
				for (int i = 0; i < screen.length; i++) {
					t[i] = new Pixel(screen[i][x].on);
				}
				for(int i = 0; i < screen.length; i++) {
					if(i + rotate < screen.length) {
						screen[i + rotate][x] = t[i];
					} else {
						int tempo = (i + rotate) % screen.length;
						screen[tempo][x] = t[i];
					}
				}
			}
		}
		return screen; 
	}


	static class Pixel {
		boolean on; 

		Pixel() {
			this.on = false; 
		}
		Pixel(boolean b) {
			this.on = b; 
		}
	}
}
