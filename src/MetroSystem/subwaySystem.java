package MetroSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import java.util.Iterator;

public class subwaySystem extends GenericSubway {
    
	public subwaySystem(int startStationIndex) {
		super(startStationIndex);
	}

	// Add a station to the metrosystem without connecting it to any other stations
	public void addStation(int stationIndex) throws Exception {
		if (subway.containsKey(stationIndex)) {
			throw new Exception("Station "+ stationIndex +" already exists");
		} else {
			station newStation = new station(stationIndex);
			subway.put(stationIndex, newStation);
			System.out.println("Added Station" + stationIndex);
		}
	}
		
	public static void main(String args[]) {
		
		SortedSet<Integer> ss = new TreeSet();
		ss.add(2);
		ss.add(1);
		ss.add(4);
		ss.add(0);
		
		Iterator it = (Iterator) ss.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
		
		subwaySystem sub = new subwaySystem(1);
		try {
			/*sub.addStationWithLine(2, 1, 2, 2, 3);
			sub.addStationWithLine(3, 2, 2, 2, 3);
			sub.connectStations(1, 3, 22, 3, 1);
			sub.addStationWithLine(4, 2, 2, 2, 3);
			sub.addStationWithLine(5, 2, 2, 2, 3);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
