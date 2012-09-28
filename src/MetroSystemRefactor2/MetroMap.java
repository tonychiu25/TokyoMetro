package MetroSystemRefactor2;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class MetroMap {
	private WeightedGraph<Integer, Railway> graph;
	private RailFactory rf;
	private HashMap<Integer, Station> stationIndexTable;
	
	public MetroMap() {
		rf = new RailFactory();
		graph = new SimpleWeightedGraph<>(rf);
		stationIndexTable = new HashMap<Integer, Station>();
	}

	public void addStationToMap(Integer stationIndex, String stationName, HashSet<String> lines) {
		Station newStation;
		if (!stationIndexTable.containsKey(stationIndex)) {
			newStation = new Station(stationIndex, stationName);
			for (String l : lines) {
				newStation.addLine(l);
			}
			stationIndexTable.put(stationIndex, newStation);
			graph.addVertex(stationIndex);
		}
	}
	
	public void connectStations(Integer station1Index, Integer station2Index, double length, double time, int cost) {
		Railway r;
		// Trying to add a connection to itself
		/*if (station1Index == station2Index) {
			System.err.println("Error : Cannot add a link between the same station : station "+station1Index);
			System.exit(-1);
		// Trying to add another connection between two connection stations
		} else if (graph.containsEdge(station1Index, station2Index)) {
			System.err.println("Error : a link already exists between station "+station1Index+" and station "+station2Index);
			System.exit(-1);
		}*/
		
		r = new Railway(length, time, cost, station1Index, station2Index);
		try {
			graph.addEdge(station1Index, station2Index, r);
		} catch(IllegalArgumentException e) {
			if (!graph.containsVertex(station1Index)) {
				System.err.println("Error : Station index "+station1Index+" have not yet been added to the map; cannot connect " + e.getMessage());
			} else if (!graph.containsVertex(station2Index)) {
				System.err.println("Error : Station index "+station2Index+" have not yet been added to the map; cannot connect " + e.getMessage());
			} else if (station1Index == station2Index){
				System.err.println("Error : Loop at station "+station1Index+":"+e.getMessage());
			}
		}
	}
	
	public Railway getRail(Integer station1Index, Integer station2Index) {
		return graph.getEdge(station1Index, station2Index);
	}
	
	public Station getStationByIndex(Integer stationIndex) {
		return stationIndexTable.get(stationIndex);
	}
	
	public Collection<Station> getStationSet() {
		return stationIndexTable.values();
	}
}
