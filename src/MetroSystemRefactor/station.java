package MetroSystemRefactor;

import java.util.HashSet;

public class station {
	int stationIndex;
	String name;
        HashSet<String> line;
        
	public station(int sIndex, String sName, String sLine) {
            stationIndex = sIndex;
            name = sName;
            line.add(sLine);
	}
	
        public HashSet<String> getLine() {
            return line;
        }
        
        public void addLine(String sLine) {
            line.add(sLine);
        }
        
	public int getIndex() {
		return stationIndex;
	}
	
}
