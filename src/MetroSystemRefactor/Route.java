package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class Route {
    
    private Integer totalDistance, totalTime;     // Start/End station index
    private ArrayList<Integer> sectionStations;
    private ArrayList<String> sectionLines;
    private LinkedList path;
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
    	Set<String> addedLines = sectionTime.keySet();
    	for (String l : lineSet) {
    		if (addedLines.contains(l)) {
    			added = true;
    			break;
    		}
    	}
    	
    	return added;
    	
    	/* added = false;
    	for (String l : lineSet) {
    		if (sectionLines.contains(l)) {
    			added = true;
    			break;
    		}
    	}
    	
    	return added;*/
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
    
    private void addSectionToRoute(String l, Integer sTime, Integer sDistance) {
        sectionTime.put(l, sTime);
        totalTime += sTime;
       
        sectionDistance.put(l, sDistance);
        totalDistance += sDistance;
    }
    
    // Compress the complete path into a route.
    public void getRouteFromPath() {
    	HashSet<String> prevLineSet, nextLineSet, currentLineSet, intersectLineSet, currPrevLineIntersect;
    	Integer sIndex, sPrevIndex, sNextIndex, timePerSection, distancePerSection;
    	String line;
    	railway rail;
    	
    	timePerSection = 0;
    	distancePerSection = 0;
    	sectionStations.add((Integer)path.getFirst());
    	for (int i=1; i<path.size()-1; i++) {
    		// ONLY add lines at cross section
    		sIndex = (Integer)path.get(i);
    		sPrevIndex = (Integer) path.get(i-1);
    		sNextIndex = (Integer) path.get(i+1);
    		prevLineSet = lineStationMed.getLineFromStationIndex(sPrevIndex);
    		currentLineSet = lineStationMed.getLineFromStationIndex(sIndex);
    		nextLineSet = lineStationMed.getLineFromStationIndex(sNextIndex);
    		
    		rail = stationMed.getRail(sIndex, sPrevIndex);
    		timePerSection += rail.getTime();
    		distancePerSection += rail.getLength();
    		if (checkCrossSection(prevLineSet, currentLineSet, nextLineSet)) {
    			intersectLineSet = (HashSet<String>)currentLineSet.clone();
    			intersectLineSet.retainAll(prevLineSet);
    			//intersectLineSet.retainAll(sectionStations);
    			if (intersectLineSet.isEmpty()) {	// Implies prev line not added and at cross-section already
    				line = (String) prevLineSet.toArray()[0];
    				addSectionToRoute(line, timePerSection,distancePerSection);
    				timePerSection = 0;
    				distancePerSection = 0;
    			}
    		} /*else if (currentLineSet.size() == 1) {
    			if (!checkLineAdded(currentLineSet)) {
    				line = (String) currentLineSet.toArray()[0];
    				addSectionToRoute(line, timePerSection, sectionDistance);
    			}
    		}*/
    		else {
    			currPrevLineIntersect = (HashSet<String>)currentLineSet.clone();
    			currPrevLineIntersect.retainAll(prevLineSet);
    			if (!checkLineAdded(currPrevLineIntersect)) {
    				if (currPrevLineIntersect.size() == 1) {
    					line = (String) currPrevLineIntersect.toArray()[0];
    					System.out.println(line+":"+timePerSection);
    					addSectionToRoute(line, timePerSection, distancePerSection);
    					timePerSection=0;
    				}
    			}
    		}
    	}
    	
    	// Check the last node that was missed by the for loop.
    	Integer sLastIndex = (Integer)path.getLast();
    	HashSet<String> lastLineSet = lineStationMed.getLineFromStationIndex(sLastIndex);
    	
    	Integer secondToLastIndex = (Integer) path.get(path.size()-2);
    	HashSet<String> secondToLastLineSetIntersect = lineStationMed.getLineFromStationIndex(secondToLastIndex);
    	secondToLastLineSetIntersect.retainAll(lastLineSet);

    	/*HashSet<String> setPathIntersectLine = (HashSet<String>) lastLineSet.clone();
    	setPathIntersectLine.retainAll(path);*/
    	if (!checkLineAdded(secondToLastLineSetIntersect)) {	// Implies a line still hasn't been added;
    		line = (String) secondToLastLineSetIntersect.toArray()[0];
    		rail = stationMed.getRail(secondToLastIndex, sLastIndex);
    		addSectionToRoute(line, timePerSection, distancePerSection);
    	}
    }
    
    public static void main(String args[]) {
    	HashSet<String> prev = new HashSet();
    	HashSet<String> curr = new HashSet();
    	HashSet<String> next = new HashSet();
    	
    	prev.add("D");
    	prev.add("B");
    	
    	curr.add("D");
    	curr.add("C");
    	
    	next.add("A");
    	next.add("E");
    	
    	prev.retainAll(curr);
    	
    	System.out.println(curr);
    }
    
 /*   // Check if a path contains a set of line
    public boolean routeContainsLine(HashSet<String> lineSet) {
        boolean containsLine = false;
        for(String lineName : lineSet) {
            if (routeContainsLine(lineName)) {
                containsLine = true;
                break;
            }
        }
        
        return containsLine;
    }
    
    public boolean routeContainsLine(String line) {
        return sectionTime.keySet().contains(line);
    }
    
    public void addStationToRoute(Integer sIndex) {
    	sectionStations.add(sIndex);
    }
    
    public void printLineTime() {
    	System.out.println(sectionTime);
    }
    
    public void addSectionToRoute(String l, Integer sTime, Integer sDistance) {
    	sectionTime.put(l, sTime);
    	totalTime += sTime;
    	
    	sectionDistance.put(l, sDistance);
    	totalDistance += sDistance;
    }
    */
}