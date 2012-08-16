package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class Route {
    
    private Integer totalDistance, totalTime, sPrevIndex, currentSectionTime;     // Start/End station index
    private LinkedList<String> lines;
    private ArrayList<Integer> path, sectionTime;
    private String currentLine;
    
    public Route() {
        path = new ArrayList();
        sectionTime = new ArrayList();
        lines = new LinkedList();
        sPrevIndex = null;
        currentLine = "";
        totalDistance = 0;
        totalTime = 0;
    }
    
    public Integer getTotalTime () {
        return totalTime;
    }
    
    public void addSectionTime(Integer time) {
         totalTime += time;
         sectionTime.add(time);
    }
    
    // Check if a path contains a set of line
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
        return lines.contains(line);
    }
    
    public void addStationToPath(Integer sIndex) {
        if (!path.contains(sIndex)) {
    	  path.add(sIndex);
        }
    }
    
    public String getCurrentLine() {
    	return currentLine;
    }
    
    public void setCurrentLine(String currentL) {
    	currentLine = currentL;
    }
    
    public boolean addLineToRoute(String l) {
    	if (!lines.contains(l)) {
    		lines.add(l);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void printRoute() {
    	for(Integer sIndex : path) {
    		System.out.print(sIndex+",");
    	}
    	System.out.println("");
    }
    
    public void printLines() {
    	for(String l : lines) {
    		System.out.print(l+",");
    	}
    	System.out.println("");
    }
}