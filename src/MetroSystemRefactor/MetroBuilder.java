package MetroSystemRefactor;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetroBuilder {
    
    // A horribily written function littered with code smells.
    public MSTMetro buildSubwayFromCSV(String filepath) throws Exception {
        
        subwaySystem subSystem = new subwaySystem();
        
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
            subSystem.addNode(stationIndex);
            stationIndex++;
        }
		
        String [] nextLine;
        String [] edgeAttr;
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
						subSystem.connectStations(sIndex, connectStationIndex, distance, cost, time);
					} else {
						throw new Exception("An edge must be define with three values separated by a delimiter ;");
					}
				}
				connectStationIndex++;
			}
			sIndex++;
        }
        
        MSTMetro mstSub = new MSTMetro();
        mstSub.setEdgeSet(subSystem.getEdgeSet());
        mstSub.setNodeSet(subSystem.getNodeSet());
        mstSub.kruskalAlgorithm();
        
        return mstSub;
    }

    public MSTMetro buildSubwayFromMatrix(int[][] mapMatrix) {
        subwaySystem subSystem = new subwaySystem();
        MSTMetro mstMetro = new MSTMetro();
        
        for (int i=0; i<mapMatrix[0].length; i++) {
            for (int j=0; j<i; j++) {
                if (!subSystem.checkNodeExists(j+1)) {
                    subSystem.addNode(j+1);
                }
                
                if (mapMatrix[i][j] > 0) {
                    try {
                        subSystem.connectStations(i+1, j+1, mapMatrix[i][j], 1, 1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        
        mstMetro.setNodeSet(null);
        
        return null;
    }
    
	public static void main(String args[]) {
            MetroBuilder subBuilder = new MetroBuilder();
            MSTMetro mstSub = null;
            try {
                mstSub = subBuilder.buildSubwayFromCSV("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/Book1.csv");
                mstSub.getStationMediator().printStationNeighbours();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            /*subwaySystem subway = new subwaySystem();
            subway.addNode(1);
            subway.addNode(2);
            subway.addNode(3);
            subway.addNode(4);
            try {
                subway.connectStations(1, 2, 1, 1, 1);
                subway.connectStations(2, 3, 1, 2, 3);
                subway.connectStations(3, 4, 12, 1, 1);
                subway.connectStations(4, 1, 1, 1, 1);
                subway.connectStations(1, 3, 1 ,1, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
                
            MSTMetro mst = new MSTMetro();
            mst.setEdgeSet(subway.getEdgeSet());
            mst.setNodeSet(subway.getNodeSet());
            //mst.setStationMediator(subway.getStationMediator());
            mst.kruskalAlgorithm();
            mst.getStationMediator().printStationNeighbours();
            LinkedList<Integer> path = mst.getShortestPath(1, 4);
            System.out.println(path);*/
        }
}
