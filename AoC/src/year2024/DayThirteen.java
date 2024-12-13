package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Property;

public class DayThirteen {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day13.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String temp = br.readLine();
		while(temp != null) {
			input.add(temp);			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		int id = 0;
		List<ClawMachine> claws = new ArrayList<ClawMachine>();
		for (int i = 0; i < input.size(); i++) {
			String comma = input.get(i).replaceAll(",", " ");
			ClawMachine claw = new ClawMachine();
			if (input.get(i).contains("A")) {
				String[] te = comma.split("\\s+");
				claw.buttonA.x = Integer.parseInt(te[2].substring(1));
				claw.buttonA.y = Integer.parseInt(te[3].substring(1));
			}
			i++;
			comma = input.get(i).replaceAll(",", " ");
			if (input.get(i).contains("B")) {
				String[] te = comma.split("\\s+");
				claw.buttonB.x = Integer.parseInt(te[2].substring(1));
				claw.buttonB.y = Integer.parseInt(te[3].substring(1));
			}
			i++;
			comma = input.get(i).replaceAll(",", " ");
			if (input.get(i).contains("Prize")) {
				String[] te = comma.split("\\s+");
				claw.prizex = Integer.parseInt(te[1].substring(2));
				claw.prizey = Integer.parseInt(te[2].substring(2));
			}
			i++;
			claw.id = id;
			claws.add(claw);
			id++;
		}

		int costA = 3; 
		int costB = 1; 
		long totalCost = 0;
		long totalCostA = 0;
		long totalCostB = 0;

		for (ClawMachine clawMachine : claws) {

			long[] pressing = amountPress(clawMachine);
			if (pressing != null) {
				long buttA = pressing[0];
				long buttB = pressing[1];

				if(buttA != -1 && buttB != -1) {
					totalCostA += (costA * buttA);
					totalCostB += (costB * buttB);
				}
			}
		}
		totalCost = totalCostA + totalCostB;

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Thirteen, Part One: " + totalCost);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		totalCost = 0;
		totalCostA = 0;
		totalCostB = 0;

		for (ClawMachine clawMachine : claws) {
			clawMachine.prizex = clawMachine.prizex + 10000000000000L;
			clawMachine.prizey = clawMachine.prizey + 10000000000000L;	
		}

		for (ClawMachine clawMachine : claws) {
			long[] pressing = amountPress(clawMachine);
			long buttA = pressing[0];
			long buttB = pressing[1];

			if(buttA != -1 && buttB != -1) {
				totalCostA += (costA * buttA);
				totalCostB += (costB * buttB);
			}
		}
		totalCost = totalCostA + totalCostB;

		stopTime = System.currentTimeMillis();

		System.out.println("Day Thirteen, Part Two: " + totalCost);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static long[] amountPress(ClawMachine clawMachine) {
		long form = clawMachine.buttonA.x * clawMachine.buttonB.y - clawMachine.buttonB.x * clawMachine.buttonA.y; 

		if (form == 0) 
			return new long [] {-1, -1};

		long x = clawMachine.prizex * clawMachine.buttonB.y - clawMachine.buttonB.x * clawMachine.prizey;
		long y = clawMachine.buttonA.x * clawMachine.prizey - clawMachine.prizex * clawMachine.buttonA.y;

		if (x % form != 0 || y % form != 0)
			return new long[]{-1, -1};

		long buttX = x / form; 
		long buttY = y / form;

		return new long[] {buttX, buttY};
	}

	static class ClawMachine {
		int id; 
		Button buttonA; 
		Button buttonB; 
		long prizex; 
		long prizey; 

		ClawMachine() {
			this.buttonA = new Button(); 
			this.buttonB = new Button(); 
			this.prizex = 0; 
			this.prizey = 0; 
		}
	}
	static class Button {
		int x; 
		int y; 
		Button() {
			this.x = 0; 
			this.y = 0; 
		}
		public String toString() {
			return this.x + "/" + this.y;
		}
	}
}
