package MetroSystemRefactor2;

public class Railway {
	
	private int length, time, cost;
	
	public Railway(int length, int time, int cost) {
		this.length = length;
		this.time = time;
		this.cost = cost;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getTime() {
		return time;
	}
}
