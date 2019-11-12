package dijkstra;

import java.util.ArrayList;

public class ASet implements ASetInterface {
	private ArrayList<VertexInterface> set;
	
	public ASet() {
		set = new ArrayList<VertexInterface>();
	}
	
	public void add(VertexInterface other) {
		set.add(other);
	}

	public boolean isInList(VertexInterface V) {
		for (VertexInterface v : set) {
			if (v.getIndex() == V.getIndex()) {
				return true;
			}
		}
		return false;
	}

}
