package MetroSystemRefactor;
import java.util.ArrayList;
import java.util.HashMap;

import MetroSystemRefactor.railway;

public class subwaySystem extends graph<station, railway>{
	// nodes in superclass replaced stations.
        LineStationMediator metroLineMediator;
        stationsMediator stationMediator;
        
	public subwaySystem() {
            stationMediator = new stationsMediator();
            metroLineMediator = new LineStationMediator();
            nodes = new HashMap<Integer, station>();
            edges = new ArrayList<railway>();
        }
	
	public boolean addNode(Integer sIndex, String sName, String lineName) {
		if (!checkNodeExists(sIndex)) {
			station station = new station(sIndex, sName);
			nodes.put(sIndex, station);
			addLineToStation(lineName, sIndex);
			return true;
		} else {
			return false;
		}
	}
	
	public void addLineToStation(String lineName, Integer sIndex) {
            metroLineMediator.addToLineStationMediator(lineName, sIndex);
	}
        
        public LineStationMediator getLineStationMediator() {
            return metroLineMediator;
        }
        
	public void connectStations(Integer s1Index, Integer s2Index, Integer distance, Integer cost, Integer time) throws Exception {
		if(!checkNodeExists(s1Index)) {
			throw new Exception("Station "+s1Index+" does not exist");
		} else if(!checkNodeExists(s2Index)) {
			throw new Exception("Station "+s2Index+" doesn not exist");
		}
                
                railway r = new railway(distance, cost, time);
		r.setEnds(s1Index, s2Index);
		edges.add(r);
                
		stationMediator.addNeighbouringStation(s1Index, s2Index, r);
		stationMediator.addNeighbouringStation(s2Index, s1Index, r);
	}
        
        public void disconnectStations(Integer s1Index, Integer s2Index) {
            stationMediator.removeConnection(s1Index, s2Index);
            stationMediator.removeConnection(s2Index, s1Index);
        }
        
        public void printNeighbouringStations() {
            stationMediator.printStationNeighbours();
        }
        
        
}
