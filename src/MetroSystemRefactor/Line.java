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
	private Integer currentDistance, currentTime;	// current distance, time from terminal of furthest station
	
	public Line(String lName) {
		lineName = lName;
		lineStations = new ArrayList<Integer>();
		currentDistance = 0;
		currentTime = 0;
	}
	
	public boolean stationExists(Integer sIndex) {
		return addedStations.contains(sIndex);
	}
	
	public void addStation(Integer sIndex, Integer distanceFromPrev, Integer timeFromPrev) {
		if (!stationExists(sIndex)) {
			currentDistance += distanceFromPrev;
			currentTime += timeFromPrev;
			
			lineStations.add(sIndex);
			addedStations.add(sIndex);
			distanceFromTerminal.put(sIndex, currentDistance);
			timeFromTerminal.put(sIndex, currentTime);
		}
	}
	
	public String getLineName() {
		return lineName;
	}
	
	
}
