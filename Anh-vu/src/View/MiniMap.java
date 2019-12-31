package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Controler.Maze;

public class MiniMap extends JPanel {
	private static final long serialVersionUID = 1L;
	private int currentIndex;
	private JButton downButton;
	private JButton upButton;
	private JPanel[] panels;
	private int length;
	
	
	public MiniMap(Maze maze) {
		this.length = maze.getLength();
        //this.setTitle("Maze look over");
        this.setSize(500, 500);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
		
		panels = new JPanel[length];
		for (int i = 0; i < length; i++) {
			panels[i] = new JPanel();
			panels[i].setLayout(new GridLayout(length,length));
			int area = length * length;
			for (int j = i * area; j < i * area + area; j++) {
				MiniMapBox mn = new MiniMapBox(maze.getBox(j));
				if (j == maze.arrivalIndex) mn.setSpecial("ARRIVAL");
				else if (j == maze.startIndex) mn.setSpecial("START");
				panels[i].add(mn);
			}
		}
		
		//this.add(new JPanel());
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
		Dimension size = getCurrentPanel().getSize();
		remove(getCurrentPanel());
		setCurrentIndex((currentIndex + dh + length) % length);
		JPanel panel = getCurrentPanel();
		panel.setPreferredSize(size);
		add(panel, BorderLayout.CENTER);
		updateUI();
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
