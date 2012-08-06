package MetroSystemRefactor;

public class MetroBuilder {
	public static void main(String args[]) {
		subwaySystem subway = new subwaySystem();
		subway.addNode(1);
		subway.addNode(2);
		subway.addNode(3);
                subway.addNode(4);
		railway r = new railway(1, 1, 1);
                railway r1 = new railway(2, 1, 1);
		try {
			subway.connectStations(1, 2, 1, 1, 1);
                        subway.connectStations(2, 3, 1, 2, 3);
                        subway.connectStations(3, 4, 12, 1, 1);
                        subway.connectStations(4, 1, 1, 1, 1);
                        subway.connectStations(1, 3, 1 ,1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
                
                /*for (railway q : subway.getEdgeSet()) {
                    System.out.println(q.getEnds()[0] + ":" + q.getEnds()[1]);
                }*/
                
                MSTMetro mst = new MSTMetro();
                mst.setEdgeSet(subway.getEdgeSet());
                mst.setNodeSet(subway.getNodeSet());
                //mst.setStationMediator(subway.getStationMediator());
                mst.kruskalAlgorithm();
                mst.getStationMediator().printStationNeighbours();
                
                
	}
}
