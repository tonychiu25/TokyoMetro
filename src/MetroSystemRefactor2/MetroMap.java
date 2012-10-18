package MetroSystemRefactor2;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.rmi.CORBA.Util;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import au.com.bytecode.opencsv.CSVReader;

import MetroSystemRefactor.LineStationMediator;
import MetroSystemRefactor.station;
import Utility.Utility;

public class MetroMap {
	private WeightedMultigraph<Integer, Connection> graph;
	private RailFactory rf;
	private HashMap<Integer, Station> stationIndexTable;
	private HashMap<String, ArrayList<Integer>> metroLine;
	private HashMap<String, MetroLine> mLines;
	private HashMap<String, Integer> stationNameTable;
	
	public MetroMap() {
		metroLine = new HashMap<String, ArrayList<Integer>>();
		mLines = new HashMap<>();
		
		rf = new RailFactory();
		graph = new WeightedMultigraph<>(rf);
		stationIndexTable = new HashMap<Integer, Station>();
		stationNameTable = new HashMap<String, Integer>();
	}
	
	public WeightedMultigraph<Integer, Connection> getMetroGraph() {
		return graph;
	}
	
	public void addStationToMetroLine(String linename, Integer stationIndex) {
		ArrayList<Integer> stationsList;
		MetroLine line;
//		if (!metroLine.containsKey(linename)) {
//			stationsList = new ArrayList<Integer>();
//			stationsList.add(stationIndex);
//			metroLine.put(linename, stationsList);
//		} else {
//			stationsList = metroLine.get(linename);
//			stationsList.add(stationIndex);
//			metroLine.put(linename, stationsList);
//		}
		if (!mLines.containsKey(linename)) {
			line = new MetroLine(linename, this);
			line.addStationIndexToLine(stationIndex);
			mLines.put(linename, line);
		} else {
			line = mLines.get(linename);
			line.addStationIndexToLine(stationIndex);
			mLines.put(linename, line);
		}
	}
	
	public ArrayList<Integer> getStationIndexListByLine(String line) {
//		return metroLine.get(line);
		return mLines.get(line).getLineStationIndexList();
	}
	
	public void addStationToMap(Integer stationIndex, String stationName, HashSet<String> lines, String firstTrainTime, String lastTrainTime, int departureFrequency) {
		TerminalStation newTerminalStation;
		if (!stationIndexTable.containsKey(stationIndex)) {
			newTerminalStation = new TerminalStation(stationIndex, stationName, firstTrainTime, lastTrainTime, departureFrequency);
			for (String l : lines) {
				newTerminalStation.addLine(l);
			}
			stationIndexTable.put(stationIndex, newTerminalStation);
			stationNameTable.put(stationName, stationIndex);
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
			stationNameTable.put(stationName, stationIndex);
			stationIndexTable.put(stationIndex, newStation);
			graph.addVertex(stationIndex);
		}
	}
	
	public Integer getStationIndexByName(String stationName) {
		return stationNameTable.get(stationName);
	}
	
	public void connectStations(Integer station1Index, Integer station2Index, double length, double time, int cost, String line) {
		Connection r;
		// Trying to add a connection to itself
		if (station1Index == station2Index) {
			System.err.println("Error : Cannot add a link between the same station : station "+station1Index);
			System.exit(-1);
		}
		
		// TONY MODIFIED
		r = new Connection(length, time, cost, station1Index, station2Index, line);
		try {
			graph.addEdge(station1Index, station2Index, r);
			graph.setEdgeWeight(r, time);
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
	
	public Connection getRail(Integer station1Index, Integer station2Index) {
		return graph.getEdge(station1Index, station2Index);
	}
	
	public Set<Connection> getRails(Integer station1Index, Integer station2Index) {
		return graph.getAllEdges(station1Index, station2Index);
	}
	
	public Station getStationByIndex(Integer stationIndex) {
		return stationIndexTable.get(stationIndex);
	}
	
	public Collection<Station> getStationSet() {
		return stationIndexTable.values();
	}
	
	public boolean checkStationAdded(String stationName) {
		return stationNameTable.containsKey(stationName);
	}
	
	public void appendStationToLine(Integer stationIndex, double length, double time, int cost, String line) {
		ArrayList<Integer> lineStationsIndex;
		Integer sTailIndex, sTail2Index;
		addStationToMetroLine(line, stationIndex);
		lineStationsIndex = mLines.get(line).getLineStationIndexList();
		if (lineStationsIndex.size() > 1) {
			sTailIndex = lineStationsIndex.get(lineStationsIndex.size()-1);
			sTail2Index = lineStationsIndex.get(lineStationsIndex.size()-2);
			connectStations(sTailIndex, sTail2Index, length, time, cost, line);
		}
 	}
	
	/**
	 * 
	 * @return a compressed path consisting of staiton indecies with all cycles removed
	 */
	public ArrayList<Integer> compressStationIndexPath(List<Integer> stationIndexList, boolean firstInvocation) {
		ArrayList<Integer> compressedList = new ArrayList<>();
		Integer[] stationsIndexArray = (Integer[]) stationIndexList.toArray(new Integer[stationIndexList.size()]);
		int headPointer = 0;
		int tailPointer = stationIndexList.size()-1;
		int headStationIndex, tailStationIndex;
		HashSet<String> headLineSet, tailLineSet, intersectLineSet;
		Station headStation, tailStation;
		Connection rail;
		
		headStationIndex = stationsIndexArray[headPointer];
		compressedList.add(headStationIndex);
		while (headPointer < tailPointer) {
			tailStationIndex = stationsIndexArray[tailPointer];
			headStation = stationIndexTable.get(headStationIndex);
			tailStation = stationIndexTable.get(tailStationIndex);
			headLineSet = headStation.getLines();
			tailLineSet = tailStation.getLines();
			intersectLineSet = Utility.getSetIntersect(headLineSet, tailLineSet);
			rail = graph.getEdge(headStationIndex, tailStationIndex);
			/** If pointers are next to each other, check if stations are satellites **/ 
			if (rail != null && rail.getLine() == Connection.SATELITE_CONNECTION_LINE) {
				compressedList.add(tailStationIndex);
				headPointer = tailPointer;
				tailPointer = stationIndexList.size()-1;
				headStationIndex = tailStationIndex;
				tailStationIndex = stationsIndexArray[tailPointer];
			/** Otherwise proper line change **/
			} else if (!intersectLineSet.isEmpty()) {
				compressedList.add(tailStationIndex);
				headPointer = tailPointer;
				tailPointer = stationIndexList.size()-1;
				headStationIndex = tailStationIndex;
				tailStationIndex = stationsIndexArray[tailPointer];
			} else {
				tailPointer--;
			}
		}
	
		return compressedList;
	}
	
	// TONY ADDED
	public ArrayList<Integer> getQuickestRoute(Integer sStartIndex, Integer sEndIndex) {
		Integer s1Index, s2Index;
		Route route;
		ArrayList<Integer> stationIndexList, compressedIndexList;
		ArrayList<Connection> railSet = (ArrayList<Connection>) DijkstraShortestPath.findPathBetween(graph, sStartIndex, sEndIndex);
		
		stationIndexList = new ArrayList<Integer>();
		stationIndexList.add(sStartIndex);
		for (Connection r : railSet) {
			s1Index = (Integer) r.getConnectedStationIndex().toArray()[0];
			s2Index = (Integer) r.getConnectedStationIndex().toArray()[1];
			if (!stationIndexList.contains(s1Index)) {
				stationIndexList.add(s1Index);
			}
			if (!stationIndexList.contains(s2Index)) {
				stationIndexList.add(s2Index);
			}
		}

		compressedIndexList = compressStationIndexPath(stationIndexList,true);
		route = new Route(this);
		route.setPath(compressedIndexList);
		System.out.println(route.getTotalTime());
		System.out.println(route.getSectionTimes());
		System.out.println(route.getSectionLines());
		return stationIndexList;
	}
	
	public MetroLine getMetroLine(String lineName) {
		return mLines.get(lineName);
	}
	
	public static void main(String args[]) throws Exception {		
		MetroBuilder mb = new MetroBuilder();
		mb.setDirectoryPath("./SubwayMaps/TokyoMetroMap/");
		MetroMap mmap = mb.buildSubwayFromCSV();
		int s1Index = mmap.getStationIndexByName("Nagatacho");
		int s2Index = mmap.getStationIndexByName("Ginza");
		ArrayList<Integer> path = mmap.getQuickestRoute(s1Index, s2Index);
	}
}
