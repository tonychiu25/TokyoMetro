package Algorithms;
import MetroSystem.GenericSubway;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import MetroSystem.railway;
import MetroSystem.station;
import MetroSystem.subwaySystem;
import MetroSystemRefactor.stationsMediator;
import java.util.*;

public class GraphAlgorithms {
    
	public static boolean checkCycle(Map<Integer, station> stations, List<railway> edges) throws Exception {
		if (edges.size() <= 1) {
			return false;
		}
		
		railway lastAddedEdge = edges.get(edges.size()-1);
		station lastLinkedStation = lastAddedEdge.getConnectedStation()[0];
		if (lastLinkedStation == null) {
			throw new Exception("Error with last added Edge");
		}
		
		Queue<Integer> queue = new LinkedList<Integer>();
		Set<Integer> visted = new HashSet<Integer>();
		Map<Integer, Integer> parents = new HashMap<Integer, Integer>();
		queue.add(lastLinkedStation.getStationIndex());
		while (queue.size() > 0) {
			Integer stationIndex = queue.poll();
			station s = stations.get(stationIndex);
			Map<station, railway> neighbourSet = s.getNeighborStation();
			for (station neighbour : neighbourSet.keySet()) {
				Integer neighbourIndex = neighbour.getStationIndex();
				parents.put(neighbourIndex, stationIndex);
				if(visted.contains(neighbourIndex) && parents.get(stationIndex) != neighbourIndex) {
					return true;
				}
				if(!visted.contains(neighbourIndex)) {
				  queue.add(neighbourIndex);
				}
			}
			
			visted.add(stationIndex);
		}
		
		return false;
	}
        
        /*public static boolean checkCycle2(stationsMediator sMediator, ArrayList<railway> edges) {
            railway lastAddedEdge = edges.get(edges.size()-1);
            Integer lastConnectedStationIndex = lastAddedEdge.getEnds()[0];
            Queue q = new LinkedList();
            while(!q.isEmpty()) {
                
            }
            
            return false;
        }*/
	
	/*private GenericSubway kruskalAlgorithm() {
		Iterator<Integer> stationIndexIt = MSTStations.keySet().iterator();
		GenericSubway MSTSubway = new subwaySystem(stationIndexIt.next());
		GenericSubway MSTSubwayTmp;
		while (stationIndexIt.hasNext()) {
			try {
				MSTSubway.addStation(stationIndexIt.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		MSTSubwayTmp = MSTSubway;
		
		for (railway r : sortedLinks) {
			Integer s1Index = r.getConnectedStation()[0].getStationIndex();
			Integer s2Index = r.getConnectedStation()[1].getStationIndex();
			try {
				MSTSubway.connectStations(s1Index, s2Index, r.getlength(), r.getCost(), r.getTime());
				if (GraphAlgorithms.checkCycle(MSTSubway.getSubway(), MSTSubway.getRails())) {
					MSTSubway.disconnectStations(s1Index, s2Index);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MSTSubway;
	}*/
}