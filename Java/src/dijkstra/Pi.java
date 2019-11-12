package dijkstra;


public class Pi implements PiInterface {
	private int[] pi;
	
	public Pi(int length) {
		pi = new int[length];
	}
	@Override
	public void setValue(VertexInterface v, int value) {
		for (int i = 0; i < pi.length; i++) {
			if (v.getIndex() == i) pi[i] = value;
		}
		
	}

	@Override
	public int getValue(VertexInterface v) {
		for (int i = 0; i < pi.length; i++) {
			if (v.getIndex() == i) return pi[i];
		}
		return 0;
	}

}
