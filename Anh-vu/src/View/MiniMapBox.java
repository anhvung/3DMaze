package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MiniMapBox extends JButton {
	protected boolean[] walls;
	private final float prop = 0.05f;
	
	
	public MiniMapBox() {
		walls = new boolean[] {true, true, true, true,true, true};
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (walls[0]) {
			
		}
		if (walls[1]) {
			
		}
		if (walls[2]) {
			int x = getX();
			int y = getY();
			int width = getSize().width;
			int height = (int) Math.round(getSize().height * prop);
			g.fillRect(x, y, width, height);
		}
		if (walls[3]) {
			int x = getX();
			int y = getY() + getSize().height - 
					(int) Math.round(getSize().height * prop);
			int width = getSize().width;
			int height = (int) Math.round(getSize().height * prop);
			g.fillRect(x, y, width, height);
		}
		if (walls[4]) {
			int x = getX();
			int y = getY();
			int width = getSize().width;
			int height = getSize().height;
			g.fillRect(x, y, width, height);
		}
		if (walls[5]) {
			int x = getX();
			int y = getY();
			int width = getSize().width;
			int height = getSize().height;
			g.fillRect(x, y, width, height);
		}
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
	
	
	
}
