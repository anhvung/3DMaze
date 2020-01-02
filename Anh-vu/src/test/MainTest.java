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
import View.MiniMap;

public class MainTest {
	public static void main(String[] args) {
		PrintStream out = System.out;
		int length = 3;
		String source = "src/data/3Dmaze.txt";
		MazeMaker M = new MazeMaker(length,source);
		M.makeMaze();
		Maze maze = new Maze(source);
		//maze.showBoxes();
		
		
		GraphInterface g = new Graph(maze);
		ASetInterface a = new ASet();
		PreviousInterface previous = new Previous(g.getLength());
		VertexInterface r = new Vertex(maze.startIndex);
		VertexInterface s = new Vertex(maze.arrivalIndex);
		PiInterface pi = new Pi(g.getLength());
		Dijkstra D = new Dijkstra();
		D.dijkstra(g, r, a, pi, previous);
		

		MiniMap miniMap = new MiniMap(maze, previous);
		miniMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*out.println();
		for (VertexInterface v : previous.getShortestPathFrom(s)) {
			out.println(v.getIndex());
		}
		out.println();
		
		for (int i = 0; i < previous.getList().length; i++){
			if(previous.getList()[i]!=null) {
				out.print(i);
				out.print(" ");
				out.println(previous.getList()[i].getIndex());
			}
				
		}
		/*
		for (int i = 0; i < g.getLength(); i++) {
			for (int j = 0; j < g.getLength(); j++) {
				out.print(g.getMatrix()[i][j]);
				out.print(" ");
			}
			out.println();
		}*/
		
	}

}
