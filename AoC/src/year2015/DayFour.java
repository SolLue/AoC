package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import utility.Property;

public class DayFour {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();
		
		long counter = getCounter(input, 5);
		System.out.println("Day Four, Part One: " + counter);
		
		counter = getCounter(input, 6);
		System.out.println("Day Four, Part Two: " + counter);

	}
	
	static long getCounter(String input, int zeroes) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		boolean found = false; 
		int counter = 1; 
		int lengthoriginal = input.length();
		String hashtext = ""; 	
		byte[] digest = null; 
		
		while(!found) {
			md.update(input.getBytes());
			digest = md.digest();
			BigInteger no = new BigInteger(1, digest);  
			hashtext = no.toString(16);  
			
			while (hashtext.length() < 32) {  
				hashtext = "0" + hashtext;  
			}  
			
			if(!hashtext.startsWith("0".repeat(zeroes))) {
				counter++; 
				input = input.substring(0, lengthoriginal) + counter; 
			} else {
				found = true; 
			}
		}
		return (long) counter; 
	}
}
