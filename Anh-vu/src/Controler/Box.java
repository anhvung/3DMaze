package Controler;

import javax.swing.JButton;

public class Box extends JButton implements BoxInterface {
	private static final long serialVersionUID = 6471035674279403491L;
	protected int i, j, k;
	protected boolean[] walls;

	public Box(int i, int j, int k) {
		super();
		walls = new boolean[] { true, true, true, true, true, true };
		this.i = i;
		this.j = j;
		this.k = k;
	}

	public int getNeighbourIndex(String dir, int length) {
		int[] move = movement(dir);
		int[] index = getIndex();
		for (int x = 0; x < 3; x++) {
			int ind = index[x] + move[x];
			if (ind < 0 || ind >= length)
				return -1;
		}
		return (index[0] + move[0]) * length * length + (index[1] + move[1]) * length + (index[2] + move[2]);
	}

	public void deleteWall(String side) {
		walls[sideToInt(side)] = false;
	}

	protected int sideToInt(String side) {
		if (side == "ABOVE")
			return 0;
		else if (side == "BELOW")
			return 1;
		else if (side == "UP")
			return 2;
		else if (side == "DOWN")
			return 3;
		else if (side == "LEFT")
			return 4;
		else if (side == "RIGHT")
			return 5;
		else
			return -1;
	}

	public int[] getIndex() {
		return new int[] { i, j, k };
	}

	public boolean[] getWalls() {
		return walls;
	}

	public boolean getWall(String dir) {
		return walls[sideToInt(dir)];
	}

	protected int[] movement(String dir) {
		/*
		 * [ [[* , *, *], [* , above, *], [* , *, *]],
		 * 
		 * [[* , up, *], [left, current, right], [* , down, *]],
		 * 
		 * [[* , *, *], [* , below, *], [* , *, *]] ]
		 */
		switch (dir) {
		case "ABOVE":
			return new int[] { -1, 0, 0 };
		case "BELOW":
			return new int[] { 1, 0, 0 };
		case "UP":
			return new int[] { 0, -1, 0 };
		case "DOWN":
			return new int[] { 0, 1, 0 };
		case "LEFT":
			return new int[] { 0, 0, -1 };
		case "RIGHT":
			return new int[] { 0, 0, 1 };
		default:
			return null;
		}
	}

}
