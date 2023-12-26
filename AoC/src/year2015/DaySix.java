package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import utility.Coordinate;
import utility.Property;

public class DaySix {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day6.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String tString = br.readLine();
		while(tString != null) {
			input.add(tString);
			tString = br.readLine();
		}
		br.close();

		int countLights = christmasLights1(input);
		System.out.println("Day Six, Part One: " + countLights);

		int countBrightness = christmasLights2(input);
		System.out.println("Day Six, Part Two: " + countBrightness);
	}

	static int christmasLights1(List<String> input) {
		Light[][] lights = new Light[1000][];
		for(int i = 0; i < lights.length; i++) {
			lights[i] = new Light[1000];
			for(int j = 0; j < lights[i].length; j++) {
				lights[i][j] = new Light(i, j, false);
			}
		}
		for (String temp : input) {
			int xstart = 0; 
			int ystart = 0; 
			int xend = 0; 
			int yend = 0; 
			String instructions = ""; 
			String sub = temp;
			if(temp.contains("toggle")) {
				instructions = "toggle";
				sub = sub.substring(temp.indexOf(" ") + 1).trim();
			} else if(temp.contains("turn on")) {
				instructions = "on";
				sub = sub.substring(temp.indexOf("on") + 3).trim();
			} else if(temp.contains("turn off")) {
				instructions = "off";
				sub = sub.substring(temp.indexOf("off") + 4).trim();
			}
			xstart = Integer.parseInt(sub.substring(0, sub.indexOf(",")));
			sub = sub.substring(sub.indexOf(",") + 1);
			ystart = Integer.parseInt(sub.substring(0, sub.indexOf(" ")));
			sub = sub.substring(sub.indexOf("through") + 7).trim();

			xend = Integer.parseInt(sub.substring(0, sub.indexOf(",")));
			sub = sub.substring(sub.indexOf(",") + 1);
			yend = Integer.parseInt(sub.substring(0));

			for(int i = ystart; i <= yend; i++) {
				for(int j = xstart; j <= xend; j++) {
					if(instructions.equals("toggle")) {
						lights[i][j].toggle(); 						
					} else if(instructions.equals("on")) {
						lights[i][j].on = true; 						
					} else if(instructions.equals("off")) {
						lights[i][j].on = false; 						
					}
				}	
			}
		}
		int countLights = 0;
		for(int i = 0; i < lights.length; i++) {
			for(int j = 0; j < lights[i].length; j++) {
				if(lights[i][j].on == true)
					countLights++;
			}	
		}
		return countLights;
	}

	static int christmasLights2(List<String> input) {
		Light[][] lights = new Light[1000][];
		for(int i = 0; i < lights.length; i++) {
			lights[i] = new Light[1000];
			for(int j = 0; j < lights[i].length; j++) {
				lights[i][j] = new Light(i, j, 0);
			}
		}
		for (String temp : input) {
			int xstart = 0; 
			int ystart = 0; 
			int xend = 0; 
			int yend = 0; 
			String instructions = ""; 
			String sub = temp;
			if(temp.contains("toggle")) {
				instructions = "toggle";
				sub = sub.substring(temp.indexOf(" ") + 1).trim();
			} else if(temp.contains("turn on")) {
				instructions = "on";
				sub = sub.substring(temp.indexOf("on") + 3).trim();
			} else if(temp.contains("turn off")) {
				instructions = "off";
				sub = sub.substring(temp.indexOf("off") + 4).trim();
			}
			xstart = Integer.parseInt(sub.substring(0, sub.indexOf(",")));
			sub = sub.substring(sub.indexOf(",") + 1);
			ystart = Integer.parseInt(sub.substring(0, sub.indexOf(" ")));
			sub = sub.substring(sub.indexOf("through") + 7).trim();

			xend = Integer.parseInt(sub.substring(0, sub.indexOf(",")));
			sub = sub.substring(sub.indexOf(",") + 1);
			yend = Integer.parseInt(sub.substring(0));

			for(int i = ystart; i <= yend; i++) {
				for(int j = xstart; j <= xend; j++) {
					if(instructions.equals("toggle")) {
						lights[i][j].toggle2(); 						
					} else if(instructions.equals("on")) {
						lights[i][j].on(); 						
					} else if(instructions.equals("off")) {
						if(lights[i][j].brightness > 0)
							lights[i][j].off(); 						
					}
				}	
			}
		}
		int countBrightness = 0;
		for(int i = 0; i < lights.length; i++) {
			for(int j = 0; j < lights[i].length; j++) {
				countBrightness += lights[i][j].brightness;
			}	
		}
		return countBrightness;
	}

	static class Light extends Coordinate {
		boolean on; 
		int brightness;
		Light(int x, int y, boolean b) {
			super(x, y);
			this.on = b; 
		}
		Light(int x, int y, int b) {
			super(x, y);
			this.brightness = b; 
		}
		void on() {
			this.brightness++;
		}
		void off() {
			this.brightness--;
		}
		void toggle2() {
			this.brightness += 2;
		}
		void toggle() {
			this.on = !on;
		}
	}
}
