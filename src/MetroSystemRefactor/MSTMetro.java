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
            HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
            Route r = new Route();
            HashSet<Integer> neighbours;
            Queue<Integer> q = new LinkedList<Integer>();
            Integer currentIndex = s1Index;
            boolean exitwhile = false;
            Object[] pathA = path.toArray();
            railway rail;
            
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
            
            path.addFirst(s2Index);
            while(!path.contains(s1Index)) {
                path.addFirst(currentIndex);
                currentIndex = parent.get(currentIndex);
            }
            
            Integer sectionTime = 0;
            for (int i=1; i < pathA.length-1; i++) {
                Integer sIndex = (Integer) pathA[i];
                Integer sIndexPrev = (Integer) pathA[i-1];
                Integer sIndexNext = (Integer) pathA[i+1];
                
                rail = stationMediator.getRail(sIndex, sIndexPrev);
                sectionTime += rail.getTime();
                
                HashSet<String> lines_cur = lsMediator.getLineFromStationIndex(sIndex);
                HashSet<String> lines_prev = lsMediator.getLineFromStationIndex(sIndexPrev);
                HashSet<String> lines_next = lsMediator.getLineFromStationIndex(sIndexNext);
                
                HashSet<String> intersectAll = (HashSet<String>)lines_cur.clone();
                //HashSet<String> intersectAll = lines_cur;
                intersectAll.retainAll(lines_prev);
                intersectAll.retainAll(lines_next);
                
                HashSet<String> intersectPrev = lines_cur;
                
                HashSet<String> intersectNext = lines_cur;
                intersectNext.retainAll(lines_next);
                
                String randomIntersectAllLine;
                if (intersectAll.size() < 1) {    // This implies a line chage at the station sIndex
                    if (!r.routeContainsLine(intersectPrev)) {
                        r.addLineToRoute((String)intersectPrev.toArray()[0]);
                    }
                } else if (intersectAll.size() == 1) {
                    randomIntersectAllLine = (String)intersectAll.toArray()[0];
                    if (!r.routeContainsLine(randomIntersectAllLine)) {     // Only add if route did not previously added this line
                        r.addLineToRoute(randomIntersectAllLine);           // Otherwise route was added
                    }
                } else {                         // Case of multiple lines
                    randomIntersectAllLine = (String)intersectAll.toArray()[0];
                    if (i+1 == path.size()) {
                        r.addLineToRoute(randomIntersectAllLine);
                    }
                }
            }
            
            
            r.printLines();
            System.out.println(path);
            //r.printRoute();
            /*path.add(s2Index);
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
            r.printRoute();*/
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