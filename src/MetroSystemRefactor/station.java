package MetroSystemRefactor;

import java.util.HashSet;

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
	
}
