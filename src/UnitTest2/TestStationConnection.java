package UnitTest2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class TestStationConnection extends TestInit{

	/** Test stations on lines are connected **/
	@Test
	public void testLineConnected() {
		ArrayList<Integer> stationLineIndexActual;
		Integer sIndex, sPrevIndex;
		
		for (String line : lines.keySet()) {
			stationLineIndexActual = mmap.getStationIndexListByLine(line);
			for (int i=1; i<stationLineIndexActual.size(); i++) {
				sIndex = stationLineIndexActual.get(i);
				sPrevIndex = stationLineIndexActual.get(i-1);
				assertNotNull(mmap.getRail(sIndex, sPrevIndex));
			}
		}
	}
	
	/** Test satellite stations are connected to interchange stations **/
	@Test
	public void testSatelliteConnection() {
		Integer parentStationIndex, satelliteStationIndex;
		for (String station : expectSatelliteStation.keySet()) {
			parentStationIndex = mmap.getStationIndexByName(station);
			for (String satellite : expectSatelliteStation.get(station)) {
				satelliteStationIndex = mmap.getStationIndexByName(satellite);
				assertNotNull(mmap.getRail(parentStationIndex, satelliteStationIndex));
			}
		}
		
		
		mmap.compressStationIndexPath(mmap.getQuickestRoute(3, 100), true);
	}
	
	
}
