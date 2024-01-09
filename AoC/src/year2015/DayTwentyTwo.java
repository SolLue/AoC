package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import utility.Property;

public class DayTwentyTwo {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day22.txt");
		BufferedReader br = new BufferedReader(fr);

		Map<String, Integer> boss = new HashMap<>();
		String t = br.readLine();
		while(t != null) {
			boss.put(t.substring(0, t.indexOf(":")), Integer.parseInt(t.substring(t.indexOf(":") + 2).trim()));
			t = br.readLine();
		}
		br.close();

		Set<Spell> spells = new HashSet<Spell>();
		spells.add(new Spell("Magic Missile", 53, 4, 0, 0, 0, 1));
		spells.add(new Spell("Poison", 173, 3, 0, 0, 0, 6));
		spells.add(new Spell("Drain", 73, 2, 0, 2, 0, 1));
		spells.add(new Spell("Shield", 113, 0, 7, 0, 0, 6));
		spells.add(new Spell("Recharge", 229, 0, 0, 0, 101, 5));

		boolean hardmode = false;

		long time = System.currentTimeMillis();
		int min = bfs(spells, boss, hardmode);
		long time1 = System.currentTimeMillis();
		System.out.println("Day TwentyOne, Part One: " + min);
		System.out.println("Took " + ((time1 - time) / 1000) + " seconds");

		hardmode = true; 
		time = System.currentTimeMillis();
		min = bfs(spells, boss, hardmode);
		time1 = System.currentTimeMillis();
		System.out.println("Day TwentyOne, Part Two: " + min);
		System.out.println("Took " + (time1 - time) + " milliseconds");

	}

	static int bfs(Set<Spell> spells, Map<String, Integer> boss, boolean hardmode) {
		int bosshit = boss.get("Hit Points");
		int bossdamage = boss.get("Damage");
		Queue<Status> path = new PriorityQueue<>();

		int playerhealth = 50;
		int mana = 500;
		int min = Integer.MAX_VALUE;

		for (Spell s : spells) {
			Status begin = new Status(s, new ArrayList<>(), new HashMap<String, Integer>(),
					mana, 0, playerhealth, 0, bosshit, bossdamage);
			path.add(begin);			
		}

		while(!path.isEmpty()) {		
			Status current = path.poll();
			
			if(current.getCost() < min) {
				if(hardmode)
					current.playerhealth--;

				current.resolveEffects();
				
				if(current.isBossDead()) {
					if(current.getCost() < min) {
						min = current.getCost();
					}
				}
				if(!current.isBossDead()) {
					if(current.round % 2 == 0) {
						current.playerRound();
						current.round++;
					}
				} 

				if(current.isBossDead()) {
					if(current.getCost() < min) {
						min = current.getCost();
					}
				}

				if(hardmode)
					current.playerhealth--;

				current.resolveEffects();

				if(current.isBossDead()) {
					if(current.getCost() < min) {
						min = current.getCost();
					}
				} else {
					if(current.round % 2 == 1) {
						if((current.bossdamage - current.armourscoreplayer) < 1) {
							current.playerhealth = current.playerhealth - 1;	
						} else {
							current.playerhealth = current.playerhealth - (current.bossdamage - current.armourscoreplayer);
						}
						current.round++; 				
					}
				}

				if(!current.isPlayerDead() && !current.isBossDead()) {
					List<Spell> edges = Spell.getAvailable(current.spell, current.effect, current.mana, spells);
					for (Spell e : edges) {
						Status s = new Status(e, new ArrayList<>(current.spellList), new HashMap<>(current.effect),
								current.mana, current.round, current.playerhealth, 
								current.armourscoreplayer, current.bosshealth, current.bossdamage);		
						path.add(s);
					}
				}
			}
		}
		return min; 
	}

	static class Status implements Comparable<Status> {
		int bosshealth; 
		int playerhealth; 
		List<Spell> spellList; 
		Map<String, Integer> effect; 
		int round; 
		int mana; 
		int armourscoreplayer;
		int bossdamage;
		Spell spell;
		int cost; 

		Status(Spell cu, List<Spell> history, Map<String, Integer> e, 
				int ma, int round, int playerhealth, int armourhealth, 
				int boss, int b) {
			this.spell = cu; 
			this.spellList = history; 
			this.effect = e; 
			this.mana = ma; 
			this.round = round;
			this.playerhealth = playerhealth;
			this.armourscoreplayer = armourhealth;
			this.bosshealth = boss;
			this.bossdamage = b;
		}

		boolean isPlayerDead() {
			return (this.playerhealth <= 0 || (this.mana < 53 && 
					!this.effect.containsKey("Recharge"))) ? true : false; 
		}

		boolean isBossDead() {
			return this.bosshealth <= 0 ? true : false; 
		}

		int getCost() {
			return this.spellList.stream().mapToInt(i -> i.cost).sum();
		}

		void playerRound() {
			if (this.spell.name.equals("Magic Missile")) {
				this.bosshealth = this.bosshealth - this.spell.damage;	
			}
			if (this.spell.name.equals("Drain")) {
				this.bosshealth = this.bosshealth - this.spell.damage;
				this.playerhealth = this.playerhealth + this.spell.healing; 
			}
			if(this.spell.length > 1) {
				this.effect.put(this.spell.name, this.spell.length);
			}
			this.mana -= this.spell.cost;
			this.spellList.add(this.spell);
		}

		void resolveEffects() {
			if (this.effect.containsKey("Poison")) {
				this.bosshealth -= 3;
				this.effect.put("Poison", this.effect.get("Poison") - 1);
				if(this.effect.get("Poison") == 0) {
					this.effect.remove("Poison");
				}
			}
			if (this.effect.containsKey("Recharge")) {
				this.mana += 101;
				this.effect.put("Recharge", this.effect.get("Recharge") - 1);
				if(this.effect.get("Recharge") == 0) {
					this.effect.remove("Recharge");
				}
			}
			if(this.effect.containsKey("Shield")) {
				this.armourscoreplayer = 7;
				this.effect.put("Shield", this.effect.get("Shield") - 1);
				if(this.effect.get("Shield") == 0) {
					this.effect.remove("Shield");
				}
			} else
				this.armourscoreplayer = 0; 
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || getClass() != o.getClass())
				return false;
			Status current = (Status) o;
			return this.spellList.equals(current.spellList);
		}

		@Override
		public int hashCode() {
			return this.spellList.hashCode();
		}

		@Override
		public String toString() {
			String temp = ""; 
			for (Spell spell : spellList) {
				temp += spell + " "; 
			}
			return temp; 
		}

		@Override
		public int compareTo(Status o) {
			return Integer.compare(this.getCost(), o.getCost());
		}
	}

	static class Spell implements Comparable<Spell> {
		String name; 
		int cost; 
		int damage; 
		int armour; 
		int healing; 
		int manaHeal; 
		int length; 

		Spell (String name, int cost, int damage, int armour, int healing, int manaHeal, int length) {
			this.name = name; 
			this.cost = cost; 
			this.damage = damage; 
			this.armour = armour; 
			this.healing = healing; 
			this.manaHeal = manaHeal; 
			this.length = length; 
		}

		@Override
		public int compareTo(Spell o) {
			return Integer.compare(this.cost, o.cost);
		}

		static List<Spell> getAvailable(Spell current, Map<String, Integer> effects, int mana, 
				Set<Spell> spells) {
			List<Spell> available = new ArrayList<>();
			for (Spell spell : spells) {
				if(mana - spell.cost >= 0) {
					if(effects.containsKey(spell.name)) {
						if(effects.get(spell.name) <= 1)
							available.add(spell);
					} else {
						available.add(spell);
					}
				}
			}
			return available;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Spell current = (Spell) o;
			return this.name.equals(current.name);
		}

		@Override
		public int hashCode() {
			return this.name.hashCode();
		}

		@Override
		public String toString() {
			return this.name; 
		}
	}
}
