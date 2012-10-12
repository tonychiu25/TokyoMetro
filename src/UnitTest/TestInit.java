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

	protected String csvPath = "C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/metromap4.csv";
	protected int totalStationCount = 221;
	
	protected Integer[] shinjukuLineExpect = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
	protected Integer[] oedoLineExpect = {26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,15,42,43,44,45,46,47,48,49,50,51,52,53,54,1,26,55,56,57,58,59,60,61,62,63,64};
	protected Integer[] asakusaLineExpect = {65,66,67,68,69,70,71,72,48,73,74,75,76,77,78,79,80,81,82,40,83,84,85};
	protected Integer[] mitaLineExpect = {86,87,88,72,89,90,91,76,77,92,7,93,33,34,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108};
	protected Integer[] ginzaLineExpect = {83,109,110,111,37,112,113,114,115,116,79,117,75,73,118,119,120,121,122,52,123,124,125};
	protected Integer[] marunouchiLineExpect = {126,127,128,34,33,35,129,10,9,8,92,130,75,131,120,119,132,122,133,134,135,136,1,137,56,138,139,140,141,142};
	protected Integer[] hibiyaLineExpect = {143,144,145,146,111,38,147,113,148,149,80,150,151,152,74,75,76,77,131,153,51,154,155,156};
	protected Integer[] tozaiLineExpect = {157,158,159,160,161,32,6,162,92,79,150,43,163,164,165,166,167,168,169,170,171,172,173};
	protected Integer[] chiyodaLineExpect = {174,143,175,176,177,178,179,9,10,8,92,180,76,77,131,120,119,181,182,124,183,184,185};
	protected Integer[] yurakuchoLineExpect = {186,187,188,189,190,191,192,193,126,194,195,196,32,5,197,122,198,199,77,76,200,201,44,202,203,204};
	protected Integer[] hanzomonLineExpect = {125,124,205,122,198,206,6,7,92,116,207,208,17,209,85};
	protected Integer[] nambakuLineExpect = {210,211,212,213,214,215,216,217,34,33,32,5,133,122,121,119,120,218,50,88,87,86};
	protected Integer[] fukutoshinLineExpect = {186,187,188,189,190,191,192,193,126,219,220,28,136,221,183,125};

	protected HashMap<String, Integer[]> expectedLinesStationMap;
	protected MetroMap mmap;
	protected MetroBuilder builder;
	protected Collection<Station> stationSet;
	protected Set<Integer> stationIndexSet;
	
	@Before
	public void initialize() {
		builder = new MetroBuilder();
		stationIndexSet = new HashSet<Integer>();
		builder.setDirectoryPath("./SubwayMaps/TokyoMetroMap/");
		try {
			mmap = builder.buildSubwayFromCSV();
		} catch (Exception e) {
			e.printStackTrace();
		}

		stationSet = mmap.getStationSet();
		for (Station s : stationSet) {
			stationIndexSet.add(s.getIndex());
		}
		
		expectedLinesStationMap = new HashMap<String, Integer[]>();
		
		expectedLinesStationMap.put("S", shinjukuLineExpect);
		expectedLinesStationMap.put("E", oedoLineExpect);
		expectedLinesStationMap.put("A", asakusaLineExpect);
		expectedLinesStationMap.put("I", mitaLineExpect);
		expectedLinesStationMap.put("G", ginzaLineExpect);
		expectedLinesStationMap.put("M", marunouchiLineExpect);
		expectedLinesStationMap.put("H", hibiyaLineExpect);
		expectedLinesStationMap.put("T", tozaiLineExpect);
		expectedLinesStationMap.put("C", chiyodaLineExpect);
		expectedLinesStationMap.put("Y", yurakuchoLineExpect);
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
