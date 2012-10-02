package MetroSystemRefactor2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Route {
	private LinkedHashMap<String, Double> sectionTime;
	
	public Route() {
		sectionTime = new LinkedHashMap<String, Double>();
	}
	
	public void addSectionTime(String line, Double time) {
		if (!sectionTime.containsKey(line)) {
			sectionTime.put(line, time);
		} else {
			System.err.println("Error : Line "+line+" already exists in this route; cannot have duplicate lines in the same route");
			System.exit(-1);
		}
	}
	
	public Double getTotalTime() {
		Double totalTime = 0.0;
		for (Double time : sectionTime.values()) {
			totalTime += time;
		}
		
		return totalTime;
	}
	
	public static void main(String args[]) {
		LinkedHashMap<String, Integer> yo = new LinkedHashMap<>();
		yo.put("One", 1);
		yo.put("Two", 2);
		yo.put("Three", 3);
		
		for (Integer i : yo.values()) {
			System.out.println(i);
		}
	}
}