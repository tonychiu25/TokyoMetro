package UnitTest2;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;
import MetroSystemRefactor2.Connection;
import MetroSystemRefactor2.Station;
import Utility.Utility;

public class TestPathCompression extends TestInit{
	/** Test compressed path is linked from head to tail stations **/
	@Test 
	public void testCompressedPathLinked() {
		HashSet<String> station1LineSet, station2LineSet, lineSetIntersect;
		ArrayList<Integer> compressedPath, path;
		for (Integer s1Index : mmap.getMetroGraph().vertexSet()) {
			for (Integer s2Index : mmap.getMetroGraph().vertexSet()) {
				if (s1Index != s2Index) {
					path = mmap.getQuickestRoute(s1Index, s2Index);
					compressedPath = mmap.compressStationIndexPath(path, true);
					/** Test the tail station is included in the compressed path **/
					assertEquals(path.get(path.size()-1), compressedPath.get(compressedPath.size()-1));
					
					/** Test the compressed path is actaully linked from head to tail **/
					boolean sameLine;
					Integer curStationIndex, preStationIndex;
					Connection rail;
					for (int i=1; i < compressedPath.size(); i++) {
						curStationIndex = compressedPath.get(i);
						preStationIndex = compressedPath.get(i-1);
						station1LineSet = mmap.getStationByIndex(curStationIndex).getLines();
						station2LineSet = mmap.getStationByIndex(preStationIndex).getLines();
						lineSetIntersect = Utility.getSetIntersect(station1LineSet, station2LineSet);
						sameLine = !lineSetIntersect.isEmpty();
						rail = mmap.getRail(curStationIndex, preStationIndex);
						/** Two stations are linked if (belong to same line) OR (They are satelite stations) **/
						assertTrue(sameLine || (rail != null && rail.getLine() == "SATELITE"));
					}
				}
			}
		}
	}
	
	/** Test the path compression does not re-add a station with the same line **/
	@Test
	public void testNoDuplicateLineAdded() {
		ArrayList<Integer> compressedPath, path;
		HashSet<String> visitedLines, station1LineSet, station2LineSet, lineSetIntersect;
		Connection rail;
		for (Integer s1Index : mmap.getMetroGraph().vertexSet()) {
			for (Integer s2Index : mmap.getMetroGraph().vertexSet()) {
				if (s1Index != s2Index) {
					path = mmap.getQuickestRoute(s1Index, s2Index);
					compressedPath = mmap.compressStationIndexPath(path, true);
					/** Test Compressed Path contains at least two stations **/
					assertTrue(compressedPath.size() >= 2);
					visitedLines = new HashSet<>();
					Integer curStationIndex, preStationIndex;
					for (int i=1; i < compressedPath.size(); i++) {
						curStationIndex = compressedPath.get(i);
						preStationIndex = compressedPath.get(i-1);
						station1LineSet = mmap.getStationByIndex(curStationIndex).getLines();
						station2LineSet = mmap.getStationByIndex(preStationIndex).getLines();
						lineSetIntersect = Utility.getSetIntersect(station1LineSet, station2LineSet);
						/** Test compress path cannot contain duplicated visted lines **/
						if (!lineSetIntersect.isEmpty()) {
							for (String l : lineSetIntersect) {
								assertTrue(!visitedLines.contains(l));
								visitedLines.add(l);
							}
						} else {
							rail = mmap.getRail(curStationIndex, preStationIndex);
							assertTrue(rail != null && rail.getLine() == "SATELITE");
						}
					}
				}
			}
		}
	}

}
