package MetroSystem;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class subwayBuilder {
	
    public subwayBuilder() {
	
	}
	
    public static void main(String args[]) throws Exception {
        CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("C:/Users/chiu.sintung/Desktop/Book1.csv"));
        } catch (IOException e) {
			System.out.println("Invalid File Path");
			e.printStackTrace();
		}
		
        String [] nextLine = null;
        String [] edgeAttr = null;
		Integer sIndex = 1;
        while (reader.readNext() != null) {
			station s = new station(sIndex);
            // nextLine[] is an array of values from the line
			try {
				nextLine = reader.readNext();
			} catch (IOException e) {
				System.out.println("SomeThing bad happened during the read");
				e.printStackTrace();
			}
			
			for(String val:nextLine) {
				if (val.contains(";")) {
					edgeAttr = val.split(";");
					if (edgeAttr.length == 3) {
						
					} else {
						throw new Exception("An edge must be define with three values separated by a delimiter ;");
					}
				}
			}
            /*for (String val:nextLine) {
                if(val.contains(";")) {
                    edgeAttr = val.split(";");
                    
                }
            }*/
			sIndex++;s
        }
		
   }
}
