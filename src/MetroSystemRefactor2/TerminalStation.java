package MetroSystemRefactor2;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TerminalStation extends Station{
	private Calendar firstTrain, lastTrain;
	
	public TerminalStation(int sIndex, String sName, String timeFirstTrain, String timeLastTrain) {
		super (sIndex, sName);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date firstTrainDateObj = null;
		Date lastTrainDateObj = null;
		
		try {
			lastTrainDateObj = df.parse(timeLastTrain);
			firstTrainDateObj = df.parse(timeFirstTrain);
		} catch (ParseException e) {
			System.err.println("Date Format must be in HH:mm format : "+ timeFirstTrain + " or " + timeLastTrain + " is not the correct format");
			e.printStackTrace();
		}
		
		firstTrain = Calendar.getInstance();
		lastTrain = Calendar.getInstance();
		
		firstTrain.setTime(firstTrainDateObj);
		lastTrain.setTime(lastTrainDateObj);

		
		firstTrain.set(Calendar.HOUR, firstTrain.get(Calendar.HOUR)+1);
	}
	
	public Calendar getFirstTrainTime() {
		return firstTrain;
	}
	
	public Calendar getLastTrainTime() {
		return lastTrain;
	}
	
	public static void main(String args[]) throws ParseException {
		TerminalStation tm = new TerminalStation(1, "D", "05:33", "21:33");
	}
}
