package MetroSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
	
	static class railComparitor implements Comparator {

		private String compareType;
		
		public railComparitor(String compareType) {
			this.compareType = compareType;
		}
		
		public int compare(Object arg0, Object arg1) {
			railway r1 = (railway) arg0;
			railway r2 = (railway) arg1;
			int diff = 0;

			if (compareType == "d") {
				diff = r1.getlength() - r2.getlength();
			} else if (compareType == "t") {
				diff = r1.getTime() - r2.getTime();
			} else if (compareType == "c") {
				diff = r1.getCost() - r2.getCost();
			}
			
			if (diff < 0) {
				return -1;
			} else if (diff == 0) {
				return 0;
			} else {
				return 1;
			}
		}
	}
	
	public static void main(String args[]) {
		List<railway> rails = new ArrayList<railway>();
		rails.add(new railway(3,1,1,new station(1), new station(2)));
		rails.add(new railway(2,1,1,new station(3), new station(4)));
		rails.add(new railway(1,1,1,new station(5), new station(6)));
		rails.add(new railway(1,1,1,new station(7), new station(8)));
		Collections.sort(rails, new railComparitor("d"));
		for (railway r:rails) {
			System.out.println(r.getlength());
		}
	}
}
