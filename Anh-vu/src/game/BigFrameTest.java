package game;

import java.awt.event.*;
import java.io.PrintStream;
import java.awt.*;
import javax.swing.*;

import Controler.Maze;
import Model.MazeMaker;
import View.MiniMap;
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

class BigFrameTest  {

	/**
	 * 
	 */

	public static final String source = "src/data/3Dmaze.txt";

	public static void main(String[] args) {
		PrintStream out = System.out;
		int length = 3;

		// Maze maze = new Maze(source);
		// intégré dans Maze déjà --> Maze.getMaze() pour avoir le laby
		MazeMaker M = new MazeMaker(length, source);
		M.makeMaze();

		// maze.showBoxes();
		MiniMap miniMap = new MiniMap(Maze.getMaze());
		//miniMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GraphInterface g = new Graph(Maze.getMaze());
		ASetInterface a = new ASet();
		PreviousInterface previous = new Previous(g.getLength());
		VertexInterface r = new Vertex(Maze.getMaze().startIndex);
		VertexInterface s = new Vertex(Maze.getMaze().arrivalIndex);
		PiInterface pi = new Pi(g.getLength());
		Dijkstra D = new Dijkstra();
		D.dijkstra(g, r, a, pi, previous);
		Player player =new Player(miniMap);
	
		
	}
}