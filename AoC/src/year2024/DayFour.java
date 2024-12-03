package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayFour {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day4.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> repoList = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			repoList.add(temp);			
			temp = br.readLine();
		}
		br.close();



	}
}
