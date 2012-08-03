package MetroSystem;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.util.Queue;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import java.util.Iterator;

public class subwaySystem extends GenericSubway {
    
	public subwaySystem(int startStationIndex) {
		super(startStationIndex);
	}

	public ArrayList<railway> findShortestPath(Integer startIndex, Integer endIndex) {
		HashMap<Integer, Integer> parentMap = new HashMap<Integer, Integer>();
		ArrayList<railway> path = new ArrayList<railway>();
		Stack<railway> prePath = new Stack<railway>();
		Map<station, railway> neighbours = subway.get(startIndex).getNeighborStation();
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(startIndex);
		parentMap.put(startIndex, -1);
		while (q.size() > 0) {
			Integer sIndex = q.poll();
			station s = subway.get(sIndex);
			for (station ns: s.getNeighborStation().keySet()) {
				if (parentMap.get(sIndex) == ns.getStationIndex()) {
					continue;
				} else {
					Integer nsIndex = ns.getStationIndex();
					parentMap.put(nsIndex, sIndex);
					if (nsIndex == endIndex) {
						// Unwind
						Integer homeIndex = sIndex;
						station currentStation = ns;
						while(homeIndex != startIndex) {
							railway r = currentStation.getNeighborStation().get(homeIndex);
							System.out.println("ASD");
							prePath.push(r);
							Integer nextStationIndex = parentMap.get(currentStation.getStationIndex());
							currentStation = subway.get(nextStationIndex);
							homeIndex = parentMap.get(homeIndex);
						}
						
						while(!prePath.empty()) {
							path.add(prePath.pop());
						}
						
						return path;
					} else {
						q.add(nsIndex);
					}
				}
			}
 		}
		/*ArrayList<railway> rails = new ArrayList<railway>();
		Map<station, railway> neighbours = subway.get(startIndex).getNeighborStation();
		for (station s : neighbours.keySet()) {
			// base case
			if (s.getStationIndex() == endIndex) {
				railway r = neighbours.get(s);
				rails.add(r);
				return rails;
			} else {
				
				rails.addAll(findShortestPath(s.getStationIndex(), endIndex));
				break;
			}
		}
		
		return rails;*/
		
		return null;
	}
	
	// Add a station to the metrosystem without connecting it to any other stations
	public void addStation(int stationIndex) throws Exception {
		if (subway.containsKey(stationIndex)) {
			throw new Exception("Station "+ stationIndex +" already exists");
		} else {
			station newStation = new station(stationIndex);
			subway.put(stationIndex, newStation);
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
