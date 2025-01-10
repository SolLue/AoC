package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.Property;

public class DayFourteen {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day14.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine().trim();
		br.close();
		long startTime = System.currentTimeMillis(); 

		List<Long> keys = findKey(input, 1);
		long stopTime = System.currentTimeMillis(); 
		System.out.println("Day Fourteen, Part One: " + keys.get(keys.size() - 1));
		System.out.println("Time in ms " + (stopTime - startTime));
		startTime = System.currentTimeMillis(); 

		keys.clear();
		keys = findKey(input, 2);
		
		stopTime = System.currentTimeMillis(); 
		System.out.println("Day Fourteen, Part Two: " + keys.get(keys.size() - 1));
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static List<Long> findKey(String input, int part) throws NoSuchAlgorithmException {
		List<Long> keys = new ArrayList<Long>();
		Map<Long, String> memory = new HashMap<Long, String>();
		Map<String, String> memoryPart2 = new HashMap<String, String>();
		long counterOriginal = 0L;
		long tripleCounter = 0L;

		while(keys.size() < 64) {
			String charForTriplets = "";
			long counter = counterOriginal;
			boolean findTriplet = false; 
			while(!findTriplet) {
				String hash = findAppropriateHash(part, memory, memoryPart2, input, counter);
				String temp = findMultiples(hash, "(.)\\1{2}");
				if (!temp.isBlank()) {
					charForTriplets = temp.charAt(0) + "";
					findTriplet = true;
					tripleCounter = counter;
					counter++;
				} else 
					counter++;
			}

			boolean findQuintet = false; 
			if(!charForTriplets.isBlank()) {
				while(!findQuintet) {
					if (counter <= tripleCounter + 1000) {
						String hash = findAppropriateHash(part, memory, memoryPart2, input, counter);
						String temp = findMultiples(hash, "("+charForTriplets+")\\1{4}");
						if (!temp.isBlank()) {
							findQuintet = true;
						} else
							counter++;
					} else 
						break;
				}
			}
			if (findTriplet && findQuintet && !keys.contains(tripleCounter)) {
				keys.add(tripleCounter);
			}
			counterOriginal++;
		}
		return keys;
	}

	static String findAppropriateHash(int part, Map<Long, String> memory, Map<String, String> memoryPart2, 
			String input, long counter) throws NoSuchAlgorithmException {
		String hash = ""; 
		if (part == 1) {
			if (!memory.containsKey(counter)) {
				hash = getHash(input + counter);
				memory.put(counter, hash);
			} else 
				hash = memory.get(counter);
		} else if(part == 2) {	
			if (!memoryPart2.containsKey(input + counter)) {
				hash = getHash2016(input + counter);
				memoryPart2.put(input + counter, hash);
			} else
				hash = memoryPart2.get(input + counter);
		}
		return hash;
	}

	static String getHash2016(String input) throws NoSuchAlgorithmException {
		String hash = input; 
		for(int i = 0; i < 2017; i++) {
			hash = getHash(hash);
		}
		return hash; 
	}

	static String findMultiples(String hash, String p) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(hash);
		String matched = ""; 
		if(matcher.find()) {
			matched = matcher.group();
		}
		return matched;
	}

	static String getHash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = null; 
		md.update(input.getBytes());
		digest = md.digest();

		BigInteger no = new BigInteger(1, digest);
		String hashtext = no.toString(16);
        if (hashtext.length() < 32) {
            hashtext = "0".repeat(32 - hashtext.length()) + hashtext;
        }   
		return hashtext; 
	}

}
