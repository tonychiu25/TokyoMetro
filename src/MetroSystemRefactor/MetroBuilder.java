package MetroSystemRefactor;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.collections.keyvalue.MultiKey;

public class MetroBuilder {

	// TODO : Refactor using metroLine mediator
	public subwaySystem buildSubwayFromLineCSV(String filePath) throws Exception {
		subwaySystem subSystem = new subwaySystem();
		MSTMetro mstSub = new MSTMetro();

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(filePath));
		} catch (IOException e) {
			System.out.println("Invalid File Path");
			e.printStackTrace();
		}

		String[] nextLine, stationAttr;
		Integer sIndex, sPrevDistance, sPrevTime, sPrevCost, sPrev;
		String sName, otherMetroLines, currentMetroLine;

		while ((nextLine = reader.readNext()) != null) {
			currentMetroLine = nextLine[0];
			sPrev = null;
			for (String val : nextLine) {
				if (val.contains(";")) { // Skip first line declaration
					stationAttr = val.split(";");
					sIndex = Integer.parseInt(stationAttr[0]);
					sName = stationAttr[1];
					sPrevDistance = Integer.parseInt(stationAttr[3]);
					sPrevTime = Integer.parseInt(stationAttr[4]);
					sPrevCost = Integer.parseInt(stationAttr[5]);
					otherMetroLines = stationAttr[2];
					subSystem.addLineToStation(currentMetroLine, sIndex);
					if (!subSystem.checkNodeExists(sIndex)) {
						subSystem.addNode(sIndex, sName, currentMetroLine);
					}

					if (otherMetroLines.length() > 0) {
						for (String l : otherMetroLines.split("&")) {
							// TONY Took OUT
							//subSystem.addLineToStation(l, sIndex);
						}
					}

					if (sPrev != null) {
						subSystem.connectStations(sIndex, sPrev, sPrevDistance,
								sPrevCost, sPrevTime);
					}
					sPrev = sIndex;
				}
			}
		}

		return subSystem;
		
		/*mstSub.setNodeSet(subSystem.getNodeSet());
		mstSub.setEdgeSet(subSystem.getEdgeSet());
		mstSub.setLineStationMediator(subSystem.getLineStationMediator());
		mstSub.kruskalAlgorithm();

		return mstSub;*/
	}
	
	public MSTMetro buildMSTMetro(subwaySystem subSystem) {
		MSTMetro mstSub = new MSTMetro();
		
		mstSub.setNodeSet(subSystem.getNodeSet());
		mstSub.setEdgeSet(subSystem.getEdgeSet());
		mstSub.setLineStationMediator(subSystem.getLineStationMediator());
		mstSub.kruskalAlgorithm();

		return mstSub;
	}
 
	public static void main(String args[]) {
		MetroBuilder subBuilder1 = new MetroBuilder();
		MSTMetro mstSub;
		subwaySystem subsys;
		Route r;
		int sIndexMax = 128;
		
		HashMap<MultiKey, Integer> map = new HashMap();
		MultiKey multiKey = new MultiKey(1333, 32);

		map.put(multiKey,4);
		map.remove(new MultiKey(1323,32));
		//System.out.println(map.get(new MultiKey(1333,32)));
		
		
		try {
			subsys = subBuilder1.buildSubwayFromLineCSV("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/metromap.csv");
			mstSub = subBuilder1.buildMSTMetro(subsys);
			//System.out.println(mstSub.getStationMediator().getNeighbourStations(128));
			mstSub.getStationMediator().printStationNeighbours();
			/*for (int i=1; i<=sIndexMax; i++) {
				for (int j=1; j<=sIndexMax; j++) {
					if (i != j) {
						System.out.println(i+":"+j);
						r = mstSub.getShortestPath(i, j);
					}
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}