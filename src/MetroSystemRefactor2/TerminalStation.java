package MetroSystemRefactor2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TerminalStation extends Station{
	
	private Calendar firstTrainTime, lastTrainTime; 	// Time String (HH:mm) of first/last train departure time from station.
	private int frequency;								// Train departure frequencies from station in minutes
	
	public TerminalStation(int stationIndex, String stationName, String startTime, String endTime, int frequency) {
		super(stationIndex, stationName);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		
		firstTrainTime = Calendar.getInstance();
		lastTrainTime = Calendar.getInstance();
		this.frequency = frequency;
		
		try {
			firstTrainTime.setTime(format.parse(startTime));
			lastTrainTime.setTime(format.parse(endTime));
		} catch (ParseException e) {
			System.err.println(startTime + " Or " + endTime + " formatting incorrect; must be in HH:mm format");
			e.printStackTrace();
		}
	}
	
	public int getDepartureFrequency() {
		return frequency;
	}
	
	public Calendar getFirstTrainTime() {
		return firstTrainTime;
	}
	
	public Calendar getLastTrainTime() {
		return lastTrainTime;
	}
	
	public static void main(String args[]) throws ParseException {
		TerminalStation ts = new TerminalStation(1, "Bro", "04:22", "23:21", 4);
	}
}
