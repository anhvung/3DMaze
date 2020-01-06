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
	private static JCheckBox auto = new JCheckBox("RESOLUTION AUTOMATIQUE (cam�ra auto)");

	public StartMenu() {
		Color color = new Color(200, 0, 128);
		auto = new JCheckBox("RESOLUTION AUTOMATIQUE (cam�ra auto)");

		auto.setForeground(color);
		auto.setSelected(false);
		JLabel text = new JLabel(
				"<html><br><font color=\"red\"> &nbsp;&nbsp;&nbsp;  Bienvenue !</font><br><br> &nbsp;&nbsp;&nbsp; Veuiller choisir la difficulte  <br>  <br> <br>&nbsp;&nbsp;&nbsp;  Le labyrinthe sera genere aleatoirement <br>&nbsp;&nbsp;&nbsp;Les cases de d�part et d'arriv�e �galement (cf Model.MazeMaker)<br></html>");

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
				level(2);
			}
		});
		JButton load = new JButton("charger le dernier laby sauvegard�");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level(-1);
			}
		});
		JButton right = new JButton("Difficile");
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level(4);
			}
		});
		JButton go = new JButton("Normal");
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level(3);
			}
		});
		JButton impossible = new JButton("Impossible");
		paintButton(impossible);
		paintButton(go);
		paintButton(right);
		paintButton(left);
		paintButton(load);
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
		this.add(container, BorderLayout.CENTER);
		this.add(text, BorderLayout.NORTH);
		this.add(load, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();

	}

	private void level(int length) {

		// On lance le niveau choisi
		String source = game.MainExecuteHere.source;
		setVisible(false);
		pack();
		// Maze maze = new Maze(source);
		// int�gr� dans Maze d�j� --> Maze.getMaze() pour avoir le laby
		if (length!=-1) {
		MazeMaker M = new MazeMaker(length, source);
		M.makeMaze();
		}
		else length =Maze.getMaze().getLength();
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
		new Player(miniMap, length, auto.isSelected(), path, previous);

	}

	private void paintButton(JButton b) {
		b.setFocusPainted(true);
		b.setContentAreaFilled(false);
		b.setForeground(new Color(0.8f, 0.2f, 0.5f));
		// b.setBorder(BorderFactory.createLineBorder(new Color(0.8f, 0.2f, 0.5f),1));
	}

}
