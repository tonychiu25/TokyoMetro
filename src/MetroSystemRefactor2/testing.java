package MetroSystemRefactor2;

public class testing {
	public static void main(String args[]) {
		MetroBuilder mb = new MetroBuilder();
		MetroMap mmap = null;
		mb.setFilePath("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/metromap.csv");
		
		try {
			mmap = mb.buildSubwayFromLineCSV();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
