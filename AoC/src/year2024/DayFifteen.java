package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import utility.Property;

public class DayFifteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day15.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> gridinput = new ArrayList<String>();
		List<String> moveinput = new ArrayList<String>();

		String temp = br.readLine();
		while(temp != null) {
			if(temp.contains("#"))
				gridinput.add(temp);
			else if(!temp.isBlank()){
				moveinput.add(temp);
			}
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		Robot robot = null;
		char[][] grid = new char[gridinput.size()][];
		for (int i = 0; i < gridinput.size(); i++) {
			grid[i] = gridinput.get(i).toCharArray();
			if(gridinput.get(i).contains("@")) {
				robot = new Robot(i, gridinput.get(i).indexOf("@"));	
			}
		}

		int gps = moveAndCalculateBoxLocation(moveinput, grid, 'O', robot, 1);

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Fifteen, Part One: " + gps);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		robot = null;
		char[][] gridDouble = new char[gridinput.size()][];
		for (int i = 0; i < gridinput.size(); i++) {
			char[] arr = gridinput.get(i).toCharArray();
			gridDouble[i] = new char[arr.length * 2];
			int count = 0; 
			for (int j = 0; j < arr.length; j++) {
				if(arr[j] == '@') {
					gridDouble[i][count + j] = arr[j];
					gridDouble[i][count + j + 1] = '.';	
					robot = new Robot(i, count + j);
				} else if (arr[j] == 'O'){
					gridDouble[i][count + j] = '[';
					gridDouble[i][count + j + 1] = ']';					
				} else {
					gridDouble[i][count + j] = arr[j];
					gridDouble[i][count + j + 1] = arr[j];
				}
				count++;
			}
		}

		gps = moveAndCalculateBoxLocation(moveinput, gridDouble, '[', robot, 2);

		stopTime = System.currentTimeMillis();
		System.out.println("Day Fifteen, Part Two: " + gps);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static int moveAndCalculateBoxLocation(List<String> moveinput, char[][] grid, char target,
			Robot robot, int part) {
		for (String string : moveinput) {
			for (char c : string.toCharArray()) {
				if (part == 1)
					robot.move(c, grid);
				if (part == 2)
					robot.moveDouble(c, grid);
			}
		}
		int gps = 0;
		for (int i = 0; i < grid.length; i++ ) {
			for (int j = 0; j < grid[i].length; j++ ) {			
				if(grid[i][j] == target)
					gps = gps + (i * 100) + j;
			}		
		}
		return gps;
	}

	static class Robot {
		Position pos; 

		Robot(int x, int y) {
			this.pos = new Position(x, y); 
		}

		Set<Position> checkSpace(char[][] grid, char move, int xchange, int i, int change) {
			Set<Position> positions = new HashSet<Position>();
			if(grid[i][this.pos.y] == '[') {
				positions.add(new Position(i, this.pos.y));
				positions.add(new Position(i, this.pos.y + 1));
			} else if (grid[i][this.pos.y] == ']') {
				positions.add(new Position(i, this.pos.y));
				positions.add(new Position(i, this.pos.y - 1));
			}
			Set<Position> temp = new HashSet<Position>(positions);

			boolean allFound = false; 
			Set<Position> newOnes = new HashSet<Position>();
			while (!allFound) {
				for(Position pos : temp) {						
					if(grid[pos.x][pos.y] == '[' ) {
						if(grid[pos.x + xchange][pos.y] == ']') {
							newOnes.add(new Position(pos.x + xchange, pos.y));
							newOnes.add(new Position(pos.x + xchange, pos.y - 1));
						} else if(grid[pos.x + xchange][pos.y] == '[') {
							newOnes.add(new Position(pos.x + xchange, pos.y));
							newOnes.add(new Position(pos.x + xchange, pos.y + 1));
						}
					} else if(grid[pos.x][pos.y] == ']' ) {
						if(grid[pos.x + xchange][pos.y] == '[') {
							newOnes.add(new Position(pos.x + xchange, pos.y));
							newOnes.add(new Position(pos.x + xchange, pos.y + 1));
						} else if(grid[pos.x + xchange][pos.y] == ']') {
							newOnes.add(new Position(pos.x + xchange, pos.y));
							newOnes.add(new Position(pos.x + xchange, pos.y - 1));
						}
					} 
				} 
				if (newOnes.size() == 0) {
					allFound = true;	
				}
				temp = new HashSet<Position>(newOnes);
				positions.addAll(temp);
				newOnes.clear();
			}
			return positions;
		}

		boolean checkMoveOk(Set<Position> pos, char[][] grid, int x, int y) {
			boolean freeSpace = true;
			for (Position position : pos) {
				if (grid[position.x + x][position.y + y] == '#')
					freeSpace = false; 
				if (grid[position.x + x][position.y + y] == '[' || grid[position.x + x][position.y + y] == ']') {
					boolean found = false;
					for (Position position2 : pos) {
						if (position2.x == position.x + x && position2.y == position.y)
							found = true;
					}
					if(!found)
						freeSpace = false;
				}
			}
			return freeSpace;
		}

		void movePart2(char[][] grid, Set<Position> pos, int x, int y) {
			List<Position> po = new ArrayList<Position>(pos);
			if (x < 0)
				po = po.stream().sorted(new PositionComparator()).collect(Collectors.toList());
			if (x > 0)
				po = po.stream().sorted(new PositionComparator().reversed()).collect(Collectors.toList());		

			for (Position position : po) {
				if (grid[position.x + x][position.y] == '.') {
					if(grid[position.x][position.y] == '[') {
						grid[position.x][position.y] = '.';
						grid[position.x + x][position.y] = '[';
					}	else {
						grid[position.x][position.y] = '.';
						grid[position.x + x][position.y] = ']';
					}
				}
			}
			if (x < 0)
				this.pos.x--;
			if (x > 0)
				this.pos.x++;
		}

		void moveEasy(char[][] grid, int i, int y) {
			boolean freeSpace = true;
			while (grid[this.pos.x][i + y] == '[' || grid[this.pos.x][i + y] == ']') {
				if(grid[this.pos.x][i + (y * 2)] == '#') {
					freeSpace = false; 
					break;
				}
				i += y;
			}
			if(freeSpace) {
				if (y > 0) {
					for (; i > this.pos.y; i--) {
						if(grid[this.pos.x][i] == '[') {
							grid[this.pos.x][i] = '.';
							grid[this.pos.x][i + 1] = '[';
							grid[this.pos.x][i + 2] = ']';
						} 
					}
					this.pos.y++;
				}
				else if (y < 0) {
					for (; i < this.pos.y; i++) {
						if(grid[this.pos.x][i] == ']') {
							grid[this.pos.x][i] = '.';
							grid[this.pos.x][i - 1] = ']';
							grid[this.pos.x][i - 2] = '[';
						}
					}
					this.pos.y--;
				}
			}	

		}

		void moveDouble(char move, char[][] grid) {
			int x = this.pos.x; 
			int y = this.pos.y; 

			if (move == '^') {
				if (grid[this.pos.x - 1][this.pos.y] != '#') {
					if ((grid[this.pos.x - 1][this.pos.y] == '[' || grid[this.pos.x - 1][this.pos.y] == ']')) {
						int i = this.pos.x - 1;

						Set<Position> pos = checkSpace(grid, move, -1, i, y);
						boolean freeSpace = checkMoveOk(pos, grid, -1, 0);

						if (freeSpace)
							movePart2(grid, pos, -1, 0);
					} else {
						this.pos.x--;
					}
				}
			} else if (move == '>') {
				if (grid[this.pos.x][this.pos.y + 1] != '#') {
					if ((grid[this.pos.x][this.pos.y + 1] == '[' || grid[this.pos.x][this.pos.y + 1] == ']')) {
						int i = this.pos.y + 1;
						moveEasy(grid, i, 1);
					} else  
						this.pos.y++;
				}
			} else if (move == 'v') {
				if (grid[this.pos.x + 1][this.pos.y] != '#') {
					if ((grid[this.pos.x + 1][this.pos.y] == '[' || grid[this.pos.x + 1][this.pos.y] == ']')) {
						int i = this.pos.x + 1;

						Set<Position> pos = checkSpace(grid, move, 1, i, y);
						boolean freeSpace = checkMoveOk(pos, grid, 1, 0);

						if (freeSpace)
							movePart2(grid, pos, 1, 0);
					} else 
						this.pos.x++;
				}
			} else if (move == '<') {
				if (grid[this.pos.x][this.pos.y - 1] != '#') {
					if ((grid[this.pos.x][this.pos.y - 1] == '[' || grid[this.pos.x][this.pos.y - 1] == ']')) {
						int i = this.pos.y - 1;
						moveEasy(grid, i, -1);
					} else 
						this.pos.y--;
				}
			}
			grid[x][y] = '.';
			grid[this.pos.x][this.pos.y] = '@';			
		}

		void move(char move, char[][] grid) {
			int x = this.pos.x; 
			int y = this.pos.y; 

			if (move == '^') {
				if (grid[this.pos.x - 1][this.pos.y] != '#') {
					if (grid[this.pos.x - 1][this.pos.y] == 'O' && grid[this.pos.x - 2][this.pos.y] != '#') {
						int i = this.pos.x - 1;
						boolean freeSpace = true;
						while(grid[i - 1][this.pos.y] == 'O') {
							if(grid[i - 2][this.pos.y] == '#') {
								freeSpace = false; 
								break;
							}
							i--;
						}
						if(freeSpace) {
							for (; i < this.pos.x; i++) {
								grid[i][this.pos.y] = '.';
								grid[i - 1][this.pos.y] = 'O';
							}
							this.pos.x--;
						}	
					} else if (grid[this.pos.x - 1][this.pos.y] != 'O')
						this.pos.x--;
				}
			} else if (move == '>') {
				if (grid[this.pos.x][this.pos.y + 1] != '#') {
					if (grid[this.pos.x][this.pos.y + 1] == 'O' && grid[this.pos.x][this.pos.y + 2] != '#') {
						int i = this.pos.y + 1;
						boolean freeSpace = true;
						while(grid[this.pos.x][i + 1] == 'O') {
							if(grid[this.pos.x][i + 2] == '#') {
								freeSpace = false; 
								break;
							}
							i++;
						}
						if(freeSpace) {
							for (; i > this.pos.y; i--) {
								grid[this.pos.x][i] = '.';
								grid[this.pos.x][i + 1] = 'O';
							}
							this.pos.y++;
						}	
					} else if (grid[this.pos.x][this.pos.y + 1] != 'O') 
						this.pos.y++;
				}
			} else if (move == 'v') {
				if (grid[this.pos.x + 1][this.pos.y] != '#') {
					if (grid[this.pos.x + 1][this.pos.y] == 'O' && grid[this.pos.x + 2][this.pos.y] != '#') {
						int i = this.pos.x + 1;
						boolean freeSpace = true;
						while(grid[i + 1][this.pos.y] == 'O') {
							if(grid[i + 2][this.pos.y] == '#') {
								freeSpace = false; 
								break;
							}
							i++;
						}
						if(freeSpace) {
							for (; i > this.pos.x; i--) {
								grid[i][this.pos.y] = '.';
								grid[i + 1][this.pos.y] = 'O';
							}
							this.pos.x++;
						}	
					} else if (grid[this.pos.x + 1][this.pos.y] != 'O') 
						this.pos.x++;
				}
			} else if (move == '<') {
				if (grid[this.pos.x][this.pos.y - 1] != '#') {
					if (grid[this.pos.x][this.pos.y - 1] == 'O' && grid[this.pos.x][this.pos.y - 2] != '#') {
						int i = this.pos.y - 1;
						boolean freeSpace = true;
						while(grid[this.pos.x][i - 1] == 'O') {
							if(grid[this.pos.x][i - 2] == '#') {
								freeSpace = false; 
								break;
							}
							i--;
						}
						if(freeSpace) {
							for (; i < this.pos.y; i++) {
								grid[this.pos.x][i] = '.';
								grid[this.pos.x][i - 1] = 'O';
							}
							this.pos.y--;
						}	
					} else if(grid[this.pos.x][this.pos.y - 1] != 'O')
						this.pos.y--;
				}
			}
			grid[x][y] = '.';
			grid[this.pos.x][this.pos.y] = '@';
		}
	}

	static class PositionComparator implements Comparator<Position> {
		@Override
		public int compare(Position o1, Position o2) {
			return o1.x - o2.x;
		}
	}

	static class Position implements Comparable<Position> {
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

		@Override
		public int compareTo(Position o) {
			return this.x - o.x;
		}
	}
}
