/**
 * @author chiu.sintung
 * 
 * Test to verify that the subway map was generated correctly from the map
 */
package Test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;

import au.com.bytecode.opencsv.CSVReader;

import MetroSystemRefactor.MSTMetro;
import MetroSystemRefactor.MetroBuilder;
import MetroSystemRefactor.stationsMediator;

public class TestMetroBuilder {

	@Test
	// Test that all subway staions were successfully added to the stationsMediator
	public void testAllStationsAdded() throws IOException {
		MetroBuilder subwayBuilder = new MetroBuilder();
		MSTMetro mstSub = null;
		stationsMediator subMediator = null;
		CSVReader reader = null;
		Integer sIndex;
		String[] nextLine, stationAttr;
		
		try {
			mstSub = subwayBuilder.buildSubwayFromLineCSV("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/Book2.csv");
			reader = new CSVReader(new FileReader("C:/Users/chiu.sintung/workspace/TokyoMetro/SubwayMaps/Book2.csv"));
			subMediator = mstSub.getStationMediator();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	
	

}
