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
import Utility.Utility;
import au.com.bytecode.opencsv.CSVReader;

public class TestInit {

	final static String basepath = "./SubwayMaps/TokyoMetroMap/"; 
	static CSVReader reader;
	static HashMap<String, ArrayList<String>> lines;
	static HashMap<String, HashSet<String>> expectStationLines, expectSatelliteStation;
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
		expectStationLines = new HashMap<>();
		expectSatelliteStation = new HashMap<>();
		
		File[] listOfFiles = folder.listFiles();
		String fileName, line, stationName, satelliteStations;;
		String[] nextLine;
		HashSet<String> stationLines;
		ArrayList<String> lineStations;
		lines = new HashMap<>();
		for (File file : listOfFiles) {
			fileName = file.getName();
			reader = new CSVReader(new FileReader(basepath+fileName));
			line = reader.readNext()[0];
			lineStations = new ArrayList<>();
			while ((nextLine = reader.readNext()) != null) {
				stationName = nextLine[0];
				stationLines = Utility.extractToHashSet(nextLine[4], "&");
				stationLines.add(line);
				expectStationLines.put(stationName, stationLines);
				addedStations.add(stationName);
				lineStations.add(stationName);
				
				System.out.println(line+ " : " +stationName);
				
				if (!nextLine[5].isEmpty()) {
					satelliteStations = nextLine[5];
					HashSet<String> satelliteTmp = new HashSet<>();
					for (String s : satelliteStations.split(";")) {
						satelliteTmp.add(s.split("!")[0]);
					}
					expectSatelliteStation.put(stationName, satelliteTmp);
				}
			}
			lines.put(line, lineStations);
		}
    }
	
	@Test
	public void initialize() {
		
		assertTrue(true);
	}

}
