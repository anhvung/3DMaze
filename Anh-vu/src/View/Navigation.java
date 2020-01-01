package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Controler.Maze;
import Controler.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import game.Player;
import affichage3d.Display3d;

public class Navigation extends JPanel {
	//Ici on s'occupe des boutons de navigation 

	private static final long serialVersionUID = -1537227921727720927L;
	private static boolean firstTime = true;
	private static JButton upButton = new JButton("START");
	private static JButton down = new JButton("START");
	private static JButton left = new JButton("START");
	private static JButton right = new JButton("START");
	private static JButton go = new JButton("START");
	private static JButton[] ref = { down, upButton, go, left, right };

	public Navigation() { 

		this.setSize(300, 300);
		this.setLayout(new BorderLayout());
		ref[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn Up");
				if (firstTime) {
					changeTxt();
				} else if (ref[1].getBackground() == Color.GREEN) {
					Player.updateTurnUp();
					Display3d.maze3d.turnUp();

				}

			}
		});

		ref[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn down");
				if (firstTime) {
					changeTxt();

				} else if (ref[0].getBackground() == Color.GREEN) {
					Player.updateTurnDown();
					Display3d.maze3d.turnDown();

				}

			}
		});

		ref[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn left");
				if (firstTime) {
					changeTxt();

				} else if (ref[3].getBackground() == Color.GREEN) {
					Player.updateTurnLeft();
					Display3d.maze3d.turnLeft();

				}

			}
		});

		ref[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn right");
				if (firstTime) {
					changeTxt();

				} else if (ref[4].getBackground() == Color.GREEN) {
					Player.updateTurnRight();
					Display3d.maze3d.turnRight();

				}
			}
		});

		ref[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("go forward");

				if (firstTime) {
					changeTxt();

				} else if (ref[2].getBackground() == Color.GREEN) {
					Player.updateGo();
					Display3d.maze3d.goForward();

				}
			}
		});
		this.add(upButton, BorderLayout.NORTH);
		this.add(right, BorderLayout.EAST);
		this.add(down, BorderLayout.SOUTH);
		this.add(left, BorderLayout.WEST);
		this.add(go, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private void changeTxt() { // Texte initial
		firstTime = false;
		right.setText("turn right");
		left.setText("Turn Left");
		upButton.setText("Turn Up");
		down.setText("Turn Down");
		go.setText("go");
		Box start = Maze.getMaze().getGrid()[Maze.getMaze().startIndex];
		affichage3d.Display3d.animate(start.getIndex());
	}

	public static void update(String dir, Box box) { //Mise � jour des directions autoris�es
		ArrayList<Integer> blocked = new ArrayList<Integer>();
		switch (dir) {

		case ("north"):
			if (box.getWalls()[2]) {
				blocked.add(2);
			}
			break;
		case ("east"):
			if (box.getWalls()[5]) {
				blocked.add(2);
			}
			break;
		case ("south"):
			if (box.getWalls()[3]) {
				blocked.add(2);
			}
			break;
		case ("west"):
			if (box.getWalls()[4]) {
				blocked.add(2);
			}
			break;
		case ("up"):
			if (box.getWalls()[0]) {
				blocked.add(2);

			}
			blocked.add(3);
			blocked.add(4);
			blocked.add(1);
			break;
		case ("down"):
			if (box.getWalls()[1]) {
				blocked.add(2);

			}
			blocked.add(3);
			blocked.add(4);
			blocked.add(0);
			break;
		}
		noGo(blocked);
	}

	private static void noGo(ArrayList<Integer> list) { // Mise � jour de la couleur des buttons en fonction des directions autoris�es
		for (JButton b : ref) {
			b.setBackground(Color.GREEN);
		}
		for (Integer i : list) {
			ref[i].setBackground(Color.RED);
		}
	}
}
