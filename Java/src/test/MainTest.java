package test;

import dijkstra.Graph;
import maze.MazeMaker;
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
		//PrintStream out = System.out;
		//Maze maze = new Maze("data/labyrinthe.txt");
		//out.println(maze.getLength());
		//GraphInterface g = new Graph(maze);
		//ASetInterface a = new ASet();
		//PreviousInterface previous = new Previous(g.getLength());
		//VertexInterface r = new Vertex(1);
		//VertexInterface s = new Vertex(68);
		//PiInterface pi = new Pi(g.getLength());
		//Dijkstra D = new Dijkstra();
		//D.dijkstra(g, r, a, pi, previous);
		
		//for (VertexInterface v : previous.getShortestPathTo(s)) {
			//out.println(v.getIndex());
		//}
		MazeMaker M = new MazeMaker(4);
		M.makeMaze();
		
	}

}
