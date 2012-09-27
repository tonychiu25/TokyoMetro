package MetroSystemRefactor;

import java.util.*;

class railComparitor implements Comparator<railway> {

	private String compareType;

	public railComparitor(String compareType) {
		this.compareType = compareType;
	}

	public int compare(railway arg0, railway arg1) {
		railway r1 = arg0;
		railway r2 = arg1;
		int diff = 0;

		if (compareType == "d") {
			diff = r1.getLength() - r2.getLength();
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

public class MSTMetro extends graph<station, railway> {

	private List<MetroSystemRefactor.railway> sortedLinks;
	private stationsMediator stationMediator;
	private LineStationMediator lsMediator;

	public MSTMetro() {
		sortedLinks = new ArrayList<>();
		stationMediator = new stationsMediator();

		nodes = new HashMap<Integer, station>();
		edges = new ArrayList<railway>();
	}
	
	public void setLineStationMediator(LineStationMediator lsmed) {
		lsMediator = lsmed;
	}
	
	public LineStationMediator getLineStationMediator() {
		return lsMediator;
	}

	public void addNode(Integer sIndex) {
		if (!checkNodeExists(sIndex)) {
			station station = new station(sIndex, "no");
			nodes.put(sIndex, station);
		}
	}

	public stationsMediator getStationMediator() {
		return stationMediator;
	}

	public Route getShortestPath(Integer s1Index, Integer s2Index) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
		HashSet<Integer> neighbours;
		Queue<Integer> q = new LinkedList<Integer>();
		Integer currentIndex = s1Index;
		boolean exitwhile = false;

		q.add(currentIndex);
		while (q.size() > 0) {
			currentIndex = q.poll();
			neighbours = stationMediator.getNeighbourStations(currentIndex);
			for (Integer n : neighbours) {
				if (n != parent.get(currentIndex)) {
					q.add(n);
					parent.put(n, currentIndex);
					if (n == s2Index) {
						exitwhile = true;
						break;
					}
				}
			}

			if (exitwhile) {
				break;
			}
		}

		path.addFirst(s2Index);
		while (!path.contains(s1Index)) {
			path.addFirst(currentIndex);
			currentIndex = parent.get(currentIndex);
		}

		Route r = new Route(stationMediator, lsMediator, path);
		r.getRouteFromPath();

		return r;
	}

	public boolean checkCycle(Integer lastAddedNodeIndex) {
		Queue<Integer> q = new LinkedList<Integer>();
		HashSet<Integer> visited = new HashSet<Integer>();
		HashSet<Integer> neighbours;
		HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
		stationsMediator tmpMediator = stationMediator;

		q.add(lastAddedNodeIndex);
		while (q.size() > 0) {
			Integer currentIndex = q.poll();
			visited.add(currentIndex);
			neighbours = tmpMediator.getNeighbourStations(currentIndex);
			for (Integer nIndex : neighbours) {
				if (parent.get(currentIndex) != nIndex) {
					parent.put(nIndex, currentIndex);
				}
				if (visited.contains(nIndex)
						&& parent.get(currentIndex) != nIndex
						&& currentIndex != lastAddedNodeIndex) {
					return true;
				} else if (parent.get(currentIndex) != nIndex) {
					q.add(nIndex);
				}
			}
		}

		return false;
	}

	public subwaySystem kruskalAlgorithm() {
		sortedLinks.addAll(edges);
		Collections.sort(sortedLinks, new railComparitor("t"));
		int j;

		for (String lineName : lsMediator.getLines()) {
			ArrayList<Integer> stationsIndex = lsMediator.getStationOnLine(lineName);
			for(int i=1; i<stationsIndex.size(); i++) {
				j = i-1;
				
			}
			
		}
		
		for (railway r : sortedLinks) {
			Integer s1Index = r.getEnds()[0];
			Integer s2Index = r.getEnds()[1];
			stationMediator.addNeighbouringStation(s1Index, s2Index, r);
			stationMediator.addNeighbouringStation(s2Index, s1Index, r);
			if (checkCycle(s2Index)) {
				stationMediator.removeConnection(s1Index, s2Index);
				stationMediator.removeConnection(s2Index, s1Index);
			}
		}

		return null;
	}

}