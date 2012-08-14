package MetroSystemRefactor;

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

        private List<MetroSystemRefactor.railway> sortedLinks;
        private stationsMediator stationMediator;
        private LineStationMediator lsMediator;
        
	public MSTMetro() {
            sortedLinks = new ArrayList<>();
            stationMediator = new stationsMediator();
                
            super.nodes = new HashMap();
            super.edges = new ArrayList();
        }

        public void setLineStationMediator(LineStationMediator lsmed) {
            lsMediator = lsmed;
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
        
        public LinkedList<Integer> getShortestPath(Integer s1Index, Integer s2Index) {
            LinkedList<Integer> path = new LinkedList<Integer>();
            LinkedList<String> pathString = new LinkedList<String>();
            HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
            Route r = new Route();
            HashSet<Integer> neighbours;
            Queue<Integer> q = new LinkedList<Integer>();
            Integer currentIndex = s1Index;
            boolean exitwhile = false;
            
            q.add(currentIndex);
            while (q.size() > 0) {
                currentIndex = q.poll();
                neighbours = stationMediator.getNeighbourStations(currentIndex);
                for(Integer n:neighbours) {
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
            
            
            path.add(s2Index);
            while (!path.contains(s1Index)) {
            	HashSet<String> lines = lsMediator.getLineFromStationIndex(currentIndex);
            	if (currentIndex == s2Index) {
            		if (lines.size() > 1 && r.getCurrentLine() == "") {		// ie; end at a multi-lined station
            			r.addLineToRoute((String)lines.toArray()[0]);
            		} else if (lines.size() == 1 && r.getCurrentLine() == "") {
            			r.addLineToRoute((String)lines.toArray()[0]);
            		}
            	} else {						// Not the final station
            		if (lines.size() > 1 && r.getCurrentLine() != "") {                         // Station with multiple lines
            			r.setCurrentLine("");
            		} else {
            			if (r.getCurrentLine() == "") {         // went from intersection to single line
            				r.setCurrentLine((String) lines.toArray()[0]);
            				r.addLineToRoute((String) lines.toArray()[0]);
            			}
            		}
            	}
                
                path.addFirst(currentIndex);
                pathString.addFirst(nodes.get(currentIndex).getName());
                currentIndex = parent.get(currentIndex);
            }
            

            System.out.println(path);
            r.printRoute();
            
            return path;
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
                        return true;
                    } else if(parent.get(currentIndex) != nIndex){
                        q.add(nIndex);
                    }
                }
            }

            return false;
        }
        
   	public subwaySystem kruskalAlgorithm() {
            sortedLinks.addAll(edges);
            Collections.sort(sortedLinks, new railComparitor("d"));
            /*mstSub.setNodeSet(nodes);
            mstSub.setEdgeSet(edges);
            
            for(railway r: sortedLinks) {
                Integer s1Index = r.getEnds()[0];
                Integer s2Index = r.getEnds()[1];
                try {
                    System.out.println(s1Index + ":" + s2Index);
                    mstSub.connectStations(s1Index, s2Index, r);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                if (checkCycle(s1Index, mstSub.getStationMediator())) {
                    mstSub.disconnectStations(s1Index, s2Index);
                }
            }
            mstSub.getStationMediator().printStationNeighbours();*/
            for(railway r : edges) {
                //System.out.println(r.getEnds()[0] + ":" + r.getEnds()[1]);
            }
            //while(!checkComplete()) {
                for (railway r: sortedLinks) {
                    Integer s1Index = r.getEnds()[0];
                    Integer s2Index = r.getEnds()[1];
                    stationMediator.addNeighbouringStation(s1Index, s2Index, r);
                    stationMediator.addNeighbouringStation(s2Index, s1Index, r);
                    if (checkCycle(s1Index)) {
                        stationMediator.removeConnection(s1Index, s2Index);
                        stationMediator.removeConnection(s2Index, s1Index);
                    }
                }
                //stationMediator.printStationNeighbours();
            //}
            
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
