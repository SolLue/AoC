package utility.PathFinding;

import java.util.List;
import java.util.Map;

public interface Graph<T> {

	public List<T> getAdjacent(T t);
	public Map<T, List<T>> getEdges();
	public boolean addEdge(T c, T t, int i);
	public boolean addNode(T t);
}
