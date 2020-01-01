package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import View.MiniMap;
import View.Navigation;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controler.Box;
import Controler.Maze;

public class Player extends JFrame { 
	private static int[] position = new int[3];
	private static String direction = "north";
	private static String prev = "north";
	private static final long serialVersionUID = 1L;
	private static Box[] grid;
	private static int playerIndex;
	private static int length;

	public Player(MiniMap miniMap, int length) {
		Player.length = length;
		initializeFrame(miniMap);
	}

	private void initializeFrame(MiniMap miniMap) {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		affichage3d.Display3d.display(true);
		Canvas3D pane1 = affichage3d.Display3d.maze3d;
		JPanel pane2 = miniMap;
		pane2.setPreferredSize(new Dimension(500, 500));
		this.add(pane1, BorderLayout.WEST);
		this.add(new View.Navigation(), BorderLayout.CENTER);
		this.add(pane2, BorderLayout.EAST);
		this.setSize(1200, 1200);
		this.pack();
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		grid = Maze.getMaze().getGrid();
		playerIndex = Maze.getMaze().startIndex;
		updatePosition(playerIndex);
		updateNav(direction, grid[playerIndex]);

	}

	private static void updateNav(String dir, Box box) {
		updatePosition(playerIndex);
		Navigation.update(dir, box);

	}

	private static void updatePosition(int index) {
		position = grid[index].getIndex();

	}

	public static void updateTurnUp() {
		if (direction == "down") {
			direction = prev;
			prev = "down";
		} else {
			prev = direction;
			direction = "up";
		}
		updateNav(direction, grid[playerIndex]);

	}

	public static void updateTurnDown() {
		if (direction == "up") {
			direction = prev;
			prev = "up";
		} else {
			prev = direction;
			direction = "down";
		}
		updateNav(direction, grid[playerIndex]);

	}

	public static void updateTurnLeft() {
		prev = direction;
		direction = getLeft(direction);
		updateNav(direction, grid[playerIndex]);

	}

	public static void updateTurnRight() {
		prev = direction;
		direction = getLeft(getLeft(getLeft(direction)));
		updateNav(direction, grid[playerIndex]);

	}

	public static void updateGo() {
		playerIndex = grid[playerIndex].getNeighbourIndex(translate(direction), length);
		updateNav(direction, grid[playerIndex]);

	}

	private static String translate(String dir) {
		switch (dir) {
		case "north":
			return "UP";
		case "west":
			return "LEFT";
		case "south":
			return "DOWN";
		case "east":
			return "RIGHT";
		case "up":
			return "ABOVE";
		case "down":
			return "BELOW";
		}

		return null;
	}

	private static String getLeft(String dir) {
		switch (dir) {
		case "north":
			return "west";
		case "west":
			return "south";
		case "south":
			return "east";
		case "east":
			return "north";

		}
		return null;

	}
}