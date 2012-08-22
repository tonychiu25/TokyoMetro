package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

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

	// Check if the current station in the path is a cross-section
	private boolean checkCrossSection(HashSet<String> linePrev,
		HashSet<String> lineCurrent, HashSet<String> lineNext) {
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

	// Compress the complete path into a route.
	public void getRouteFromPath() {
		
		Integer currStationIndex, nextStationIndex, prevStationIndex;
		HashSet<String> refLineSet, currLineSet, intersectLineSet;
		String line;
		
		currStationIndex = path.getFirst();
		refLineSet = lineStationMed.getLineFromStationIndex(currStationIndex);
		
		for (int i=1; i<path.size(); i++) {
			currStationIndex = path.get(i);
			currLineSet = lineStationMed.getLineFromStationIndex(currStationIndex);
		}
		
		// Second oldest implementation
		/*Integer refStationIndex, currStationIndex, nextStationIndex, prevStationIndex, secondToLastStationIndex;
		HashSet<String> refLineSet,  currLineSet, nextLineSet, prevLineSet, intersectLineSet;
		String line;
		
		refStationIndex = path.getFirst();
		refLineSet = lineStationMed.getLineFromStationIndex(refStationIndex);
		
		sectionStations.add(refStationIndex);
		for (int i=1; i<path.size()-1; i++) {
			currStationIndex = path.get(i);
			nextStationIndex = path.get(i+1);
			prevStationIndex = path.get(i-1);
			
			prevLineSet = lineStationMed.getLineFromStationIndex(prevStationIndex); 
			currLineSet = lineStationMed.getLineFromStationIndex(currStationIndex);
			nextLineSet = lineStationMed.getLineFromStationIndex(nextStationIndex);
			
			if (currStationIndex == 5) {
			  System.out.println(prevLineSet);
			  System.out.println(currLineSet);
			  System.out.println(nextLineSet);
			}
			
			if (checkCrossSection(prevLineSet, currLineSet, nextLineSet)) {
				intersectLineSet = (HashSet<String>)currLineSet.clone();
				intersectLineSet.retainAll(refLineSet);
				if (!checkLineAdded(intersectLineSet)) {
					line = (String)intersectLineSet.toArray()[0];
					sectionLines.add(line);
					refLineSet = currLineSet;
				}
			}
		}
		// Finally at the end.
		currStationIndex = path.getLast();
		
		currLineSet = lineStationMed.getLineFromStationIndex(currStationIndex);
		intersectLineSet = (HashSet<String>)currLineSet.clone();
		intersectLineSet.retainAll(refLineSet);
		//System.out.println(refLineSet);
		//System.out.println(intersectLineSet);
		//System.out.println(refLineSet);
		//System.out.println(currLineSet);
		if (!checkLineAdded(intersectLineSet)) {
			line = (String)intersectLineSet.toArray()[0];
			sectionLines.add(line);
		}*/

		//System.out.println(path);
		//System.out.println(sectionLines);
		
		// Oldest Implementation
		/*
		HashSet<String> prevLineSet, nextLineSet, currentLineSet, intersectLineSet, currPrevLineIntersect;
		Integer sIndex, sPrevIndex, sNextIndex, timePerSection, distancePerSection;
		String line, lastAddedLine;
		railway rail;

		timePerSection = 0;
		distancePerSection = 0;
		sectionStations.add((Integer) path.getFirst());
		
		sIndex = (Integer)path.getFirst();
		currentLineSet = lineStationMed.getLineFromStationIndex(sIndex);
		if (currentLineSet.size() <= 1) {
			line = (String)currentLineSet.toArray()[0];
			sectionLines.add(line);
		}
		
		for (int i = 1; i < path.size() - 1; i++) {
			// ONLY add lines at cross section
			sIndex = (Integer) path.get(i);
			sPrevIndex = (Integer) path.get(i - 1);
			sNextIndex = (Integer) path.get(i + 1);
			prevLineSet = lineStationMed.getLineFromStationIndex(sPrevIndex);
			currentLineSet = lineStationMed.getLineFromStationIndex(sIndex);
			nextLineSet = lineStationMed.getLineFromStationIndex(sNextIndex);

			rail = stationMed.getRail(sIndex, sPrevIndex);
			timePerSection += rail.getTime();
			distancePerSection += rail.getLength();
			
			totalTime += rail.getTime();
			totalDistance += rail.getLength();
			
			if (checkCrossSection(prevLineSet, currentLineSet, nextLineSet)) {
				intersectLineSet = (HashSet<String>) currentLineSet.clone();
				intersectLineSet.retainAll(prevLineSet);
				System.out.println("Crossed");
				sectionStations.add(sIndex);
				if (intersectLineSet.isEmpty()) { 
					// Implies prev line not added and at cross-section already
					line = (String) prevLineSet.toArray()[0];
					sectionLines.add(line);
				}
				lastAddedLine = sectionLines.get(sectionLines.size() - 1);
				sectionTime.put(lastAddedLine, timePerSection);
				sectionDistance.put(lastAddedLine, distancePerSection);
				timePerSection = 0;
				distancePerSection = 0;
			} else {
				currPrevLineIntersect = (HashSet<String>) currentLineSet.clone();
				currPrevLineIntersect.retainAll(prevLineSet);
				if (!checkLineAdded(currPrevLineIntersect)) {
					if (currPrevLineIntersect.size() == 1) {
						line = (String) currPrevLineIntersect.toArray()[0];
						sectionLines.add(line);
					}
				}
			}
		}

		// Check the last node that was missed by the for loop.
		Integer sLastIndex = (Integer) path.getLast();
		HashSet<String> lastLineSet = lineStationMed.getLineFromStationIndex(sLastIndex);
		sectionStations.add(sLastIndex);

		Integer secondToLastIndex = (Integer) path.get(path.size() - 2);
		HashSet<String> secondToLastLineSetIntersect = lineStationMed.getLineFromStationIndex(secondToLastIndex);
		secondToLastLineSetIntersect.retainAll(lastLineSet);

		rail = stationMed.getRail(sLastIndex, secondToLastIndex);
		distancePerSection += rail.getLength();
		timePerSection += rail.getTime();
		totalTime += rail.getTime();
		totalDistance += rail.getLength();

		if (!checkLineAdded(secondToLastLineSetIntersect)) {
			// Implies a line still hasn't been added;
			line = (String) secondToLastLineSetIntersect.toArray()[0];
			rail = stationMed.getRail(secondToLastIndex, sLastIndex);
			sectionLines.add(line);
		}

		lastAddedLine = sectionLines.get(sectionLines.size() - 1);
		sectionTime.put(lastAddedLine, timePerSection);
		sectionDistance.put(lastAddedLine, distancePerSection);

		System.out.println(sectionTime);
		System.out.println(sectionStations);
		System.out.println(path);
		System.out.println(sectionLines);
		System.out.println(totalTime);*/
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