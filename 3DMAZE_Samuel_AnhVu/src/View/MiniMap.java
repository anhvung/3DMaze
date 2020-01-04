package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import Controler.Maze;
import dijkstra.PreviousInterface;
import dijkstra.Vertex;
import dijkstra.VertexInterface;
import game.Player;

//JPANEL DONNANT LA MINI MAP ETAGE PAR ETAGE
public class MiniMap extends JPanel {
	private static final long serialVersionUID = 1L;
	private int currentIndex;
	private JButton downButton;
	private JButton upButton;
	private JPanel[] panels;
	public int length;
	public static JButton rightButton;
	public static boolean showSol;
	private PreviousInterface previous;
	private Maze maze;
	private static ArrayList<MiniMapBox> list= new ArrayList<MiniMapBox>();

	public MiniMap(Maze maze, PreviousInterface p) {
		this.length = maze.getLength();
		// this.setTitle("Maze look over");
		this.setSize(500, 500);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		showSol = false;
		previous = p;
		this.maze = maze;

		panels = new JPanel[length];
		for (int i = 0; i < length; i++) {
			panels[i] = new JPanel();
			panels[i].setLayout(new GridLayout(length, length));
			int area = length * length;
			for (int j = i * area; j < i * area + area; j++) {
				MiniMapBox mn = new MiniMapBox(maze.getBox(j), this);
				if (j == maze.arrivalIndex)
					mn.setSpecial("ARRIVAL");
				else if (j == maze.startIndex)
					mn.setSpecial("START");
				panels[i].add(mn);
				list.add(mn);
			}
		}

		// this.add(new JPanel());
		rightButton = new JButton("Show Solution");
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showSol) {
					showSol = false;
					rightButton.setText("Show Solution");
				} else {
					showSol = true;
					rightButton.setText("Hide Solution");

				}
				Player.updateText();
			}

		});

		this.setLayout(new BorderLayout());
		this.add(getCurrentPanel(), BorderLayout.CENTER);
		this.add(rightButton, BorderLayout.EAST);
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
		// changer d'etage
		Dimension size = getCurrentPanel().getSize();
		remove(getCurrentPanel());
		setCurrentIndex((currentIndex + dh + length) % length);
		JPanel panel = getCurrentPanel();
		panel.setPreferredSize(size);
		add(panel, BorderLayout.CENTER);
		updateUI();
		repaint();
	}

	public String getNextDirection(MiniMapBox mn) {
		int[] index = mn.getIndex();
		int i = maze.index(index[0], index[1], index[2]);
		Vertex v = new Vertex(i);
		VertexInterface vi = previous.getValue(v);
		if (!(vi == null)) {
			int nextIndex = vi.getIndex();
			if (nextIndex == i + 1)
				return "RIGHT";
			else if (nextIndex == i - 1)
				return "LEFT";
			else if (nextIndex == i - length)
				return "UP";
			else if (nextIndex == i + length)
				return "DOWN";
			else if (nextIndex == i + length * length)
				return "BELLOW";
			else if (nextIndex == i - length * length)
				return "ABOVE";
			else
				return "";
		}
		return "";
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
	public static void refreshAll() {
		for (MiniMapBox square : list) {
			square.repaint();
		}
	}

}
