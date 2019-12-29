package test;

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
class BigFrame extends JFrame { 
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// frame 
    static JFrame f; 
    public static final String source = "src/data/3Dmaze.txt";
 
    public static void main(String[] args) 
    { 
    	PrintStream out = System.out;
		int length = 3;
		
		//Maze maze = new Maze(source);  
		//intégré dans Maze déjà --> Maze.getMaze() pour avoir le laby
		MazeMaker M = new MazeMaker(length,source);
		M.makeMaze();
		
		//maze.showBoxes();
		MiniMap miniMap = new MiniMap(Maze.getMaze());
		miniMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		GraphInterface g = new Graph(Maze.getMaze());
		ASetInterface a = new ASet();
		PreviousInterface previous = new Previous(g.getLength());
		VertexInterface r = new Vertex(Maze.getMaze().startIndex);
		VertexInterface s = new Vertex(Maze.getMaze().arrivalIndex);
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
		

	
        // create a new frame 
        f = new JFrame("frame"); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set layout of frame 
        f.setLayout(new FlowLayout()); 
  
        // create a internal frame 
        JInternalFrame in = new JInternalFrame("MINI MAP", true, true, true, true); 
  
        // create a internal frame 
        JInternalFrame in1 = new JInternalFrame("LABY", true, true, true, true); 
  
        in.setPreferredSize(new Dimension(250, 250));
        in.setMaximumSize(new Dimension(250, 250));
        in.setMinimumSize(new Dimension(250, 250));
        in.setMaximizable(true);
        in1.setPreferredSize(new Dimension(800, 800));
        in1.setMaximumSize(new Dimension(800, 800));
        in1.setMinimumSize(new Dimension(800, 800));
        in1.setMaximizable(true);
       
        // set visibility internal frame 
        in.setVisible(true); 
        in1.setVisible(true); 
  
        // add panel to internal frame 
        in.add(miniMap.getContentPane()); 
        in1.add(affichage3d.Display3d.get3DMaze().getContentPane()); 
        
        // add internal frame to frame 
        f.add(in); 
        f.add(in1); 
  
        // set the size of frame 
        f.setSize(1200, 800); 
  
        f.show(); 
    } 
} 