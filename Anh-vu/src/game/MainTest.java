package game;

import dijkstra.Graph;
import java.util.ArrayList;
import java.util.Date;
import dijkstra.GraphInterface;
import dijkstra.ASet;
import dijkstra.ASetInterface;
import dijkstra.Pi;
import dijkstra.PiInterface;
import dijkstra.Previous;
import dijkstra.PreviousInterface;
import dijkstra.Vertex;
import dijkstra.VertexInterface;
import dijkstra.Dijkstra;
import java.io.PrintStream;

import javax.swing.JFrame;

import Controler.Maze;
import Model.MazeMaker;
import View.MiniMap;
import affichage3d.Display3d;

public class MainTest {
	public static final String source = "src/data/3Dmaze.txt";

	public static void main(String[] args) {
		///////////////////// : Ancien test
		/*
		 * PrintStream out = System.out; final int length = 10;
		 * 
		 * // Maze maze = new Maze(source); // intégré dans Maze déjà --> Maze.getMaze()
		 * pour avoir le laby MazeMaker M = new MazeMaker(length, source); M.makeMaze();
		 * 
		 * // maze.showBoxes(); MiniMap miniMap = new MiniMap(Maze.getMaze());
		 * //miniMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * 
		 * GraphInterface g = new Graph(Maze.getMaze()); ASetInterface a = new ASet();
		 * PreviousInterface previous = new Previous(g.getLength()); VertexInterface r =
		 * new Vertex(Maze.getMaze().startIndex); VertexInterface s = new
		 * Vertex(Maze.getMaze().arrivalIndex); PiInterface pi = new Pi(g.getLength());
		 * //Dijkstra D = new Dijkstra(); //D.dijkstra(g, r, a, pi, previous);
		 * System.out.println("fini dijkstra"); /* for (VertexInterface v :
		 * previous.getShortestPathTo(s)) { out.println(v.getIndex()); }
		 * 
		 * for (int i = 0; i < g.getLength(); i++) { for (int j = 0; j < g.getLength();
		 * j++) { out.print(g.getMatrix()[i][j]); out.print(" "); } out.println(); }
		 * 
		 * 
		 * // partie de l'affichage3D
		 * 
		 * affichage3d.Display3d.display(0,0,0,true);
		 */
///////////////////////////////
		int length = 3;

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

		new Player(miniMap, length);

	}

}
