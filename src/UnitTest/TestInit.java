/**
 * @author chiu.sintung
 * Base class for initializing test case variables
 */
package UnitTest;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import MetroSystemRefactor2.MetroBuilder;
import MetroSystemRefactor2.MetroMap;
import MetroSystemRefactor2.Station;

public class TestInit {

	protected String csvPath = "C:/Users/tonychiu/workspace/TokyoMetro/SubwayMaps/metromap2.csv";
	protected int totalStationCount = 140;
	
	protected Integer[] ginzaLineExpect = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
	protected Integer[] marunouchiLineExpect = {20,21,22,23,24,25,26,27,28,11,29,30,15,31,32,33,34,35,36,37,38,39,40,41,42};
	protected Integer[] hibiyaLineExpect = {43,44,45,46,4,47,48,49,50,51,52,53,54,11,55,29,56,57,58,59,60};
	protected Integer[] tozaiLineExpect = {61,62,63,64,65,66,67,68,27,9,51,69,70,71,72,73,74,75,76,77,78,79,80};
	protected Integer[] chiyodaLineExpect = {81,43,82,83,84,85,86,87,27,88,55,29,30,89,90,18,91,92,93};
	protected Integer[] yurakchoLineExpect = {94,95,96,97,98,99,100,101,20,102,103,104,66,105,106,15,107,108,109,110,111,112,113,114};
	protected Integer[] hanzomonLineExpect = {19,18,115,15,116,67,117,27,8,118,119,120,121,122};
	protected Integer[] nambakuLineExpect = {123,124,125,126,127,128,129,130,23,66,105,31,15,131,132,133,134,135,136};
	protected Integer[] fukutoshinLineExpect = {94,95,96,97,98,99,100,101,20,137,138,139,34,140,91,19};

	protected HashMap<String, Integer[]> expectedLinesStationMap;
	protected MetroMap mmap;
	protected MetroBuilder builder;
	protected Collection<Station> stationSet;
	protected Set<Integer> stationIndexSet;
	
	@Before
	public void initialize() {
		builder = new MetroBuilder();
		stationIndexSet = new HashSet<Integer>();
		builder.setFilePath(csvPath);
		try {
			mmap = builder.buildSubwayFromLineCSV();
		} catch (Exception e) {
			e.printStackTrace();
		}

		stationSet = mmap.getStationSet();
		for (Station s : stationSet) {
			stationIndexSet.add(s.getIndex());
		}
		
		expectedLinesStationMap = new HashMap<String, Integer[]>();
		
		expectedLinesStationMap.put("G", ginzaLineExpect);
		expectedLinesStationMap.put("M", marunouchiLineExpect);
		expectedLinesStationMap.put("H", hibiyaLineExpect);
		expectedLinesStationMap.put("T", tozaiLineExpect);
		expectedLinesStationMap.put("C", chiyodaLineExpect);
		expectedLinesStationMap.put("Y", yurakchoLineExpect);
		expectedLinesStationMap.put("Z", hanzomonLineExpect);
		expectedLinesStationMap.put("N", nambakuLineExpect);
		expectedLinesStationMap.put("F", fukutoshinLineExpect);
	}
	
	/**
	 * Remove Objects after each test case
	 */
	@After
	public void tareDown() {
		mmap = null;
		builder = null;
		stationSet = null;
		expectedLinesStationMap = null;
	}
}
