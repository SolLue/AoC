package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class TwentyOne {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day21.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> instructions = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			instructions.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 

		String password = "abcdefgh";

		String out = password; 
		for (String instruction : instructions) { 
			String[] arr = instruction.split(" ");
			if (instruction.contains("swap")) {
				if(instruction.contains("letter")) {
					out = swapLetter(arr[2], arr[5], out);					
				} else {
					out = swapPosition(Integer.parseInt(arr[2]), Integer.parseInt(arr[5]), out);
				}
			} else if(instruction.contains("reverse")) {
				out = reversePosition(Integer.parseInt(arr[2]), Integer.parseInt(arr[4]), out);					
			} else if(instruction.contains("move")) {
				out = movePositions(Integer.parseInt(arr[2]), Integer.parseInt(arr[5]), out);					
			} else if(instruction.contains("rotate")) {
				if (instruction.contains("left") || instruction.contains("right")) {
					out = rotate(arr[1], Integer.parseInt(arr[2]), out);					
				} else {
					out = rotateBasedOnIndex(out, arr[6]);					
				}
			}
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyOne, Part One: " + out);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		// to the left
		int[] rotMap = new int[] { 1, 1, 6, 2, 7, 3, 0, 4 };
		String unscramble = "fbgdceah";
		out = unscramble;

		for (int i = instructions.size() - 1; i >= 0; i--) {
			String instruction = instructions.get(i);

			String[] arr = instruction.split(" ");
			if (instruction.contains("swap")) {
				if(instruction.contains("letter"))
					out = swapLetter(arr[2], arr[5], out);					
				else 
					out = swapPosition(Integer.parseInt(arr[2]), Integer.parseInt(arr[5]), out);
			} else if(instruction.contains("reverse"))
				out = reversePosition(Integer.parseInt(arr[2]), Integer.parseInt(arr[4]), out);					
			else if(instruction.contains("move")) 
				out = movePositions(Integer.parseInt(arr[5]), Integer.parseInt(arr[2]), out);					
			else if(instruction.contains("rotate")) {
				if (instruction.contains("left") || instruction.contains("right")) {
					String direction = arr[1];
					if(direction.equals("left"))
						direction = "right";
					else
						direction = "left";
					out = rotate(direction, Integer.parseInt(arr[2]), out);					
				} else  {
					int pos = out.indexOf(arr[6].charAt(0));
					for (int j = 0; j < rotMap[pos]; j++) {
						out = rotateLeft(out);
					}				
				}
			}
		}		

		stopTime = System.currentTimeMillis();
		System.out.println("Day TwentyOne, Part Two: " + out);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static String swapPosition(int x, int y, String str) {
		StringBuilder sb = new StringBuilder(str); 
		char c = str.charAt(x);
		sb.setCharAt(x, str.charAt(y));
		sb.setCharAt(y, c);
		return sb.toString();
	}

	static String swapLetter(String x, String y, String str) {
		StringBuilder sb = new StringBuilder(str); 
		int indexx = str.indexOf(x);
		int indexy = str.indexOf(y);		
		sb.setCharAt(indexx, y.charAt(0));
		sb.setCharAt(indexy, x.charAt(0));
		return sb.toString();
	}

	static String rotate(String direction, int amount, String str) {
		for(int i = 0; i < amount; i++) {
			if (direction.equals("left"))
				str = rotateLeft(str);
			else
				str = rotateRight(str);
		}
		return str;
	}

	static String rotateLeft(String str) {
		return str.substring(1, str.length()) + str.charAt(0);
	}

	static String rotateRight(String str) {
		return str.charAt(str.length() - 1) + str.substring(0, str.length() - 1);
	}

	static String rotateBasedOnIndex(String str, String pos) {
		int index = str.indexOf(pos);
		if (index >= 4)
			index += 2;
		else
			index++;
		String out = str; 
		for (int i = 0; i < index; i++) {
				out = rotateRight(out);
		}
		return out; 
	}

	static String reversePosition(int x, int y, String str) {
		String substart = str.substring(0, Math.min(x, y));
		String subend = str.substring(Math.max(x, y) + 1);

		StringBuilder sb = new StringBuilder(str.substring(Math.min(x, y), Math.max(x, y) + 1));
		sb.reverse();
		return substart + sb.toString() + subend;
	}
	static String movePositions(int x, int y, String str) {
		StringBuilder sb = new StringBuilder();
		char chara = str.charAt(x);
		str = str.replace(chara + "", "");
		sb.append(str.substring(0, y));
		sb.append(chara);
		sb.append(str.substring(y));

		return sb.toString();
	}
}
