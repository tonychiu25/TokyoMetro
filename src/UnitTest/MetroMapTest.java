package UnitTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;

import org.junit.*;
import MetroSystemRefactor2.*;
import static org.junit.Assert.*;
import Utility.*;

public class MetroMapTest {

	private MetroMap mmap;
	private ArrayList<ArrayList<String>> csvMatrix;
	
	@Before
	public void initialize() {
		mmap = new MetroMap();
		csvMatrix = new ArrayList<ArrayList<String>>();
	}
	
	@After
	public void tareDown() {
		mmap = null;
	}
	
	/**
	 * Test that stations were correctly added to the MetroMap class.
	 * @throws IOException
	 */
	@Test
	public void testAddStationToMap() throws IOException {
		// Test added new stations
		HashSet<String> station1Lines = new HashSet<String>();
		station1Lines.add("Line1");
		station1Lines.add("Line2");
		
		HashSet<String> station2Lines = new HashSet<String>();
		station2Lines.add("Line1");
		station2Lines.add("Line3");
		
		HashSet<String> station3Lines = new HashSet<String>();
		station3Lines.add("Line4");
		station3Lines.add("Line5");
		
		String station1Name, station2Name, station3Name;
		int station1Index, station2Index, station3Index;
		
		station1Index = 1;
		station2Index = 2;
		
		station1Name = "Ginza";
		station2Name = "Shibuya";
		
		mmap.addStationToMap(station1Index, station1Name, station1Lines);
		mmap.addStationToMap(station2Index, station2Name, station2Lines);
		
		Station station1 = mmap.getStationByIndex(1);
		HashSet<String> s1AddedLines = station1.getLines();
		Station station2 = mmap.getStationByIndex(2);
		HashSet<String> s2AddedLines = station2.getLines();
		
		assertEquals(station1.getIndex(), station1Index);
		assertEquals(station1.getName(), station1Name);
		assertTrue(station1Lines.containsAll(s1AddedLines));
		
		assertEquals(station2.getIndex(), station2Index);
		assertEquals(station2.getName(), station2Name);
		assertTrue(station2Lines.containsAll(s2AddedLines));
		
		
		// Test add station with with index that has already been added;
		station3Name = "Ueno";
		station3Index = 2;			// But station 2 has already been added, it was Shibuya
		
		mmap.addStationToMap(station3Index, station3Name, station3Lines);
		Station station3 = mmap.getStationByIndex(station3Index);
		assertFalse(station3.getName() == station3Name);
		assertTrue(station3.getName() == station2Name);
	}
	
	// Test that two unconnected stations connected successfully
	private void testConnectNewStation(Integer station1Index, Integer station2Index, double distance, double time, int cost) {
		mmap.connectStations(station1Index, station2Index, distance, time, cost, "G");
		Railway r = mmap.getRail(station1Index, station2Index);
		assertTrue(r.getConnectedStationIndex().contains(station1Index));
		assertTrue(r.getConnectedStationIndex().contains(station2Index));
		assertTrue(r.getLength() == distance);
		assertTrue(r.getTime() == time);
		assertTrue(r.getCost() == cost);
	}
	
	@Test
	public void testConnectStation() {
		Integer station1Index, station2Index, station3Index, station4Index, cost;
		Double distance, time;
		
		Railway r;
		Set<Railway> railSet;
		station1Index = 1;
		station2Index = 2;
		station3Index = 3;
		station4Index = 4;
		
		mmap.addStationToMap(station1Index, "Station1", new HashSet<String>());
		mmap.addStationToMap(station2Index, "Station1", new HashSet<String>());
		mmap.addStationToMap(station3Index, "Station1", new HashSet<String>());
		mmap.addStationToMap(station4Index, "Station1", new HashSet<String>());
		
		// If station not connect a null Railway object is returned
		r = mmap.getRail(station1Index, station2Index);
		assertNull(r);
		
		// Test connection two stations that have not been connected
		testConnectNewStation(1, 2, 1.1, 1.2, 3);
		testConnectNewStation(1, 3, 1.5, 0.2, 2);
		
		// Test adding multiple edges between two stations
		distance  = 3.4;
		time = 2.3;
		cost = 4;
		
		mmap.connectStations(2, 3, distance, time, cost, "Ginza");
		mmap.connectStations(2, 3, distance+1, time+1, cost+1, "Ginza");
		
		railSet = mmap.getRails(2, 3);
		
		// Test that loops are not connected
		mmap.connectStations(1, 1, 1, 1, 1, "Ginza");
		r = mmap.getRail(1, 1);
		assertNull(r);
	}

}
