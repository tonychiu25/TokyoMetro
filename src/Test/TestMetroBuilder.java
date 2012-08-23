/**
 * @author chiu.sintung
 * 
 * Test to verify that the subway map was generated correctly from the map
 */
package Test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.jws.soap.InitParam;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;

import au.com.bytecode.opencsv.CSVReader;

import MetroSystemRefactor.LineStationMediator;
import MetroSystemRefactor.MSTMetro;
import MetroSystemRefactor.MetroBuilder;
import MetroSystemRefactor.stationsMediator;

public class TestMetroBuilder {

	private Integer maxStationIndex = 76;
	
	private MetroBuilder subwayBuilder;
	private MSTMetro mstSub;
	private stationsMediator subMediator;
	private LineStationMediator lsMed;
	private CSVReader reader;
	private Integer sIndex;
	private String[] nextLine, stationAttr;
	private LineAggregator lineAggr;
	
	@Before
	public void setUp() {
		subwayBuilder = new MetroBuilder();
		lineAggr = new LineAggregator();
		
		try {
			mstSub = subwayBuilder.buildSubwayFromLineCSV("C:/Users/tonychiu/workspace/TokyoMetro/SubwayMaps/Book2.csv");
			reader = new CSVReader(new FileReader("C:/Users/tonychiu/workspace/TokyoMetro/SubwayMaps/Book2.csv"));
			subMediator = mstSub.getStationMediator();
			lsMed = mstSub.getLineStationMediator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tareDown() throws IOException {
		reader.close();
	}
	
	@Test
	// Test that all subway staions were successfully added to the stationsMediator
	public void testAllStationsAdded() throws IOException {
		/**
		 * 1.  Check node is added to the subway map
		 * 2.  Check node is added to the subwayStaionMediator
		 */
		while ((nextLine = reader.readNext()) != null) {
			for (String val : nextLine) {
				if(val.length() <= 1) {
					continue;
				}
				stationAttr = val.split(";");
				sIndex = Integer.parseInt(stationAttr[0]);
				
				assertTrue(mstSub.checkNodeExists(sIndex));	// Test station added to map
				assertTrue(subMediator.getStationsMediator().containsKey(sIndex));	// Test station was added to stations mediator
			}
		}
	}
	
	@Test
	// Test the line station mediator was setup correctly
	public void testLineStationCorrect() throws IOException {
		String currline = null;
		String[] lines = null;
		HashSet<String> lineSet;
		while ((nextLine = reader.readNext()) != null) {
			for (String val : nextLine) {
				if(val.length() <= 1) {
					currline = val;
					continue;
				}
				stationAttr = val.split(";");
				sIndex = Integer.parseInt(stationAttr[0]);
				lineSet = lsMed.getLineFromStationIndex(sIndex);
				
				lines = stationAttr[2].split("&");
				
				assertTrue(lineSet.contains(currline));
				for (String l:lines) {
					if (l.length() != 0) {
						assertTrue(lineSet.contains(l));
					}
				}
			}
		}
	}
	
	@Test
	// Test station mediator was correctly setup; ie all stations have the correct neighbors attached
	// TODO: write stationMediator added correctly test.
	public void testStationNeighborCorrect() throws IOException {
		
	}
	
	

}
