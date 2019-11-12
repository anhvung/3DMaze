package dijkstra;

import java.util.ArrayList;
import maze.Maze;

public class Graph implements GraphInterface {
	private int[][] matrix;
	public int length;
	private Vertex[] VertexList;
	
	public Graph(Maze maze) {
		length = maze.getLength() * maze.getLength();
		matrix = new int[length][length];
		VertexList = new Vertex[length];
		for (int i = 0; i < maze.getLength(); i++) {
			for (int j = 0; j < maze.getLength(); j++) {
				VertexList[maze.index(i,j)] = new Vertex(maze.index(i,j));
				if (maze.isAccessible(i, j)) {
					if (1<=i && maze.isAccessible(i - 1, j)) {
						matrix[maze.index(i,j)][maze.index(i-1,j)] = 1;
					}
					if (i<=maze.getLength()-2 && maze.isAccessible(i + 1, j)) {
						matrix[maze.index(i,j)][maze.index(i+1,j)] = 1;
					}
					if (1<=j && maze.isAccessible(i, j - 1)) {
						matrix[maze.index(i,j)][maze.index(i,j-1)] = 1;
					}
					if (j<=maze.getLength()-2 && maze.isAccessible(i, j + 1)) {
						matrix[maze.index(i,j)][maze.index(i,j+1)] = 1;
					}
					
				}
			}
		}
	}

	
	private VertexInterface getVertex(int index) {
		for (VertexInterface V : VertexList) {
			if (V.getIndex() == index) return V;
		}
		return null;
	}
	
	public ArrayList<VertexInterface> getNeighbours(VertexInterface V){
		ArrayList<VertexInterface> neighbours = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			if (matrix[V.getIndex()][i] != 0) neighbours.add(getVertex(i));
		}
		return neighbours;
	}
	
	public VertexInterface[] getVertexList() {
		return VertexList;
	}
	
	public int getDistance(VertexInterface VertexOrigin, VertexInterface VertexArrival) {
		return matrix[VertexOrigin.getIndex()][VertexArrival.getIndex()];
	}
	
	public int getLength() {
		return length;
	}
	
	public int[][] getMatrix(){
		return matrix;
	}
}
