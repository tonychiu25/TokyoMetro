package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;

public class LineStationMediator {
    private HashMap<String, ArrayList<Integer>> lineStationMed;
    private HashMap<Integer, String> stationLineMediator;
            
    public LineStationMediator() {
        lineStationMed = new HashMap();
        stationLineMediator = new HashMap();
    }
    
    public void addStationToLine(String line, Integer sIndex) {
        
        if (stationLineMediator.containsKey(sIndex)) {
            return;
        }
        
        ArrayList<Integer> curLineStations;
        if (!lineStationMed.containsKey(line)) {
            curLineStations = new ArrayList();
            curLineStations.add(sIndex);
            lineStationMed.put(line, curLineStations);
        } else {
            curLineStations = lineStationMed.get(line);
            curLineStations.add(sIndex);
            lineStationMed.put(line, curLineStations);
        }
        
        stationLineMediator.put(sIndex, line);
    }
    
    
}
