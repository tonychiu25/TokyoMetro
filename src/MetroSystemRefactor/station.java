package MetroSystemRefactor;

public class station {
	int stationIndex;
	String name;

	public station(int sIndex, String sName) {
		stationIndex = sIndex;
		name = sName;
	}

	public int getIndex() {
		return stationIndex;
	}

	public String getName() {
		return name;
	}

}
