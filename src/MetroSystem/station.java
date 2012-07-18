package MetroSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class station {
  private int index;
  private Map<station, railway> neighborStationLineMap;
  
  public station(int index) {
	  this.index=index;
	  neighborStationLineMap = new HashMap<station, railway>();
  }
  
  public Map<station, railway> getNeighborStation() {
	  return neighborStationLineMap;
  }
  
  public void removeNeighbouringStation(station s) {
	  
	  /*for(station s:neighborStationLineMap.keySet()) {
		  if (s.index == sIndex) {
			  neighborStationLineMap.remove(s);
		  }
	  }*/
	  neighborStationLineMap.remove(s);
  }
  
  public void printNeighborStation() {
	  Iterator<station> it = neighborStationLineMap.keySet().iterator();
	  System.out.print("Station "+index+" has neighbours {");
	  while(it.hasNext()) {
		  System.out.print(it.next().index+",");
	  }
	  System.out.println("}");
  }
  
  public void addNeighbourStation(station s, railway r) {
	  neighborStationLineMap.put(s, r);
  }
    
  public int getStationIndex() {
	  return this.index;
  }
  
  public static void main(String args[]) {
	  List<railway> col = new ArrayList<railway>();
	  col.add(new railway(1,1,1, new station(1), new station(2)));
	  col.add(new railway(2,1,1, new station(2), new station(4)));
	  col.add(new railway(3,1,1, new station(3), new station(6)));
	  
	  station s = new station(1);
	  station s2 = new station(2);
	  station s3 = new station(3);
	  railway r = new railway(1,2,3,s,s2);
	  railway r2 = new railway(1,23,4 ,s, s3);
	  
	  s.addNeighbourStation(s2, r);
	  s.addNeighbourStation(s3, r2);
	  s.removeNeighbouringStation(s2);
	  s.printNeighborStation();
	  
  }
  
}
