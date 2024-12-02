package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayTwo {

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day2.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> repoList = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			repoList.add(temp);			
			temp = br.readLine();
		}
		br.close();

		int[][] reports = new int[repoList.size()][];

		for (int i = 0; i < repoList.size(); i++) {
			String[] in = repoList.get(i).split("\\s+");
			int[] tempo = new int[in.length];
			for (int j = 0; j < in.length; j++) {
				tempo[j] = Integer.parseInt(in[j]);
			} 
			reports[i] = tempo; 
		}

		int safeReports = 0; 
		int almostSafeReports = 0; 

		for (int[] report : reports) {
			boolean safe = checkReport(report);
			if(safe) 
				safeReports++;
			else {
				for (int i = 0; i < report.length; i++) {
					int[] newReport = fillNewReport(report, i);
					boolean almostSafe = checkReport(newReport);
					if (almostSafe) {
						almostSafeReports++;
						break;
					}
				}
			}
		}

		System.out.println("Day Two, Part One: " + safeReports);
		System.out.println("Day Two, Part Two: " + (almostSafeReports + safeReports));
	}

	static int[] fillNewReport(int[] report, int leaveOut) {
		int[] newReport = new int[report.length - 1];
		int j = 0;
		for (int i = 0; i < report.length; i++) {
			if (i != leaveOut) {
				newReport[j] = report[i];
				j++;
			}
		}
		return newReport;
	}

	static boolean checkReport(int[] report) {
		boolean safe = true; 
		for (int i = 1; i < report.length; i++) {
			if (report[0] <= report[1] && i != 0) {
				if (report[i - 1] < report[i]) {
					int difference = report[i] - report[i - 1];
					if (difference < 1 || difference > 3) {
						safe = false;
					}
				} else {
					safe = false; 
				}
			} else if(report[0] >= report[1] && i != 0) {
				if (report[i - 1] > report[i]) {
					int difference = report[i - 1] - report[i]; 
					if (difference < 1 || difference > 3) {
						safe = false;
					}
				} else {
					safe = false;
				}
			}
		}
		return safe; 
	}
}
