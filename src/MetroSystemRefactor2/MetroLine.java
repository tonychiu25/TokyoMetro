package MetroSystemRefactor2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MetroLine {
	private final int OPEN_LOOP = 2;
	private final int CLOSED_LOOP = 1;
	private final int NO_LOOP = 0;
	
	private ArrayList<Integer> stationIndexList;
	private String name;
	private MetroMap mmap;
	private double lineTotalTime;
	private double loopTime;
	private int loopType;
	int loop;			//  Determines if line form a loop with itself.
	
	
	public MetroLine(String lineName, MetroMap metroMap) {
		name = lineName;
		stationIndexList = new ArrayList<>();
		mmap = metroMap;
		loop = NO_LOOP;
		lineTotalTime = 0;
		loopTime = 0;
//		loopType = 0;
	}
	
	/** Get the travel time for one cycle on this metro-line **/
//	private double getLineLoopTime() {
//		double loopTime = 0;
//		double betweenStationTime;
//		Integer currStationIndex, prevStationIndex;
//		/** If loop if a closed loop ==> loopTime == lineTotalTime **/
//		if (stationIndexList.get(0) == stationIndexList.get(stationIndexList.size()-1)) {
//			loopTime = lineTotalTime;
//		} else {
//		/** Loop contains some line auxillary line connected at the node station **/
//			for (int i=1; i < stationIndexList.size(); i++) {
//				currStationIndex = stationIndexList.get(i);
//				prevStationIndex = stationIndexList.get(i-1);
//				betweenStationTime = mmap.getRail(currStationIndex, prevStationIndex).getTime();
//				loopTime += betweenStationTime;
//				if (currStationIndex == stationIndexList.get(0)) {
//					break;
//				}
//			}
//		}
//		 
//		return loopTime;
//	}
	
	public void addStationIndexToLine(Integer newStationIndex) {
		double betweenStationTime;
		/** Adding a new station to a closed loop results in a open loop **/
		if (loop == CLOSED_LOOP) {
			loop = OPEN_LOOP;
		}
		betweenStationTime = mmap.getRail(stationIndexList.get(stationIndexList.size()-1), newStationIndex).getTime();
		lineTotalTime += betweenStationTime;
		stationIndexList.add(newStationIndex);
		if (stationIndexList.contains(newStationIndex) && loop == NO_LOOP) {
			loop = CLOSED_LOOP;
			loopTime = lineTotalTime;
		}
	}
	
	public ArrayList<Integer> getStationIndexList() {
		return stationIndexList;
	}
	
	private double getTimeBetweenStationHelper(Integer s1Index, Integer s2Index) {
		Integer currStationIndex, prevStationIndex;
		double totalTime = 0;
		int hitCount = 0;
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
		
		return totalTime;
	}
	
	private boolean checkStationWithinMainLoopHelper(Integer sIndex) {
		boolean within = false;
		HashSet<Integer> visited = new HashSet<>();
		
		for (Integer stationIndex : stationIndexList) {
			if (!visited.contains(stationIndex)) {
				visited.add(stationIndex);
			} else {
				if (visited.contains(sIndex)) {
					within = true;
				} else {
					within = false;
				}
			}			
		}
		
		return within;
	}
	
	public double calculateTimeBetweenStations(Integer s1Index, Integer s2Index) {
		double totalTime = 0;
		double travelTime;
		HashSet<Integer> visitedStations;
		/** Calculation method if line is not circular **/
		if (loop == NO_LOOP) {
			totalTime = getTimeBetweenStationHelper(s1Index, s2Index);
		} else if (loop == CLOSED_LOOP){
			/** For lines with cycles **/
			travelTime = getTimeBetweenStationHelper(s1Index, s2Index);
			totalTime = Math.min(travelTime, loopTime-travelTime);
		} else {	/** Case for Open loops **/
			// Check to see if the target stations lie in OR outside of the loop
			boolean s1InLoop = checkStationWithinMainLoopHelper(s1Index);
			boolean s2InLoop = checkStationWithinMainLoopHelper(s2Index);
			if (s1InLoop && s2InLoop) {
				travelTime = getTimeBetweenStationHelper(s1Index, s2Index);
				totalTime = Math.min(travelTime, loopTime-travelTime);
			} else if (!s1InLoop && !s2InLoop) {
				totalTime = getTimeBetweenStationHelper(s1Index, s2Index);
			} else {
				
			}
		}
		return totalTime;
	}
}
