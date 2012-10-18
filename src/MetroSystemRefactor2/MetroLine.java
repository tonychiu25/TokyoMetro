package MetroSystemRefactor2;

import java.util.ArrayList;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

public class MetroLine {
	private ArrayList<Integer> stationIndexList;
	private String name;
	private MetroMap mmap;
	private WeightedMultigraph<Integer, Connection> lineGraph;
	private RailFactory rf;
	private boolean edgesSet;
	
	public MetroLine(String lineName, MetroMap metroMap) {
		name = lineName;
		stationIndexList = new ArrayList<>();
		mmap = metroMap;
		rf = new RailFactory();
		lineGraph = new WeightedMultigraph<>(rf);
		edgesSet = false;
	}
	
	public ArrayList<Integer> getLineStationIndexList() {
		return stationIndexList;
	}
	
	public void addStationIndexToLine(Integer newStationIndex) {
		lineGraph.addVertex(newStationIndex);
		stationIndexList.add(newStationIndex);
	}
	
	public double getTimeBetweenStation(int s1Index, int s2Index) {
		Integer prevStationIndex = null;
		Connection c;
		double totalTime = 0;
		if (!edgesSet) {
			for (Integer stationIndex : stationIndexList) {
				lineGraph.addVertex(stationIndex);
				if (prevStationIndex != null) {
					c = (Connection) mmap.getRail(prevStationIndex, stationIndex).clone();
					lineGraph.addEdge(prevStationIndex, stationIndex, c);
					lineGraph.setEdgeWeight(c, c.getTime());
				}
				
				prevStationIndex = stationIndex;
				edgesSet = true;
			}
		} 
			ArrayList<Connection> railSet = (ArrayList<Connection>) DijkstraShortestPath.findPathBetween(lineGraph, s1Index, s2Index);

			for (Connection rail : railSet) {
				totalTime += rail.getTime();
			}
		
		
		return totalTime;
	}
	
	public static void main(String args[]) throws Exception {
		MetroBuilder mb = new MetroBuilder();
		mb.setDirectoryPath("./SubwayMaps/TokyoMetroMap/");
		MetroMap mmap = mb.buildSubwayFromCSV();
		
		System.out.println(mmap.getStationIndexListByLine("YM"));
	}
}
