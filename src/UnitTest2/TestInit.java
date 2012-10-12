package UnitTest2;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import MetroSystemRefactor2.MetroBuilder;
import MetroSystemRefactor2.MetroMap;
import au.com.bytecode.opencsv.CSVReader;

public class TestInit {

	final static String basepath = "./SubwayMaps/TokyoMetroMap/"; 
	static CSVReader reader;
	static HashMap<String, ArrayList<String>> lines;
	static HashSet<String> addedStations;
	static MetroMap mmap;
	static MetroBuilder builder;
	
    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
		File folder = new File(basepath);
		builder = new MetroBuilder();
		addedStations = new HashSet<>();
		builder.setDirectoryPath(basepath);
		mmap = builder.buildSubwayFromCSV();
		
		
		File[] listOfFiles = folder.listFiles();
		String fileName, line, stationName;
		String[] nextLine;
		ArrayList<String> lineStations;
		lines = new HashMap<>();
		for (File file : listOfFiles) {
			fileName = file.getName();
			reader = new CSVReader(new FileReader(basepath+fileName));
			line = reader.readNext()[0];
			lineStations = new ArrayList<>();
			while ((nextLine = reader.readNext()) != null) {
				stationName = nextLine[0];
				addedStations.add(stationName);
				lineStations.add(stationName);
			}
			lines.put(line, lineStations);
		}
    }
	
	@Test
	public void initialize() {
		
		assertTrue(true);
	}

}
