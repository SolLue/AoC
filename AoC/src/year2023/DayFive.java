package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayFive {

	static long location = -1;
	static List<String> seedSoil = new ArrayList<String>();
	static List<String> soilFert = new ArrayList<String>();
	static List<String> fertWater = new ArrayList<String>();
	static List<String> waterLight = new ArrayList<String>();
	static List<String> lightTemp = new ArrayList<String>();
	static List<String> tempHumid = new ArrayList<String>();
	static List<String> humidLoc = new ArrayList<String>();

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day5.txt");
		Scanner scan = new Scanner(fr);

		List<String> seeds = new ArrayList<String>();

		while (scan.hasNextLine()) {
			String temp = scan.nextLine();

			if (temp.startsWith("seeds:")) {
				temp = temp.substring(temp.indexOf(" "));
				temp = temp.trim();
				String[] s = temp.split("\\s+");
				for (String string : s) {
					string = string.trim();
					if (!string.equals(""))
						seeds.add(string);
				}
			} else if (temp.startsWith("seed-to-soil map:")) {
				while (!temp.equals("")) {
					temp = scan.nextLine();
					String[] s = temp.split("\\s+");
					for (String string : s) {
						string = string.trim();
						if (!string.equals(""))
							seedSoil.add(string);
					}
				}
			} else if (temp.startsWith("soil-to-fertilizer map:")) {
				while (!temp.equals("")) {

					temp = scan.nextLine();
					String[] s = temp.split("\\s+");
					for (String string : s) {
						string = string.trim();
						if (!string.equals(""))
							soilFert.add(string);
					}
				}
			} else if (temp.startsWith("fertilizer-to-water map:")) {
				while (!temp.equals("")) {

					temp = scan.nextLine();
					String[] s = temp.split("\\s+");
					for (String string : s) {
						string = string.trim();
						if (!string.equals(""))
							fertWater.add(string);
					}
				}
			} else if (temp.startsWith("water-to-light map:")) {
				while (!temp.equals("")) {
					temp = scan.nextLine();
					String[] s = temp.split("\\s+");
					for (String string : s) {
						string = string.trim();
						if (!string.equals(""))
							waterLight.add(string);
					}
				}
			} else if (temp.startsWith("light-to-temperature map:")) {
				while (!temp.equals("")) {
					temp = scan.nextLine();
					String[] s = temp.split("\\s+");
					for (String string : s) {
						string = string.trim();
						if (!string.equals(""))
							lightTemp.add(string);
					}
				}
			} else if (temp.startsWith("temperature-to-humidity map:")) {
				while (!temp.equals("")) {
					temp = scan.nextLine();
					String[] s = temp.split("\\s+");
					for (String string : s) {
						string = string.trim();
						if (!string.equals(""))
							tempHumid.add(string);
					}
				}
			} else if (temp.startsWith("humidity-to-location map:")) {
				while (scan.hasNextLine()) {
					temp = scan.nextLine();
					String[] s = temp.split("\\s+");
					for (String string : s) {
						string = string.trim();
						if (!string.equals(""))
							humidLoc.add(string);
					}
				}
			}
		}
		scan.close();

		for (int i = 0; i < seeds.size(); i++) {
			findLocation(Long.parseLong(seeds.get(i)));
		}

		System.out.println("Day Five, Part One: " + location);

		location = -1;

		for (int i = 0; i < seeds.size(); i = i + 2) {
			for (long k = 0; k < Long.parseLong(seeds.get(i + 1)); k++) {

				long seed = Long.parseLong(seeds.get(i)) + k;

				long soil = -1;
				long fert = -1;
				long water = -1;
				long light = -1;
				long temp = -1;
				long humid = -1;
				long loc = -1;

				for (int j = 0; j < seedSoil.size(); j = j + 3) {
					long interval = Long.parseLong(seedSoil.get(j + 2)) - 1;
					long sourcestart = Long.parseLong(seedSoil.get(j + 1));
					long sourceend = sourcestart + interval;
					long destinationstart = Long.parseLong(seedSoil.get(j));

					if (seed >= sourcestart && seed <= sourceend) {
						long dif = Math.abs(seed - sourcestart);
						soil = destinationstart + dif;
					}
				}
				if (soil == -1) {
					soil = seed;
				}

				for (int j = 0; j < soilFert.size(); j = j + 3) {
					long interval = Long.parseLong(soilFert.get(j + 2)) - 1;
					long sourcestart = Long.parseLong(soilFert.get(j + 1));
					long sourceend = sourcestart + interval;
					long destinationstart = Long.parseLong(soilFert.get(j));

					if (soil >= sourcestart && soil <= sourceend) {
						long dif = Math.abs(soil - sourcestart);
						fert = destinationstart + dif;
					}
				}
				if (fert == -1) {
					fert = soil;
				}

				for (int j = 0; j < fertWater.size(); j = j + 3) {
					long interval = Long.parseLong(fertWater.get(j + 2)) - 1;
					long sourcestart = Long.parseLong(fertWater.get(j + 1));
					long sourceend = sourcestart + interval;
					long destinationstart = Long.parseLong(fertWater.get(j));

					if (fert >= sourcestart && fert <= sourceend) {
						long dif = Math.abs(fert - sourcestart);
						water = destinationstart + dif;
					}
				}
				if (water == -1) {
					water = fert;
				}

				for (int j = 0; j < waterLight.size(); j = j + 3) {
					long interval = Long.parseLong(waterLight.get(j + 2)) - 1;
					long sourcestart = Long.parseLong(waterLight.get(j + 1));
					long sourceend = sourcestart + interval;
					long destinationstart = Long.parseLong(waterLight.get(j));

					if (water >= sourcestart && water <= sourceend) {
						long dif = Math.abs(water - sourcestart);
						light = destinationstart + dif;
					}
				}
				if (light == -1) {
					light = water;
				}

				for (int j = 0; j < lightTemp.size(); j = j + 3) {
					long interval = Long.parseLong(lightTemp.get(j + 2)) - 1;
					long sourcestart = Long.parseLong(lightTemp.get(j + 1));
					long sourceend = sourcestart + interval;
					long destinationstart = Long.parseLong(lightTemp.get(j));

					if (light >= sourcestart && light <= sourceend) {
						long dif = Math.abs(light - sourcestart);
						temp = destinationstart + dif;
					}
				}
				if (temp == -1) {
					temp = light;
				}

				for (int j = 0; j < tempHumid.size(); j = j + 3) {
					long interval = Long.parseLong(tempHumid.get(j + 2)) - 1;
					long sourcestart = Long.parseLong(tempHumid.get(j + 1));
					long sourceend = sourcestart + interval;
					long destinationstart = Long.parseLong(tempHumid.get(j));

					if (temp >= sourcestart && temp <= sourceend) {
						long dif = Math.abs(temp - sourcestart);
						humid = destinationstart + dif;
					}
				}
				if (humid == -1) {
					humid = temp;
				}

				for (int j = 0; j < humidLoc.size(); j = j + 3) {
					long interval = Long.parseLong(humidLoc.get(j + 2)) - 1;
					long sourcestart = Long.parseLong(humidLoc.get(j + 1));
					long sourceend = sourcestart + interval;
					long destinationstart = Long.parseLong(humidLoc.get(j));

					if (humid >= sourcestart && humid <= sourceend) {
						long dif = Math.abs(humid - sourcestart);
						loc = destinationstart + dif;
					}
				}
				if (loc == -1) {
					loc = humid;
				}

				smallest(loc);
			}

		}

		System.out.println("Day Five, Part Two: " + location);
	}

	public static void smallest(long loc) {
		if (location == -1) {
			location = loc;
		}
		if (loc <= location) {
			location = loc;
		}
	}

	public static void findLocation(long seed) {
		long soil = -1;
		long fert = -1;
		long water = -1;
		long light = -1;
		long temp = -1;
		long humid = -1;
		long loc = -1;

		for (int j = 0; j < seedSoil.size(); j = j + 3) {
			long interval = Long.parseLong(seedSoil.get(j + 2)) - 1;
			long sourcestart = Long.parseLong(seedSoil.get(j + 1));
			long sourceend = sourcestart + interval;
			long destinationstart = Long.parseLong(seedSoil.get(j));

			if (seed >= sourcestart && seed <= sourceend) {
				long dif = Math.abs(seed - sourcestart);
				soil = destinationstart + dif;
			}
		}
		if (soil == -1) {
			soil = seed;
		}

		for (int j = 0; j < soilFert.size(); j = j + 3) {
			long interval = Long.parseLong(soilFert.get(j + 2)) - 1;
			long sourcestart = Long.parseLong(soilFert.get(j + 1));
			long sourceend = sourcestart + interval;
			long destinationstart = Long.parseLong(soilFert.get(j));

			if (soil >= sourcestart && soil <= sourceend) {
				long dif = Math.abs(soil - sourcestart);
				fert = destinationstart + dif;
			}
		}
		if (fert == -1) {
			fert = soil;
		}

		for (int j = 0; j < fertWater.size(); j = j + 3) {
			long interval = Long.parseLong(fertWater.get(j + 2)) - 1;
			long sourcestart = Long.parseLong(fertWater.get(j + 1));
			long sourceend = sourcestart + interval;
			long destinationstart = Long.parseLong(fertWater.get(j));

			if (fert >= sourcestart && fert <= sourceend) {
				long dif = Math.abs(fert - sourcestart);
				water = destinationstart + dif;
			}
		}
		if (water == -1) {
			water = fert;
		}

		for (int j = 0; j < waterLight.size(); j = j + 3) {
			long interval = Long.parseLong(waterLight.get(j + 2)) - 1;
			long sourcestart = Long.parseLong(waterLight.get(j + 1));
			long sourceend = sourcestart + interval;
			long destinationstart = Long.parseLong(waterLight.get(j));

			if (water >= sourcestart && water <= sourceend) {
				long dif = Math.abs(water - sourcestart);
				light = destinationstart + dif;
			}
		}
		if (light == -1) {
			light = water;
		}

		for (int j = 0; j < lightTemp.size(); j = j + 3) {
			long interval = Long.parseLong(lightTemp.get(j + 2)) - 1;
			long sourcestart = Long.parseLong(lightTemp.get(j + 1));
			long sourceend = sourcestart + interval;
			long destinationstart = Long.parseLong(lightTemp.get(j));

			if (light >= sourcestart && light <= sourceend) {
				long dif = Math.abs(light - sourcestart);
				temp = destinationstart + dif;
			}
		}
		if (temp == -1) {
			temp = light;
		}

		for (int j = 0; j < tempHumid.size(); j = j + 3) {
			long interval = Long.parseLong(tempHumid.get(j + 2)) - 1;
			long sourcestart = Long.parseLong(tempHumid.get(j + 1));
			long sourceend = sourcestart + interval;
			long destinationstart = Long.parseLong(tempHumid.get(j));

			if (temp >= sourcestart && temp <= sourceend) {
				long dif = Math.abs(temp - sourcestart);
				humid = destinationstart + dif;
			}
		}
		if (humid == -1) {
			humid = temp;
		}

		for (int j = 0; j < humidLoc.size(); j = j + 3) {
			long interval = Long.parseLong(humidLoc.get(j + 2)) - 1;
			long sourcestart = Long.parseLong(humidLoc.get(j + 1));
			long sourceend = sourcestart + interval;
			long destinationstart = Long.parseLong(humidLoc.get(j));

			if (humid >= sourcestart && humid <= sourceend) {
				long dif = Math.abs(humid - sourcestart);
				loc = destinationstart + dif;
			}
		}
		if (loc == -1) {
			loc = humid;
		}

		smallest(loc);
	}

}
