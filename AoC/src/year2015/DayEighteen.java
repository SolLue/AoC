package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import utility.Coordinate;
import utility.Property;

public class DayEighteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day18.txt");
		BufferedReader br = new BufferedReader(fr);

		String t = br.readLine();
		List<String> input = new ArrayList<String>();
		while (t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		Light[][] lights = setUpLights(input);
		Light[][] lightcopy = setUpCopy(input.size());

		int christmas = christmasLights(lights, lightcopy);
		System.out.println("Day Eighteen, Part One: " + christmas);

		lights = setUpLights(input);
		lightcopy = setUpCopy(input.size());
		christmas = christmasLightsStuckCorners(lights, lightcopy);
		System.out.println("Day Eighteen, Part Two: " + christmas);
	}

	static Light[][] setUpLights(List<String> input) {
		Light[][] lights = new Light[input.size()][];
		for(int i = 0; i < lights.length; i++) {
			lights[i] = new Light[input.get(i).length()];
			for(int j = 0; j < lights[i].length; j++) {
				lights[i][j] = new Light(i, j, false);
			}
		}
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < input.get(i).length(); j++) {
				if(input.get(i).charAt(j) == '#') {
					lights[i][j].on = true; 
				}
			}
		}
		return lights; 
	}
	static Light[][] setUpCopy(int size) {
		Light[][] lightcopy = new Light[size][];
		for(int i = 0; i < lightcopy.length; i++) {
			lightcopy[i] = new Light[size];
			for(int j = 0; j < lightcopy[i].length; j++) {
				lightcopy[i][j] = new Light(i, j, false);
			}
		}
		return lightcopy;
	}

	static int christmasLights(Light[][] lights, Light[][] lightcopy) {
		for(int round = 0; round < 100; round++) {
			for(int i = 0; i < lights.length; i++) {
				for(int j = 0; j < lights[i].length; j++) {
					List<Light> neighbours = Light.getNeighboursOn(lights[i][j], lights);
					if(lights[i][j].on) {
						if(neighbours.size() == 2 || neighbours.size() == 3) {						
							lightcopy[i][j].on = true;
						} else {
							lightcopy[i][j].on = false;
						} 
					} else if(!lights[i][j].on) {
						if(neighbours.size() == 3) {
							lightcopy[i][j].on = true;
						} else {
							lightcopy[i][j].on = false;
						}
					}
				}
			}
			lights = cloneCopy(lights, lightcopy); 
			lightcopy = reset(lightcopy); 
		}

		List<Light> l = Arrays.stream(lights).flatMap(Arrays::stream).collect(Collectors.toList());
		l = l.stream().filter(e -> e.on).collect(Collectors.toList());

		return l.size();
	}

	static int christmasLightsStuckCorners(Light[][] lights, Light[][] lightcopy) {
		lights[0][0].on = true; 
		lights[lights.length - 1][0].on = true;
		lights[0][lights[0].length - 1].on = true; 
		lights[lights.length - 1][lights[0].length - 1].on = true;

		for(int round = 0; round < 100; round++) {
			for(int i = 0; i < lights.length; i++) {
				for(int j = 0; j < lights[i].length; j++) {
					List<Light> neighbours = Light.getNeighboursOn(lights[i][j], lights);
					if(lights[i][j].on) {
						if(neighbours.size() == 2 || neighbours.size() == 3) {						
							lightcopy[i][j].on = true;
						} else {
							lightcopy[i][j].on = false;
						} 
					} else if(!lights[i][j].on) {
						if(neighbours.size() == 3) {
							lightcopy[i][j].on = true;
						} else {
							lightcopy[i][j].on = false;
						}
					}
				}
			}

			lights = cloneCopy(lights, lightcopy); 
			lights[0][0].on = true; 
			lights[lights.length - 1][0].on = true;
			lights[0][lights[0].length - 1].on = true; 
			lights[lights.length - 1][lights[0].length - 1].on = true;
			lightcopy = reset(lightcopy); 
		}

		List<Light> l = Arrays.stream(lights).flatMap(Arrays::stream).collect(Collectors.toList());
		l = l.stream().filter(e -> e.on).collect(Collectors.toList());

		return l.size();
	}

	static Light[][] cloneCopy(Light[][] lights, Light[][] lightcopy) {
		for(int i = 0; i < lights.length; i++) {
			for(int j = 0; j < lights[i].length; j++) {
				lights[i][j].on = lightcopy[i][j].on; 
			}
		}
		return lights;
	}

	static Light[][] reset(Light[][] lightcopy) {
		for(int i = 0; i < lightcopy.length; i++)
			for(int j = 0; j < lightcopy[i].length; j++)
				lightcopy[i][j].on = false;
		return lightcopy;
	}

	static class Light extends Coordinate {
		boolean on; 
		Light(int x, int y, boolean b) {
			super(x, y);
			this.on = b; 
		}

		static List<Light> getNeighbours(Light c, Light[][] light) {
			List<Coordinate> temp = new ArrayList<>(); 
			temp.add(new Coordinate(c.getX(), c.getY() - 1)); //N
			temp.add(new Coordinate(c.getX() + 1, c.getY() - 1)); //NE
			temp.add(new Coordinate(c.getX() + 1, c.getY())); //E
			temp.add(new Coordinate(c.getX() + 1, c.getY() + 1)); //SE
			temp.add(new Coordinate(c.getX(), c.getY() + 1)); //S
			temp.add(new Coordinate(c.getX() - 1, c.getY() + 1)); //SW
			temp.add(new Coordinate(c.getX() - 1, c.getY())); //W
			temp.add(new Coordinate(c.getX() - 1, c.getY() - 1)); //NW

			List<Light> l = new ArrayList<>(); 
			temp = temp.stream().filter(e -> inBounds(e, light.length, light[1].length)).collect(Collectors.toList());
			for (Coordinate co : temp) {
				l.add(light[co.getX()][co.getY()]);
			}
			return l;
		}
		static boolean inBounds(Coordinate c, int rows, int col) {
			return (c.getX() >= 0 && c.getX() < rows) 
					&& (c.getY() >= 0 && c.getY() < col);
		}
		static List<Light> getNeighboursOn(Light c, Light[][] light) {	
			List<Light> lights = getNeighbours(c, light); 
			lights = lights.stream().filter(e -> e.on).collect(Collectors.toList());
			return lights;
		}
	}
}