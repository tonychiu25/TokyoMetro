package MetroSystemRefactor2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import Utility.Utility;
import au.com.bytecode.opencsv.CSVReader;

public class MetroBuilder {

	private String basepath;

	public MetroBuilder() {
	}

	public void setDirectoryPath(String filepath) {
		this.basepath = filepath;
	}

	public MetroMap buildSubwayFromCSV() throws Exception {
		MetroMap mmap = new MetroMap();
		Integer stationIndex = 1;
		Integer currStationIndex, stationIndexTmp;
		// Directory path here

		String files;
		CSVReader reader = null;
		File folder = new File(basepath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			String currentLine[];
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				String nextLine[];
				String currlineAbbr, currlineName, stationName, lineAbbr, otherLine, satelliteStation; 
				Double distance, time;
				Integer cost;
//				HashMap<String, Integer> addedStations = new HashMap<>();	// Map to keep track of added stations
				try {
					reader = new CSVReader(new FileReader(basepath+files));
					currentLine = reader.readNext();
					currlineAbbr = currentLine[0];
					currlineName = currentLine[1];
					while ((nextLine = reader.readNext()) != null) {
						stationName = nextLine[0];
						distance = Double.parseDouble(nextLine[1]);
						time = Double.parseDouble(nextLine[2]);
						cost = Integer.parseInt(nextLine[3]);
						otherLine = nextLine[4];
						satelliteStation = nextLine[5];
						/** Add new station **/
						if (!mmap.checkStationAdded(stationName)) {
							HashSet<String> lineSet = Utility.extractToHashSet(otherLine, "&");
							mmap.addStationToMap(stationIndex, stationName, lineSet);
//							addedStations.put(stationName, stationIndex);
							currStationIndex = stationIndex;
							stationIndex++;
							/** connect to current line's head **/
							mmap.appendStationToLine(currStationIndex, distance, time, cost, currlineAbbr);
							/** check for satellite stations **/
							if (!satelliteStation.isEmpty()) {
								for (String sSatellite : satelliteStation.split(";")) {
									String otherSatellite, satelliteLine;
									otherSatellite = sSatellite.split("!")[0];
									satelliteLine = sSatellite.split("!")[1];
									lineSet = Utility.extractToHashSet(satelliteLine, "&");
									if (!mmap.checkStationAdded(otherSatellite)) {
										/** Make a new Station **/
										mmap.addStationToMap(stationIndex, otherSatellite, lineSet);
//										addedStations.put(otherSatellite, stationIndex);
										mmap.connectStations(stationIndex, currStationIndex, 0, 0, 0, Connection.SATELITE_CONNECTION_LINE);
										stationIndex++;
									} else {
										stationIndexTmp = mmap.getStationIndexByName(otherSatellite);
										mmap.connectStations(currStationIndex, stationIndexTmp, 0, 0, 0, Connection.SATELITE_CONNECTION_LINE);
									}
								}
							}
						} else {
							/** If station was previously added **/
							stationIndexTmp = mmap.getStationIndexByName(stationName);
							mmap.appendStationToLine(stationIndexTmp, distance, time, cost, currlineAbbr);
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			reader.close();
		}

		return mmap;
	}

//	public MetroMap buildSubwayFromLineCSV() throws Exception {
//		MetroMap mmap = new MetroMap();
//		CSVReader reader = null;
//		try {
//			reader = new CSVReader(new FileReader(basepath));
//		} catch (IOException e) {
//			System.out.println("Invalid File Path");
//			e.printStackTrace();
//		}
//
//		String[] nextLine, stationAttr;
//		Integer sIndex, sPrevCost, sPrevIndex;
//		Double sPrevDistance, sPrevTime;
//		String sName, otherMetroLineString, currentMetroLine;
//		HashSet<String> metroLineSet;
//
//		while ((nextLine = reader.readNext()) != null) {
//			currentMetroLine = nextLine[0];
//			sPrevIndex = null;
//			for (String val : nextLine) {
//				if (val.contains(";")) { // Skip first line declaration
//					metroLineSet = new HashSet<String>();
//					metroLineSet.add(currentMetroLine);
//
//					stationAttr = val.split(";");
//					sIndex = Integer.parseInt(stationAttr[0]);
//					sName = stationAttr[1];
//					sPrevDistance = Double.parseDouble(stationAttr[3]);
//					sPrevTime = Double.parseDouble(stationAttr[4]);
//					sPrevCost = Integer.parseInt(stationAttr[5]);
//					otherMetroLineString = stationAttr[2];
//					if (otherMetroLineString.length() > 0) {
//						for (String line : otherMetroLineString.split("&")) {
//							metroLineSet.add(line);
//						}
//					}
//
//					mmap.addStationToMap(sIndex, sName, metroLineSet);
//					mmap.addStationToMetroLine(currentMetroLine, sIndex);
//					if (sPrevIndex != null) {
//						mmap.connectStations(sIndex, sPrevIndex, sPrevDistance,
//								sPrevTime, sPrevCost, currentMetroLine);
//					}
//					sPrevIndex = sIndex;
//				}
//			}
//		}
//
//		return mmap;
//	}
}
