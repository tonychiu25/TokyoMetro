package MetroSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import Algorithms.GraphAlgorithms;

class railComparitor implements Comparator {
        
	private String compareType;
	
	public railComparitor(String compareType) {
		this.compareType = compareType;
	}
	
	public int compare(Object arg0, Object arg1) {
		railway r1 = (railway) arg0;
		railway r2 = (railway) arg1;
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

public class MSTMetro {
	
	private List<railway> sortedLinks;
	private Map<Integer, station> MSTStations;
	private List<railway> MSTLinks;
	
	public void printMST() {
		/*for(station s : MSTStations.values()) {
			System.out.print(s.getStationIndex());
			System.out.print(" Neighbours :{");
			Map<station, railway> neighbours = s.getNeighborStation();
			for (station sNeighibour : neighbours.keySet()) {
				System.out.print(sNeighibour.getStationIndex()+",");
			}
			System.out.println("");
		}*/
		System.out.println(MSTStations.size());
	}
	
	public MSTMetro(subwaySystem sub, String type) throws Exception {
		Map<Integer, station> subwaySystem = sub.getSubway();
		ArrayList<railway> rails = sub.getRails();
		if (type != "d" && type != "t" && type != "c") {
			throw new Exception("Invalid MST type :"+" only {d,t,c} are allowed!");
		}
		sortedLinks = new ArrayList<railway>();
		MSTLinks = new ArrayList<railway>();
		MSTStations = new HashMap<Integer, station>();
		
		Iterator<Integer> subwaySystemIt = subwaySystem.keySet().iterator();
		
		while(subwaySystemIt.hasNext()) {
			int stationIndex = subwaySystemIt.next();
			MSTStations.put(stationIndex, new station(stationIndex));
		}
		
		Iterator<railway> railIt = rails.iterator();
		while(railIt.hasNext()) {
			railway r = railIt.next();
			sortedLinks.add(r);
		}
		// Finally sort the ArrayList
		Collections.sort(sortedLinks, new railComparitor(type));
	}
	
	public Map<Integer, station> getStation() {
		return MSTStations;
	}
	
	public List<railway> getRail() {
		return MSTLinks;
	}
	
	// TODO Rewrite Kruskal
	public subwaySystem kruskalAlgorithm() {
		Iterator<Integer> stationIndexIt = MSTStations.keySet().iterator();
		subwaySystem MSTSubway = new subwaySystem(stationIndexIt.next());
		subwaySystem MSTSubwayTmp;
		while (stationIndexIt.hasNext()) {
			try {
				MSTSubway.addStation(stationIndexIt.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		MSTSubwayTmp = MSTSubway;
		
		for (railway r : sortedLinks) {
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
		}
		return MSTSubway;
	}
	
	public HashMap<Integer, station> getMST() {
		return (HashMap<Integer, station>) MSTStations;
	}
	
	public static void main(String args[]) {
		
		subwaySystem sub = new subwaySystem(1);
		MSTMetro MST = null;
		try {
			sub.addStationWithLine(2, 1, 1, 1, 1);
			sub.addStationWithLine(3, 2, 4, 1, 1);
			sub.addStationWithLine(4, 3, 1, 1, 1);
                        sub.addStationWithLine(5, 3, 33, 1, 1);
			sub.connectStations(1, 4, 5, 1, 1);
                        sub.connectStations(1, 5, 1, 1, 1);
			sub.connectStations(1, 3, 2, 1, 1);
                        sub.connectStations(2, 4, 7, 1, 1);
                        sub.connectStations(4, 5, 1, 1, 1);
                        sub.connectStations(2, 5, 1, 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			MST = new MSTMetro(sub, "d");
			GenericSubway finalMST = MST.kruskalAlgorithm();
			// Still need to add neighbouring station
			for (station s : finalMST.getSubway().values()) {
				s.printNeighborStation();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
