package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LineStationMediator {
    private HashMap<String, ArrayList<Integer>> lineStationMediator;
    private HashMap<String, Line> lineStationMediator2;
    private HashMap<Integer, HashSet<String>> stationLineMediator;
    private HashSet<Line> lines;
    
    public LineStationMediator() {
        lineStationMediator = new HashMap<String, ArrayList<Integer>>();
        stationLineMediator = new HashMap<Integer, HashSet<String>>();
        lineStationMediator2 = new HashMap<String, Line>();
        lines = new HashSet<Line>();
    }

    
    private void addStationToLine(String lineName, Integer sIndex) {
    	ArrayList<Integer> sIndexes;
    	if (!lineStationMediator.containsKey(lineName)) {
            sIndexes = new ArrayList();
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
    		lines = new HashSet();
    		lines.add(lineName);
    	} else {
    		lines = stationLineMediator.get(sIndex);
    		lines.add(lineName);
    	}
        
        stationLineMediator.put(sIndex, lines);
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