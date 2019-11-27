package dijkstra;

import java.util.ArrayList;

public class Previous implements PreviousInterface {
	private VertexInterface[] P;
	
	public Previous(int length) {
		P = new VertexInterface[length];
	}
	
	@Override
	public void setValue(VertexInterface father, VertexInterface son) {
		for (int i = 0; i < P.length; i++) {
			if (father.getIndex() == i) P[i] = son;
		}
		
	}

	@Override
	public VertexInterface getValue(VertexInterface v) {
		for (int i = 0; i < P.length; i++) {
			if (v != null && v.getIndex() == i) return P[i];
		}
		return null;
	}

	@Override
	public ArrayList<VertexInterface> getShortestPathTo(VertexInterface v) {
		ArrayList<VertexInterface> path = new ArrayList<VertexInterface>();
		while (v != null) {
			path.add(v);
			v = getValue(v);
		}
		return path;
	}

}
