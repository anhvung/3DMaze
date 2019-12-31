package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controler.Maze;
import Controler.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import game.Player;
import affichage3d.Display3d;

public class Navigation extends JPanel {
	/**
	 * 
	 */

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
		upButton.setBackground(Color.RED);
		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn Up");
				if (firstTime) {
					changeTxt();
				} else {
					Player.updateTurnUp();
					Display3d.maze3d.turnUp();

				}

			}
		});

		down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn down");
				if (firstTime) {
					changeTxt();

				} else {
					Player.updateTurnDown();
					Display3d.maze3d.turnDown();

				}

			}
		});

		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn left");
				if (firstTime) {
					changeTxt();

				} else {
					Player.updateTurnLeft();
					Display3d.maze3d.turnLeft();

				}

			}
		});

		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn right");
				if (firstTime) {
					changeTxt();

				} else {
					Player.updateTurnRight();
					Display3d.maze3d.turnRight();

				}
			}
		});

		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("go forward");

				if (firstTime) {
					changeTxt();

				} else {
					if (Player.canGo()) {
						Player.updateGo();
						Display3d.maze3d.goForward();
					}

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

	private void changeTxt() {
		firstTime = false;
		right.setText("turn right");
		left.setText("Turn Left");
		upButton.setText("Turn Up");
		down.setText("Turn Down");
		go.setText("Forward");
		Box start = Maze.getMaze().getGrid()[Maze.getMaze().startIndex];
		affichage3d.Display3d.animate(start.getIndex());
	}

	public static void update(boolean[] dir, int ind) {

	}

}
