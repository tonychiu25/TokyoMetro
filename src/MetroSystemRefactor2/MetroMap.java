package MetroSystemRefactor2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.omg.CORBA.FREE_MEM;

public class MetroMap {
	private WeightedMultigraph<Integer, Railway> graph;
	private RailFactory rf;
	private HashMap<Integer, Station> stationIndexTable;
	private HashMap<String, ArrayList<Integer>> metroLine;
	
	public MetroMap() {
		metroLine = new HashMap<String, ArrayList<Integer>>();
		rf = new RailFactory();
		graph = new WeightedMultigraph<>(rf);
		stationIndexTable = new HashMap<Integer, Station>();
	}

	public void addStationToMetroLine(String linename, Integer stationIndex) {
		ArrayList<Integer> stationsList;
		if (!metroLine.containsKey(linename)) {
			stationsList = new ArrayList<Integer>();
			stationsList.add(stationIndex);
			metroLine.put(linename, stationsList);
		} else {
			stationsList = metroLine.get(linename);
			stationsList.add(stationIndex);
			metroLine.put(linename, stationsList);
		}
	}
	
	public ArrayList<Integer> getStationIndexListByLine(String line) {
		return metroLine.get(line);
	}
	
	public void addStationToMap(Integer stationIndex, String stationName, HashSet<String> lines, String firstTrainTime, String lastTrainTime, int departureFrequency) {
		TerminalStation newTerminalStation;
		if (!stationIndexTable.containsKey(stationIndex)) {
			newTerminalStation = new TerminalStation(stationIndex, stationName, firstTrainTime, lastTrainTime, departureFrequency);
			for (String l : lines) {
				newTerminalStation.addLine(l);
			}
			stationIndexTable.put(stationIndex, newTerminalStation);
			graph.addVertex(stationIndex);
		}
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
	
	public void connectStations(Integer station1Index, Integer station2Index, double length, double time, int cost, String metroline) {
		Railway r;
		// Trying to add a connection to two already connected stations		
		r = new Railway(length, time, cost, station1Index, station2Index, metroline);
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
	
	public Set<Railway> getRails(Integer station1Index, Integer station2Index) {
		return graph.getAllEdges(station1Index, station2Index);
	}
	
	public Station getStationByIndex(Integer stationIndex) {
		return stationIndexTable.get(stationIndex);
	}
	
	public Collection<Station> getStationSet() {
		return stationIndexTable.values();
	}
}
