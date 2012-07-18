package MetroSystemRefactor;
import java.util.ArrayList;
import java.util.HashMap;

import MetroSystemRefactor.railway;

public class subwaySystem extends graph<station, railway>{
	// nodes in superclass replaced stations.
	
	public subwaySystem() {
		stationMediator = new stationsMediator();
		nodes = new HashMap<Integer, station>();
		edges = new ArrayList<railway>();
	}
	
	public void addNode(Integer sIndex) {
		if (!checkNodeExists(sIndex)) {
			station station = new station(sIndex);
			nodes.put(sIndex, station);
		}
	}
	
	public void connectStations(Integer s1Index, Integer s2Index, railway r) throws Exception {
		if(!checkNodeExists(s1Index)) {
			throw new Exception("Station "+s1Index+" does not exist");
		} else if(!checkNodeExists(s2Index)) {
			throw new Exception("Station "+s2Index+" doesn not exist");
		}
		
		stationMediator.addNeighbouringStation(s1Index, s2Index);
		stationMediator.addNeighbouringStation(s2Index, s1Index);
		
		r.setEnds(s1Index, s2Index);
		edges.add(r);
	}
        
        public void printNeighbouringStations() {
            stationMediator.printStationNeighbours();
        }
}
