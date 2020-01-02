package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Controler.Maze;
import dijkstra.PreviousInterface;
import dijkstra.Vertex;
import dijkstra.VertexInterface;

public class MiniMap extends JFrame {
	private static final long serialVersionUID = 1L;
	private int currentIndex;
	private JButton downButton;
	private JButton upButton;
	private JButton rightButton;
	private JPanel[] panels;
	public int length;
	public boolean showSol;
	private PreviousInterface previous;
	private Maze maze;
	
	
	public MiniMap(Maze maze, PreviousInterface p) {
		this.length = maze.getLength();
        this.setTitle("Maze look over");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        showSol = false;
        previous = p;
        this.maze = maze;
		
		panels = new JPanel[length];
		for (int i = 0; i < length; i++) {
			panels[i] = new JPanel();
			panels[i].setLayout(new GridLayout(length,length));
			int area = length * length;
			for (int j = i * area; j < i * area + area; j++) {
				MiniMapBox mn = new MiniMapBox(maze.getBox(j), this);
				if (j == maze.arrivalIndex) mn.setSpecial("ARRIVAL");
				else if (j == maze.startIndex) mn.setSpecial("START");
				panels[i].add(mn);
			}
		}
		
		this.setContentPane(new JPanel());
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
		
		rightButton = new JButton("Show Solution");
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showSol) {
					showSol = false;
					rightButton.setText("Show Solution");
				}
				else {
					showSol = true;
					rightButton.setText("Hide Solution");
				}
			}
			
		});
		
		this.add(upButton, BorderLayout.NORTH);
		this.add(downButton, BorderLayout.SOUTH);
		this.add(rightButton, BorderLayout.EAST);
	}

	private void changePanel(int dh) {
		Dimension size = getCurrentPanel().getSize();
		remove(getCurrentPanel());
		setCurrentIndex((currentIndex + dh + length) % length);
		JPanel panel = getCurrentPanel();
		panel.setPreferredSize(size);
		add(panel, BorderLayout.CENTER);
		pack();
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

	public String getNextDirection(MiniMapBox mn) {
		int [] index = mn.getIndex();
		int i = maze.index(index[0], index[1], index[2]);
		Vertex v = new Vertex(i);
		VertexInterface vi = previous.getValue(v);
		if (! (vi == null)) {
			int nextIndex = vi.getIndex();
			if (nextIndex == i + 1) return "RIGHT";
			else if (nextIndex == i - 1) return "LEFT";
			else if (nextIndex == i - length) return "UP";
			else if (nextIndex == i + length) return "DOWN";
			else if (nextIndex == i + length * length) return "BELLOW";
			else if (nextIndex == i - length * length) return "ABOVE";
			else return "";
		}
		return "";
	}
}
