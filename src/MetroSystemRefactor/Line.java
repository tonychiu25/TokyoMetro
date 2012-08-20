package MetroSystemRefactor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Line {
	
	private String lineName;
	private ArrayList<Integer> lineStations;
	private HashSet<Integer> addedStations;
	private HashMap<Integer, Integer> distanceFromTerminal;
	private HashMap<Integer, Integer> timeFromTerminal;
	
	public Line(String lName) {
		lineName = lName;
		lineStations = new ArrayList<Integer>();
	}
	
	public boolean stationExists(Integer sIndex) {
		return addedStations.contains(sIndex);
	}
	
	public void addStationToLine(Integer sIndex, Integer terminalDistance, Integer terminalTime) {
		if (!stationExists(sIndex)) {
			lineStations.add(sIndex);
			addedStations.add(sIndex);
			distanceFromTerminal.put(sIndex, terminalDistance);
			timeFromTerminal.put(sIndex, terminalTime);
		}
	}
	
	public String getLineName() {
		return lineName;
	}
	
	
}
