package year2023;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayEighteen {
	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("day18.txt");
		Scanner scan = new Scanner(fr);

		List<String> input = new ArrayList<String>();

		while(scan.hasNextLine()) {
			input.add(scan.nextLine());
		}		
		scan.close();

		List<Point> points = new ArrayList<Point>();
		int lasty = 0; 
		int lastx = 0; 
		
		for (String s : input) {
			char letter = s.charAt(0);
			int amount = Integer.parseInt(s.substring(s.indexOf(" ") + 1, s.indexOf("(") - 1));
			if(letter == 'U') {
				lasty = lasty + amount; 
			}
			if(letter == 'R') {
				lastx = lastx + amount;
			}
			if(letter == 'D') {
				lasty = lasty - amount;
			}
			if(letter == 'L') {
				lastx = lastx - amount;
			}
			points.add(new Point(lastx, lasty));
		}

		List<Point> dis = new ArrayList<Point>();
		dis.add(new Point(0, 0));
		for(Point p : points) {
			dis.add(p);
		}
		int distance = 0;
        for (int i = 0; i < dis.size() - 1; i++) {
        	int x = dis.get(i + 1).x - dis.get(i).x;
        	int y = dis.get(i + 1).y - dis.get(i).y;
        	distance = distance + (int) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        }
		        
		int shoelace = shoelace(points); 
		shoelace = shoelace + (distance / 2) + 1;

		System.out.println("Day Eighteen, Part One: " + shoelace);
		
		List<PointL> pointsL = new ArrayList<PointL>();
		List<PointL> disL = new ArrayList<PointL>();

		long lastyL = 0; 
		long lastxL = 0; 
		
		for (String s : input) {
			String hexa = s.substring(s.indexOf("#") + 1, s.indexOf(")"));
			String l = String.valueOf(hexa.charAt(hexa.length() - 1));
			int letter = Integer.parseInt(l);
			String hexaNumber = s.substring(s.indexOf("#") + 1, s.length() - 2);
			long amount = Long.parseLong(hexaNumber, 16);
			if(letter == 3) {
				lastyL = lastyL + amount; 
			}
			if(letter == 0) {
				lastxL = lastxL + amount;
			}
			if(letter == 1) {
				lastyL = lastyL - amount;
			}
			if(letter == 2) {
				lastxL = lastxL - amount;
			}
			pointsL.add(new PointL(lastxL, lastyL));
		}
		
		disL.add(new PointL(0, 0));
		for(PointL p : pointsL) {
			disL.add(p);
		}
		
		long distanceL = 0;
        for (int i = 0; i < disL.size() - 1; i++) {
        	long x = disL.get(i + 1).x - disL.get(i).x;
        	long y = disL.get(i + 1).y - disL.get(i).y;
        	distanceL = distanceL + (long) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        }
		long shoelaceL = shoelaceL(pointsL); 
		shoelaceL = shoelaceL + (distanceL / 2) + 1;
		
		System.out.println("Day Eighteen, Part Two: " + shoelaceL);
	}

    static int shoelace(List<Point> v) {
        int n = v.size();
        int a = 0;
        
        for (int i = 0; i < n - 1; i++) {
            a += v.get(i).x * v.get(i + 1).y - v.get(i + 1).x * v.get(i).y;
        }
        return Math.abs(a + v.get(n - 1).x * v.get(0).y - v.get(0).x * v.get(n - 1).y) / 2;
    }
    
    static long shoelaceL(List<PointL> v) {
        int n = v.size();
        long a = 0;
        
        for (int i = 0; i < n - 1; i++) {
            a += v.get(i).x * v.get(i + 1).y - v.get(i + 1).x * v.get(i).y;
        }
        return Math.abs(a + v.get(n - 1).x * v.get(0).y - v.get(0).x * v.get(n - 1).y) / 2;
    }
    
    static class Point {
    	int x; 
    	int y; 
    	Point(int x, int y) {
    		this.x = x;
    		this.y = y; 
    	}
    }
    static class PointL {
    	long x; 
    	long y; 
    	PointL(long x, long y) {
    		this.x = x;
    		this.y = y; 
    	}
    }
}