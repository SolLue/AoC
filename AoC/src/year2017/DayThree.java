package year2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2017/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		int input = Integer.parseInt(br.readLine());
		br.close();

		int layer = getLayer(input);
		int index = getIndex(input, layer);
		// North 1, East 0, South 3, West 2
		int side = getSide(index, layer);

		int middle = (2 * side + 1) * layer;
		int distanceMiddle = index - middle;		
		int distance = Math.abs(distanceMiddle) + layer;

		System.out.println("Day Three, Part One: " + distance);

		Map<Integer, Cell> cells = getAGrid(input);
		Map<Cell, Integer> cellsValues = new HashMap<Cell, Integer>();

		cellsValues.put(cells.get(1), 1);
		cellsValues.put(cells.get(2), 1);

		int currentMax = 1;
		int currentIndex = 3;

		while(currentMax < input) {
			Cell current = cells.get(currentIndex);
			int value = getValue(current, cellsValues);
			cellsValues.put(current, value);
			currentIndex++;
			currentMax = value;
		}


		System.out.println("Day Three, Part Two: " + currentMax);

	}

	static int getValue(Cell current, Map<Cell, Integer> cellValue) {
		Cell n = new Cell(current.x, current.y - 1);
		Cell w = new Cell(current.x - 1, current.y);
		Cell s = new Cell(current.x, current.y + 1);
		Cell e = new Cell(current.x + 1, current.y);
		Cell nw = new Cell(current.x - 1, current.y - 1);
		Cell ne = new Cell(current.x + 1, current.y - 1);
		Cell sw = new Cell(current.x - 1, current.y + 1);
		Cell se = new Cell(current.x + 1, current.y + 1);

		List<Cell> dir = Arrays.asList(n, w, s, e, nw, ne, sw, se);
		int value = 0;
		for(Cell cell : dir) {
			value += cellValue.getOrDefault(cell, 0);
		}

		return value;
	}


	static Map<Integer, Cell> getAGrid(int input) {
		Map<Integer, Cell> cells = new HashMap<Integer, Cell>();
		Cell currentPos = new Cell(0, 0);
		int currentValue = 1;
		Direction direction = Direction.RIGHT;
		cells.put(currentValue++, currentPos);

		int move = 1;
		int stepCounter = 0;

		while (currentValue < input + 10) {

			for (int i = 0; i < move; i++) {
				currentPos = nextCell(direction, currentPos);
				cells.put(currentValue++, currentPos);
			}
			stepCounter++;
			direction = getNextDirection(direction);
			if (stepCounter % 2 == 0) {
				move++;
			}

		}
		return cells;
	}

	enum Direction { UP, DOWN, LEFT, RIGHT };

	static Cell nextCell(Direction dir, Cell current) {
		Cell next = current;

		switch (dir) {
		case UP:
			next = new Cell(current.x, current.y - 1);
			break;
		case DOWN:
			next = new Cell(current.x, current.y + 1);
			break;
		case LEFT:
			next = new Cell(current.x - 1, current.y);
			break;
		case RIGHT:
			next = new Cell(current.x + 1, current.y);
			break;
		}
		return next;

	}

	static Direction getNextDirection(Direction dir) {
		Direction nextDir = dir;
		switch (dir) {
		case UP:
			nextDir = Direction.LEFT;
			break;
		case DOWN:
			nextDir = Direction.RIGHT;
			break;
		case LEFT:
			nextDir = Direction.DOWN;
			break;
		case RIGHT:
			nextDir = Direction.UP;
			break;
		}
		return nextDir;
	}

	static int getSide(int index, int layer) {
		if (layer == 0)
			return 0;
		// North 1, East 0, South 3, West 2
		return index / (2 * layer);
	}

	static int getIndex(int in, int layer) {
		return in - (int)Math.pow((2 * layer - 1), 2);
	}

	static int getLayer(int in) {
		int layer = 0;	
		int layerMax = 0;
		int layerMin = 0;
		int iterate = 0; 
		boolean findLayer = true;
		do {
			layerMax = (int)Math.pow(2 * iterate + 1, 2);
			layerMin = (int)Math.pow(2 * iterate - 1, 2);
			if(layerMin <= in && layerMax >= in) {
				findLayer = false;
				layer = iterate; 
			} else {
				iterate++; 
			}
		} while(findLayer);
		return layer;
	}

	static class Cell {
		int x; 
		int y; 
		Cell(int xs, int ys) {
			this.x = xs;
			this.y = ys;
		}

		@Override
		public boolean equals(Object o) {
			if(this == o)
				return true;
			if(o == null)
				return false;
			if(!(o instanceof Cell))
				return false;

			Cell tmp = (Cell) o;

			return this.x == tmp.x && this.y == tmp.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

	}
}
