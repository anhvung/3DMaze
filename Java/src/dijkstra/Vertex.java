package dijkstra;

public class Vertex implements VertexInterface{
	private int index;
	
	public int getIndex() {
		return index;
	}

	public Vertex(int index) {
		super();
		this.index = index;
	}
}
