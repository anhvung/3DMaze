package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controler.Maze;
import Model.MazeMaker;
import affichage3d.Display3d;
import dijkstra.ASet;
import dijkstra.ASetInterface;
import dijkstra.Dijkstra;
import dijkstra.Graph;
import dijkstra.GraphInterface;
import dijkstra.Pi;
import dijkstra.PiInterface;
import dijkstra.Previous;
import dijkstra.PreviousInterface;
import dijkstra.Vertex;
import dijkstra.VertexInterface;
import game.Player;

public class StartMenu extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3525L;

	public StartMenu() {
		JPanel container = new JPanel();
		this.setTitle("START MENU");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		container.setLayout(new BorderLayout());

		JButton left = new JButton("Facile");
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level(3);
			}
		});
		JButton right = new JButton("Difficile");
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level(5);
			}
		});
		JButton go = new JButton("Normal");
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level(4);
			}
		});
		JButton impossible = new JButton("Impossible");
		impossible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level(10);
			}
		});
		container.add(right, BorderLayout.EAST);
		container.add(left, BorderLayout.WEST);
		container.add(go, BorderLayout.CENTER);
		container.add(impossible, BorderLayout.SOUTH);
		this.setContentPane(container);
		this.setVisible(true);
	}

	private void level(int length) {
		String source = game.MainTest.source;
		setVisible(false); // you can't see me!
		dispose(); // Destroy the JFrame object
		// Maze maze = new Maze(source);
		// intégré dans Maze déjà --> Maze.getMaze() pour avoir le laby
		MazeMaker M = new MazeMaker(length, source);
		M.makeMaze();
		Controler.Maze.updateMaze();
		// maze.showBoxes();
		MiniMap miniMap = new MiniMap(Maze.getMaze());
		// miniMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GraphInterface g = new Graph(Maze.getMaze());
		ASetInterface a = new ASet();
		PreviousInterface previous = new Previous(g.getLength());
		VertexInterface r = new Vertex(Maze.getMaze().startIndex);
		VertexInterface s = new Vertex(Maze.getMaze().arrivalIndex);
		PiInterface pi = new Pi(g.getLength());
		Dijkstra D = new Dijkstra();
		D.dijkstra(g, r, a, pi, previous);
		new Player(miniMap);
	}
}
