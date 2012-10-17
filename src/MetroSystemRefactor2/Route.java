package MetroSystemRefactor2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import javax.rmi.CORBA.Util;

import Utility.Utility;

public class Route{
	private LinkedHashMap<String, Double> sectionTime;
	private LinkedHashMap<String, String> sectionLines;
	private MetroMap mmap;
	private ArrayList<Integer> path;
	private double TotalTime;
	
	public Route(MetroMap mm) {
		sectionTime = new LinkedHashMap<String, Double>();
		sectionLines = new LinkedHashMap<>();
		mmap = mm;
		TotalTime = 0;
	}
	
	public void setPath(ArrayList<Integer> p) {
		path = p;
	}
	
	public LinkedHashMap<String, Double> getSectionStationsTimes() {
		return sectionTime;
	}
	
	public void setupRouteVariables() {
		Integer s1Index, s2Index;
		Station s1, s2;
		HashSet<String> commonLines;
		ArrayList<Integer> stationIndexList;
		String line;
		double time;
		
		s1 = mmap.getStationByIndex(path.get(0));
		sectionTime.put(s1.getName(), 0.0);
		for (int i=1; i < path.size()-1; i++) {
			s1Index = path.get(i-1);
			s2Index = path.get(i);
			s1 = mmap.getStationByIndex(s1Index);
			s2 = mmap.getStationByIndex(s2Index);
			time = mmap.getRail(s1Index, s2Index).getTime();
			
			commonLines = Utility.getSetIntersect(s1.getLines(), s2.getLines());
			if (!commonLines.isEmpty()) {
				int timeToStart, timeToEnd, timeTotal;
				
				line = (String) commonLines.toArray()[0];
				stationIndexList = mmap.getStationIndexListByLine(line);
				
				
			} else {
				sectionTime.put(s2.getName(), 0.0);
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		MetroBuilder mb = new MetroBuilder();
		mb.setDirectoryPath("./SubwayMaps/TokyoMetroMap/");
		MetroMap mmap = mb.buildSubwayFromCSV();
		Route r = new Route(mmap);
		
		
		LinkedHashMap<String, Double> sectionTime = new LinkedHashMap<>();
		System.out.println(sectionTime.keySet());
	}
	
	
}