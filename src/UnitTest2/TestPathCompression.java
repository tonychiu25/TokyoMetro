package UnitTest2;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import MetroSystemRefactor2.Station;

public class TestPathCompression extends TestInit{
	/** Test path compression for all paths **/
	@Test 
	public void test() {
		ArrayList<Integer> compressedPath, path;
		for (Integer s1Index : mmap.getMetroGraph().vertexSet()) {
			for (Integer s2Index : mmap.getMetroGraph().vertexSet()) {
				path = mmap.getQuickestRoute(s1Index, s2Index);
				compressedPath = mmap.compressStationIndexPath(path, true);
				for (Integer i : path) {
					Station s = mmap.getStationByIndex(i);
				}
				System.out.println(compressedPath);
				//assertTrue(compressedPath.size() >= 2);
			}
		}
	}

}
