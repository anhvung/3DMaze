package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import affichage3d.Display3d;

public class Navigation extends JPanel {
	private static boolean firstTime = true;
	private static JButton upButton = new JButton("START");
	private static JButton down = new JButton("START");
	private static JButton left = new JButton("START");
	private static JButton right = new JButton("START");
	private static JButton go = new JButton("START");

	public Navigation() {

		this.setSize(300, 300);
		this.setLayout(new BorderLayout());

		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn Up");
				if (firstTime) {
					changeTxt();
				} else {
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

	private void changeTxt() {
		firstTime = false;
		right.setText("turn right");
		left.setText("Turn Left");
		upButton.setText("Turn Up");
		down.setText("Turn Down");
		go.setText("Forward");
		affichage3d.Display3d.animate(0, 0, 0);
	}

}
