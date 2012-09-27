package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

// Describes a route from one staiton to another.
public class Route {

	private Integer totalDistance, totalTime;
	private ArrayList<Integer> sectionStations;
	private ArrayList<String> sectionLines;
	private LinkedList<Integer> path;
	private HashMap<String, Integer> sectionTime, sectionDistance;
	private stationsMediator stationMed;
	private LineStationMediator lineStationMed;

	public Route(stationsMediator sm, LineStationMediator lsm, LinkedList<Integer> p) {
		path = p;
		stationMed = sm;
		lineStationMed = lsm;
		sectionTime = new HashMap<String, Integer>();
		sectionDistance = new HashMap<String, Integer>();
		sectionStations = new ArrayList<Integer>();
		sectionLines = new ArrayList<String>();
		totalDistance = 0;
		totalTime = 0;
	}

	private boolean checkLineAdded(HashSet<String> lineSet) {
		boolean added = false;
		for (String l : lineSet) {
			if (sectionLines.contains(l)) {
				added = true;
				break;
			}
		}

		return added;
	}

	public LinkedList<Integer> getPath() {
		return path;
	}
	
	// Check if the current station in the path is a cross-section
	private boolean checkCrossSection(HashSet<String> linePrev, HashSet<String> lineCurrent, HashSet<String> lineNext) {
		HashSet<String> intersectAll = (HashSet<String>) lineCurrent.clone();
		intersectAll.retainAll(linePrev);
		intersectAll.retainAll(lineNext);

		if (intersectAll.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Integer getRouteTotalTime() {
		return totalTime;
	}
	
	public Integer getRouteTotalDistance() {
		return totalDistance;
	}
	
	private boolean setLineIntersect(HashSet<String> lineSet1, HashSet<String> lineSet2) {
		lineSet1.retainAll(lineSet2);
		if (lineSet1.size() < 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static HashSet<String> getSetIntersection(HashSet<String> s1, HashSet<String> s2) {
		HashSet<String> intersectingSet = (HashSet<String>)s1.clone(); 
		intersectingSet.retainAll(s2);
		return intersectingSet;
	}

	public ArrayList<String> getSectionLines() {
		return sectionLines;
	}
	
	// Compress the complete path into a route.
	public void getRouteFromPath() {
		Integer currStationIndex, prevStationIndex, timePerSection, distancePerSection, railTime, railDistance;
		HashSet<String> refLineSet, currLineSet, intersectLineSet, prevLineSet;
		String line;
		railway rail;
		
		timePerSection = 0;
		distancePerSection = 0;
		
		currStationIndex = path.getFirst();
		refLineSet = lineStationMed.getLineFromStationIndex(currStationIndex);
		sectionStations.add(currStationIndex);
		for (int i=1; i<path.size(); i++) {
			currStationIndex = path.get(i);
			currLineSet = lineStationMed.getLineFromStationIndex(currStationIndex);
			intersectLineSet = getSetIntersection(refLineSet, currLineSet);
			
			prevStationIndex = path.get(i-1);
			rail = stationMed.getRail(currStationIndex, prevStationIndex);
			
			if (intersectLineSet.isEmpty()) {		// Implies a line change
				prevLineSet = lineStationMed.getLineFromStationIndex(prevStationIndex);
				intersectLineSet = getSetIntersection(refLineSet, prevLineSet);
				line = (String)intersectLineSet.toArray()[0];
				if (!checkLineAdded(intersectLineSet)) {
					sectionLines.add(line);
				}
				
				sectionStations.add(prevStationIndex);
				sectionTime.put(line, timePerSection);
				sectionDistance.put(line, distancePerSection);
				
				timePerSection = 0;
				distancePerSection = 0;
				refLineSet = currLineSet;
			}
			
			railTime = rail.getTime();
			railDistance = rail.getLength();
			
			timePerSection += railTime;
			distancePerSection += railDistance;
			totalTime += railTime;
			totalDistance += railDistance;
		}
		
		currStationIndex = path.getLast();
		currLineSet = lineStationMed.getLineFromStationIndex(currStationIndex);
		intersectLineSet = getSetIntersection(currLineSet, refLineSet);
		line = (String)intersectLineSet.toArray()[0];
		if (!checkLineAdded(intersectLineSet)) {
			sectionLines.add(line);
		}
		
		sectionStations.add(currStationIndex);
		sectionTime.put(line, timePerSection);
		sectionDistance.put(line, distancePerSection);
	}

	public static void main(String args[]) {
		HashSet<String> set1 = new HashSet<String>();
		HashSet<String> set2 = new HashSet<String>();
		
		set1.add("A");
		set1.add("B");
		
		set2.add("A");
		set2.add("C");
		
		System.out.println(Route.getSetIntersection(set1, set2));
		System.out.println(set1);
		System.out.println(set2);
	}
}