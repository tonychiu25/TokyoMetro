package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashMap;
import Algorithms.GraphAlgorithms;
import MetroSystem.GenericSubway;
import java.util.*;

class railComparitor implements Comparator {
        
	private String compareType;
	
	public railComparitor(String compareType) {
		this.compareType = compareType;
	}
	
	public int compare(Object arg0, Object arg1) {
		MetroSystemRefactor.railway r1 = (MetroSystemRefactor.railway) arg0;
		MetroSystemRefactor.railway r2 = (MetroSystemRefactor.railway) arg1;
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

public class MSTMetro extends graph<station, railway>{

        subwaySystem mstSub;
        private List<MetroSystemRefactor.railway> sortedLinks;
    
	public MSTMetro() {
                sortedLinks = new ArrayList<>();
            	stationMediator = new stationsMediator();
		nodes = new HashMap<>();
		edges = new ArrayList<>();
                mstSub = new subwaySystem();
        }

        public void addNode(Integer sIndex) {
				if (!checkNodeExists(sIndex)) {
				station station = new station(sIndex);
				nodes.put(sIndex, station);
			}
        }
        
        public boolean checkCycle(Integer lastAddedNodeIndex) {
            Queue <Integer> q = new LinkedList();
            HashSet<Integer> visited = new HashSet();
            HashSet<Integer> neighbours;
            HashMap<Integer, Integer> parent = new HashMap();
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
                    if (visited.contains(nIndex) && parent.get(currentIndex) != nIndex && currentIndex != lastAddedNodeIndex) {
                        System.out.println(nIndex);
                        return true;
                    } else if(parent.get(currentIndex) != nIndex){
                        q.add(nIndex);
                    }
                }
            }

            return false;
        }
        
   	private subwaySystem kruskalAlgorithm() {
            sortedLinks.addAll(edges);
            Collections.sort(sortedLinks, new railComparitor("d"));
            
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
