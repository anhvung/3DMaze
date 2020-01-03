package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import View.MiniMap;
import View.Navigation;
import affichage3d.Display3d;
import dijkstra.VertexInterface;
import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Controler.Box;
import Controler.Maze;

//CLASSE DU JOUEUR RELIANT LES AUTRES 
public class Player extends JFrame {
	private static int[] position = new int[3];
	private static String direction = "north";
	private static String prev = "north";
	private static final long serialVersionUID = 1L;
	private static Box[] grid;
	private static int playerIndex;
	private static int length;

	public Player(MiniMap miniMap, int length, boolean auto, ArrayList<VertexInterface> path) {
		Player.length = length;
		initializeFrame(miniMap, auto, path);
	}

	private void initializeFrame(MiniMap miniMap, boolean auto, ArrayList<VertexInterface> path) {
		this.setTitle("3D MAZE WHOUAAAOUUUU!!!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		affichage3d.Display3d.display(true);
		Canvas3D pane1 = affichage3d.Display3d.maze3d;
		if (Maze.getMaze().getLength() == 10 && auto) {
			affichage3d.Display3d.setSpeed(100);
		}
		JPanel pane2 = miniMap;
		pane2.setPreferredSize(new Dimension(500, 500));
		this.add(pane1, BorderLayout.CENTER);
		View.Navigation nav = new View.Navigation(auto, path);
		// if (!auto)
		if (!auto)
			this.add(nav, BorderLayout.WEST);
		else
			this.add(nav, BorderLayout.SOUTH);
		if (!auto)
			this.add(pane2, BorderLayout.EAST);
		if (!auto)
			this.setPreferredSize(new Dimension(1500, 650));
		else
			this.setPreferredSize(new Dimension(600, 660));
		this.pack();
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		grid = Maze.getMaze().getGrid();
		playerIndex = Maze.getMaze().startIndex;
		updatePosition(playerIndex);
		updateNav(direction, grid[playerIndex]);

	}
	// Mise à jour des coordonnées du joueur

	private static void updateNav(String dir, Box box) {
		// Maj de la position et des boutons
		updatePosition(playerIndex);
		Navigation.update(dir, box);
	}

	private static void updatePosition(int index) {
		// Maj de la position
		position = grid[index].getIndex();

	}

	public static void updateTurnUp() {
		// Maj de la direction
		if (direction == "down") {
			direction = prev;
			prev = "down";
			updateNav(direction, grid[playerIndex]);

		} else if (direction != "up") {
			prev = direction;
			direction = "up";
			updateNav(direction, grid[playerIndex]);

		}

	}

	public static void updateTurnDown() {
		// Maj de la direction
		if (direction == "up") {
			direction = prev;
			prev = "up";
			updateNav(direction, grid[playerIndex]);
		} else if (direction != "down") {
			prev = direction;
			direction = "down";
			updateNav(direction, grid[playerIndex]);
		}

	}

	public static void updateTurnLeft() {
		// Maj de la direction
		prev = direction;
		direction = getLeft(direction);
		updateNav(direction, grid[playerIndex]);

	}

	public static void updateTurnRight() {
		// Maj de la direction
		prev = direction;
		direction = getLeft(getLeft(getLeft(direction)));
		updateNav(direction, grid[playerIndex]);

	}

	public static void updateGo() {
		// Maj de la direction et de l'index de la case sur lequel est le joueur
		playerIndex = grid[playerIndex].getNeighbourIndex(translate(direction), length);
		updateNav(direction, grid[playerIndex]);

	}

	private static String translate(String dir) {
		// Traduction de la direction entre classes
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
		// Donne la direction de gauche
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

	public static int getIndex() {
		return playerIndex;
	}

	public static void goTo(int[] target) {
		if (target[0] < position[0]) {
			updateTurnUp();
			Display3d.maze3d.turnUp();
			updateTurnUp();
			Display3d.maze3d.turnUp();
		} else if (target[0] > position[0]) {
			updateTurnDown();
			Display3d.maze3d.turnDown();
			updateTurnDown();
			Display3d.maze3d.turnDown();
		} else {
			if (direction == "up") {
				updateTurnDown();
				Display3d.maze3d.turnDown();
			} else if (direction == "down") {
				updateTurnUp();
				Display3d.maze3d.turnUp();
			}
			String temp = direction;
			String targetDir = "init";
			int turns = 0;
			switch (10 * (target[1] - position[1]) + target[2] - position[2]) {
			case (-10):
				targetDir = "north";
				break;
			case (10):
				targetDir = "south";
				break;
			case (-1):
				targetDir = "west";
				break;
			case (1):
				targetDir = "east";
				break;
			}
			while (temp != targetDir && targetDir != "init") {
				temp = getLeft(temp);
				turns++;

			}

			if (turns == 3) {
				updateTurnRight();
				Display3d.maze3d.turnRight();
			} else {
				for (int i = 0; i < turns; i++) {
					updateTurnLeft();
					Display3d.maze3d.turnLeft();

				}
			}
		}

		updateGo();
		Display3d.maze3d.goForward();
		Navigation.goNext();
	}
}