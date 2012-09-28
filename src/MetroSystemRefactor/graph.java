package MetroSystemRefactor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jgraph.graph.Edge;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

abstract class graph<N, E> {
	protected Map<Integer, N> nodes;
	protected List<E> edges;

	public void addEdge(E edge) {
		edges.add(edge);
	}

	public void setNodeSet(Map<Integer, N> nodeSet) {
		nodes = nodeSet;
	}

	public void setEdgeSet(List<E> edgeSet) {
		edges = edgeSet;
	}

	public Map<Integer, N> getNodeSet() {
		return nodes;
	}

	public List<E> getEdgeSet() {
		return edges;
	}

	public boolean checkNodeExists(Integer nIndex) {
		return nodes.containsKey(nIndex);
	}
	
	
	public static void main(String args[]) {
		
		EdgeFactory<station, Object> ef = new EdgeFactory<station, Object>() {
			public Object createEdge(station arg0, station arg1) {
				return new Object();
			}
		};
		
		
		
		WeightedGraph<station, Object> graph = new SimpleWeightedGraph<>(ef);
		station s1 = new station(1, "Hanzomon");
		station s2 = new station(2, "Ginza");
		station s3 = new station(3, "Hibiya");
		station s4 = new station(4, "Yurakcho");
		
		graph.addVertex(s1);
		graph.addVertex(s2);
		graph.addVertex(s3);
		graph.addVertex(s4);
		
		railway r = new railway(2,2,2);
		railway r2 = new railway(1,1,1);
		railway r3 = new railway(1,1,1);
		railway r4 = new railway(1,1,1);
		
		graph.addEdge(s1, s2, r);
		graph.addEdge(s2, s3, r2);
		graph.addEdge(s3, s4, r3);
		graph.addEdge(s2, s4, r4);
		
		DefaultWeightedEdge e1 = new DefaultWeightedEdge();
		DefaultWeightedEdge e2 = new DefaultWeightedEdge();
		DefaultWeightedEdge e3 = new DefaultWeightedEdge();
		DefaultWeightedEdge e4 = new DefaultWeightedEdge();
		
		graph.setEdgeWeight(e1, 3.3);
		graph.setEdgeWeight(e2, 1);
		graph.setEdgeWeight(e3, 1);
		graph.setEdgeWeight(e4, 1);
		
		DijkstraShortestPath dk = new DijkstraShortestPath(graph, s1, s3);
		GraphPath<station, railway> path = dk.getPath();
		
		
	}
}
