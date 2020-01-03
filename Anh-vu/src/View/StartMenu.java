package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Controler.Maze;
import Model.MazeMaker;
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

//FENETRE DE SELECTION DE LA DIFFICULTE
public class StartMenu extends JFrame {
	private static final long serialVersionUID = 3525L;
	private static JCheckBox auto = new JCheckBox("RESOLUTION AUTOMATIQUE (caméra auto)");
	public StartMenu() {
		Color color=new Color(200,0,128);
		auto = new JCheckBox("RESOLUTION AUTOMATIQUE (caméra auto)");
	 
		auto.setForeground(color);
		auto.setSelected(false);
		JLabel text = new JLabel(
				"<html><br><font color=\"red\"> Bienvenue !</font><br><br> Veuiller choisir la difficulté  <br>  <br> <br> Le labyrinthe sera généré aléatoirement <br><br></html>");
		
		this.setTitle("START MENU");
		this.setPreferredSize(new Dimension(500, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		JPanel check = new JPanel();
		check.setLayout(new BorderLayout());
		check.add(auto);
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
		container.add(auto, BorderLayout.NORTH);
		this.add(container,BorderLayout.CENTER);
		this.add(text,BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();

	}

	private void level(int length) {
		
		// On lance le niveau choisi
		String source = game.MainTest.source;
		setVisible(false);
		dispose();
		// Maze maze = new Maze(source);
		// intégré dans Maze déjà --> Maze.getMaze() pour avoir le laby
		MazeMaker M = new MazeMaker(length, source);
		M.makeMaze();
		Controler.Maze.updateMaze();

		GraphInterface g = new Graph(Maze.getMaze());
		ASetInterface a = new ASet();
		PreviousInterface previous = new Previous(g.getLength());
		VertexInterface r = new Vertex(Maze.getMaze().startIndex);

		VertexInterface r2 = new Vertex(Maze.getMaze().arrivalIndex);
		PiInterface pi = new Pi(g.getLength());
		Dijkstra D = new Dijkstra();
		D.dijkstra(g, r2, a, pi, previous);
		MiniMap miniMap = new MiniMap(Maze.getMaze(), previous);

		ArrayList<VertexInterface> path = previous.getShortestPathFrom(r);
		for (VertexInterface v : path) {
			System.out.println(v.getIndex());
		}
		new Player(miniMap, length,auto.isSelected(),path);

	}
}
