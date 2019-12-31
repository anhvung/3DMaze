package game;

import affichage3d.Display3d;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import View.MiniMap;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class Player extends JFrame {
	private static int[] position = new int[3];
	private static boolean[] direction = new boolean[3];
	private static final long serialVersionUID = 1L;
	

	Player(MiniMap miniMap) {
		initializeFrame(miniMap);
	}

	private void initializeFrame(MiniMap miniMap) {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		affichage3d.Display3d.display(true);
		Canvas3D pane1 =affichage3d.Display3d.maze3d;
		JPanel  pane2 = miniMap;
		pane2.setPreferredSize(new Dimension(500,500));
		
		this.add(pane1,BorderLayout.WEST);
		this.add(new View.Navigation(),BorderLayout.CENTER);
		this.add(pane2,BorderLayout.EAST);
		
		this.setSize(1200, 1200);
        this.pack();
        this.setVisible(true);
		this.setFocusable(true);
        this.requestFocusInWindow();
        affichage3d.Display3d.animate(0,0,0);
        
	}


	
}