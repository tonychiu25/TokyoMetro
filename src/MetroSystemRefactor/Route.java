package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashSet;


public class Route {
    
    private Integer totalDistance, totalTime, sPrevIndex;     // Start/End station index
    private ArrayList<String> lines;
    private ArrayList<Integer> path;
    private ArrayList<Integer> sectionTime;
    private String currentLine;
    
    public Route() {
        path = new ArrayList<Integer>();
        lines = new ArrayList<String>();
        sPrevIndex = null;
        currentLine = "";
        totalDistance = 0;
        totalTime = 0;
    }
    
    public void addStationToPath(Integer sIndex) {
    	
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
    	for(String l : lines) {
    		System.out.print(l+",");
    	}
    	System.out.println("");
    }
}