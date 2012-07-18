package MetroSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class GenericSubway {

	protected Map<Integer, station> subway;
	protected ArrayList<railway> railLines;
	
	// Need at least one station for a subway system
	public GenericSubway(int startStationIndex) {
		railLines = new ArrayList<railway>();
		subway = new HashMap<Integer, station>();
		station initialStation = new station(startStationIndex);
		subway.put(startStationIndex, initialStation);
	}
	
	public boolean hasStation(Integer stationIndex) {
		if (subway.keySet().contains(stationIndex)) {
			return true;
		} else {
			return false;
		}
	}
	
	// TODO FINISH THIS
	public void disconnectStations(Integer s1Index, Integer s2Index) {
		// Remove from line
		for(int i=0; i<railLines.size(); i++) {
			railway r = railLines.get(i);
			if (r.isConnected(s1Index) && r.isConnected(s2Index)) {
				railLines.remove(i);
				break;
			}
		}
		// Remove neighbour stations stored in stations class
		station s1 = subway.get(s1Index);
		station s2 = subway.get(s2Index);
		s1.removeNeighbouringStation(s2);
		s2.removeNeighbouringStation(s1);
		subway.put(s1Index, s1);
		subway.put(s2Index, s2);
	}
	
	// Add a station to the metrosystem without connecting it to any other stations
	abstract void addStation(int stationIndex) throws Exception;
	
	/* Two stations must be either bi-directionally connected or not connected at all
	 * Any uni-direction connection is strictly not allowed.
	 */
	private boolean checkConnected(station s1, station s2) {
		boolean s1Connected = s1.getNeighborStation().containsKey(s2);
		boolean s2Connected = s2.getNeighborStation().containsKey(s1);
		boolean connected;
		
		if (s1Connected && s2Connected) {	// Truely connected
			connected = true;
		} else {	// Truely disconnected; stations are all either bi-directionally connected/disconteect due to the add process
			connected = false;
		}
		
		return connected;
	}
	
	// Pre : s1 and s2 must be strictly disconnected; do checking before
	public void connectStations(int s1Index, int s2Index, int distance, int cost, int time) throws Exception {
		station s1 = subway.get(s1Index);
		station s2 = subway.get(s2Index);
		// Check the stations are not null
		if (s1 == null) {
			throw new Exception("Station "+s1Index+" does not exists");
		} else if(s2 == null) {
			throw new Exception("Station "+s2Index+" doest not exists");
		} else if(checkConnected(s1, s2)) {
			System.out.println("Station "+s1Index+" & Station "+s2Index+" are already connected");
		} else if(s1.equals(s2)) {
			System.out.println("Warning : trying to connect the same stations");
		} else {
			railway r = new railway(distance, cost, time, s1, s2);
			railLines.add(r);
			s1.addNeighbourStation(s2, r);
			s2.addNeighbourStation(s1, r);
			System.out.println("Successfully Connected Station "+s2Index+" to "+s1Index);
		}
	}
	
	public void addStationWithLine(int newStationIndex, int neighborStationIndex, int distance, int cost, int time) throws Exception {		
		if(subway.keySet().contains(newStationIndex)) {
			Exception stationExistsException = new Exception("Error : Station :"+newStationIndex+" already exists in the subway system; use another index");
			throw stationExistsException;
		} else if(!subway.keySet().contains(neighborStationIndex)) {
			Exception neighborStationExistException = new Exception("Error : Neighboring station :"+neighborStationIndex+" does not exist.");
			throw neighborStationExistException;
		} else {
			station newStation = new station(newStationIndex);
			subway.put(newStationIndex, newStation);
			connectStations(neighborStationIndex, newStationIndex, distance, cost, time);
			//System.out.println("Successfully added station "+newStationIndex+" to station "+neighborStationIndex);
		}
	}
	
	public Map<Integer, station> getSubway() {
		return subway;
	}
	
	public ArrayList<railway> getRails() {
		return railLines;
	}
		
	public static void main(String args[]) {
		
		SortedSet<Integer> ss = new TreeSet();
		ss.add(2);
		ss.add(1);
		ss.add(4);
		ss.add(0);
		
		Iterator it = (Iterator) ss.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
		
		subwaySystem sub = new subwaySystem(1);
		try {
			sub.addStationWithLine(2, 1, 2, 2, 3);
			sub.addStationWithLine(3, 2, 2, 2, 3);
			sub.connectStations(1, 3, 22, 3, 1);
			sub.disconnectStations(1,3);
			for(station s: sub.getSubway().values()) {
				s.printNeighborStation();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
