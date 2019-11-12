package maze;

public class MBox {
	protected int i,j;
	protected int w;
	protected MBox[] neighbours;
	
	public MBox(int i, int j, int w, MBox[] neighbours) {
		super();
		this.i = i;
		this.j = j;
		this.w = w;
		this.neighbours = neighbours;
	}
}
