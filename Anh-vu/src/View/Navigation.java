package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import affichage3d.Display3d;

public class Navigation extends JPanel {

	public Navigation() {
		
		this.setSize(300, 300);
		this.setLayout(new BorderLayout());
		JButton upButton = new JButton("Up");
		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn Up");
				Display3d.maze3d.turnUp();
			}
		});
		JButton down = new JButton("Down");
		down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn down");
				Display3d.maze3d.turnDown();
			}
		});
		JButton left = new JButton("Turn Left");
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn left");
				Display3d.maze3d.turnLeft();
			}
		});
		JButton right = new JButton("Turn Right");
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("turn right");
				Display3d.maze3d.turnRight();
			}
		});
		JButton go = new JButton("Forward");
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("go forward");
				Display3d.maze3d.goForward();
			}
		});
		this.add(upButton, BorderLayout.NORTH);
		this.add(right, BorderLayout.EAST);
		this.add(down, BorderLayout.SOUTH);
		this.add(left, BorderLayout.WEST);
		this.add(go, BorderLayout.CENTER);
		this.setVisible(true);
	}
}
