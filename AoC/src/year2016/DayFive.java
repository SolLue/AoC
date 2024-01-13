package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		String[] code = getCode(input);

		System.out.println("Day Five, Part One: " + code[0]);	

		System.out.println("Day Five, Part Two: " + code[1]);
	}

	static String[] getCode(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		String[] code = new String[2];
		String part1 = "";
		char[] part2char = { '-', '-', '-', '-', '-', '-', '-', '-' };
		String hashtext = ""; 	
		byte[] digest = null; 
		long num = 0; 
		int lengthoriginal = input.length();
		input = input + String.valueOf(num);
		boolean password1 = false; 
		boolean password2 = false; 
		boolean done = false; 

		while(!done) {
			md.update(input.getBytes());
			digest = md.digest();
			BigInteger no = new BigInteger(1, digest);  
			hashtext = no.toString(16);  

			while (hashtext.length() < 32) {  
				hashtext = "0" + hashtext;  
			}

			if(!hashtext.startsWith("0".repeat(5))) { 
				input = input.substring(0, lengthoriginal) + String.valueOf(num); 
				num++;
			} else {
				if(!password1) {
					part1 += hashtext.charAt(5);
					if(part1.length() == 8)
						password1 = true; 
				}

				if(!password2) {
					int index = Character.getNumericValue(hashtext.charAt(5));
					if(index < 8 && part2char[index] == '-') {
						part2char[index] = hashtext.charAt(6);
					}
					if (!new String(part2char).contains("-")) {
						password2 = true; 
					}
				} 
				if(password2 && password1)
					done = true; 

				input = input.substring(0, lengthoriginal) + String.valueOf(num); 
				num++;
			}
		}

		code[0] = part1; 
		String part2 = new String(part2char);
		code[1] = part2;
		return code; 
	}
}
