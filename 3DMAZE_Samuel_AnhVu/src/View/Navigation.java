package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Controler.Maze;
import Controler.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import game.Player;
import affichage3d.Display3d;
import dijkstra.VertexInterface;

//Ici on s'occupe des boutons de navigation 
public class Navigation extends JPanel {

	private static final long serialVersionUID = -1537227921727720927L;
	private static JLabel label = new JLabel("DIRECTION");
	private static boolean firstTime = true;
	private static JButton upButton = new JButton("START");
	private static JButton down = new JButton("START");
	private static JButton left = new JButton("START");
	private static JButton right = new JButton("START");
	private static JButton go = new JButton("START");
	private static JButton[] ref = { down, upButton, go, left, right };
	private static boolean auto;
	private static JButton start = new JButton("START");
	private static ArrayList<VertexInterface> path;
	private static int autoIndex = 0;

	public Navigation(boolean auto, ArrayList<VertexInterface> path) {
		// Ajout des boutons et de leur comportements
		this.setSize(300, 300);
		Navigation.auto = auto;
		Navigation.path = path;
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

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (firstTime)
					changeTxt();

			}
		});

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		panel2.setSize(300, 300);
		panel2.setLayout(new BorderLayout());
		panel1.add(label);
		if (!auto) {
			panel2.add(upButton, BorderLayout.NORTH);
			panel2.add(right, BorderLayout.EAST);
			panel2.add(down, BorderLayout.SOUTH);
			panel2.add(left, BorderLayout.WEST);
			panel2.add(go, BorderLayout.CENTER);
			this.add(panel1, BorderLayout.NORTH);
			this.add(panel2, BorderLayout.SOUTH);
		} else {
			this.add(panel1, BorderLayout.NORTH);
			this.add(start, BorderLayout.CENTER);
		}

		this.setVisible(true);
	}

	private void changeTxt() {
		// Texte initial
		if (!auto) {
			firstTime = false;
			right.setText("turn right");
			left.setText("Turn Left");
			upButton.setText("Turn Up");
			down.setText("Turn Down");
			go.setText("go");
		}
		Box start = Maze.getMaze().getGrid()[Maze.getMaze().startIndex];
		affichage3d.Display3d.animate(start.getIndex());
		if (auto) {
			this.setVisible(false);
			goNext();

		}

	}

	public static void goNext() {
		autoIndex++;

		goTo(autoIndex);

	}

	private static void goTo(int i) {
		if (i >= path.size()) {
			System.out.print("fini");

		} else {
			final int[] position = Maze.getMaze().getGrid()[path.get(i).getIndex()].getIndex();
			Player.goTo(position);
		}

	}

	public static void update(String dir, Box box) {
		// Mise � jour des directions autoris�es
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
		updateDirectionText(dir);

	}

	private static void updateDirectionText(String dir) {
		if (auto)
			label.setText("<html><font color=\"red\">Longueur du trajet : <br>" + path.size() + "</font></html>");
		else
			label.setText(
					"<html>A droite se situe la carte par �tage<br>En bas se trouvent les commandes <br> Bonne chance !  <br>  <br> <br> Direction : <font color=\"blue\">"
							+ dir + "</font><br><br>Case actuelle sur la mini map :  <font color=\"red\">"
							+ Player.getIndex()
							+ "</font><br><br>SOLUTION --> CLIQUER SUR LE BOUTON A DROITE <br>Le trait rouge montre la direction � prendre <br>La croix rouge montre l'�tage � changer</html>");

	}

	private static void noGo(ArrayList<Integer> list) {
		// Mise � jour de la couleur des buttons en fonction des
		// directions autoris�es
		for (JButton b : ref) {
			b.setBackground(Color.GREEN);
		}
		for (Integer i : list) {
			ref[i].setBackground(Color.RED);
		}
	}
}