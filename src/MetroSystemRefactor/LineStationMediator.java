package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LineStationMediator {
    private HashMap<String, ArrayList<Integer>> lineStationMediator;
    private HashMap<String, Integer[]> lineIndexRange;
    private HashMap<Integer, HashSet<String>> stationLineMediator;
    
    public LineStationMediator() {
        lineStationMediator = new HashMap();
        stationLineMediator = new HashMap();
        lineIndexRange = new HashMap();
    }

/*
    private void setMaxMin(String line, Integer maxminIndex, Integer value) {
        Integer [] range;
        if (!lineIndexRange.containsKey(line)) {
            range = new Integer[2];
        } else {
            range = lineIndexRange.get(line);
        }
        
        range[maxminIndex] = value;
    }
    
    public void setLineMaxIndex(String line, Integer max) {
        setMaxMin(line, 1, max);
    }
    
    public void setLineMinIndex(String line, Integer min) {
        setMaxMin(line, 0, min);
    }

*/
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
    
}