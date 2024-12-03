package year2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayFive {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2017/input/day5.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> passphrase = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			passphrase.add(temp);			
			temp = br.readLine();
		}
		br.close();
	
	}
}
