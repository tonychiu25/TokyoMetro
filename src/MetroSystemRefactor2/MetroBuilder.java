package MetroSystemRefactor2;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import au.com.bytecode.opencsv.CSVReader;

public class MetroBuilder {
	
	private String filepath;
	
	public MetroBuilder() {}
	
	public void setFilePath(String filepath) {
		this.filepath = filepath;
	}
	
	public MetroMap buildSubwayFromLineCSV() throws Exception {
		MetroMap mmap = new MetroMap();	
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(filepath));
		} catch (IOException e) {
			System.out.println("Invalid File Path");
			e.printStackTrace();
		}

		String[] nextLine, stationAttr;
		Integer sIndex, sPrevCost, sPrevIndex;
		Double sPrevDistance, sPrevTime;
		String sName, otherMetroLineString, currentMetroLine;
		HashSet<String> metroLineSet;

		while ((nextLine = reader.readNext()) != null) {
			currentMetroLine = nextLine[0];
			sPrevIndex = null;
			for (String val : nextLine) {
				if (val.contains(";")) { // Skip first line declaration
					metroLineSet = new HashSet<String>();
					metroLineSet.add(currentMetroLine);
					
					stationAttr = val.split(";");
					sIndex = Integer.parseInt(stationAttr[0]);
					sName = stationAttr[1];
					sPrevDistance = Double.parseDouble(stationAttr[3]);
					sPrevTime = Double.parseDouble(stationAttr[4]);
					sPrevCost = Integer.parseInt(stationAttr[5]);
					otherMetroLineString = stationAttr[2];
					if (otherMetroLineString.length() > 0) {
						for (String line : otherMetroLineString.split("&")) {
							metroLineSet.add(line);
						}
					}
					
					mmap.addStationToMap(sIndex, sName, metroLineSet);
					if (sPrevIndex != null) {
						mmap.connectStations(sIndex, sPrevIndex, sPrevDistance, sPrevTime, sPrevCost, currentMetroLine);
					}
					sPrevIndex = sIndex;
				}
			}
		}

		return mmap;
	}
}
