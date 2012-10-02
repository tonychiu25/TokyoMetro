package Utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import au.com.bytecode.opencsv.CSVReader;

public class CSVParser {
	
	/**
	 * @param Lineset A
	 * @param Lineset B
	 * @return utility function for calculating the intersection of sets
	 */
	public static HashSet<String> getSetIntersect(HashSet<String> setA, HashSet<String> setB) {
		HashSet<String> intersection = new HashSet<>();
		intersection = (HashSet<String>) setA.clone();
		intersection.retainAll(setB);
		
		return intersection;
	}

	
	// Utility function to return parsed csv in a 2d ArrayList
	public static ArrayList<ArrayList<String>> generate2DArrayList(String csvPath) throws IOException {
		ArrayList<ArrayList<String>> arrayList2d = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		String[] nextLine;
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvPath));
		} catch (IOException e) {
			System.out.println("Invalid File Path");
			e.printStackTrace();
		}
		
		while ((nextLine = reader.readNext()) != null) {
			row = new ArrayList<String>();
			for (String s : nextLine) {
				row.add(s);
			}
			arrayList2d.add(row);
		}
		
		return arrayList2d;
	}
	
	
}
