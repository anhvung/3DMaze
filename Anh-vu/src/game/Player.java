package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import View.MiniMap;
import View.Navigation;
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
		JPanel pane2 = miniMap;
		pane2.setPreferredSize(new Dimension(500, 500));
		this.add(pane1, BorderLayout.WEST);
		View.Navigation nav = new View.Navigation(auto, path);
		System.out.println(auto);
		//if (!auto)
			this.add(nav, BorderLayout.CENTER);
		this.add(pane2, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(1500, 650));
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
		} else {
			prev = direction;
			direction = "up";
		}
		updateNav(direction, grid[playerIndex]);

	}

	public static void updateTurnDown() {
		// Maj de la direction
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
}