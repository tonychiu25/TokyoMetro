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
	
	public LinkedHashMap<String, String> getSectionLines() {
		return sectionLines;
	}
	
	public LinkedHashMap<String, Double> getSectionTimes() {
		return sectionTime;
	}
	
	public void setPath(ArrayList<Integer> p) {
		path = p;
		setupRouteVariables();
	}
	
	public LinkedHashMap<String, Double> getSectionStationsTimes() {
		return sectionTime;
	}
	
	public double getTotalTime() {
		return TotalTime;
	}
	
	private void setupRouteVariables() {
		Integer prevStationIndex = null;
		Station s;
		Double timepersection = 0.0;
		HashSet<String> intersectLineSet;
		for (Integer stationIndex : path) {
			s = mmap.getStationByIndex(stationIndex);
			if (prevStationIndex == null) {
				sectionTime.put(s.getName(), 0.0);
			} else {
//				rail = mmap.getRail(prevStationIndex, stationIndex);
//				intersectLineSet = Utility.getSetIntersect(mmap.getStationByIndex(stationIndex).getLines(), 
//														   mmap.getStationByIndex(prevStationIndex).getLines());
//				
//				sectionTime.put(s.getName(), rail.getTime());
//				if (!intersectLineSet.isEmpty()) {
//					sectionLines.put(s.getName(), (String)intersectLineSet.toArray()[0]);
//				} else {
//					sectionLines.put(s.getName(), Connection.SATELITE_CONNECTION_LINE);
//				}
//				
//				TotalTime += rail.getTime();
				intersectLineSet = Utility.getSetIntersect(mmap.getStationByIndex(stationIndex).getLines(), 
						   								   mmap.getStationByIndex(prevStationIndex).getLines());
				
				if (intersectLineSet.isEmpty()) {
					sectionLines.put(s.getName(), Connection.SATELITE_CONNECTION_LINE);
					sectionTime.put(s.getName(), 0.0);
				} else {
					for (String lineName : intersectLineSet) {
						if (mmap.getMetroLine(lineName).getLineStationIndexList().contains(stationIndex) &&
							mmap.getMetroLine(lineName).getLineStationIndexList().contains(prevStationIndex)) {
							timepersection = mmap.getMetroLine(lineName).getTimeBetweenStation(stationIndex, prevStationIndex);
							sectionLines.put(s.getName(), lineName);
							sectionTime.put(s.getName(), timepersection);
							break;
						}
					}
				}
			}
			TotalTime += timepersection;
			prevStationIndex = stationIndex;
		}
	}
	
	public static void main(String args[]) throws Exception {
		MetroBuilder mb = new MetroBuilder();
		mb.setDirectoryPath("./SubwayMaps/TokyoMetroMap/");
		MetroMap mmap = mb.buildSubwayFromCSV();
		Route r = new Route(mmap);
		
		LinkedHashMap<String, Double> sectionTime = new LinkedHashMap<>();
	}
	
	
}