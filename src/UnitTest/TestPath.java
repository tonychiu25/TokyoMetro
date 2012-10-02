/**
 * @author chiu.sintung
 * Test class for testing functions related to the calculation 
 * of the shortest paths between two metro stations. 
 */
package UnitTest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.*;
import MetroSystemRefactor2.Station;

public class TestPath extends TestInit {
	
	@Test
	/**
	 *  Test that a path exists between any two stations in the metro system.
	 */
	public void testPathExists() {
		ArrayList<Integer> path;
		
		Set<Integer> addedStationsIndex = mmap.getMetroGraph().vertexSet();
		for (int i=1; i<=totalStationCount; i++) {
			for (int j=1; j<=totalStationCount; j++) {
				if (i != j && addedStationsIndex.contains(i) && addedStationsIndex.contains(j)) {
					// 1. Test for path existence between stations (i, j)
					path = mmap.getQuickestRoute(i, j);
					assertTrue(!path.isEmpty());
					// 2. Test that station i and j are contained in this path
					assertTrue(path.contains(i) && path.contains(j));
					// 3. Test that station i and j are the head and tail of the path respectively
					assertTrue(path.get(0) == i && path.get(path.size()-1) == j);
				}
			}
		}
	}
	
	/**
	 * @param Lineset A
	 * @param Lineset B
	 * @return utility function for calculating the intersection of sets
	 */
	public HashSet<String> getSetIntersect(HashSet<String> setA, HashSet<String> setB) {
		HashSet<String> intersection = new HashSet<>();
		intersection = (HashSet<String>) setA.clone();
		intersection.retainAll(setB);
		
		return intersection;
	}
	
	@Test
	/**
	 * Test the path compression algorithm returns the a correct detour less path.
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
					addedLineSet.clear();	// clear out items from previous iteration
					path = mmap.getQuickestRoute(i, j);
					compressedPath = mmap.compressStationIndexPath(path);
					// 1. Test the compressed path reatins its start and end stations ordering
					assertTrue(compressedPath.get(0) == i && compressedPath.get(compressedPath.size()-1) == j);
					// 2. Test the compressed path doesn't have detours; ie G->G->Z->Z->G is a detour
					for (int k=1; k<compressedPath.size()-1; k++) {
						lineSetPrev = mmap.getStationByIndex(compressedPath.get(k-1)).getLines();
						lineSet = mmap.getStationByIndex(compressedPath.get(k)).getLines();
						lineSetIntesect = getSetIntersect(lineSetPrev, lineSet);
						/** Test the current station and the previous 
						  *  station is linked by a similar line.*/
						assertTrue(!lineSetIntesect.isEmpty());
						
						if (addedLineSet.isEmpty()) {
							addedLineSet.addAll(lineSetIntesect);
						} else {
							/** Empty intersection implies the current path doesn't form a detour hitherto".
							 *  This also shows the path is properly compressed; ie only first, tail, and 
							 *  line junction stations are added; any stations inbetween are omitted for now */
							assertTrue(getSetIntersect(lineSetIntesect, addedLineSet).isEmpty());
						}
					}
				}
			}
		}
	}
}