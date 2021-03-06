package Model;

import Controler.Box;

//CASE DANS LA GENERATION ALEATOIRE DU LABY
public class CreateBox extends Box {
	static final long serialVersionUID = -448289361019749594L;
	private CreateBox[] neighbours;
	public boolean visited;

	public CreateBox(int i, int j, int k) {
		super(i, j, k);
		neighbours = new CreateBox[6];
		visited = false;
	}

	public void setVisited() {
		visited = true;
	}

	public void setNeighbours(int length, String[] directions, CreateBox[] grid) {
		for (String dir : directions) {
			int spot = getNeighbourIndex(dir, length);
			if (spot == -1) {
				neighbours[sideToInt(dir)] = null;
			} else {
				neighbours[sideToInt(dir)] = grid[spot];
			}

		}
	}

	public CreateBox getNeighbour(String side) {
		return neighbours[sideToInt(side)];
	}

}
