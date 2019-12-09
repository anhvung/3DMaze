package View;

import java.awt.GridLayout;

import javax.swing.JPanel;

import Controler.Box;

public class MiniMap extends JPanel {
	public int length;
	//TODO : Add a button to show or hide the solution
	//TODO : Add images to the background of boxes to show the doors and stairs
	public MiniMap(Box[] grid, int length) {
		this.length = length;
		setLayout(new GridLayout(length,length,0,0));
	}
}
