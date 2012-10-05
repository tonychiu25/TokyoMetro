/**
 * @author chiu.sintung
 * Test class for testing the metro map data structure was constructed correctly
 */
package UnitTest;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import MetroSystemRefactor2.*;

public class MetroBuilderTest extends TestInit {

	private boolean checkArrayMatchArrayList(ArrayList<Integer> arrayList, Integer[] array) {
		for (int i=0; i<array.length; i++) {
			int arrayListInt = arrayList.get(i);
			int arrayInt = array[i];
			if (arrayListInt != arrayInt) {
				return false;
			}
		}
		
		return true;
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
		Set<Connection> railset;
		Set<String> metroLineSet;
		
		for (String metroLine : expectedLinesStationMap.keySet()) {
			expectedLine = expectedLinesStationMap.get(metroLine);
			for (int i=1; i<expectedLine.length; i++) {
				metroLineSet = new HashSet<String>();	// The set of all metrolines that connect currStation and prevStation
				currStationIndex = expectedLine[i];
				prevStationIndex =  expectedLine[i-1];
				railset = mmap.getRails(prevStationIndex, currStationIndex);	// Two stations can support multiple rails that belong to different metro lines.
				for (Connection r : railset) {
					metroLineSet.add(r.getBelongsToMetroLine());
				}
				// Confirm currStation and prevStation have an edge belonging to the current metroline
				assertTrue(metroLine.contains(metroLine));
				// Confirm this edge connects prevStation and currentStation
				// Note : Connection overridden the Objects.equals() to compare Connection based on (startStationIndex, endStationIndex, metroLine)
				assertTrue(railset.contains(new Connection(0, 0, 0, prevStationIndex, currStationIndex, metroLine)));
			}
		}
	}
}
