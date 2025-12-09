package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utility.Property;

public class DayNine {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day9.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis();

		List<Coordinates> coords = new ArrayList<Coordinates>();
		for (String string : input) {
			String[] arr = string.split(",");
			Coordinates c = new Coordinates(Long.parseLong(arr[0]), Long.parseLong(arr[1]));
			coords.add(c);
		}
		
		Set<Rectangle> possibleRec = new HashSet<Rectangle>();
		for (int i = 0; i < coords.size(); i++) {
			for (int j = 0; j < coords.size(); j++) {
				if (i != j) {
					Rectangle r = new Rectangle(coords.get(i), coords.get(j));
					possibleRec.add(r);
				}
			}
		}

		long max_area = 0;
		for (int i = 0; i < coords.size(); i++) {
			for (int j = 0; j < coords.size(); j++) {
				long length = Math.abs(coords.get(i).x - coords.get(j).x) + 1;
				long height = Math.abs(coords.get(i).y - coords.get(j).y) + 1;

				long area = length * height;
				if (area > max_area)
					max_area = area;
			}
		}
		
		long stopTime = System.currentTimeMillis();
		
		System.out.println("Day Nine, Part One: " + max_area);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis();

		List<Rectangle> polyToRec = new ArrayList<Rectangle>();
		for (int i = 0; i < coords.size(); i++) {
			Rectangle r = new Rectangle(coords.get(i), coords.get((i + 1) % coords.size()));
			polyToRec.add(r);
		}

		max_area = 0;
		for (Rectangle rectangle : possibleRec) {
			boolean overlaps = true;
			for (int i = 0; i < polyToRec.size(); i++) {
				overlaps = rectangle.overlapping(polyToRec.get(i));
				if (!overlaps) {
					overlaps = false;
				} else
					break;
			}
			if (!overlaps) {
				long current = rectangle.area();
				if (current > max_area)
					max_area = current;
			}
		}

		stopTime = System.currentTimeMillis();
		System.out.println("Day Nine, Part Two: " + max_area);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	record Coordinates(long x, long y) {}

	static class Rectangle {
		Coordinates min;
		Coordinates max;

		Rectangle(Coordinates c, Coordinates c1) {
			this.min = new Coordinates(Math.min(c.x, c1.x), Math.min(c.y, c1.y));
			this.max = new Coordinates(Math.max(c.x, c1.x), Math.max(c.y, c1.y));
		}

		static Rectangle fromCoords(Coordinates c, Coordinates c1) {
			return new Rectangle(new Coordinates(Math.min(c.x, c1.x), Math.min(c.y, c1.y)),
					new Coordinates(Math.max(c.x, c1.x), Math.max(c.y, c1.y)));
		}

		boolean overlapping(Rectangle r) {
			return this.min.x < r.max.x && this.max.x > r.min.x && this.min.y < r.max.y && this.max.y > r.min.y;
		}

		long area() {
			return (this.max.x - this.min.x + 1) * (this.max.y - this.min.y + 1);
		}

		@Override
		public String toString() {
			return "[" + this.min + " " + this.max + "]";
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Rectangle) {
				Rectangle r = (Rectangle) o;
				return this.min.x == r.min.x && this.min.y == r.min.y && this.max.x == r.max.x && this.max.y == r.max.y;
			}
			return false;
		}

		@Override
		public int hashCode() {
			long hash = this.min.x + this.min.y + this.max.x + this.max.y;
			return (int) hash;
		}
	}
}
