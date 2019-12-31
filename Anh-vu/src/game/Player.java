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
	private static boolean[] direction = new boolean[3];
	private static final long serialVersionUID = 1L;
	private static Box[] grid;
	private static int playerIndex;

	public Player(MiniMap miniMap) {
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
		updateNav(direction, playerIndex);

	}

	private static void updateNav(boolean[] dir, int ind) {
		Navigation.update(dir, ind);

	}

	private void updatePosition(int index) {
		position = grid[index].getIndex();

	}

	public static void updateTurnUp() {
		updateNav(direction, playerIndex);

	}

	public static void updateTurnDown() {
		updateNav(direction, playerIndex);

	}

	public static void updateTurnLeft() {
		updateNav(direction, playerIndex);

	}

	public static void updateTurnRight() {
		updateNav(direction, playerIndex);

	}

	public static void updateGo() {
		updateNav(direction, playerIndex);

	}

	public static boolean canGo() {
		return true;
	}

}