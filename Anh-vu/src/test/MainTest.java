package test;

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

public class MainTest {
	public static void main(String[] args) {
		PrintStream out = System.out;
		int length = 3;
		String source = "src/data/3Dmaze.txt";
		MazeMaker M = new MazeMaker(length,source);
		M.makeMaze();
		Maze maze = new Maze(source);
		//maze.showBoxes();
		maze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphInterface g = new Graph(maze);
		ASetInterface a = new ASet();
		PreviousInterface previous = new Previous(g.getLength());
		VertexInterface r = new Vertex(maze.startIndex);
		VertexInterface s = new Vertex(maze.arrivalIndex);
		PiInterface pi = new Pi(g.getLength());
		Dijkstra D = new Dijkstra();
		D.dijkstra(g, r, a, pi, previous);
		
		/*for (VertexInterface v : previous.getShortestPathTo(s)) {
			out.println(v.getIndex());
		}
		
		for (int i = 0; i < g.getLength(); i++) {
			for (int j = 0; j < g.getLength(); j++) {
				out.print(g.getMatrix()[i][j]);
				out.print(" ");
			}
			out.println();
		}*/
		
	}

}
