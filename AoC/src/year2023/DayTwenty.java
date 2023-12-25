package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;

public class DayTwenty {

	static long sum = 0;
	static Map<String, Module> input;
	static Map<Module, List<String>> des;
	static Map<Module, List<Module>> conMap;

	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day20.txt");
		Scanner scan = new Scanner(fr);

		List<String> in = new ArrayList<String>(); 
		while(scan.hasNextLine()) {
			in.add(scan.nextLine());
		}
		scan.close();

		input = new HashMap<String, Module>();
		des = new HashMap<Module, List<String>>();
		conMap = new HashMap<>(); 

		setUpLists(in);

		// del 1
		int amount = 1000; 
		for (int i = 0; i < amount; i++) {
			pressButton(input.get("butt"), new LinkedList<>());
		}
		multiply(Module.lowPulse, Module.highPulse);
		System.out.println("Day Twenty, Part One: " + sum);

		// del 2
		input.clear();
		des.clear();
		conMap.clear();
		setUpLists(in);
		Module.resetCounter();

		List<Integer> pulseToStart = getPart2();
		long sum = 1;
		for (int nrTillPulse : pulseToStart) {
			sum = lcm(sum, nrTillPulse);
		}
		System.out.println("Day Twenty, Part Two: " + sum);
	}

	static void pressButton(Module m, LinkedList<Module> q) {
		q.add(m);
		while (!q.isEmpty()) {
			Module current = q.pop();
			current.sendPulse();
			if (!(current instanceof EndModule) && current.pulseDone()) {
				q.addAll(current.getDestination());
			}
		}
	}

	static long lcm(long l1, long l2) {
		long max = Math.max(l1, l2);
		long min = Math.min(l1, l2);
		long l = max;
		while (l % min != 0) {
			l += max;
		}
		return l;
	}

	static List<Integer> getPart2() {
		Module br = input.get("broadcaster");
		List<Module> bc = br.getDestination();
		List<Integer> numbers = new ArrayList<>();
		for (Module m : bc) {
			List<Module> group = getGroup(m);
			String binaryString = build(group);
			numbers.add(Integer.parseInt(binaryString, 2));
		}
		return numbers;
	}

	static List<Module> getGroup(Module m) {
		List<Module> group = new ArrayList<>();
		group.add(m);
		for (Module e : m.getDestination()) {
			if (e instanceof FlipFlopModule) {
				group.addAll(getGroup(e));
			}
		}
		return group;
	}

	static String build(List<Module> group) {
		StringBuilder sb = new StringBuilder();
		for (Module module : group) {
			boolean foundC = false;
			for (Module d : module.getDestination()) {
				if (d instanceof Conjunction) {
					foundC = true;
					break;
				}
			}
			sb.append(foundC ? "1" : "0");
		}
		return sb.reverse().toString();
	}
	static void setUpLists(List<String> in) {
		for(String temp : in) {
			if(temp.startsWith("broadcaster")) {
				Module m = new Broadcaster(temp.substring(0, temp.indexOf(" ")));
				temp = temp.substring(temp.indexOf(">") + 1);
				List<String> out = new ArrayList<String>();
				String[] d = temp.split(",");
				for (String string : d) {
					out.add(string.trim());
				}
				input.put("broadcaster", m);
				des.put(m, out);
			} else if(temp.startsWith("%")) {
				String name = temp.substring(1, temp.indexOf(" "));
				Module m = new FlipFlopModule(name);
				temp = temp.substring(temp.indexOf(">") + 1);
				List<String> out = new ArrayList<String>();
				String[] d = temp.split(",");
				for (String string : d) {
					out.add(string.trim());
				}
				input.put(name, m);
				des.put(m, out);
			} else if(temp.startsWith("&")) {
				String name = temp.substring(1, temp.indexOf(" "));
				Module m = new Conjunction(name);
				temp = temp.substring(temp.indexOf(">") + 1);
				List<String> out = new ArrayList<String>();
				String[] d = temp.split(",");
				for (String string : d) {
					out.add(string.trim());
				}
				input.put(name, m);
				des.put(m, out);
			}
		}

		for (Entry<Module, List<String>> entry : des.entrySet()) {
			Module mod = entry.getKey();
			List<String> outputs = entry.getValue();
			List<Module> outputList = new ArrayList<>();
			for (String o : outputs) {
				if (!input.containsKey(o)) {
					input.put(o, new EndModule(o));
				}
				Module dest = input.get(o);
				outputList.add(dest);
				if (dest instanceof Conjunction) {
					conMap.putIfAbsent(dest, new ArrayList<>());
					List<Module> inputs = conMap.get(dest);
					inputs.add(mod);
					conMap.put(dest, inputs);
				}
			}
			mod.setDestinationModules(outputList);
		}

		for (Entry<Module, List<Module>> entry : conMap.entrySet()) {
			Conjunction c = (Conjunction) entry.getKey();
			c.setParent(entry.getValue());
		}

		Module button = new ButtonModule("butt");
		List<Module> bd = new ArrayList<>();
		bd.add(input.get("broadcaster"));
		button.setDestinationModules(bd);
		input.put(button.key, button);
	}

	static void multiply(long s, long s1) {
		System.out.println(s + " " + s1);
		sum = sum + s * s1;
	}

	static abstract class Module {
		static long lowPulse = 0; 
		static long highPulse = 0;
		String key; 
		List<Module> destination;
		Queue<PulseIn> inputq;

		Module(String n) {
			this.key = n; 
			this.inputq = new LinkedList<>();
		}
		void setDestinationModules(List<Module> dest) {
			this.destination = dest; 
		}
		List<Module> getDestination() {
			return this.destination;
		}
		abstract void sendPulse();
		abstract void setInput(PulseIn p);

		PulseIn next() {
			return this.inputq.poll();
		}

		static void resetCounter() {
			Module.lowPulse = 0; 
			Module.highPulse = 0;
		}
		abstract boolean pulseDone();

	}

	static class ButtonModule extends Module {
		ButtonModule(String n) {
			super(n);
		}

		@Override
		void sendPulse() {
			for(Module m : destination)  {
				m.setInput(new PulseIn(key, 1));
			}
			lowPulse += destination.size();		
		}

		@Override
		void setInput(PulseIn p) {
			System.out.println("no no");
		}
		@Override
		boolean pulseDone() {
			return true;
		}
	}

	record PulseIn(String key, int p) {};

	static class Broadcaster extends Module {

		Broadcaster(String n) {
			super(n);
		}

		@Override
		void sendPulse() {
			PulseIn in = next();
			for(Module m : destination)  {
				m.setInput(new PulseIn(key, in.p));
			}
			if (in.p == 1) {
				lowPulse += destination.size();
			} else {
				highPulse += destination.size();
			}		
		}

		@Override
		void setInput(PulseIn p) {
			this.inputq.add(p);
		}

		@Override
		boolean pulseDone() {
			return true; 
		}
	}

	static class Conjunction extends Module {
		Map<String, Integer> in; 

		Conjunction(String n) {
			super(n);
			in = new HashMap<>();		
		}

		void setParent(List<Module> p) {
			for (Module m : p) {
				in.put(m.key, 1);
			}
		}

		@Override
		void sendPulse() {
			if (in.values().stream().allMatch(p -> p == 2)) {
				for(Module m : destination)  {
					m.setInput(new PulseIn(key, 1));
				}
				lowPulse += destination.size();
			} else {
				for(Module m : destination)  {
					m.setInput(new PulseIn(key, 2));
				}
				highPulse += destination.size();
			}
		}

		@Override
		void setInput(PulseIn p) {
			in.put(p.key, p.p);
		}

		@Override
		boolean pulseDone() {
			return true;
		}
	}

	static class FlipFlopModule extends Module {
		int state = 0; 
		boolean done = false; 

		FlipFlopModule(String n) {
			super(n);
		}

		@Override
		void sendPulse() {
			done = false;
			PulseIn in = next();
			if (in.p == 1) {
				if(state == 0) {
					state = 1; 
					for (Module m : destination) {
						m.setInput(new PulseIn(this.key, 2));
					}
					done = true;
					highPulse += destination.size();
				} else {
					state = 0; 
					for (Module m : destination) {
						m.setInput(new PulseIn(this.key, 1));
					}
					done = true;
					lowPulse += destination.size();
				}
			}
		}

		@Override
		void setInput(PulseIn p) {
			this.inputq.add(p);
		}

		@Override
		boolean pulseDone() {
			return done;
		}
	}

	static class EndModule extends Module {

		EndModule(String n) {
			super(n);
		}

		@Override
		void sendPulse() {
			return;
		}

		@Override
		boolean pulseDone() {
			return false;
		}

		@Override
		void setInput(PulseIn p) {
		}

	}
}