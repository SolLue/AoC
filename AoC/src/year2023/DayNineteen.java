package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class DayNineteen {

	static int sum;
	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day19.txt");
		Scanner scan = new Scanner(fr);

		List<String> workf = new ArrayList<String>();
		List<String> input = new ArrayList<String>();

		boolean work = true; 
		while(scan.hasNextLine()) {
			String temp = scan.nextLine();
			if(temp.isBlank()) {
				work = false; 
				temp = scan.nextLine();
			}
			if(work)
				workf.add(temp);
			if(!work)
				input.add(temp);
		}		
		scan.close();

		InstructionsTree tree =  parseWorkflow(workf);
		List<Part> partList = new ArrayList<Part>();
		partList = parseParts(partList, input);

		goThroughList(tree, partList);

		System.out.println("Day Nineteen, Part One: " + sum);


		long sum2 = 0; 

		traversePart2(tree);
		sum2 = distinctValues();

		System.out.println("Day Nineteen, Part Two: " + sum2);
	}

	static List<int[]> x = new ArrayList<int[]>(); 
	static List<int[]> m = new ArrayList<int[]>(); 
	static List<int[]> a = new ArrayList<int[]>(); 
	static List<int[]> s = new ArrayList<int[]>(); 

	static void traversePart2(InstructionsTree tree) {
		List<List<String>> allPaths = new ArrayList<List<String>>();
		allPaths = InstructionsTree.paths();

		List<List<String>> acceptedPaths = new ArrayList<List<String>>();
		List<List<String>> rejectedPaths = new ArrayList<List<String>>();


		for (List<String> list : allPaths) {
			if(list.get(list.size() - 1).contains("R"))
				rejectedPaths.add(list);
			if(list.get(list.size() - 1).contains("A"))
				acceptedPaths.add(list);
		}
	
		int xmin = 1; int xmax = 4000;
		int mmin = 1; int mmax = 4000;
		int amin = 1; int amax = 4000;
		int smin = 1; int smax = 4000;

		for (List<String> list : acceptedPaths) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).length() > 3) {
					String letter = String.valueOf(list.get(i).charAt(0));
					String symbol = String.valueOf(list.get(i).charAt(1));
					int number = Integer.parseInt(list.get(i).substring(list.get(i).indexOf(symbol) + 1, list.get(i).indexOf(" ")));
					int flag = Integer.parseInt(String.valueOf(list.get(i + 1).charAt(list.get(i + 1).length() - 1)));
					if(flag == 0) {
						if(symbol.equals(">"))
							symbol = "<=";
						else
							symbol = ">=";
					}

					if(letter.equals("x")) {
						if(symbol.equals(">") && number > xmin)  
							xmin = number + 1;
						else if(symbol.equals(">=") && number >= xmin)  
							xmin = number;
						else if(symbol.equals("<") && number < xmax)  
							xmax = number - 1;
						else if(symbol.equals("<=") && number <= xmax)  
							xmax = number;
					}
					if(letter.equals("m")) {
						if(symbol.equals(">") && number > mmin)  
							mmin = number + 1;
						else if(symbol.equals(">=") && number >= mmin)  
							mmin = number;
						else if(symbol.equals("<") && number < mmax)  
							mmax = number - 1;
						else if(symbol.equals("<=") && number <= mmax)  
							mmax = number;
					}
					if(letter.equals("a")) {
						if(symbol.equals(">") && number > amin)  
							amin = number + 1;
						else if(symbol.equals(">=") && number >= amin)  
							amin = number;
						else if(symbol.equals("<") && number < amax)  
							amax = number - 1;
						else if(symbol.equals("<=") && number <= amax)  
							amax = number;
					}
					if(letter.equals("s")) {
						if(symbol.equals(">") && number > smin)  
							smin = number + 1;
						else if(symbol.equals(">=") && number >= smin)  
							smin = number;
						else if(symbol.equals("<") && number < smax)  
							smax = number - 1;
						else if(symbol.equals("<=") && number <= smax)  
							smax = number;
					}
				}
			}

			x.add(new int[] {xmin, xmax});
			m.add(new int[] {mmin, mmax});
			a.add(new int[] {amin, amax});
			s.add(new int[] {smin, smax});

			xmin = 1; xmax = 4000;
			mmin = 1; mmax = 4000;
			amin = 1; amax = 4000;
			smin = 1; smax = 4000;
		}		

	}


	static long distinctValues() {
		long sum2 = 0; 
		for(int i = 0; i < x.size(); i++) {
			sum2 = sum2 + (long) ((x.get(i)[1] - x.get(i)[0]) + 1) * ((m.get(i)[1] - m.get(i)[0]) + 1)
					* ((a.get(i)[1] - a.get(i)[0]) + 1) * ((s.get(i)[1] - s.get(i)[0]) + 1);
		}
		return sum2;  
	}

	static InstructionsTree parseWorkflow(List<String> workf) {
		InstructionsTree tree = new InstructionsTree();
		String root = "";
		for(int i = 0; i < workf.size(); i++) {
			if(workf.get(i).startsWith("in")) {
				root = workf.get(i);
				workf.remove(i);
				break;
			}
		}

		String current = root; 
		current = current.substring(current.indexOf("{") + 1, current.length() - 1);

		Instructions r = new Instructions(current.substring(0, current.indexOf(":")));
		tree.insert(r);

		current = current.substring(current.indexOf(":") + 1);
		String left = current.substring(0, current.indexOf(","));
		String right = current.substring(current.indexOf(",") + 1);

		tree.insertOk(r, new Instructions(left));
		tree.insertNotOk(r, new Instructions(right));

		Stack<Pair> inst = new Stack<Pair>();

		inst.add(new Pair(r, left));
		inst.add(new Pair(r, right));

		while(!inst.isEmpty()) {
			Pair p = inst.pop(); 
			String child = "";
			boolean lookUp = false; 
			boolean ins = false;
			if(!p.child.equals("R") && !p.child.equals("A")) {
				if(!p.child.contains("<") && !p.child.contains(">")) {
					for(int i = 0; i < workf.size(); i++) {
						if(workf.get(i).substring(0, workf.get(i).indexOf("{")).equals(p.child)) {
							child = workf.get(i);
							workf.remove(i);
							lookUp = true; 
							break;
						}
					}	
				}
			} else
				child = p.child;

			if(p.child.length() > 2 && (p.child.contains("<") || p.child.contains(">"))) {
				ins = true;
				child = p.child;
			} 

			if(ins) {
				Instructions newParent = InstructionsTree.findInstruction(p.child);
				if(newParent != null)
					newParent.data = child.substring(0, child.indexOf(":"));

				child = child.substring(child.indexOf(":") + 1);
				tree.insertOk(newParent, new Instructions(child.substring(0, child.indexOf(","))));

				left = child.substring(0, child.indexOf(","));
				right = child.substring(child.indexOf(",") + 1);

				if(!left.equals("A") && !left.equals("R")) {
					inst.add(new Pair(newParent, left));
				}

				if(!right.equals("A") && !right.equals("R")) {
					inst.add(new Pair(newParent, right));
				}
				tree.insertNotOk(newParent, new Instructions(right));
			}


			if(lookUp && !ins) {
				child = child.substring(child.indexOf("{") + 1, child.length() - 1);
				Instructions newParent = InstructionsTree.findInstruction(p.child);
				if(newParent != null) {
					newParent.data = child.substring(0, child.indexOf(":"));
				}

				child = child.substring(child.indexOf(":") + 1);
				inst.add(new Pair(newParent, child.substring(0, child.indexOf(","))));
				tree.insertOk(newParent, new Instructions(child.substring(0, child.indexOf(","))));

				child = child.substring(child.indexOf(",") + 1);
				inst.add(new Pair(newParent, child));
				tree.insertNotOk(newParent, new Instructions(child));

			}
		}

		return tree;

	}

	static List<Part> parseParts(List<Part> p, List<String> in) {
		for(String st : in) {
			st = st.substring(1, st.length() - 1);

			String[] options = st.split(",");
			int x = 0; int s = 0; int a = 0; int m = 0;
			for (String string : options) {
				string = string.trim();
				if(string.startsWith("x")) {
					x = Integer.parseInt(string.substring(string.indexOf("=") + 1));
				}
				if(string.startsWith("s")) {
					s = Integer.parseInt(string.substring(string.indexOf("=") + 1));
				}
				if(string.startsWith("a")) {
					a = Integer.parseInt(string.substring(string.indexOf("=") + 1));
				}
				if(string.startsWith("m")) {
					m = Integer.parseInt(string.substring(string.indexOf("=") + 1));
				}
			}
			Part part = new Part(x, m, a, s);
			p.add(part);
		}		
		return p;
	}

	static void goThroughList(InstructionsTree tree, List<Part> parts) {
		Instructions current = InstructionsTree.root; 
		String letter = ""; String symbol = ""; int number = 0;

		while(!parts.isEmpty()) {
			boolean ok = false;
			if(current.data.length() > 2) {
				letter = current.data.substring(0, 1);
				symbol = current.data.substring(1, 2);
				number = Integer.parseInt(current.data.substring(current.data.indexOf(symbol) + 1));
			}

			if(letter.equals("x")) {
				if(symbol.equals("<"))
					ok = parts.get(0).x < number;
				else
					ok = parts.get(0).x > number;
			} else if(letter.equals("m")) {
				if(symbol.equals("<"))
					ok = parts.get(0).m < number;
				else
					ok = parts.get(0).m > number;		
			} else if(letter.equals("a")) {
				if(symbol.equals("<"))
					ok = parts.get(0).a < number;
				else
					ok = parts.get(0).a > number;
			} else if(letter.equals("s")) {
				if(symbol.equals("<"))
					ok = parts.get(0).s < number;
				else
					ok = parts.get(0).s > number;
			}

			if(ok) {
				current = current.ok; 
				if(current.data.equals("R")) {
					parts.remove(0);
					current = InstructionsTree.root;
				} else if(current.data.equals("A")) {
					sum(parts.get(0));
					parts.remove(0);
					current = InstructionsTree.root;
				} 
			}

			if(!ok) {
				current = current.notOk;
				if(current.data.equals("R")) {
					parts.remove(0);
					current = InstructionsTree.root;
				} else if(current.data.equals("A")) {
					sum(parts.get(0));
					parts.remove(0);
					current = InstructionsTree.root;
				} 
			}
		}
	}

	static void sum(Part p) {
		sum = sum + p.x + p.m + p.a + p.s; 
	}

	record Part(int x, int m, int a, int s) {};

	record Pair(Instructions parent, String child) {};

	static class Instructions {
		String data; 
		Instructions parent;
		Instructions ok;
		Instructions notOk; 

		Instructions(String data) {
			this.data = data;
			this.ok = null;
			this.notOk = null;
			this.parent = null;
		}
		void setOk(Instructions o) {
			this.ok = o;
		}
		void setNotOk(Instructions o) {
			this.notOk = o;
		}
		boolean checkOk(int check) {
			if(this.data.charAt(1) == '>') {
				return check > Integer.parseInt(String.valueOf(this.data.substring(2)));
			} else {
				return check < Integer.parseInt(String.valueOf(this.data.substring(2)));
			}
		}
		public String toString() {
			return this.data;
		}
	}

	static class InstructionsTree {
		static Instructions root; 
		InstructionsTree() {
			root = null;
		}
		void insert(Instructions i) {
			if(root == null) {
				root = i;
			}
		}
		void insertOk(Instructions parent, Instructions i) {
			Instructions p = findInstruction(parent.data);	

			if(p.data.equals(parent.data)) {
				p.setOk(i);
				i.parent = p;
			}
		}	

		void insertNotOk(Instructions parent, Instructions i) {
			Instructions p = findInstruction(parent.data);

			if(p.data.equals(parent.data)) {
				p.setNotOk(i);
				i.parent = p;
			}
		}	

		static Instructions findInstruction(String data) {
			return find(root, data);
		}

		static Instructions find(Instructions node, String data){
			if(node != null){
				if(node.data.equals(data)){
					return node;
				} else {
					Instructions foundNode = find(node.ok, data);
					if(foundNode == null) {
						foundNode = find(node.notOk, data);
					}
					return foundNode;
				}
			} else {
				return null;
			}
		}

		static void inOrder(Instructions node) {
			inOrderTraversalNode(node, 0);
		}

		static void inOrderTraversalNode(Instructions node, int counter) {
			if(node != null){
				System.out.println(counter + " " + node);
				counter++;				
				inOrderTraversalNode(node.ok, counter);
				inOrderTraversalNode(node.notOk, counter);
			}
		}
		static void helperTraversePath(Instructions root, List<String> arr, List<List<String>> ans, int side) {
			if (root == null)
				return;
			arr.add(root.data + " " + side);
			if (root.ok == null && root.notOk == null) {
				ans.add(new ArrayList<String>(arr));
				return;
			}

			helperTraversePath(root.ok, new ArrayList<String>(arr), ans, 1);
			helperTraversePath(root.notOk, new ArrayList<String>(arr), ans, 0);
		}

		static List<List<String>> paths() {
			List<List<String>> ans = new ArrayList<List<String>>();
			if (root == null)
				return ans;
			List<String> arr = new ArrayList<String>();
			helperTraversePath(root, arr, ans, -1);

			return ans;
		}

	}
}
