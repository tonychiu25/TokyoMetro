/**
 * @author chiu.sintung
 * Test class for testing functions related to the calculation 
 * of the shortest paths between two metro stations. 
 */
package UnitTest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.*;

import MetroSystemRefactor2.Station;

public class TestPath extends TestInit {
	
	@Test
	// Test that a path exists between any two stations in the metro system.
	public void testPathExists() {
		ArrayList<Integer> path;
		
		Set<Integer> addedStationsIndex = mmap.getMetroGraph().vertexSet();
		for (int i=1; i<=totalStationCount; i++) {
			for (int j=1; j<=totalStationCount; j++) {
				if (i != j && addedStationsIndex.contains(i) && addedStationsIndex.contains(j)) {
					path = mmap.getQuickestRoute(i, j);
					// Assert that station i and j are contained in this path
					assertTrue(path.contains(i) && path.contains(j));
					// Assert that the path starts at i and ends at j
					assertTrue(path.get(0) == i && path.get(path.size()-1) == j);
				}
			}
		}
	}
	
	@Test
	/**
	 * Test the path compression algorithm returns the a correct detourless path.
	 * @Note : Read the "compressStationIndexPath(List<Integer> stationIndexList)" 
	 * 		   function in MetroMap.java for comments on path compression.
	 */
	public void testPathCompression() {
		ArrayList<Integer> path, compressedPath;
		Set<Integer> addedStationsIndex = mmap.getMetroGraph().vertexSet();
		HashSet<String> lineSet, lineSetPrev, addedLineSet, lineSetIntesect;
		addedLineSet = new HashSet<String>();
		
		for (int i=1; i<=totalStationCount; i++) {
			for (int j=1; j<=totalStationCount; j++) {
				if (i != j && addedStationsIndex.contains(i) && addedStationsIndex.contains(j)) {
					path = mmap.getQuickestRoute(i, j);
					compressedPath = mmap.compressStationIndexPath(path);
					// 1. Test the compressed path reatins its start and end stations
					assertTrue(compressedPath.get(0) == i && compressedPath.get(compressedPath.size()-1) == j);
					// 2. Test the compressed path doesn't have detours; ie G->G->Z->Z->G is a detour
					
				}
			}
		}
	}
}