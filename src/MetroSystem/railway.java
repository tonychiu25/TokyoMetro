package MetroSystem;

import java.util.Comparator;

public class railway {
	
	private int length;
	private int cost;
	private int time;
	private station[] connectedStations;
	
	public railway(int length, int cost, int time, station s1, station s2) {
		this.length = length;
		this.cost = cost;
		this.time = time;
		connectedStations = new station[2];
		connectedStations[0] = s1;
		connectedStations[1] = s2;
	}
	
	public int getlength() {
		return length;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getTime() {
		return time;
	}
	
	public station[] getConnectedStation() {
		return connectedStations;
	}
	
	public boolean isConnected(Integer stationIndex) {
 		boolean connected = false;
		for (int i=0; i<connectedStations.length; i++) {
			if (connectedStations[i].getStationIndex() == stationIndex) {
				connected = true;
			}
		}
		
		return connected;
	}
	
	private void setlength(int length) {
		if (length <=0) {
			this.length = 0;
		} else {
			this.length = length;
		}
	}
}
