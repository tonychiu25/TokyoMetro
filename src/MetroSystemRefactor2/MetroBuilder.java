package MetroSystemRefactor2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
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
		int stationIndex = 1;
		// Directory path here

		String files;
		CSVReader reader = null;
		File folder = new File(basepath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			String currentLine[];
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				String nextLine[], satelliteStation[];
				String currlineAbbr, currlineName, stationName, lineAbbr; 
				Double distance, time;
				Integer cost;
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
						if (nextLine.length == 5) {
							System.out.println(currlineName);
							System.out.println(stationName + " is Empty");
						}
					}
					
				} catch (IOException e) {
					System.out.println("Invalid File Path");
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
