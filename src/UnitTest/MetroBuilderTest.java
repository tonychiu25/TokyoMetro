package UnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import MetroSystemRefactor2.*;

public class MetroBuilderTest {

	final private String csvPath = "C:/Users/tonychiu/workspace/TokyoMetro/SubwayMaps/metromap.csv";
	final int totalStationCount = 141;
	
	final private Integer[] ginzaLineExpect = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	final private Integer[] marunouchiLineExpect = {20,21,22,23,24,25,26,27,28,11,29,30,15,31,32,33,34,35,36,37,38,39,40,41,42};
	final private Integer[] hibiyaLineExpect = {43,44,45,46,4,47,48,49,50,51,52,53,54,11,55,29,56,57,58,59,60};
	final private Integer[] tozaiLineExpect = {61,62,63,64,65,66,67,68,27,9,51,69,70,71,72,73,74,75,76,77,78,79,80};
	final private Integer[] chiyodaLineExpect = {81,43,82,83,84,85,86,87,27,88,55,29,30,89,90,18,91,92,93};
	final private Integer[] yurakchoLineExpect = {94,95,96,97,98,99,100,101,20,102,103,104,66,105,106,107,108,109,110,111,112,113,114,115};
	final private Integer[] hanzomonLineExpect = {19,18,116,107,117,67,118,27,8,119,120,121,122,123};
	final private Integer[] nambakuLineExpect = {124,125,126,127,128,129,130,131,23,66,105,31,107,132,133,134,135,136,137};
	final private Integer[] fukutoshinLineExpect = {94,95,96,97,98,99,100,101,20,138,139,140,34,141,91,19};

	private HashMap<String, Integer[]> expectedLinesStationMap;
	private MetroMap mmap;
	private MetroBuilder builder;
	private Collection<Station> stationSet;
	private Set<Integer> stationIndexSet;

	private boolean checkArrayMatchArrayList(ArrayList<Integer> arrayList, Integer[] array) {
		for (int i=0; i<array.length; i++) {
			int arrayListInt = arrayList.get(i);
			int arrayInt = array[i];
			if (arrayListInt != arrayInt) {
				System.out.println(i);
				System.out.println(arrayListInt+":"+arrayInt);
				return false;
			}
		}
		
		return true;
	}
	
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

	/**
	 * Test that no duplicate stations were added to the MetroMap
	 */
	@Test
	public void testNoDuplicateStation() {
		Set<Integer> addedStationIndex = new HashSet<Integer>();
		Integer stationIndex;

		for (Station s : stationSet) {
			stationIndex = s.getIndex();
			assertTrue(!addedStationIndex.contains(stationIndex));
			addedStationIndex.add(stationIndex);
		}
	}

	/**
	 * Test that all the stations were successfully read in from the csv data
	 * file and added to MetroMap class
	 */
	@Test
	public void testAddedStations() {
		for (int i = 1; i <= totalStationCount; i++) {
			assertTrue(stationIndexSet.contains(i));
		}
	}
	
	/**
	 *	Test each metro line contains the correct stations as defined in the metromap.csv data file.
	 */
	@Test
	public void testMetroLineSet() {				
		for (String metroLine : expectedLinesStationMap.keySet()) {
			ArrayList<Integer> actualLineStations = mmap.getStationIndexListByLine(metroLine);
			Integer[] expectedLineStations = expectedLinesStationMap.get(metroLine);
			assertTrue(checkArrayMatchArrayList(actualLineStations, expectedLineStations));
		}
	}
	
	/**
	 * Test that all of the stations were properly connected.
	 */
	@Test
	public void testCorrectStationConnection() {
		Integer[] expectedLine;
		int currStationIndex, prevStationIndex;
		Set<Railway> railset;
		Set<String> metroLineSet;
		
		for (String metroLine : expectedLinesStationMap.keySet()) {
			expectedLine = expectedLinesStationMap.get(metroLine);
			for (int i=1; i<expectedLine.length; i++) {
				metroLineSet = new HashSet<String>();	// The set of all metrolines that connect currStation and prevStation
				currStationIndex = expectedLine[i];
				prevStationIndex =  expectedLine[i-1];
				railset = mmap.getRails(prevStationIndex, currStationIndex);	// Two stations can support multiple rails that belong to different metro lines.
				for (Railway r : railset) {
					metroLineSet.add(r.getBelongsToMetroLine());
				}
				// Confirm currStation and prevStation have an edge belonging to the current metroline
				assertTrue(metroLine.contains(metroLine));
				// Confirm this edge connects prevStation and currentStation
				// Note : Railway overridden the Objects.equals() to compare Railway based on (startStationIndex, endStationIndex, metroLine)
				assertTrue(railset.contains(new Railway(0, 0, 0, prevStationIndex, currStationIndex, metroLine)));
			}
		}
	}
	
	
}
