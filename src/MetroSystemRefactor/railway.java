package MetroSystemRefactor;

public class railway {
	private int length;
	private int cost;
	private int time;
	private int[] connectedStations;
	
	public railway(int l, int c, int t) {
		length = l;
		cost = c;
		time = t;
		connectedStations = new int[2];
	}
	
	public int getLength() {
		return length;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getTime() {
		return time;
	}
	
	// sets the two stations that this rail will be connected to.
	public void setEnds(int s1Index, int s2Index) {
		connectedStations[0] = s1Index;
		connectedStations[1] = s2Index;
	}
	
	public int[] getEnds() {
		return connectedStations;
	}
	
}
