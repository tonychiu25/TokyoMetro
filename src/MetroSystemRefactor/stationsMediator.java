package MetroSystemRefactor;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

public class stationsMediator {
	Map<Integer, HashSet<Integer>> stationsIndexMapping;
        private HashMap<String, railway> stationRailMediator;
	
	public stationsMediator() {
            stationsIndexMapping = new HashMap<Integer, HashSet<Integer>>();
            stationRailMediator = new HashMap<String, railway>();
	}
        
        private void removeRailMediatorEntry(Integer s1Index, Integer s2Index) {
            String s1 = Integer.toString(s1Index);
            String s2 = Integer.toString(s2Index);
            String key = s1+"-"+s2;
            
            stationRailMediator.remove(key);
        }
        
        private boolean addRailToMediator(Integer s1Index, Integer s2Index, railway r) {
            String s1 = Integer.toString(s1Index);
            String s2 = Integer.toString(s2Index);
            String key = s1+"-"+s2;
        
            if (!stationRailMediator.containsKey(key)) {
                stationRailMediator.put(key, r);
                return true;
            } else {
                return false;
            }
        }
        
        public railway getRail(Integer s1Index, Integer s2Index) {
            String s1 = Integer.toString(s1Index);
            String s2 = Integer.toString(s2Index);
            String key = s1+"-"+s2;
        
            return stationRailMediator.get(key);
        }
        
	public void addNeighbouringStation(Integer targetStation, Integer neighbourStation, railway r) {
		HashSet<Integer> neighbourIndexSet;
		if (!stationsIndexMapping.containsKey(targetStation)) {
			neighbourIndexSet = new HashSet<Integer>();
			neighbourIndexSet.add(neighbourStation);
			stationsIndexMapping.put(targetStation, neighbourIndexSet);
                        addRailToMediator(targetStation, neighbourStation, r);
		} else {
			neighbourIndexSet = stationsIndexMapping.get(targetStation);
			if (neighbourIndexSet.add(neighbourStation)) {
				//System.out.println("Station "+neighbourStation+" was already added as a neighbour to station "+targetStation);
			}
			stationsIndexMapping.put(targetStation, neighbourIndexSet);
                        addRailToMediator(targetStation, neighbourStation, r);
		}
	}
        
	public void removeConnection(Integer sIndex, Integer eIndex) {
		HashSet<Integer> neighbours = stationsIndexMapping.get(sIndex);
		neighbours.remove(eIndex);
		stationsIndexMapping.put(sIndex, neighbours);
                removeRailMediatorEntry(sIndex, eIndex);
                removeRailMediatorEntry(eIndex, sIndex);
	}
	
	public HashSet<Integer> getNeighbourStations(Integer targetStationIndex) {
		HashSet<Integer> neighbouringStations = null;
		try {
			neighbouringStations = stationsIndexMapping.get(targetStationIndex);
			return neighbouringStations;
		} catch (NullPointerException e) {
			System.out.println("Station :"+targetStationIndex+" does not exist in the subway");
			e.printStackTrace();
		}
		
		return neighbouringStations;
	}
	
	// check if two stations are connected
	public boolean checkConnected(Integer s1Index, Integer s2Index) {
		boolean connected = false;
		HashSet<Integer> s1Neighbours = null;
		HashSet<Integer> s2Neighbours = null;
		try {
			s1Neighbours = stationsIndexMapping.get(s1Index);
			s2Neighbours = stationsIndexMapping.get(s2Index);
			if (s1Neighbours.contains(s2Index) && s2Neighbours.contains(s1Index)) {
				connected = true;
			}
		} catch (NullPointerException e) {
			if (s1Neighbours == null) {
				System.out.println("Station "+s1Index+ " does not exist");
			} else if (s2Neighbours == null) {
				System.out.println("Station "+s2Index+ " does not exist");
			}
			e.printStackTrace();
		}
		
		return connected;
	}
        
        public void printStationNeighbours() {
            for (Integer sIndex : stationsIndexMapping.keySet()) {
                System.out.print("Station "+sIndex+" has Neighbours {");
                for (Integer neighbour : stationsIndexMapping.get(sIndex)) {
                    System.out.print(neighbour+",");
                }
                System.out.println("}");
            }
        }
        
        public Map<Integer, HashSet<Integer>> getStationsMediator() {
            return stationsIndexMapping;
        }
}
