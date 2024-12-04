package year2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.Property;

public class DayThree {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2018/input/day3.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while (temp != null) {
			input.add(temp);
			temp = br.readLine();
		}
		br.close();

		char[][] fabric = new char[1000][1000];
		for (int i = 0; i < fabric.length; i++) {
			for (int j = 0; j < fabric[0].length; j++) {
				fabric[i][j] = '.';
			}	
		}
		List<Claim> claims = new ArrayList<Claim>();
		for (String string : input) {
			String temparr[] = string.split("\\s+");

			Claim claim = new Claim(Integer.parseInt(temparr[0].substring(1)),
					Integer.parseInt(temparr[2].substring(0, temparr[2].indexOf(","))),
					Integer.parseInt(temparr[2].substring(temparr[2].indexOf(",") + 1, temparr[2].indexOf(":"))), 
					Integer.parseInt(temparr[3].substring(0, temparr[3].indexOf("x"))),
					Integer.parseInt(temparr[3].substring(temparr[3].indexOf("x") + 1)), 
					true);			

			claims.add(claim);
		}

		for (Claim claim : claims) {
			for (int i = claim.leftedge; i < claim.wide + claim.leftedge; i++) {
				for (int j = claim.topedge; j < claim.tall + claim.topedge; j++) {
					if (fabric[i][j] == '.')
						fabric[i][j] = '*'; 
					else if (fabric[i][j] == '*')
						fabric[i][j] = 'X';
				}	
			}
		}

		int countinches = (int) Arrays.stream(fabric).map(CharBuffer::wrap)
				.flatMapToInt(CharBuffer::chars)
				.filter(i -> i == 'X')
				.count();

		System.out.println("Day Three, Part One: " + countinches);

		int claimId = 0; 
		for (Claim claim : claims) {
			for (int i = claim.leftedge; i < claim.wide + claim.leftedge; i++) {
				for (int j = claim.topedge; j < claim.tall + claim.topedge; j++) {
					if (fabric[i][j] == 'X') 
						claim.intact = false;
				}	
			}
		}

		for (Claim claim : claims) {
			if(claim.intact)
				claimId = claim.id;
		}

		System.out.println("Day Three, Part Two: " + claimId);
	}

	static class Claim {
		int id; 
		int leftedge; 
		int topedge; 
		int wide; 
		int tall; 
		boolean intact; 
		Claim(int id, int l, int t, int w, int ta, boolean i) {
			this.id = id;
			this.leftedge = l; 
			this.topedge = t; 
			this.wide = w; 
			this.tall = ta; 
			this.intact = i; 	
		}
	}	
}
