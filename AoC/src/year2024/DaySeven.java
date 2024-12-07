package year2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utility.Property;

public class DaySeven {
	public static void main(String[] args) throws IOException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2024/input/day7.txt");
		BufferedReader br = new BufferedReader(fr);

		List<List<Long>> input = new ArrayList<List<Long>>();
		String temp = br.readLine();
		while (temp != null) {
			String[] t = temp.split("\\s+");
			List<Long> list = new ArrayList<Long>();
			long result = Long.parseLong(t[0].substring(0, t[0].length() - 1));
			list.add(result);
			for (int i = 1; i < t.length; i++) {
				long te = Long.parseLong(t[i]);
				list.add(te);
			}
			input.add(list);
			temp = br.readLine();
		}
		br.close();

		long startTime = System.currentTimeMillis();

		Operator[] oparray = new Operator[] { Operator.ADD, Operator.MULTIPLY };


		long calibration = goThroughPossibleOperation(input, oparray);

		long stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part One: " + calibration);
		System.out.println("Time in ms " + (stopTime - startTime));

		startTime = System.currentTimeMillis();

		Operator[] oparray1 = new Operator[] { Operator.ADD, Operator.MULTIPLY, Operator.CAT };
		calibration = goThroughPossibleOperation(input, oparray1);

		stopTime = System.currentTimeMillis();
		System.out.println("Day Seven, Part Two: " + calibration);
		System.out.println("Time in ms " + (stopTime - startTime));
	}

	static long goThroughPossibleOperation(List<List<Long>> input, Operator[] oparray) {
		long calibration = 0; 
		for (List<Long> numberList : input) {
			List<List<Operator>> possibleOperations = new ArrayList<List<Operator>>();
			possibleOperations.add(new ArrayList<Operator>()); 
			for(int i = 1; i < numberList.size() - 1; i++) {
				List<List<Operator>> placeholder = new ArrayList<List<Operator>>();
				for (List<Operator> possible : possibleOperations) {
					for (Operator op : oparray) {
						List<Operator> tempList = new ArrayList<Operator>(possible);
						tempList.add(op);
						placeholder.add(tempList);
					}
				}
				possibleOperations = placeholder;
			}

			long result = numberList.get(0);
			for (List<Operator> possible : possibleOperations) {
				long tempresult = numberList.get(1);
				for (int i = 0; i < possible.size(); i++) {
					tempresult = doOperation(tempresult, numberList.get(i + 2), possible.get(i));
					if (tempresult > result)
						break;
				}
				if (tempresult == result) {
					calibration += result;
					break;
				}
			}
		}
		return calibration;
	}

	static long doOperation(long nr1, long nr2, Operator op) {
		if (op == Operator.MULTIPLY)
			return nr1 * nr2;
		else if (op == Operator.ADD)
			return nr1 + nr2;
		else if (op == Operator.CAT) {
			return Long.parseLong(String.valueOf(nr1) + String.valueOf(nr2));
		}
		return 0;
	}

	enum Operator {
		ADD, MULTIPLY, CAT
	}
}
