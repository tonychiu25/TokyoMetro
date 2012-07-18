package MetroSystemRefactor;

public class MetroBuilder {
	public static void main(String args[]) {
		subwaySystem subway = new subwaySystem();
		subway.addNode(1);
		subway.addNode(2);
		subway.addNode(3);
		railway r = new railway(1, 1, 1);
		try {
			subway.connectStations(1, 2, r);
                        subway.connectStations(1, 3, r);
                        subway.connectStations(2, 3, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
                subway.printNeighbouringStations();
	}
}
