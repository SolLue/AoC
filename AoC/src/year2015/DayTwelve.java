package year2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utility.Property;

public class DayTwelve {
	public static void main(String[] args) throws IOException, JSONException {
		String folder = Property.getFilePathHome();
		FileReader fr = new FileReader(folder + "/year2015/input/day12.txt");
		BufferedReader br = new BufferedReader(fr);

		String input = br.readLine();
		br.close();

		List<String> list = findNumbers(input);				  
		int count = countNumbers(list);
		System.out.println("Day Twelve, Part One: " + count);

		JSONArray json = new JSONArray(input);
		
		count = 0; 
		count = intValue(json);
		System.out.println("Day Twelve, Part Two: " + count);
	}

	static List<String> findNumbers(String in) {
		Pattern pattern = Pattern.compile("-?\\d+");
		Matcher matcher = pattern.matcher(in);

		List<String> list = new ArrayList<>();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}

	static int countNumbers(List<String> in) {
		int count = 0; 
		for (String string : in) {
			count += Integer.parseInt(string);
		}		
		return count; 
	}

	static int intValue(Object object) throws JSONException {
		if(object instanceof Integer) return (int)object;
		if(object instanceof String) return 0;
		 
		int count = 0; 
		 
		if(object instanceof JSONArray) {
			for(int i = 0; i < ((JSONArray) object).length(); i++) {
				int v = intValue(((JSONArray)object).get(i));
				count += v; 
			}
			return count; 
		}
		
		if(object instanceof JSONObject) {
			JSONObject obj = (JSONObject) object; 
			JSONArray n = obj.names();
			for(int i = 0; i < n.length(); i++) {
				String name = (String) n.get(i);
				if(obj.get(name).equals("red")) {
					return 0;
				} else {
					count += intValue(((JSONObject) object).get(name)); 
				}
			}
			return count; 
		}
		
		return count;  
	}
}
