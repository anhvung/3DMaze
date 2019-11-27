package test;

import dijkstra.Graph;
import maze.MazeMaker;
import maze.Maze;
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
import maze.Maze;

public class MainTest {

	public static void main(String[] args) {
		PrintStream out = System.out;
		//Maze maze = new Maze("data/labyrinthe.txt");
		MazeMaker M = new MazeMaker(4,"src/3Dmaze.txt");
		M.makeMaze();
		Maze maze = new Maze("data/3Dmaze.txt");
		GraphInterface g = new Graph(maze);
		ASetInterface a = new ASet();
		PreviousInterface previous = new Previous(g.getLength());
		VertexInterface r = new Vertex(maze.startIndex);
		VertexInterface s = new Vertex(maze.arrivalIndex);
		PiInterface pi = new Pi(g.getLength());
		Dijkstra D = new Dijkstra();
		D.dijkstra(g, r, a, pi, previous);
		
		for (VertexInterface v : previous.getShortestPathTo(s)) {
			out.println(v.getIndex());
		}
		/*for (int i = 0; i < g.getLength(); i++) {
			for (int j = 0; j < g.getLength(); j++) {
				out.print(g.getMatrix()[i][j]);
				out.print(" ");
			}
			out.println();
		}*/
		
	}

}
