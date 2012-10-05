package MetroSystemRefactor2;

import org.jgrapht.EdgeFactory;

public class RailFactory implements EdgeFactory<Integer, Connection> {
	@Override
	public Connection createEdge(Integer station1Index, Integer station2Index) {
		return null;
	}
}
