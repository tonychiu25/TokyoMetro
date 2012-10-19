package UnitTest2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import MetroSystemRefactor2.Station;
public class TestMetroBuilder extends TestInit{

	@Test
	/** Test add stations were added **/
	public void testAllStationAdded() {
		Station station;
		HashSet<String> addedStationClone = (HashSet<String>) addedStations.clone();
		for (Integer stationIndex : mmap.getMetroGraph().vertexSet()) {
			station = mmap.getStationByIndex(stationIndex);
			addedStationClone.remove(station.getName());
		}
		
		assertTrue(addedStationClone.isEmpty());
	}
	
	@Test
	/** Test no Duplicate stations added**/
	public void testNoDuplicateStation() {
		HashSet<String> addedStationSet = new HashSet<>();
		Station station;
		for (Integer stationIndex : mmap.getMetroGraph().vertexSet()) {
			station = mmap.getStationByIndex(stationIndex);
			System.out.println(station.getName());
			assertTrue(!addedStationSet.contains(station.getName().toLowerCase()));
			addedStationSet.add(station.getName().toLowerCase());
		}
	}
	
	@Test
	/** Test stations were assigned to the correct line **/
	public void testStationCorrectLine() {
		ArrayList<Integer> stationIndexListActual;
		ArrayList<String> stationsLineExpect;
		Station s;
		for (String line : lines.keySet()) {
			stationIndexListActual = mmap.getStationIndexListByLine(line);
			stationsLineExpect = lines.get(line);
			for (int i=0; i < stationIndexListActual.size(); i++) {
				s = mmap.getStationByIndex(stationIndexListActual.get(i));
				assertEquals(s.getName(), stationsLineExpect.get(i));
				assertTrue(s.getLines().contains(line));
				assertEquals(s.getLines(), expectStationLines.get(s.getName()));
			}
		}
	}
}
