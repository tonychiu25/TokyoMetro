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
			assertTrue(!addedStationSet.contains(station.getName()));
			addedStationSet.add(station.getName());
		}
	}
	
	@Test
	/** Test stations were assigned to the correct line **/
	public void testStationCorrectLine() {
		for (String )
	}

}
