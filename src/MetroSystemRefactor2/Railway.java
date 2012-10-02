package MetroSystemRefactor2;

import java.util.HashSet;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Railway extends DefaultWeightedEdge{
	private int id;
	private int cost;
	private double length, time;
	private String line;
	private HashSet<Integer> connectedStationIndex;
	private String metroline;
	
	public Railway(double length, double time, int cost, int station1Index, int station2Index, String metroline) {
		super();
		connectedStationIndex = new HashSet<Integer>();
		connectedStationIndex.add(station1Index);
		connectedStationIndex.add(station2Index);
		this.length = length;
		this.time = time;
		this.cost = cost;
		this.metroline = metroline;
	}
	
	public String getLine() {
		return line;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((connectedStationIndex == null) ? 0 : connectedStationIndex
						.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((metroline == null) ? 0 : metroline.hashCode());
		return result;
	}

	public String getBelongsToMetroLine() {
		return metroline;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Railway other = (Railway) obj;
		if (connectedStationIndex == null) {
			if (other.connectedStationIndex != null)
				return false;
		} else if (!connectedStationIndex.equals(other.connectedStationIndex))
			return false;
		if (id != other.id)
			return false;
		/*if (metroline == null) {
			if (other.metroline != null)
				return false;
		} else if (!metroline.equals(other.metroline))
			return false;*/
		return true;
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
