package year2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Property;

public class DayEleven {
	
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2025/input/day11.txt");
		BufferedReader br = new BufferedReader(fr);

		Device start = null; 
		Device svr = null;
		
		List<Device> devices = new ArrayList<Device>();
		String temp = br.readLine();
		while(temp != null) {
			temp = temp.replace(":", ""); 
			String rr[] = temp.split("\\s+");
			List<String> arr = new ArrayList<String>(); 
			for(int i = 1; i < rr.length; i++) {
				arr.add(rr[i]);
			}
			Device d = new Device(rr[0], arr);
			
			devices.add(d);			
			if (rr[0].equals("you"))
				start = new Device(rr[0], arr);
			if (rr[0].equals("svr"))
				svr = new Device(rr[0], arr);
			
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis(); 
		
		Map<Device, Long> cache = new HashMap<Device, Long>();
		long allPaths = findPaths(devices, start, cache, true);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Day Eleven, Part One: " + allPaths);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis(); 

		cache = new HashMap<Device, Long>();
		long allPaths2 = findPaths(devices, svr, cache, false);
		
		stopTime = System.currentTimeMillis();
		System.out.println("Day Eleven, Part Two: " + allPaths2);
		System.out.println("Time in ms " + (stopTime - startTime));
	}
	
	
	static long findPaths(List<Device> devices, Device from, Map<Device, Long> cache, boolean part1) {
        if (cache.containsKey(from))
            return cache.get(from);
    
        if (from.name.equals("out")) {
            if ((from.visiteddac && from.visitedfft) || part1)
                return 1;
            return 0;
        }

        long found = 0;
        for (Device out : from.getOutput(devices)) {
        	boolean founddac = from.visiteddac || out.name.equals("dac");
        	boolean foundfft = from.visitedfft || out.name.equals("fft");
        	out.visiteddac = founddac;
        	out.visitedfft = foundfft;
        	found += findPaths(devices, out, cache, part1);

        }
        cache.put(from, found);
        return found;
    }
	
	static class Device {
		String name; 
		List<String> output; 
		boolean visiteddac = false; 
		boolean visitedfft = false; 
		
		Device(String n, List<String> o) {
			this.name = n; 
			this.output = o;
		}
	
		Device(String n) {
			this.name = n; 
			this.output = new ArrayList<String>();
		}

		List<Device> getOutput(List<Device> devices) {
			List<Device> out = new ArrayList<Device>();
			if (this.output.isEmpty()) {
				this.output = fetchOutput(devices, name);
			}
			for (String string : this.output) {
				Device d = new Device(string, fetchOutput(devices, string));
				out.add(d);
			}
			return out; 
		}
		
		List<String> fetchOutput(List<Device> devices, String name) {
			List<String> out = new ArrayList<String>(); 
			for (Device device : devices) {
				if(name.equals(device.name))
					out = device.output;
			}
			return out; 
		}
		
		@Override
		public String toString() {
			return "[" + this.name + "]";
		}
		
		@Override
	    public int hashCode() {
	        return this.name.hashCode() + String.valueOf(visiteddac).hashCode() + String.valueOf(visitedfft).hashCode();
	    }

	    @Override
	    public boolean equals(Object o) {
	    	Device c = (Device) o;
	    	if(this.name.equals(c.name) && this.visiteddac == c.visiteddac && this.visitedfft == c.visitedfft) {
	    		return true; 
	    	}
	    	return false;
	    }
	}
}
