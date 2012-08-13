package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashSet;


public class Route {
    
    LineStationMediator lsMediator;
    Integer totalDistance, totalTime, sPrevIndex;     // Start/End station index
    ArrayList<String> lines;
    ArrayList<Integer> path;
    ArrayList<Integer> sectionTime;
    String currentLine;
    
    public Route(LineStationMediator lsMediate) {
        lsMediator = lsMediate;
        path = new ArrayList();
        lines = new ArrayList();
        sPrevIndex = null;
        currentLine = "";
    }
    
    public void addStationToPath(Integer sIndex) {
        HashSet<String> stationLines = lsMediator.getLineFromStationIndex(sIndex);
        if (sPrevIndex == null) {   // First time add
           if (stationLines.size() == 1) {
               String l = (String) stationLines.toArray()[0];
               lines.add(l);
           } else {
               
           }
           
        } else {
            
        }
        
        path.add(sIndex);
    }
    
}