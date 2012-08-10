package MetroSystemRefactor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        
        public ArrayList<N> getPath() {
            return null;
        }
}
