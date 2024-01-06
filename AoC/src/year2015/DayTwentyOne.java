package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayTwentyOne {
	public static void main(String[] args) throws IOException {

		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day21.txt");
		BufferedReader br = new BufferedReader(fr);

		Map<String, Integer> boss = new HashMap<>();
		String t = br.readLine();
		while(t != null) {
			boss.put(t.substring(0, t.indexOf(":")), Integer.parseInt(t.substring(t.indexOf(":") + 2).trim()));
			t = br.readLine();
		}
		br.close();

		String we = "Dagger 8 4 0 \r\n"
				+ "Shortsword 10 5 0 \r\n"
				+ "Warhammer 25 6 0 \r\n"
				+ "Longsword 40 7 0 \r\n"
				+ "Greataxe 74 8 0";
		String arm = "Naked 0 0 0 \r\n"
				+ "Leather 13 0 1 \r\n"
				+ "Chainmail 31 0 2 \r\n"
				+ "Splintmail 53 0 3 \r\n"
				+ "Bandedmail 75 0 4 \r\n"
				+ "Platemail 102 0 5";
		String ri = "Empty 0 0 0 \r\n"
				+ "Empty 0 0 0 \r\n"
				+ "Damage+1 25 1 0 \r\n"
				+ "Damage+2 50 2 0 \r\n"
				+ "Damage+3 100 3 0 \r\n"
				+ "Defense+1 20 0 1 \r\n"
				+ "Defense+2 40 0 2 \r\n"
				+ "Defense+3 80 0 3";

		List<Item> weapons = createShop(we); 
		List<Item> armour = createShop(arm); 
		List<Item> rings = createShop(ri); 

		int[] goldspent = buyAndFight(weapons, armour, rings, boss);
		
		System.out.println("Day TwentyOne, Part One: " + goldspent[0]);
		System.out.println("Day TwentyOne, Part Two: " + goldspent[1]);
	}

	static int[] buyAndFight(List<Item> weapons, List<Item> armours, List<Item> rings, Map<String, Integer> boss) {
		int goldspent = 0;
		boolean bossdefeated = false; 
		int damagescore = 0; 
		int armourscore = 0;
		int hitpoint = 100; 
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
			for(Item weapon : weapons) {
				for(Item armour : armours) {
					for(int i = 0; i < rings.size(); i++) {
						for(int j = 0; j < rings.size(); j++) {

							goldspent += weapon.cost;
							damagescore += weapon.damage;

							armourscore += armour.armor;
							goldspent += armour.cost;
						
							armourscore += rings.get(i).armor;
							damagescore += rings.get(i).damage;
							goldspent += rings.get(i).cost;
							
							if(i != j) {
								armourscore += rings.get(j).armor;
								damagescore += rings.get(j).damage;
								goldspent += rings.get(j).cost;
							}
							bossdefeated = fight(hitpoint, damagescore, armourscore, boss);

							if(bossdefeated == true) {
								if(min >= goldspent)
									min = goldspent; 
							} else {
								if(max < goldspent)
									max = goldspent; 
							}
							goldspent = 0; 
							damagescore = 0; 
							armourscore = 0;
						}
					}
				}	
		}
		return new int[]{min, max};
	}

	static boolean fight(int hitpoint, int damagescore, int armourscore, Map<String, Integer> boss) {

		int bosshit = boss.get("Hit Points");
		int bossdamage = boss.get("Damage");
		int bossarmour = boss.get("Armor");
		boolean bossdefeated = false; 
		boolean playerdefeated = false; 

		int round = 0; 
		while(!bossdefeated && !playerdefeated) {
			if(round % 2 == 0) {
				if (damagescore - bossarmour < 1)
					bosshit = bosshit - 1;	
				else
					bosshit = bosshit - (damagescore - bossarmour);
				if(bosshit <= 0) {
					bossdefeated = true;
				}
			} else {
				if (bossdamage - armourscore < 1)
					hitpoint = hitpoint - 1;	
				else
					hitpoint = hitpoint - (bossdamage - armourscore);

				if(hitpoint <= 0) {
					playerdefeated = true;
				}
			}
			round++; 
		}
		return bossdefeated;
	}

	static List<Item> createShop(String in) {
		List<Item> items = new ArrayList<Item>();
		String[] options = in.split(" ");

		for(int i = 0; i < options.length; i += 4) {
			Item item = new Item(options[i].trim(),
					Integer.parseInt(options[i + 1].trim()),
					Integer.parseInt(options[i + 2].trim()), 
					Integer.parseInt(options[i + 3].trim()));
			items.add(item);
		}

		return items;
	}

	record Item (String name, int cost, int damage, int armor) {}
}
