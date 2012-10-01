package MetroSystemRefactor2;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import MetroSystemRefactor.LineStationMediator;
import MetroSystemRefactor.station;

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
	
	public void connectStations(Integer station1Index, Integer station2Index, double length, double time, int cost, String line) {
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
		
		// TONY MODIFIED
		r = new Railway(length, time, cost, station1Index, station2Index, line);
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
	
	public Railway getRail(Integer station1Index, Integer station2Index) {
		return graph.getEdge(station1Index, station2Index);
	}
	
	public Station getStationByIndex(Integer stationIndex) {
		return stationIndexTable.get(stationIndex);
	}
	
	public Collection<Station> getStationSet() {
		return stationIndexTable.values();
	}
	
	/**
	 * 
	 * @return a compressed path consisting of staiton indecies with all cycles removed
	 */
	public ArrayList<Integer> compressStationIndexPath(List<Integer> stationIndexList) {
		List<Integer> subList, recursedList;
		HashSet<String> headStationLineSet, stationLineSet, lineSetIntersect;
		Station station;
		ArrayList<Integer> compressedList = new ArrayList<>();
		int stationIndex;
		if (stationIndexList.size()==0) {
			return new ArrayList<Integer>();
		}
		
		stationIndex = stationIndexList.get(0);
		station = stationIndexTable.get(stationIndex);
		headStationLineSet = station.getLines();
		
		compressedList.add(stationIndex);
		for (int i=stationIndexList.size()-1; i>=0; i--) {
			stationIndex = stationIndexList.get(i);
			station = stationIndexTable.get(stationIndex);
			stationLineSet = station.getLines();
			lineSetIntersect = (HashSet<String>) stationLineSet.clone();
			lineSetIntersect.retainAll(headStationLineSet);
			if (lineSetIntersect.size() > 0) {
				// The longest continual metro line
				// Recursive call to add all compressed lines to currend list.
				compressedList.add(stationIndex);
				subList = stationIndexList.subList(i+1, stationIndexList.size());
				recursedList = compressStationIndexPath(subList);
				compressedList.addAll(recursedList);
				break;
			}
		}
		
		/*int tailIndex, headStationIndex, currListIndex, currStationIndex, lastIndex; 
		ArrayList<Integer> compressedList, recurseCompressedList;
		List<Integer> subList;
		HashSet<String> headStationLineSet, lineSet;
		
		// Base case returns an empty list if the input list is empty
		if (stationIndexList.size() == 0) {
			return new ArrayList<Integer>();
		}
		
		tailIndex = stationIndexList.size()-1;
		headStationIndex = (Integer) stationIndexList.get(0);
		Station s = stationIndexTable.get(headStationIndex);
		System.out.println(s.getLines());
		
		headStationLineSet = stationIndexTable.get(headStationIndex).getLines();
		compressedList = new ArrayList<Integer>();
		
		compressedList.add(headStationIndex);
		for (int i = tailIndex; i >= 0; i--) {
			currStationIndex = (int) stationIndexList.get(i);
			lineSet = stationIndexTable.get(currStationIndex).getLines();
			// stationLineSet <= {stationLineSet} INTERSECT {headStationIndex}
			lineSet.retainAll(headStationLineSet);
			if (lineSet.size() > 0) {
				compressedList.add(currStationIndex);
				subList = stationIndexList.subList(i+1, tailIndex+1);
				// Recursive call to compress the sublist
				recurseCompressedList = compressStationIndexPath(subList);
				compressedList.addAll(recurseCompressedList);
				//compressedList.add((Integer) subList.get(subList.size()-1));
				break;
			}
		}
		/*while (currListIndex >= 0) {
			currStationIndex = (int) stationIndexList.get(currListIndex-1);
			stationLineSet = stationIndexTable.get(currListIndex).getLines();
			// stationLineSet <= {stationLineSet} INTERSECT {headStationIndex}
			stationLineSet.retainAll(headStationLineSet);
			if (stationLineSet.size() > 0) {
				// The current station forms a longest contuinual path, on the same
				// metro line, with the headstation
				compressedList.add(currStationIndex);
				System.out.println(currListIndex+" : "+tailIndex);
				subList = stationIndexList.subList(currListIndex, tailIndex);
				// Recursive call to compress the sublist
				recurseCompressedList = compressStationIndexPath(subList);
				compressedList.addAll(recurseCompressedList);
				break;
			}
			currListIndex--;
		}*/
	
		return compressedList;
	}
	
	// TONY ADDED
	public ArrayList<Integer> getQuickestRoute(Integer station1Index, Integer station2Index) {
		Integer s1Index, s2Index;
		ArrayList<Integer> stationIndexList = new ArrayList<Integer>();
		ArrayList<Railway> railSet = (ArrayList<Railway>) DijkstraShortestPath.findPathBetween(graph, station1Index, station2Index);
		for (Railway r : railSet) {
			s1Index = (Integer) r.getConnectedStationIndex().toArray()[0];
			s2Index = (Integer) r.getConnectedStationIndex().toArray()[1];
			if (!stationIndexList.contains(s1Index)) {
				stationIndexList.add(s1Index);
			}
			if (!stationIndexList.contains(s2Index)) {
				stationIndexList.add(s2Index);
			}
			/*System.out.print(r.getLine()+ ": ");
			System.out.println(r.getConnectedStationIndex().toArray()[0]+" <=> "+r.getConnectedStationIndex().toArray()[1]);
			System.out.println(r.getLine());*/
		}

		return stationIndexList;
	}
	
	public static void main(String args[]) throws Exception {
		
		ArrayList<Integer> l = new ArrayList<>();
		for (int i=1; i<=10; i++) {
			l.add(i);
		}
		
		ArrayList<Integer> append = new ArrayList<>();
		append.add(11);
		
		l.addAll(append);
		
		MetroBuilder mb = new MetroBuilder();
		mb.setFilePath("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/metromap.csv");
		MetroMap mmap = mb.buildSubwayFromLineCSV();
		ArrayList<Integer> route = mmap.getQuickestRoute(1, 111);
		for (Integer i : route) {
			System.out.print(i+" ");
		}
		System.out.println();
		route = mmap.compressStationIndexPath(route);
		System.out.println(route);
	}
}