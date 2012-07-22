package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import Algorithms.GraphAlgorithms;
import MetroSystem.GenericSubway;
import java.util.Comparator;
import java.util.Iterator;

class railComparitor implements Comparator {
        
	private String compareType;
	
	public railComparitor(String compareType) {
		this.compareType = compareType;
	}
	
	public int compare(Object arg0, Object arg1) {
		MetroSystem.railway r1 = (MetroSystem.railway) arg0;
		MetroSystem.railway r2 = (MetroSystem.railway) arg1;
		int diff = 0;

		if (compareType == "d") {
			diff = r1.getlength() - r2.getlength();
		} else if (compareType == "t") {
			diff = r1.getTime() - r2.getTime();
		} else if (compareType == "c") {
			diff = r1.getCost() - r2.getCost();
		}
		
		if (diff < 0) {
			return -1;
		} else if (diff == 0) {
			return 0;
		} else {
			return 1;
		}
	}
}

public class MSTMetro extends graph<station, railway>{
        private stationsMediator stationMediator;
        private ArrayList<railway> MSTedges;
        
        public MSTMetro() {
        	stationMediator = new stationsMediator();
        	nodes = new HashMap<>();
        	edges = new ArrayList<>();
        	MSTedges = new ArrayList<>();
        }
	

        public void addNode(Integer sIndex) {
				if (!checkNodeExists(sIndex)) {
				station station = new station(sIndex);
				nodes.put(sIndex, station);
			}
        }
        
        private boolean checkCycle() {
        	
        	return false;
  	  }
        
   	 private subwaySystem kruskalAlgorithm() {
                
		/*Iterator<Integer> stationIndexIt = MSTStations.keySet().iterator();
		GenericSubway MSTSubway = new MetroSystem.subwaySystem(stationIndexIt.next());
		GenericSubway MSTSubwayTmp;
		while (stationIndexIt.hasNext()) {
			try {
				MSTSubway.addStation(stationIndexIt.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		MSTSubwayTmp = MSTSubway;
		
		for (MetroSystem.railway r : sortedLinks) {
			Integer s1Index = r.getConnectedStation()[0].getStationIndex();
			Integer s2Index = r.getConnectedStation()[1].getStationIndex();
			try {
				MSTSubway.connectStations(s1Index, s2Index, r.getlength(), r.getCost(), r.getTime());
				if (GraphAlgorithms.checkCycle(MSTSubway.getSubway(), MSTSubway.getRails())) {
					MSTSubway.disconnectStations(s1Index, s2Index);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
   		 return null;
   	 }
        
}
