package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MiniMapBox extends JButton {
	protected boolean[] walls;
	private final float prop = 0.01f;
	private String indexString;
	
	
	public MiniMapBox() {
		walls = new boolean[] {true, true, true, true, true, true};
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		String s = "";
		for (boolean wall : walls) {
			if (wall) s = s + '1';
			else s = s + '0';
		}
		s = s + toString();
		
		/*int x0 = getX();
		int y0 = getY();*/
		int x0 = 0;
		int y0 =0;
		int h  = getSize().height;
		int w  = getSize().width;
		
		g.drawString(s, 10, h / 2);
		

		
		if (walls[0]) {
			
		}
		if (walls[1]) {
			
		}
		if (walls[2]) {
			int x = x0;
			int y = y0;
			int width = w;
			int height = (int) Math.round(h * prop);
			g.fillRect(x, y, width, height);
		}
		if (walls[3]) {
			int x = x0;
			int y = y0 + h - (int) Math.round(h * prop);
			int width = w;
			int height = (int) Math.round(h * prop);
			g.fillRect(x, y, width, height);
		}
		if (walls[4]) {
			int x = x0;
			int y = y0;
			int width = (int) Math.round(w * prop);
			int height = h;
			g.fillRect(x, y, width, height);
		}
		if (walls[5]) {
			int x = x0 + w - (int) Math.round(w * prop);
			int y = y0;
			int width = (int) Math.round(w * prop);
			int height = h;
			g.fillRect(x, y, width, height);
		}
	}
	
	public void setIndex(int i, int j, int k, int length) {
		indexString = " depth : " +
	Integer.toString(i) +
	", row : " +
	Integer.toString(j) + 
	", column : " +
	Integer.toString(k) +
	", index : " +
	Integer.toString(i * length * length + j * length + k);
	}
	
	public void drawWalls(JPanel panel) {
		ColorPanel underPanel;
		if (walls[0]) {
		}
		if (walls[1]) {
			
		}
		if (walls[2]) {
			underPanel = new ColorPanel(Color.black);
		}
		else {
			underPanel = new ColorPanel(Color.white);
		}
		panel.add(underPanel, BorderLayout.NORTH);
		

		if (walls[3]) {
			underPanel = new ColorPanel(Color.black);
		}
		else {
			underPanel = new ColorPanel(Color.white);
		}
		panel.add(underPanel, BorderLayout.SOUTH);

		if (walls[4]) {
			underPanel = new ColorPanel(Color.black);
		}
		else {
			underPanel = new ColorPanel(Color.white);
		}
		panel.add(underPanel, BorderLayout.EAST);

		if (walls[5]) {
			underPanel = new ColorPanel(Color.black);
		}
		else {
			underPanel = new ColorPanel(Color.white);
		}
		panel.add(underPanel, BorderLayout.WEST);
	}
	
	public String toString() {
		return indexString;
	}
	
	
}
