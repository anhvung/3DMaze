package dijkstra;

import java.util.ArrayList;

import Controler.Maze;

public class Graph implements GraphInterface {
	private int[][] matrix;
	public int length;
	private Vertex[] VertexList;
	
	public Graph(Maze maze) {
		length = (int) Math.pow(maze.getLength(), 3);
		System.out.println(length);
		matrix = new int[length][length];
		VertexList = new Vertex[length];
		for (int depth = 0; depth < maze.getLength(); depth++) {
			for (int row = 0; row < maze.getLength(); row++) {
				for (int column = 0; column < maze.getLength(); column++) {
					int i = maze.index(depth, row, column);
					VertexList[i] = new Vertex(i);
					ArrayList<Integer> neighboursIndex = maze.getNeighbours(i);
					for (int j : neighboursIndex) {
						if (j>=0) matrix[i][j] = 1;
						
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
