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
	private HashMap<String, Integer> stationNameTable;
	
	public MetroMap() {
		metroLine = new HashMap<String, ArrayList<Integer>>();
		rf = new RailFactory();
		graph = new WeightedMultigraph<>(rf);
		stationIndexTable = new HashMap<Integer, Station>();
		stationNameTable = new HashMap<String, Integer>();
	}
	
	//TODO : implement it
	public Double getTravelTimeForLine(String line ,Integer station1Index, Integer station2Index) {
		return 1.1;
	}
	
	public WeightedMultigraph<Integer, Connection> getMetroGraph() {
		return graph;
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
		lineStationsIndex = metroLine.get(line);
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
//		List<Integer> subList, recursedList;
//		HashSet<String> headStationLineSet, stationLineSet, lineSetIntersect;
//		Station station;
//		ArrayList<Integer> compressedList = new ArrayList<>();
//		int stationIndex;
//		if (stationIndexList.size()==0) {
//			return new ArrayList<Integer>();
//		} else if (stationIndexList.size() <= 2) {
//			compressedList.addAll(stationIndexList);
//			return compressedList;
//		}
//		
//		stationIndex = stationIndexList.get(0);
//		station = stationIndexTable.get(stationIndex);
//		headStationLineSet = station.getLines();
//		
//		if (firstInvocation) {
//			compressedList.add(stationIndex);
//		}
//		for (int i=stationIndexList.size()-1; i>=0; i--) {
//			stationIndex = stationIndexList.get(i);
//			station = stationIndexTable.get(stationIndex);
//			stationLineSet = station.getLines();
//			lineSetIntersect = (HashSet<String>) stationLineSet.clone();
//			lineSetIntersect.retainAll(headStationLineSet);
//			if (lineSetIntersect.size() > 0) {
//				// The longest continual metro line
//				// Recursive call to add all compressed lines to currend list.
//				if (!compressedList.contains(stationIndex)) {
//					compressedList.add(stationIndex);
//				}
//				subList = stationIndexList.subList(i, stationIndexList.size());
//				recursedList = compressStationIndexPath(subList, false);
//				//compressedList.addAll(recursedList);
//				for (Integer sIndex :recursedList) {
//					if (!compressedList.contains(sIndex)) {
//						compressedList.add(sIndex);
//					}
//				}
//				break;
//			}
//		}

		
//		ArrayList<Integer> compressedList = new ArrayList<Integer>();
//		HashSet<String> stationLineIntersect, currStationLineSet;
//		Station currStation;
//		String line;
//		
//		currStation = stationIndexTable.get(stationIndexList.get(0));
//		stationLineIntersect = Utility.getSetIntersect(currStation.getLines(), currStation.getLines());
//		compressedList.add(currStation.getIndex());
//		for (int i=1; i<stationIndexList.size(); i++) {
//			currStationLineSet = stationIndexTable.get(stationIndexList.get(i)).getLines();
//			stationLineIntersect = Utility.getSetIntersect(stationLineIntersect, currStationLineSet);
//			if (stationLineIntersect.isEmpty()) {
//				// Changed line
//				compressedList.add(stationIndexList.get(i-1));
//				stationLineIntersect = (HashSet<String>) stationIndexTable.get(stationIndexList.get(i-1)).getLines().clone();
//			}
//		}
//		
//		compressedList.add(stationIndexList.get(stationIndexList.size()-1));
		ArrayList<Integer> compressedList = new ArrayList<Integer>();
		Integer headpos, tailpos, headStationIndex, tailStationIndex;
		Station headStation, tailStation;
		HashSet<String> intersection;
		headpos = 0;
		tailpos = stationIndexList.size()-1;
		
		headStationIndex = stationIndexList.get(headpos);
		compressedList.add(headStationIndex);
		while (headpos != tailpos) {
			tailStationIndex = stationIndexList.get(tailpos);
			headStation = stationIndexTable.get(headStationIndex);
			tailStation = stationIndexTable.get(tailStationIndex);
			
			intersection = Utility.getSetIntersect(headStation.getLines(), tailStation.getLines());
			if (!intersection.isEmpty()) {
				compressedList.add(tailStationIndex);
				headpos = tailpos;
				tailpos = stationIndexList.size()-1;
			} else {
				tailpos--;
			}
		}
		
		
		return compressedList;
	}
	
	// TONY ADDED
	public ArrayList<Integer> getQuickestRoute(Integer sStartIndex, Integer sEndIndex) {
		Integer s1Index, s2Index;
		Double sectionTime;
		Station station1, station2;
		String metroLine;
		ArrayList<Integer> stationIndexList, compressedIndexList;
		HashSet<String> metroLineSet;
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
		
		compressedIndexList = compressStationIndexPath(stationIndexList, true);
		for (int i=1; i < compressedIndexList.size(); i++) {
			station1 = stationIndexTable.get(compressedIndexList.get(i-1));
			station2 = stationIndexTable.get(compressedIndexList.get(i));
			metroLineSet = Utility.getSetIntersect(station1.getLines(), station2.getLines());
			// Get a random line from the intersection
			//metroLine = (String) metroLineSet.toArray()[0];
			//sectionTime = getTravelTimeForLine(metroLine, station1.getIndex(), station2.getIndex());
		}
		
		return stationIndexList;
	}
	
	public static void main(String args[]) throws Exception {		
		MetroBuilder mb = new MetroBuilder();
		mb.setDirectoryPath("./SubwayMaps/TokyoMetroMap/");
		MetroMap mmap = mb.buildSubwayFromCSV();
//		ArrayList<Integer> route = mmap.getQuickestRoute(1, 22);
//		System.out.println(route);
//		System.out.println(mmap.compressStationIndexPath(route, true));
//		System.out.println(mmap.getStationByIndex(32).getLines());
//		for (int i=1; i<=221; i++) {
//			boolean die = false;
//			for (int j=1; j<=221; j++) {
//				if (mmap.getStationByIndex(i) != null && mmap.getStationByIndex(j) != null){
//					System.out.println(i+":"+j);
//					ArrayList<Integer> route = mmap.getQuickestRoute(i, j);
//					if (route == null) {
//						die = true;
//						break;
//					}
//				}
//			}
//			if (die) {
//				break;
//			}
//		}
	}
}
