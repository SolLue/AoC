package year2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import utility.Property;

public class DayTen {
	static Map<Integer, List<Integer>> output = new HashMap<Integer, List<Integer>>();

	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2016/input/day10.txt");
		BufferedReader br = new BufferedReader(fr);

		Queue<String> input = new LinkedList<String>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		int bot = microBots(input);
		System.out.println("Day Ten, Part One: " + bot);

		long out = 0; 
		out = output.get(0).stream().reduce(1, (a, b) -> a * b)
				* output.get(1).stream().reduce(1, (a, b) -> a * b)
				* output.get(2).stream().reduce(1, (a, b) -> a * b); 
		System.out.println("Day Ten, Part Two: " + out);	
	}

	static int microBots(Queue<String> input) {
		int searchedBot = 0; 
		Map<Integer, int[]> bots = new HashMap<Integer, int[]>();

		while(!input.isEmpty()) {
			boolean done = false;
			String in = input.poll();
			if(in.contains("value")) {
				String[] options = in.split(" ");
				int value = Integer.parseInt(options[1]);
				int bot = Integer.parseInt(options[5]);

				if(!bots.containsKey(bot)) {
					int[] values = new int[2];
					values[0] = value;
					values[1] = -1;
					bots.put(bot, values);
				} else {
					int[] values = bots.get(bot);
					if(values[0] == -1)
						values[0] = value;
					else if(values[1] == -1) 
						values[1] = value; 
					bots.put(bot, values);
				}
				done = true;
			} 

			if(in.startsWith("bot")) {
				String[] options = in.split(" ");
				int bot = Integer.parseInt(options[1]);

				if (bots.containsKey(bot)) {
					int[] values = bots.get(bot);
					if(values[0] != -1 && values[1] != -1) {
						String loworhigh = options[3];

						if(options[5].equals("output")) {
							int nrOutput = Integer.parseInt(options[6]);
							botToOutput(bot, nrOutput, loworhigh, bots);
						} else if (options[5].equals("bot")) {
							int nrforreceiving = Integer.parseInt(options[6]);
							bots = botToBot(bot, nrforreceiving, loworhigh, bots);
						}

						loworhigh = options[8];
						if(options[10].equals("output")) {
							int nrOutput = Integer.parseInt(options[11]);
							botToOutput(bot, nrOutput, loworhigh, bots);
						} else if (options[10].equals("bot")) {
							int nrforreceiving = Integer.parseInt(options[11]);
							bots = botToBot(bot, nrforreceiving, loworhigh, bots);
						}
						done = true; 
					}
				}
			}
			if(!done) {
				input.add(in);
			}
			for (int c : bots.keySet()) {
				int[] test = bots.get(c);
				if(test[0] == 17 || test[0] == 61) {
					if(test[1] == 17 || test[1] == 61) {
						searchedBot = c;
					}
				}
			}			
		}
		return searchedBot; 
	}

	static void botToOutput(int bot, int nrout, String lowHigh, Map<Integer, int[]> bots)  {
		if(lowHigh.equals("low")) {
			List<Integer> o;
			if(output.containsKey(nrout)) {
				o = output.get(nrout);
			} else {
				o = new ArrayList<Integer>();
			}
			int[] values = bots.get(bot);
			int l = 0; 
			if(values[0] < values[1]) {
				l = values[0];
				values[0] = -1;
			} else {
				l = values[1];
				values[1] = -1;
			}
			bots.put(bot, values);
			o.add(l);
			output.put(nrout, o);
		} else if(lowHigh.equals("high")) {
			List<Integer> o;
			if(output.containsKey(nrout)) {
				o = output.get(nrout);
			} else {
				o = new ArrayList<Integer>();
			}
			int[] values = bots.get(bot);
			int l = 0; 
			if(values[0] > values[1]) {
				l = values[0];
				values[0] = -1;
			} else {
				l = values[1];
				values[1] = -1;
			}
			bots.put(bot, values);
			o.add(l);
			output.put(nrout, o);					
		}
	}

	static Map<Integer, int[]> botToBot(int bot, int toBot, String lowHigh, Map<Integer, int[]> bots) {
		if(!bots.containsKey(toBot)) {
			int[] values = new int[2];
			values[0] = -1;
			values[1] = -1;
			bots.put(toBot, values);
		}
		if(lowHigh.equals("low")) {
			int[] valuesreceiving = bots.get(toBot);
			int[] values = bots.get(bot);
			int l = 0; 
			if(values[0] < values[1]) {
				l = values[0];
				values[0] = -1;
			} else {
				l = values[1];
				values[1] = -1;
			}
			if(valuesreceiving[0] == -1) {
				valuesreceiving[0] = l;
			} else if(valuesreceiving[1] == -1) {
				valuesreceiving[1] = l;
			}	
			bots.put(bot, values);
			bots.put(toBot, valuesreceiving);
		} else if(lowHigh.equals("high")) {
			int[] valuesreceiving = bots.get(toBot);
			int[] values = bots.get(bot);
			int l = 0; 
			if(values[0] > values[1]) {
				l = values[0];
				values[0] = -1;
			} else {
				l = values[1];
				values[1] = -1;
			}
			if(valuesreceiving[0] == -1) {
				valuesreceiving[0] = l;
			} else if(valuesreceiving[1] == -1) {
				valuesreceiving[1] = l;
			}
			bots.put(bot, values);
			bots.put(toBot, valuesreceiving);
		}
		return bots; 
	}
}
