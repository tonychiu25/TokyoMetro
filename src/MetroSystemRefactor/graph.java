package MetroSystemRefactor;
import java.util.List;
import java.util.Map;

abstract class graph<N, E> {
        protected stationsMediator stationMediator;
	protected Map<Integer, N> nodes;
	protected List<E> edges;
	
	public abstract void addNode(Integer index);
	
	public void addEdge(E edge) {
		edges.add(edge);
	}
	
	public void setNodeSet(Map<Integer, N> nodeSet) {
		nodes = nodeSet;
	}
	
	public void setEdgeSet(List<E> edgeSet) {
		edges = edgeSet;
	}
        
        public void setStationMediator(stationsMediator sm) {
            stationMediator = sm;
        }
        
        public Map<Integer, N> getNodeSet() {
            return nodes;
        }
        
        public List<E> getEdgeSet() {
            return edges;
        }
        
        public stationsMediator getStationMediator() {
            return this.stationMediator;
        }
	
	public boolean checkNodeExists(Integer nIndex) {
		return nodes.containsKey(nIndex);
	}
	
}
