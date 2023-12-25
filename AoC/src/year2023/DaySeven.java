package year2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DaySeven {

	static int sum = 0;
	static Map<Character, Integer> strength = new HashMap<Character, Integer>();

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader("day7.txt");
		Scanner scan = new Scanner(fr);

		populate();

		CardList<Card> cardList = new CardList<Card>();

		int p = 1;
		while (scan.hasNextLine()) {
			String temp = scan.nextLine();
			Card c = new Card(temp.substring(0, temp.indexOf(" ")),
					Integer.parseInt(temp.substring(temp.indexOf(" ") + 1)), p);
			cardList.insertFirst(c);
			p++;
		}
		scan.close();

		// cardList.print();

		Card root = cardList.getFirst();

		root = cardList.getFirst();

		boolean done = false;

		while (!done) {
			while (root.next != null) {
				Map<Character, Integer> hash1 = new HashMap<Character, Integer>();
				Map<Character, Integer> hash2 = new HashMap<Character, Integer>();

				for (int i = 0; i < root.getCard().length(); i++) {
					if (hash1.get(root.getCard().charAt(i)) != null) {
						hash1.put(root.getCard().charAt(i), hash1.get(root.getCard().charAt(i)) + 1);
					} else {
						hash1.put(root.getCard().charAt(i), 1);
					}
					if (hash2.get(root.next.getCard().charAt(i)) != null) {
						hash2.put(root.next.getCard().charAt(i), hash2.get(root.next.getCard().charAt(i)) + 1);
					} else {
						hash2.put(root.next.getCard().charAt(i), 1);
					}
				}

				if (hash1.keySet().size() > hash2.keySet().size() && root.rank > root.next.rank) {
					cardList.flipCards(root, root.next);
					root = cardList.getFirst();
					break;
				} else if (hash1.keySet().size() < hash2.keySet().size() && root.rank < root.next.rank) {
					cardList.flipCards(root, root.next);
					root = cardList.getFirst();
					break;
				} else if (hash1.keySet().size() == 2 && hash2.keySet().size() == 2) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				} else if (hash1.keySet().size() == 3 && hash2.keySet().size() == 3) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				} else if (hash1.keySet().size() == 5 && hash2.keySet().size() == 5) {
					char[] c1 = new char[5];
					char[] c2 = new char[5];

					int i = 0;
					for (char c : hash1.keySet()) {
						c1[i] = c;
						i++;
					}
					i = 0;
					for (char c : hash2.keySet()) {
						c2[i] = c;
						i++;
					}

					for (int j = 0; j < c1.length; j++) {
						if (c1[j] != c2[j]) {
							int o = root.compareTo(root.next);
							if (i == o && root.rank < root.next.rank) {
								cardList.flipCards(root, root.next);
								root = cardList.getFirst();
								break;
							} else if (o == -1 && root.rank > root.next.rank) {
								cardList.flipCards(root.next, root);
								root = cardList.getFirst();
								break;
							}
						}
					}
				} else if (hash1.keySet().size() == 4 && hash2.keySet().size() == 4) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				}
				Card h = cardList.getFirst();
				while (h.next != null && !done) {
					if (h.rank > h.next.rank) {
						done = false;
					} else {
						done = true;
					}
					h = h.next;
				}
				root = root.next;

				if (!done && root.next == null) {
					done = true;
				}

			}
		} // cardList.print();

		  Card newHead = cardList.getFirst(); 
		  while (newHead != null) { 
			  //int winnings = newHead.bid * newHead.rank; 
			  // System.out.println("winnings " + winnings + " = " + newHead.bid + " " + newHead.rank); sumUp(winnings); 
			  newHead = newHead.next; 
		  }
		  
		
		System.out.println("Day seven, part one: " + sum);

		sum = 0;

		populate2();

		root = cardList.getFirst();

		done = false;

		while (!done) {
			while (root.next != null) {
				Map<Character, Integer> hash1 = new HashMap<Character, Integer>();
				Map<Character, Integer> hash2 = new HashMap<Character, Integer>();

				String temp1 = root.getCard();
				String temp2 = root.next.getCard();

				for (int i = 0; i < root.getCard().length(); i++) {
					if (hash1.get(root.getCard().charAt(i)) != null) {
						hash1.put(root.getCard().charAt(i), hash1.get(root.getCard().charAt(i)) + 1);

					} else {
						hash1.put(root.getCard().charAt(i), 1);

					}
					if (hash2.get(root.next.getCard().charAt(i)) != null) {
						hash2.put(root.next.getCard().charAt(i), hash2.get(root.next.getCard().charAt(i)) + 1);

					} else {
						hash2.put(root.next.getCard().charAt(i), 1);

					}
				}

				if (temp1.contains("J")) {
					// five kind
					if (hash1.keySet().size() == 2) {
						int amount = hash1.get('J');
						hash1.remove('J');
						for (char c : hash1.keySet()) {
							hash1.put(c, hash1.get(c) + amount);
						}
					}
					// four kind
					else if (hash1.keySet().size() == 3) {
						int amount = hash1.get('J');
						hash1.remove('J');
						int max = 0;
						char u = '0';
						for (char c : strength.keySet()) {
							if (hash1.get(c) != null && hash1.get(c) > max) {
								max = hash1.get(c);
								u = c;
							}
						}
						if (hash1.get(u) + amount == 4) {
							hash1.put(u, hash1.get(u) + amount);
						}
						// full house (3 same 2 same)
						else if (hash1.get(u) + amount == 3) {
							hash1.put(u, hash1.get(u) + amount);
						}
					}
					// three kind (3 same 2 different)
					else if (hash1.keySet().size() == 4) {
						int amount = hash1.get('J');
						hash1.remove('J');
						int max = 0;
						char u = '0';
						for (char c : strength.keySet()) {
							if (hash1.get(c) != null && hash1.get(c) > max) {
								max = hash1.get(c);
								u = c;
							}
						}
						if (hash1.get(u) + amount == 3)
							hash1.put(u, hash1.get(u) + amount);
						// two pair (2 same 2 same 1 different)
						else if (hash1.get(u) + amount == 2)
							hash1.put(u, hash1.get(u) + amount);
					}
					// one pair (2 same 3 different)
					if (hash1.keySet().size() == 5) {
						int amount = hash1.get('J');
						hash1.remove('J');
						int max = 0;
						char u = '0';
						for (char c : strength.keySet()) {
							if (hash1.get(c) != null && hash1.get(c) > max) {
								max = hash1.get(c);
								u = c;
							}
						}
						if (hash1.get(u) + amount == 2) {
							hash1.put(u, hash1.get(u) + amount);
						}
					}
				}

				if (temp2.contains("J")) {
					// five kind
					if (hash2.keySet().size() == 2) {
						int amount = hash2.get('J');
						hash2.remove('J');
						for (char c : hash2.keySet()) {
							hash2.put(c, hash2.get(c) + amount);
						}
					}
					// four kind
					else if (hash2.keySet().size() == 3) {
						int amount = hash2.get('J');
						hash2.remove('J');
						int max = 0;
						char u = '0';
						for (char c : strength.keySet()) {
							if (hash2.get(c) != null && hash2.get(c) > max) {
								max = hash2.get(c);
								u = c;
							}
						}
						if (hash2.get(u) + amount == 4) {
							hash2.put(u, hash2.get(u) + amount);
						}
						// full house (3 same 2 same)
						else if (hash2.get(u) + amount == 3) {
							hash2.put(u, hash2.get(u) + amount);
						}
					}
					// three kind (3 same 2 different)
					else if (hash2.keySet().size() == 4) {
						int amount = hash2.get('J');
						hash2.remove('J');
						int max = 0;
						char u = '0';
						for (char c : strength.keySet()) {
							if (hash2.get(c) != null && hash2.get(c) > max) {
								max = hash2.get(c);
								u = c;
							}
						}
						if (hash2.get(u) + amount == 3)
							hash2.put(u, hash2.get(u) + amount);
						// two pair (2 same 2 same 1 different)
						else if (hash2.get(u) + amount == 2)
							hash2.put(u, hash2.get(u) + amount);
					}
					// one pair (2 same 3 different)
					if (hash2.keySet().size() == 5) {
						int amount = hash2.get('J');
						hash2.remove('J');
						int max = 0;
						char u = '0';
						for (char c : strength.keySet()) {
							if (hash2.get(c) != null && hash2.get(c) > max) {
								max = hash2.get(c);
								u = c;
							}
						}
						if (hash2.get(u) + amount == 2)
							hash2.put(u, hash2.get(u) + amount);
					}
				}

				if (hash1.keySet().size() > hash2.keySet().size() && root.rank > root.next.rank) {
					cardList.flipCards(root, root.next);
					root = cardList.getFirst();
					break;
				} else if (hash1.keySet().size() < hash2.keySet().size() && root.rank < root.next.rank) {
					cardList.flipCards(root, root.next);
					root = cardList.getFirst();
					break;
				} else if (hash1.keySet().size() == 2 && hash2.keySet().size() == 2) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				} else if (hash1.keySet().size() == 3 && hash2.keySet().size() == 3) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				} else if (hash1.keySet().size() == 5 && hash2.keySet().size() == 5) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				} else if (hash1.keySet().size() == 4 && hash2.keySet().size() == 4) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				} else if (hash1.keySet().size() == 1 && hash2.keySet().size() == 1) {
					int c1 = 0;
					int c2 = 0;
					for (char c : hash1.keySet()) {
						if (hash1.get(c) > c1)
							c1 = hash1.get(c);
					}
					for (char c : hash2.keySet()) {
						if (hash2.get(c) > c2)
							c2 = hash2.get(c);
					}
					if (c1 > c2 && root.rank < root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 < c2 && root.rank > root.next.rank) {
						cardList.flipCards(root, root.next);
						root = cardList.getFirst();
						break;
					} else if (c1 == c2) {
						int i = root.compareTo(root.next);
						if (i == 1 && root.rank < root.next.rank) {
							cardList.flipCards(root, root.next);
							root = cardList.getFirst();
							break;
						} else if (i == -1 && root.rank > root.next.rank) {
							cardList.flipCards(root.next, root);
							root = cardList.getFirst();
							break;
						}
					}
				}

				Card h = cardList.getFirst();
				while (h.next != null && !done) {
					if (h.rank > h.next.rank) {
						done = false;
					} else {
						done = true;
					}
					h = h.next;
				}
				root = root.next;

				if (!done && root.next == null) {
					done = true;
				}

			}
		}
		// cardList.print();

		newHead = cardList.getFirst();
		while (newHead != null) {
			int winnings = newHead.bid * newHead.rank;
			sumUp(winnings);
			newHead = newHead.next;
		}

		System.out.println("Day seven, part two: " + sum);

	}

	public static void populate() {
		strength.put('A', 12);
		strength.put('K', 11);
		strength.put('Q', 10);
		strength.put('J', 9);
		strength.put('T', 8);
		strength.put('9', 7);
		strength.put('8', 6);
		strength.put('7', 5);
		strength.put('6', 4);
		strength.put('5', 3);
		strength.put('4', 2);
		strength.put('3', 1);
		strength.put('2', 0);
	}

	public static void populate2() {
		strength.clear();
		strength.put('A', 12);
		strength.put('K', 11);
		strength.put('Q', 10);
		strength.put('T', 9);
		strength.put('9', 8);
		strength.put('8', 7);
		strength.put('7', 6);
		strength.put('6', 5);
		strength.put('5', 4);
		strength.put('4', 3);
		strength.put('3', 2);
		strength.put('2', 1);
		strength.put('J', 0);
	}

	public static void sumUp(int power) {
		sum += power;
	}

	static class Card implements Comparable<Card> {
		int bid;
		String card;
		int rank;
		Card next;

		Card(String c, int b, int r) {
			this.card = c;
			this.bid = b;
			this.rank = r;
		}

		Card(String c, int b, int r, Card next) {
			this.card = c;
			this.bid = b;
			this.rank = r;
			this.next = next;
		}

		void setRank(int r) {
			this.rank = r;
		}

		int getRank() {
			return this.rank;
		}

		int getBid() {
			return this.bid;
		}

		String getCard() {
			return this.card;
		}

		@Override
		public int compareTo(Card c) {
			for (int i = 0; i < this.card.length(); i++) {
				if (strength.get(this.card.charAt(i)) > strength.get(c.getCard().charAt(i))) {
					return 1;
				} else if (strength.get(this.card.charAt(i)) < strength.get(c.getCard().charAt(i))) {
					return -1;
				}
			}
			return 0;
		}

	}

	static class CardList<O> {
		private Card head;

		CardList() {
			this.head = null;
		}

		void insertFirst(Card c) {
			Card temp = new Card(c.getCard(), c.getBid(), c.getRank(), head);
			this.head = temp;
		}

		Card getFirst() {
			return this.head;
		}

		void print() {
			Card temp = this.head;
			String tempS = "";
			while (temp != null) {
				tempS += temp.card + " " + temp.bid + " " + temp.rank + " \n";
				temp = temp.next;
			}
			System.out.println(tempS);
		}

		Card findCard(Card c) {
			Card temp = this.head;
			while (temp != null) {
				if (temp.card.equals(c.card))
					return c;
				temp = temp.next;
			}
			return null;
		}

		void flipCards(Card c1, Card c2) {
			Card prev1 = null;
			Card temp1 = head;

			// find c1 and previous
			while (temp1 != null && !temp1.card.equals(c1.card)) {
				prev1 = temp1;
				temp1 = temp1.next;
			}

			// find c2 and previous
			Card prev2 = null;
			Card temp2 = head;
			while (temp2 != null && !temp2.card.equals(c2.card)) {
				prev2 = temp2;
				temp2 = temp2.next;
			}

			if (temp1 == null || temp2 == null)
				return;

			if (prev1 != null)
				prev1.next = temp2;
			else
				head = temp2;

			if (prev2 != null)
				prev2.next = temp1;
			else
				head = temp1;

			int rankc1 = c1.getRank();
			int rankc2 = c2.getRank();
			Card temp = temp1.next;

			temp1.next = temp2.next;
			temp2.next = temp;

			temp1.setRank(rankc2);
			temp2.setRank(rankc1);
		}

		int getIndex(Card c) {
			Card temp = this.head;

			int size = numberOfCards();
			for (int i = 0; i < size - 1; i++) {
				if (temp.equals(c)) {
					return i;
				}
				temp = temp.next;
			}
			return -1;
		}

		Card get(int index) {
			Card temp = this.head;
			for (int i = 0; i < index - 1; i++) {
				temp = temp.next;
			}
			return temp;
		}

		int numberOfCards() {
			int size = 0;
			Card temp = head;
			while (temp != null) {
				size++;
				temp = temp.next;
			}
			return size;
		}
	}

}
