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
import MetroSystemRefactor.subwaySystem;

public class TestMetroBuilder {

	private Integer maxStationIndex = 76;
	private subwaySystem subSystem;
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
			subSystem = subwayBuilder.buildSubwayFromLineCSV("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/Book2.csv");
			mstSub = subwayBuilder.buildMSTMetro(subSystem);
			reader = new CSVReader(new FileReader("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/Book2.csv"));
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
	// Test all stations are properly connected
	public void testStationNeighborCorrect() throws IOException {
		HashSet<Integer> neighbours;
		Integer prevStationIndex = null;
		boolean skipFirst = true;
		
		while ((nextLine = reader.readNext()) != null) {
			skipFirst = true;
			for (String val : nextLine) {
				if(!val.contains(";")) {
					continue;
				}
				
				stationAttr = val.split(";");
				sIndex = Integer.parseInt(stationAttr[0]);
				// First first station iteration.
				if (skipFirst) {
					prevStationIndex = sIndex;
					skipFirst = false;
				} else {
					neighbours = subSystem.getNeighbours(sIndex);
					assertTrue(neighbours.contains(prevStationIndex));
					prevStationIndex = sIndex;
				}
			}
		}
	}
	
	
}