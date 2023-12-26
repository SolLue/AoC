package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import utility.Property;

public class DaySeven {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day7.txt");
		BufferedReader br = new BufferedReader(fr);

		List<String> input = new ArrayList<String>();
		String t = br.readLine();
		while(t != null) {
			input.add(t);
			t = br.readLine();
		}
		br.close();

		Queue<String> q = new LinkedList<String>(); 
		for (String string : input) {
			q.add(string);
		}

		Map<String, Integer> signalsOnWire = new HashMap<String, Integer>();
		signalsOnWire = runWires(signalsOnWire, q);

		System.out.println("Day Seven, Part One: " + signalsOnWire.get("a"));

		int a = signalsOnWire.get("a");
		signalsOnWire.clear();
		signalsOnWire.put("b", a);
		
		q = new LinkedList<String>(); 
		for (String string : input) {
			q.add(string);
		}

		signalsOnWire = runWires(signalsOnWire, q);
		System.out.println("Day Seven, Part Two: " + signalsOnWire.get("a"));
	}

	static Map<String, Integer> runWires(Map<String, Integer> signalsOnWire, Queue<String> q) {
		while(!q.isEmpty()) {
			boolean skip = true; 
			String temp = q.poll();
			String[] options = temp.split(" ");
			if(options.length == 3) {
				if(isNumeric(options[0])) {
					skip = false; 
				} else if(signalsOnWire.containsKey(options[0])) {
					skip = false; 
				}
			} else if(options.length == 4) {
				if(isNumeric(options[1])) {
					skip = false; 
				} else if(signalsOnWire.containsKey(options[1])) {
					skip = false; 
				}
			} else if(options.length == 5) {
				boolean n1 = false; boolean n2 = false; 
				if(isNumeric(options[0]))
					n1 = true; 
				if(isNumeric(options[2]))
					n2 = true; 
				if(!n1 && !n2) {
					if(signalsOnWire.containsKey(options[0]) && signalsOnWire.containsKey(options[2])) {
						skip = false; 
					}
				}
				if(n1 || n2) {
					if(isNumeric(options[0]) && signalsOnWire.containsKey(options[2])) {
						skip = false; 
					} 
					if(isNumeric(options[2]) && signalsOnWire.containsKey(options[0])) {
						skip = false; 
					}
				}
			}
			if(skip) 
				q.add(temp);
			if(!skip) {
				if(temp.contains("AND")) {
					int n1 = 0; int n2 = 0; 
					boolean b1 = false; boolean b2 = false; 
					if(isNumeric(options[0])) {
						n1 = Integer.parseInt(options[0]);
						b1 = true; 
					}
					if(isNumeric(options[2])) {
						n2 = Integer.parseInt(options[2]);
						b2 = true; 
					}
					if(!b1 && !b2) {		
						int result = bitwiseAnd(signalsOnWire.get(options[0]), signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(b1 && !b2) {		
						int result = bitwiseAnd(n1, signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(!b1 && b2) {		
						int result = bitwiseAnd(signalsOnWire.get(options[0]), n2);
						signalsOnWire.put(options[4], result);
					}
				}
				else if(temp.contains("OR")) {
					int n1 = 0; int n2 = 0; 
					boolean b1 = false; boolean b2 = false; 
					if(isNumeric(options[0])) {
						n1 = Integer.parseInt(options[0]);
						b1 = true; 
					}
					if(isNumeric(options[2])) {
						n2 = Integer.parseInt(options[2]);
						b2 = true; 
					}
					if(!b1 && !b2) {		
						int result = bitwiseOr(signalsOnWire.get(options[0]), signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(b1 && !b2) {		
						int result = bitwiseOr(n1, signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(!b1 && b2) {		
						int result = bitwiseOr(signalsOnWire.get(options[0]), n2);
						signalsOnWire.put(options[4], result);
					}
				}
				else if(temp.contains("LSHIFT")) {
					int n1 = 0; int n2 = 0; 
					boolean b1 = false; boolean b2 = false; 
					if(isNumeric(options[0])) {
						n1 = Integer.parseInt(options[0]);
						b1 = true; 
					}
					if(isNumeric(options[2])) {
						n2 = Integer.parseInt(options[2]);
						b2 = true; 
					}
					if(!b1 && !b2) {		
						int result = shiftLeft(signalsOnWire.get(options[0]), signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(b1 && !b2) {		
						int result = shiftLeft(n1, signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(!b1 && b2) {		
						int result = shiftLeft(signalsOnWire.get(options[0]), n2);
						signalsOnWire.put(options[4], result);
					}
				}
				else if(temp.contains("RSHIFT")) {
					int n1 = 0; int n2 = 0; 
					boolean b1 = false; boolean b2 = false; 
					if(isNumeric(options[0])) {
						n1 = Integer.parseInt(options[0]);
						b1 = true; 
					}
					if(isNumeric(options[2])) {
						n2 = Integer.parseInt(options[2]);
						b2 = true; 
					}
					if(!b1 && !b2) {		
						int result = shiftRight(signalsOnWire.get(options[0]), signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(b1 && !b2) {		
						int result = shiftRight(n1, signalsOnWire.get(options[2]));
						signalsOnWire.put(options[4], result);
					} else if(!b1 && b2) {		
						int result = shiftRight(signalsOnWire.get(options[0]), n2);
						signalsOnWire.put(options[4], result);
					}
				}
				else if(temp.contains("NOT")) {
					int result = bitwiseNot(signalsOnWire.get(options[1]));
					signalsOnWire.put(options[3], result);
				}
				else {  
					int n1 = 0; boolean b1 = false;  
					if(isNumeric(options[0])) {
						n1 = Integer.parseInt(options[0]);
						b1 = true; 
					}
					if(!signalsOnWire.containsKey(options[2])) {
						if(b1) {
							signalsOnWire.put(options[2], n1);
						} else {
							signalsOnWire.put(options[2], signalsOnWire.get(options[0]));											
						}
					} 
				}
			}
		}
		return signalsOnWire;
	}

	static int shiftRight(int n1, int n2) {
		int result = 0; 
		String binary1 = Integer.toBinaryString(n1);
		if(binary1.length() != 16) {
			int r = 16 - binary1.length();
			binary1 = "0".repeat(r) + binary1;
		}
		binary1 = binary1.substring(0, binary1.length() - n2);
		binary1 = "0".repeat(n2) + binary1;

		result = Integer.parseInt(binary1, 2);
		return result;
	}
	
	static int shiftLeft(int n1, int n2) {
		int result = 0; 
		String binary1 = Integer.toBinaryString(n1);
		if(binary1.length() != 16) {
			int r = 16 - binary1.length();
			binary1 = "0".repeat(r) + binary1;
		}
		binary1 = binary1.substring(n2, binary1.length());
		binary1 = binary1 + "0".repeat(n2);

		result = Integer.parseInt(binary1, 2);
		return result;
	}
	
	static int bitwiseAnd(int n1, int n2) {
		int result = 0; 
		String binary1 = Integer.toBinaryString(n1);
		String binary2 = Integer.toBinaryString(n2);
		if(binary1.length() != 16) {
			int r = 16 - binary1.length();
			binary1 = "0".repeat(r) + binary1;
		}
		if(binary2.length() != 16) {
			int r = 16 - binary2.length();
			binary2 = "0".repeat(r) + binary2;
		}
		String res = "";
		for(int i = 0; i < binary1.length(); i++) {
			if(binary1.charAt(i) == '1' && binary2.charAt(i) == '1') 
				res += "1";
			else
				res += "0"; 
		}
		result = Integer.parseInt(res, 2);
		return result;
	}
	
	static int bitwiseOr(int n1, int n2) {
		int result = 0; 
		String binary1 = Integer.toBinaryString(n1);
		String binary2 = Integer.toBinaryString(n2);
		if(binary1.length() != 16) {
			int r = 16 - binary1.length();
			binary1 = "0".repeat(r) + binary1;
		}
		if(binary2.length() != 16) {
			int r = 16 - binary2.length();
			binary2 = "0".repeat(r) + binary2;
		}
		String res = "";
		for(int i = 0; i < binary1.length(); i++) {
			if(binary1.charAt(i) == '0' && binary2.charAt(i) == '0')
				res += "0";
			else 
				res+= "1";
		}
		result = Integer.parseInt(res, 2);
		return result;
	}

	static int bitwiseNot(int n) {
		int result = 0; 
		String binary = Integer.toBinaryString(n);
		if(binary.length() != 16) {
			int r = 16 - binary.length();
			binary = "0".repeat(r) + binary;
		}
		String res = "";
		for(int i = 0; i < binary.length(); i++) {
			if(binary.charAt(i) == '1')
				res += "0";
			else 
				res+= "1";
		}
		result = Integer.parseInt(res, 2);
		return result;

	}

	static boolean isNumeric(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}