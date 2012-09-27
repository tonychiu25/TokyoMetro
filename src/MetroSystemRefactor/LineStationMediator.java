package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LineStationMediator {
    private HashMap<String, ArrayList<Integer>> lineStationMediator;
    private HashMap<Integer, HashSet<String>> stationLineMediator;

    
    public LineStationMediator() {
        lineStationMediator = new HashMap<String, ArrayList<Integer>>();
        stationLineMediator = new HashMap<Integer, HashSet<String>>();
    }
    
    private void addStationToLine(String lineName, Integer sIndex) {
    	ArrayList<Integer> sIndexes;
    	if (!lineStationMediator.containsKey(lineName)) {
            sIndexes = new ArrayList<Integer>();
            sIndexes.add(sIndex);
    	} else {
            sIndexes = lineStationMediator.get(lineName);
            if (!sIndexes.contains(sIndex)) {
                sIndexes.add(sIndex);
            }
    	}
        
        lineStationMediator.put(lineName, sIndexes);
    }
    
    private void addLineToStation(String lineName, Integer sIndex) {
    	HashSet<String> lines;
    	if (!stationLineMediator.containsKey(sIndex)) {
    		lines = new HashSet<String>();
    		lines.add(lineName);
    	} else {
    		lines = stationLineMediator.get(sIndex);
    		lines.add(lineName);
    	}
        
        stationLineMediator.put(sIndex, lines);
    }
    
    public ArrayList<Integer> getStationOnLine(String line) {
    	return lineStationMediator.get(line);
    }
    
    public Set<String> getLines() {
    	return lineStationMediator.keySet();
    }
    
    public void addToLineStationMediator(String lineName, Integer sIndex) {
    	addStationToLine(lineName, sIndex);
    	addLineToStation(lineName, sIndex);
    }
    
    public HashSet<String> getLineFromStationIndex(Integer sIndex) {
        return stationLineMediator.get(sIndex);
    }
    
    // Helper function to print line at station n
    public void printLinesAtStation(Integer sIndex) {
        HashSet<String> lines = stationLineMediator.get(sIndex);
        System.out.print("{");
        for(String l:lines) {
            System.out.print(l+",");
        }
        System.out.println("}");
    }
    
}