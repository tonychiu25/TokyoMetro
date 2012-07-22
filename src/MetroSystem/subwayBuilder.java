package MetroSystem;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class subwayBuilder {
	
	private subwaySystem subway;
	private subwaySystem MSTSubwayDistance;
	private subwaySystem MSTSubwayTime;
	private subwaySystem MSTSubwayCost;
	private MSTMetro MSTGraph;
	
    public subwayBuilder() {
    	subway = new subwaySystem(0);
    }
    
    public subwaySystem getDistanceMSTMetro() {
    	return MSTSubwayDistance;
    }
    
    public subwaySystem getTimeMSTMetro() {
    	return MSTSubwayTime;
    }
    
    public subwaySystem getCostMSTMetro() {
    	return MSTSubwayCost;
    }
   
    // A horribily written function littered with code smells.
    public void buildSubwayFromCSV(String filepath) throws Exception {
        CSVReader reader = null;
        CSVReader reader2 = null;
		try {
			reader = new CSVReader(new FileReader(filepath));
			reader2 = new CSVReader(new FileReader(filepath));
        } catch (IOException e) {
			System.out.println("Invalid File Path");
			e.printStackTrace();
		}
		
		Integer stationIndex = 1;
		while(reader2.readNext() != null) {
			subway.addStation(stationIndex);
			stationIndex++;
		}
		
        String [] nextLine = null;
        String [] edgeAttr = null;
		Integer sIndex = 1;
        while ((nextLine = reader.readNext()) != null) {
        	Integer connectStationIndex = 1;
            // nextLine[] is an array of values from the line
			for(String val:nextLine) {
				val = val.replace("(", "");
				val = val.replace(")", "");
				if (val.contains(";")) {
					edgeAttr = val.split(";");
					if (edgeAttr.length == 3) {
						Integer distance = Integer.parseInt(edgeAttr[0]);
						Integer cost = Integer.parseInt(edgeAttr[1]);
						Integer time = Integer.parseInt(edgeAttr[2]);
						subway.connectStations(sIndex, connectStationIndex, distance, cost, time);
					} else {
						throw new Exception("An edge must be define with three values separated by a delimiter ;");
					}
				}
				connectStationIndex++;
			}
			sIndex++;
        }
        MSTGraph = new MSTMetro(subway, "d");
        MSTSubwayDistance = MSTGraph.kruskalAlgorithm();
        MSTGraph = new MSTMetro(subway, "t");
        MSTSubwayTime = MSTGraph.kruskalAlgorithm();
        MSTGraph = new MSTMetro(subway, "c");
        MSTSubwayCost = MSTGraph.kruskalAlgorithm();
    }
    
    public GenericSubway getSubway() {
    	return subway;
    }
    
    public static void main(String args[]) {
    	subwayBuilder subBuilder = new subwayBuilder();
    	subwaySystem sub = null;
    	try {
			subBuilder.buildSubwayFromCSV("C:/Users/tonychiu/workspace/TokyoMetro/SubwayMaps/Book1.csv");
			subwaySystem MSTDistance = subBuilder.getDistanceMSTMetro();
			//System.out.println(MSTDistance.getRails().size());
			ArrayList<railway> path = MSTDistance.findShortestPath(1, 3);
			//System.out.println(path.size());
			//subBuilder.getSubway().printSubway();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
