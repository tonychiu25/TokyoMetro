package MetroSystemRefactor;

public class MetroBuilder {
	public static void main(String args[]) {
		subwaySystem subway = new subwaySystem();
		subway.addNode(1);
		subway.addNode(2);
		subway.addNode(3);
                subway.addNode(4);
		railway r = new railway(1, 1, 1);
		try {
			subway.connectStations(1, 2, r);
                        subway.connectStations(2, 3, r);
                        subway.connectStations(3, 4, r);
                        subway.connectStations(4, 1, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
                
                MSTMetro mst = new MSTMetro();
                mst.setEdgeSet(subway.getEdgeSet());
                mst.setNodeSet(subway.getNodeSet());
                mst.setStationMediator(subway.getStationMediator());
                
                System.out.println(mst.checkCycle(3));
	}
}
