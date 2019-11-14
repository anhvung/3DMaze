package maze;

import java.lang.Math;

public class CreateBox extends Box {
	public boolean[] walls;
	private CreateBox[] neighbours;
	public boolean visited;
	
	public CreateBox(int i, int j, int k) {
		super(i, j, k);
		neighbours = new CreateBox[6];
		visited = false;
		walls = new boolean[] {true, true, true, true, true, true};
	}
	public void deleteWall(String side) {
		walls[sideToInt(side)] = false;
	}
	public void setVisited() {
		visited = true;
	}
	
	private int sideToInt(String side) {
		if (side == "ABOVE") return 0;
		else if (side == "BELOW") return 1;
		else if (side == "UP") return 2;
		else if (side == "DOWN") return 3;
		else if (side == "RIGHT") return 4;
		else if (side == "LEFT") return 5;
		else return -1;
		
	}
	
	public int[] getIndex() {
		return new int[] {i, j ,k};
	}
	
	private int getNeighbourIndex(String dir, int length) {
		int[] move = movement(dir);
		int[] index = getIndex();
		int indexToReturn = 0;
		for (int x = 0; x < 3; x++) {
			int ind = index[x] + move [x];
			if (ind < 0 || ind >= length) return -1;
			indexToReturn = indexToReturn +  ind * (int) Math.pow(length, 2-x);
		}
		
		return indexToReturn;
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
	
	private int[] movement(String dir) {
		/*
		 * [
		 *  [[*,      *,*],
		 *   [*, above, *],
		 *   [*,      *,*]],
		 *  
		 *  [[*   ,     up,      *],
		 *   [left, current, right],
		 *   [*   ,    down,     *]],
		 *  
		 *  [[*,      *,*],
		 *   [*, below, *],
		 *   [*,      *,*]]
		 *  ]
		 */
		switch (dir) {
		case "ABOVE": return new int[] {-1, 0, 0};
		case "BELOW": return new int[] { 1, 0, 0};
		case "UP": return new int[]    { 0,-1, 0};
		case "DOWN": return new int[]  { 0, 1, 0};
		case "LEFT": return new int[]  { 0, 0,-1};
		case "RIGHT": return new int[] { 0, 0, 1};
		default: return null;
		}
	}
}
