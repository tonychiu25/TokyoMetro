package MetroSystemRefactor2;

import java.util.HashSet;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Connection extends DefaultWeightedEdge{
	
	final static int CONNECTED_BY_RAIL = 0;
	final static int SATELITE_CONNECTION = 1;
	
	private int id;
	private int cost;
	private int connectionType;
	private double length, time;
	private String line;
	private HashSet<Integer> connectedStationIndex;
	private String metroline;
	
	public Connection(double length, double time, int cost, int station1Index, int station2Index, String metroline) {
		super();
		connectedStationIndex = new HashSet<Integer>();
		connectedStationIndex.add(station1Index);
		connectedStationIndex.add(station2Index);
		this.length = length;
		this.time = time;
		this.cost = cost;
		this.metroline = metroline;
	}
	
	public void connectionType(int connectionTypeNumber) {
		if (connectionType > 1 || connectionType < 0) {
			System.err.println("Error : Connection not define in Connection class");
			System.exit(-1);
		} else {
			connectionType = connectionTypeNumber;
		}
	}
	
	public int getConnectType() {
		return connectionType;
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
		Connection other = (Connection) obj;
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
