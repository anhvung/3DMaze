package maze;

public class Box implements BoxInterface {
	protected int i, j, k;
	protected Box[] neighbours;
	
	public Box(int i, int j, int k) {
		super();
		this.i = i;
		this.j = j;
		this.k = k;
	}

	@Override
	public int[] getIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean[] getWalls() {
		// TODO Auto-generated method stub
		return null;
	}
}
