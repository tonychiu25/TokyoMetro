package MetroSystemRefactor2;

import java.util.ArrayList;

public class MetroLine {
	private ArrayList<Integer> stationIndexList;
	private String name;
	private MetroMap mmap;
	boolean loop;			//  Determines if line form a loop with itself.
	
	public MetroLine(String lineName, MetroMap metroMap) {
		name = lineName;
		stationIndexList = new ArrayList<>();
		mmap = metroMap;
		loop = false;
	}
	
	public void addStationIndexToLine(Integer newStationIndex) {
		if (stationIndexList.contains(newStationIndex)) {
			loop = true;
		}
		stationIndexList.add(newStationIndex);
	}
	
	public ArrayList<Integer> getStationIndexList() {
		return stationIndexList;
	}
	
	public double calculateTimeBetweenStations(Integer s1Index, Integer s2Index) {
		double totalTime = 0;
		int hitCount = 0;
		int currStationIndex, prevStationIndex;
		/** Calculation method if line is not circular **/
		if (!loop) {
			for (int i=1; i < stationIndexList.size(); i++) {
				currStationIndex = stationIndexList.get(i);
				prevStationIndex = stationIndexList.get(i-1);
				if (currStationIndex == s1Index || currStationIndex == s2Index) {
					hitCount++;
				}
				
				if (hitCount == 1) {
					totalTime += mmap.getRail(currStationIndex, prevStationIndex).getTime();
				} else if (hitCount == 2) {
					totalTime += mmap.getRail(currStationIndex, prevStationIndex).getTime();
					break;
				}
			}
		} else {
			/** For lines with cycles **/
			
		}
		return 0;
	}
}
