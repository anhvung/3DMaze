package dijkstra;

import java.util.ArrayList;

public interface PreviousInterface {
	public void setValue(VertexInterface father, VertexInterface son);
	public VertexInterface getValue(VertexInterface v);
	public ArrayList<VertexInterface> getShortestPathTo(VertexInterface v);
}
