package dijkstra;

import java.util.ArrayList;

public class Dijkstra {

	public void dijkstra(GraphInterface g, VertexInterface r, ASetInterface a, PiInterface pi,
			PreviousInterface previous) {
		for (VertexInterface v : g.getVertexList()) {
			pi.setValue(v, Integer.MAX_VALUE);
		}
		pi.setValue(r, 0);
		for (int k = 1; k < g.getLength(); k++) {
			a.add(r);
			for (VertexInterface v : g.getNeighbours(r)) {
				if (!a.isInList(v)) {
					int d = pi.getValue(r) + g.getDistance(r, v);
					if (d < pi.getValue(v)) {
						pi.setValue(v, d);
						previous.setValue(v, r);
					}
				}
			}
			// choosing next pivot
			ArrayList<VertexInterface> temp = new ArrayList<>();
			for (VertexInterface v : g.getVertexList()) {
				if (!a.isInList(v))
					temp.add(v);
			}
			r = findMin(temp, pi);
		}
	}

	private VertexInterface findMin(ArrayList<VertexInterface> elements, PiInterface values) {
		// only with positive integers in values
		VertexInterface minElement = elements.get(0);
		for (VertexInterface e : elements) {
			if (values.getValue(e) < values.getValue(minElement))
				minElement = e;
		}
		return minElement;
	}
}
