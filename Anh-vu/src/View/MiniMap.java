package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controler.Box;

public class MiniMap extends JPanel {
	private static final long serialVersionUID = 1L;
	private int currentIndex;
	public int length;
	private JButton downButton;
	private JButton upButton;
	private JPanel[] panels;
	private JFrame parent;
	
	public MiniMap(int length, JFrame parent) {
		this.length = length;
		this.parent = parent;
		panels = new JPanel[length];
		for (int i = 0; i < length; i++) {
			panels[i] = new JPanel();
			panels[i].setLayout(new GridLayout(length,length));
		}

		this.setLayout(new BorderLayout());
		this.add(getCurrentPanel(), BorderLayout.CENTER);
		MiniMap mn = this;
		upButton = new JButton("Up");
		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mn.changePanel(-1);
			}
		});
		downButton = new JButton("Down");
		downButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mn.changePanel(1);
			}
		});
		this.add(upButton, BorderLayout.NORTH);
		this.add(downButton, BorderLayout.SOUTH);
	}
	
	private void changePanel(int dh) {
		remove(getCurrentPanel());
		setCurrentIndex((currentIndex + dh + length) % length);
		add(getCurrentPanel(), BorderLayout.CENTER);
		parent.pack();
		repaint();
	}
	
	public JPanel getCurrentPanel() {
		return panels[currentIndex];
	}
	
	public void addBoxToFloor(int i, MiniMapBox box) {
		panels[i].add(box);
	}

	public void setCurrentIndex(int i) {
		currentIndex = i;
	}
}
