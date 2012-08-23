package Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// Class for sotring metro line entries; needed fr unit testing.
public class LineList {
	
	ArrayList<Integer> lineStationIndex;
	String name;
	
	public LineList(String lineName) {
		lineStationIndex = new ArrayList<Integer>();
		name = lineName;
	}
	
	public void addNewStation(Integer sIndex) {
		if (!lineStationIndex.contains(sIndex)) {
			lineStationIndex.add(sIndex);
		}
	}
		
	public Integer getPrevStationIndex(Integer sIndex) {
		
		return 1;
	}
	
}
