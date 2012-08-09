package MetroSystemRefactor;

import java.util.ArrayList;

/**
 *
 * @author chiu.sintung
 */
public class metroLine {
    
    private String lineName;
    private ArrayList<Integer> lineStations;
    
    public metroLine(String name) {
        lineName = name;
        lineStations = new ArrayList();
    }
    
    public String getLineName() {
        return lineName;
    }
    
    public ArrayList<Integer> getLineStations() {
        return lineStations;
    }
    
    public boolean checkContainsStation(Integer sIndex) {
        return lineStations.contains(sIndex);
    }
    
    public void addStation(Integer sIndex) {
        if (!lineStations.contains(sIndex)) {
            lineStations.add(sIndex);
        }
    }
}