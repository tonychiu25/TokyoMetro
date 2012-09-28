package MetroSystemRefactor2;

import java.util.HashSet;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Railway extends DefaultWeightedEdge{
	
	private int cost;
	private double length, time;
	private HashSet<Integer> connectedStationIndex;
	
	public Railway(double length, double time, int cost, int station1Index, int station2Index) {
		super();
		connectedStationIndex = new HashSet<Integer>();
		connectedStationIndex.add(station1Index);
		connectedStationIndex.add(station2Index);
		this.length = length;
		this.time = time;
		this.cost = cost;
	}
	
	public double getLength() {
		return length;
	}
	
	public double getTime() {
		return time;
	}
	
	public int getCost() {
		return cost;
	}
	
	public HashSet<Integer> getConnectedStationIndex() {
		return connectedStationIndex;
	}
}
