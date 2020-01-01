package dijkstra;

import java.util.ArrayList;

public interface GraphInterface {
	public VertexInterface[] getVertexList();

	public ArrayList<VertexInterface> getNeighbours(VertexInterface V);

	public int getDistance(VertexInterface V1, VertexInterface V2);

	public int getLength();

	public int[][] getMatrix();
	// public ArrayList<VertexInterface> getVertexTas();
	// public VertexInterface pullVertex(VertexInterface V);

}
