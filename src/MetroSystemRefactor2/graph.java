package MetroSystemRefactor2;

import java.util.List;
import java.util.Map;
import org.jgrapht.graph.AsUndirectedGraph;

public class graph<N, E> {
	protected Map<Integer, N> nodes;
	protected List<E> edges;

	public void addEdge(E edge) {
		edges.add(edge);
	}
	
	public void addNode(Integer nid ,N node) {
		nodes.put(nid, node);
	}

	public boolean checkNodeExists(Integer nIndex) {
		return nodes.containsKey(nIndex);
	}
	
	public boolean checkCycle() {
		
		return true;
	}
	
	public static void main(String args[]) {
		
	}
}
