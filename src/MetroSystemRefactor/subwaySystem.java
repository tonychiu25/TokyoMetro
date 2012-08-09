package MetroSystemRefactor;
import java.util.ArrayList;
import java.util.HashMap;

import MetroSystemRefactor.railway;

public class subwaySystem extends graph<station, railway>{
	// nodes in superclass replaced stations.
        HashMap<String, metroLine> lines;
        stationsMediator stationMediator;
        
	public subwaySystem() {
            stationMediator = new stationsMediator();
            nodes = new HashMap();
            edges = new ArrayList();
            lines = new HashMap();
        }
	
	public void addNode(Integer sIndex, String sName, String sLine) {
		if (!checkNodeExists(sIndex)) {
			station station = new station(sIndex, sName, sLine);
			nodes.put(sIndex, station);
		}
	}
        
	public void connectStations(Integer s1Index, Integer s2Index, Integer distance, Integer cost, Integer time) throws Exception {
		if(!checkNodeExists(s1Index)) {
			throw new Exception("Station "+s1Index+" does not exist");
		} else if(!checkNodeExists(s2Index)) {
			throw new Exception("Station "+s2Index+" doesn not exist");
		}
		
		stationMediator.addNeighbouringStation(s1Index, s2Index);
		stationMediator.addNeighbouringStation(s2Index, s1Index);
		
                railway r = new railway(distance, cost, time);
		r.setEnds(s1Index, s2Index);
		edges.add(r);
	}
        
        public void disconnectStations(Integer s1Index, Integer s2Index) {
            stationMediator.removeConnection(s2Index, s1Index);
            stationMediator.removeConnection(s1Index, s2Index);
        } 
        
        public void printNeighbouringStations() {
            stationMediator.printStationNeighbours();
        }
        
        
}
