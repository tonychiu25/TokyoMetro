package MetroSystemRefactor2;

import java.util.HashSet;

public class Station {
	private int stationIndex;
	private String name;
	private HashSet<String> lines;

	public Station(int sIndex, String sName) {
		stationIndex = sIndex;
		name = sName;
		lines = new HashSet<String>();
	}
	
	public HashSet<String> getLines() {
		return lines;
	}

	public int getIndex() {
		return stationIndex;
	}

	public String getName() {
		return name;
	}
	
	public void addLine(String l) {
		lines.add(l);
	}
}
