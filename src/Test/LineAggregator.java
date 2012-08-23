package Test;

import java.util.HashMap;

public class LineAggregator {
	
	private HashMap<String, LineList> lineListCollect;
	
	public LineAggregator(){
		lineListCollect = new HashMap<String, LineList>();
	}
	
	public void addStationToLine(String l, Integer sIndex) {
		LineList ll;
		if (!lineListCollect.containsKey(l)) {
			ll = new LineList(l);
			ll.addNewStation(sIndex);
			lineListCollect.put(l, ll);
		} else {
			ll = lineListCollect.get(l);
			ll.addNewStation(sIndex);
			lineListCollect.put(l, ll);
		}
	}
	
	public Integer getLeftStationIndex(String l, Integer sIndex) {
		return lineListCollect.get(l).getPrevStationIndex(sIndex);
	}
	
	public Integer getRightStationIndex(String l, Integer sIndex){
		return lineListCollect.get(l).getNextStationIndex(sIndex);
	}
}
