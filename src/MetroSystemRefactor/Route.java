package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class Route {
    
    private Integer totalDistance, totalTime, sPrevIndex;     // Start/End station index
    private LinkedList<String> lines;
    private ArrayList<Integer> path;
    private ArrayList<Integer> sectionTime;
    private String currentLine;
    
    public Route() {
        path = new ArrayList<Integer>();
        lines = new LinkedList<String>();
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
    		lines.addFirst(l);
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